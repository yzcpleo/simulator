package com.shhxzq.fin.simulator.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.shhxzq.fin.simulator.biz.service.BankCommandService;
import com.shhxzq.fin.simulator.biz.service.BankTranService;
import com.shhxzq.fin.simulator.biz.service.DzFileService;
import com.shhxzq.fin.simulator.biz.util.PropertiesUtil;
import com.shhxzq.fin.simulator.common.util.DateUtils;
import com.shhxzq.fin.simulator.common.util.Shell;
import com.shhxzq.fin.simulator.common.util.StringUtil;
import com.shhxzq.fin.simulator.common.util.ZipUtil;
import com.shhxzq.fin.simulator.model.constants.AppConstants;
import com.shhxzq.fin.simulator.model.constants.BankEnum;
import com.shhxzq.fin.simulator.model.vo.BankCommand;
import com.shhxzq.fin.simulator.model.vo.BankTran;
import com.shhxzq.fin.simulator.model.vo.DzFile;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author kangyonggan
 * @since 2017/1/11
 */
@Service
@Log4j2
public class DzFileServiceImpl extends BaseService<DzFile> implements DzFileService {

    private static final String FILE_UPLOAD_PATH = PropertiesUtil.getProperties(AppConstants.FILE_PATH_ROOT) + AppConstants.FILE_UPLOAD_PATH;

    @Autowired
    private BankTranService bankTranService;

    @Autowired
    private BankCommandService bankCommandService;

    @Override
    public List<DzFile> searchDzFiles(int pageNum, String bnkCo) {
        Example example = new Example(DzFile.class);
        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotEmpty(bnkCo)) {
            criteria.andEqualTo("bnkCo", bnkCo);
        }

        example.setOrderByClause("id desc");

        PageHelper.startPage(pageNum, AppConstants.PAGE_SIZE);
        return super.selectByExample(example);
    }

    @Override
    public String saveDzFile(Long bankTranId, String workDay) {
        BankTran bankTran = bankTranService.findBankTranById(bankTranId);
        log.info("开始生成对账文件,bankTran:{}", bankTran);

        // 1. 查询交易
        List<BankCommand> bankCommands = bankCommandService.findBankCommands4Dz(bankTran.getTranCo(), bankTran.getBnkCo(), workDay);
        log.info("需要对账的交易数量:{}", bankCommands.size());

        // 2. 对账文件模板
        String template = bankTran.getDzTemp();
        log.info("对账文件模板:{}", template);

        template = dealTemplate(template);
        log.info("处理后的模板:{}", template);

        // 3. 生成对账文件名称
        String fileName = getFileName(bankTran, workDay);
        log.info("对账文件名称:{}", fileName);

        // 4. 生成文件
        genDzFile(bankTran, bankCommands, fileName, template);

        // 5. 处理特殊格式文件
        if (BankEnum.CP.getBnkCo().equals(bankTran.getBnkCo()) && "redeem".equals(bankTran.getTranCo())) {
            log.info("将银联的{}转为{}...", fileName, FILE_UPLOAD_PATH + fileName + ".zip");
            ZipUtil.zip(FILE_UPLOAD_PATH + fileName, FILE_UPLOAD_PATH + fileName + ".zip");
            fileName = FILE_UPLOAD_PATH + fileName + ".zip";
            log.info("转换成功!zip文件路径: {}", fileName);
        }

        // 6. 落库
        save(bankTran, fileName);
        log.info("对账文件落库成功");

        return fileName;
    }

    @Override
    public void pushDzFile(Long id) throws Exception {
        DzFile dzFile = super.selectByPrimaryKey(id);
        String bnkCo = dzFile.getBnkCo();

        if (bnkCo.equals(BankEnum.CMBCT0.getBnkCo())) {
            Shell.exec("sh /srv/admin/push-cmbct0-dz.sh");
        } else {
            log.warn("该交易不需要推送对账文件，bnkNm:{}, bnkNm:{}", dzFile.getBnkNm(), dzFile.getTranNm());
        }
    }

    @Override
    public void saveDzFiles(String workDay) {
        List<BankTran> bankTrans = bankTranService.findbankTran4Gen();
        log.info("共有{}种交易类型需要生成对账文件");

        for (BankTran bankTran : bankTrans) {
            saveDzFile(bankTran.getId(), workDay);
        }
    }

    @Override
    public void pushDzFiles() throws Exception {
        List<BankTran> bankTrans = bankTranService.findbankTran4Push();
        log.info("共有{}种交易类型需要推送对账文件", bankTrans.size());

        for (BankTran bankTran : bankTrans) {
            pushDzFile(bankTran.getId());
        }
    }

    private void save(BankTran bankTran, String fileName) {
        DzFile dzFile = new DzFile();
        dzFile.setBnkCo(bankTran.getBnkCo());
        dzFile.setBnkNm(bankTran.getBnkNm());
        dzFile.setTranCo(bankTran.getTranCo());
        dzFile.setTranNm(bankTran.getTranNm());
        dzFile.setFilePath(AppConstants.FILE_UPLOAD_PATH + fileName);
        dzFile.setIsPushDz(bankTran.getIsPushDz());

        super.insertSelective(dzFile);
    }

    private String dealTemplate(String template) {
        while (template.indexOf("||") != -1) {
            template = template.replaceAll("\\|\\|", "|NULL|");
        }
        if (template.startsWith("|")) {
            template = "NULL" + template;
        }
        if (template.endsWith("|")) {
            template += "NULL";
        }
        template = template.replaceAll("NULL", "");
        template = template.replaceAll("\\w+", "%s");
        return template;
    }

    private boolean genDzFile(BankTran bankTran, List<BankCommand> bankCommands, String fileName, String template) {
        File dir = new File(FILE_UPLOAD_PATH + fileName).getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(FILE_UPLOAD_PATH + fileName));

            // 处理文件头
            if (BankEnum.CP.getBnkCo().equals(bankTran.getBnkCo())) {
//                String header = buildDzHead(bank, transType, head, transactions);
//                out.write(head);
//                out.newLine();
            }

            for (BankCommand bankCommand : bankCommands) {
                out.write(replaceWithTrans(template, bankCommand));
                out.newLine();
            }

            // 处理文件尾
            if (!bankCommands.isEmpty()) {
                String bnkCo = bankCommands.get(0).getBnkCo();
                if (BankEnum.CMBCT0.getBnkCo().equals(bnkCo)) {
                    // 民生T+0对账文件的最后要多加一行
                    out.write("###########");
                    out.newLine();
                }
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 生成对账文件名称
     *
     * @param bankTran
     * @param workDay
     * @return
     */
    private String getFileName(BankTran bankTran, String workDay) {
        String fileName = "";
        if (BankEnum.CP.getBnkCo().equals(bankTran.getBnkCo())) {
            // 文件生成时间
            String currentTime = DateUtils.getCurrentDateTime();

            // 对账文件名[商户号_交易日期(yyyymmdd)_文件生成时间(yyyymmddhhmmss).txt]
            fileName = "808080211580085_" + workDay + "_" + currentTime + ".txt";
        } else if (BankEnum.ECT.getBnkCo().equals(bankTran.getBnkCo())) {
            // RZDZ_YYYYMMDD.txt
            fileName = "RZDZ_" + workDay + ".txt";
        } else if (BankEnum.ECT.getBnkCo().equals(bankTran.getBnkCo())) {
            // 广东南粤
            fileName = "00008" + "_" + workDay + ".txt";
        } else if (BankEnum.CMBCT0.getBnkCo().equals(bankTran.getBnkCo())) {
            // 民生T+0 [DF_HXZQ_KS_DZWJ_T0_20170106.txt]
            fileName = "DF_HXZQ_KS_DZWJ_T0_" + workDay + ".txt";
        }

        return fileName;
    }

    private static String replaceWithTrans(String template, BankCommand bankCommand) throws Exception {
        Class clazz = bankCommand.getClass();
        String bnkCo = bankCommand.getBnkCo();
        String tranCo = bankCommand.getTranCo();

        String fields[] = template.split("\\|");
        String values[] = new String[fields.length];

        int i = 0;
        for (String field : fields) {
            String val;
            try {
                Method method = clazz.getMethod("get" + StringUtil.capitalize(field));
                val = String.valueOf(method.invoke(bankCommand));
            } catch (Exception e) {
                val = field;
            }
            if (BankEnum.ECT.equals(bnkCo)) {
                if ("transType".equals(field)) {
                    if ("pay".equals(val)) {
                        val = "102";
                    } else if ("redeem".equals(val)) {
                        val = "101";
                    }
                } else if ("respCode".equals(field)) {
                    if ("50050000".equals(val)) {
                        val = "00";
                    } else if ("50050001".equals(val)) {
                        val = "01";
                    } else {
                        val = "02";
                    }
                }
            } else if (BankEnum.CP.getBnkCo().equals(bnkCo)) {
                if ("transStat".equals(field)) {
                    if (tranCo.equals("redeem")) {
                        String respCo = bankCommand.getRespCo();
                        if (StringUtil.in(respCo, "0100", "0101", "0102", "0103", "0104")) {
                            val = "6";
                        } else if ("0105".equals(respCo)) {
                            val = "5";
                        }
                    }
                }
            } else if (BankEnum.GDNY.getBnkCo().equals(bnkCo)) {
                if ("transType".equals(field)) {
                    if ("pay".equals(val)) {
                        val = "1";
                    } else if ("redeem".equals(val)) {
                        val = "4";
                    }
                } else if ("stat".equals(field)) {
                    if ("Y".equals(val)) {
                        val = "S";
                    }
                }

            } else if (BankEnum.CMBCT0.getBnkCo().equals(bnkCo)) {
                if ("transType".equals(field)) {
                    val = "1002";
                } else if ("stat".equals(field)) {
                    if ("Y".equals(val)) {
                        val = "S";
                    } else {
                        val = "E";
                    }
                }
            }


            values[i++] = val;
        }

        return String.format(template, values);
    }

    /**
     * 构建报文第一行
     *
     * @return
     */
    private String buildDzHead(BankTran bankTran, String header, List<BankCommand> bankCommands) throws Exception {
//        if (bank.getBankNo().equals(AppConstants.BANK_NO_CP) && transType.equals(TransTypeEnum.PAY.getType())) {
//            // 中国银联代扣: 总笔数|总金额|成功笔数|成功金额|非成功笔数|非成功金额
//            head = String.format(head,
//                    transactions.size(), getAmount(transactions, "beSer", false, ""),
//                    getCount(transactions, "stat", true, "Y"), getAmount(transactions, "stat", true, "Y"),
//                    getCount(transactions, "stat", false, "Y"), getAmount(transactions, "stat", false, "Y"));
//        } else if (bank.getBankNo().equals(AppConstants.BANK_NO_CP) && transType.equals(TransTypeEnum.REDEEM.getType())) {
//            // 中国银联代付: 总笔数|总金额|退单总笔数|退单总金额|重汇总笔数|重汇总金额|重汇退单总笔数|重汇退单总金额|签名值
//            head = String.format(head,
//                    transactions.size(), getAmount(transactions, "beSer", false, ""),
//                    getCount(transactions, "transStat", true, "6", "9"), getAmount(transactions, "transStat", true, "6", "9"),
//                    getCount(transactions, "transStat", true, "7", "8", "9"), getAmount(transactions, "transStat", true, "7", "8", "9"),
//                    getCount(transactions, "transStat", true, "9"), getAmount(transactions, "transStat", true, "9"), "");
//        } else if (bank.getBankNo().equals(AppConstants.BANK_NO_GDNY)){
//            head = String.format(head,
//                    getAmount(transactions, "stat", true, "Y"), getCount(transactions, "stat", true, "Y"));
//        }
        return header;
    }
}
