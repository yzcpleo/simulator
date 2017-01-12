package com.shhxzq.fin.simulator.biz.service.impl.cmbct0;

import com.shhxzq.fin.simulator.biz.service.BankCommandService;
import com.shhxzq.fin.simulator.biz.service.BankTranService;
import com.shhxzq.fin.simulator.biz.util.BankCommandHelp;
import com.shhxzq.fin.simulator.common.util.DateUtils;
import com.shhxzq.fin.simulator.model.constants.BankEnum;
import com.shhxzq.fin.simulator.model.vo.BankCommand;
import com.shhxzq.fin.simulator.model.vo.BankTran;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author kangyonggan
 * @since 2017/1/11
 */
@Service
@Log4j2
public class CMBCT0Service {

    @Autowired
    private BankTranService bankTranService;

    @Autowired
    private BankCommandService bankCommandService;

    public String redeem(Map<String, String> params) {
        log.info("进入民生T+0代付");

        // 读取交易信息
        BankTran bankTran = bankTranService.findBankTranByBnkCoAndTranCo(BankEnum.CMBCT0.getBnkCo(), CMBCT0TranCo.REDEEM);
        log.info("民生T+0代付的交易配置:" + bankTran);

        //生成银行交易日期BANK_TRAN_DATE
        String bankTranDate = DateUtils.getCurrentDate();

        String tranDate = params.get("TRAN_DATE");
        String tranId = params.get("TRAN_ID");
        String tranTime = params.get("TRAN_TIME");
        String tranAcco = params.get("PAY_ACCT_NO");
        String tranAmt = params.get("TRANS_AMT");
        String workDay = params.get("COMPANY_DATE");

        log.info("根据交易响码设置交易状态");
        String code = "00";
        String status = "S";
        String tranStatus = "Y";
        if (StringUtils.isNoneEmpty(bankTran.getRespCo())) {
            code = bankTran.getRespCo();

            if (code.equals("00")) {
                status = "S";
                tranStatus = "Y";
            } else if (code.equals("99") || code.equals("R1")) {
                status = "R";
                tranStatus = "I";
            } else {
                status = "E";
                tranStatus = "F";
            }

        }

        log.info("transaction saved to db...");
        BankCommand bankCommand = bankCommandService.findBankCommandByMerSerialNo(tranId);
        if (bankCommand != null) {
            log.info("交易重复, 商户流水号为: {}", tranId);
            code = "";
        } else {
            log.info("交易不重复, 生成交易并入库...");
            // 交易内容
            BankCommand command = new BankCommand();
            command.setMerSerialNo(tranId);// 基金公司流水号
            command.setBnkSerialNo(BankCommandHelp.genSerialNo());// 银行流水号
            command.setBnkCo(bankTran.getBnkCo());// 银行代码
            command.setBnkNm(bankTran.getBnkNm());
            command.setRcvrAccoNo(tranAcco);// 交易账户(卡号)
            command.setTranCo(bankTran.getTranCo());// 交易类型
            command.setTranNm(bankTran.getTranNm());
            command.setAmount(tranAmt);// 交易金额
            command.setRespCo(code);// 响应码: e.g. "00"
            command.setRespMsg("");
            command.setTranSt(tranStatus);// 状态码: e.g. "Y"
            command.setWorkDay(workDay);// 状态码: e.g. "Y"
            log.info("交易内容为: {}", command);

            // 交易入库
            bankCommandService.saveBankCommand(command);
        }

        // 响应报文
        log.info("generate redeem response xml.");
        String result = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<TRAN_RESP>\n" +
                "<RESP_TYPE>" + status + "</RESP_TYPE>\n" +
                "<RESP_CODE>" + code + "</RESP_CODE>\n" +
                "<RESP_MSG></RESP_MSG>\n" +
                "<MCHNT_CD></MCHNT_CD>\n" +
                "<TRAN_DATE>" + tranDate + "</TRAN_DATE>\n" +
                "<TRAN_TIME>" + tranTime + "</TRAN_TIME>\n" +
                "<TRAN_ID>" + tranId + "</TRAN_ID>\n" +
                "<BANK_TRAN_DATE>" + bankTranDate + "</BANK_TRAN_DATE>\n" +
                "<BANK_TRAN_ID></BANK_TRAN_ID>\n" +
                "<RESV>redeem</RESV>\n" +
                "</TRAN_RESP>";

        return result.replaceAll("\\n", "");
    }

    public String query(Map<String, String> params) {
        log.info("进入民生T+0单笔查询");

        // 读取交易信息
        BankTran bankTran = bankTranService.findBankTranByBnkCoAndTranCo(BankEnum.CMBCT0.getBnkCo(), CMBCT0TranCo.QUERY);
        log.info("民生T+0代付的交易配置:" + bankTran);

        String tranDate = params.get("TRAN_DATE");
        String tranId = params.get("TRAN_ID");
        String tranTime = params.get("TRAN_TIME");
        String oriTranDate = params.get("ORI_TRAN_DATE");
        String oriTranId = params.get("ORI_TRAN_ID");
        String oriBankTranDate = "";
        String oriBankTranId = "";
        String oriBankMsg = "";

        String code = "00";
        //查询响应码类型
        String respType = "S";
        String oriStatus = "";
        String oriCode = "";

        log.info("query original transaction in DB.");
        BankCommand bankCommand = bankCommandService.findBankCommandByMerSerialNo(oriTranId);

        if (bankCommand != null) {
            String status = bankCommand.getTranCo();
            oriCode = bankCommand.getRespCo();

            if (status.equals("Y")) {
                oriStatus = "S";
            } else if (status.equals("I")) {
                oriStatus = "R";
            } else if (status.equals("F")) {
                oriStatus = "E";
            }

        } else {
            oriStatus = "R";
            oriCode = "R1";
        }

        // 响应报文
        log.info("generate query response xml.");
        String result = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<TRAN_RESP>\n" +
                "<RESP_TYPE>" + respType + "</RESP_TYPE>\n" +
                "<RESP_CODE>" + code + "</RESP_CODE>\n" +
                "<RESP_MSG></RESP_MSG>\n" +
                "<MCHNT_CD></MCHNT_CD>\n" +
                "<TRAN_DATE>" + tranDate + "</TRAN_DATE>\n" +
                "<TRAN_TIME>" + tranTime + "</TRAN_TIME>\n" +
                "<TRAN_ID>" + tranId + "</TRAN_ID>\n" +
                "<ORI_TRAN_DATE>" + oriTranDate + "</ORI_TRAN_DATE>\n" +
                "<ORI_TRAN_ID>" + oriTranId + "</ORI_TRAN_ID>\n" +
                "<ORI_BANK_TRAN_DATE>" + oriBankTranDate + "</ORI_BANK_TRAN_DATE>\n" +
                "<ORI_BANK_TRAN_ID>" + oriBankTranId + "</ORI_BANK_TRAN_ID>\n" +
                "<ORI_RESP_TYPE>" + oriStatus + "</ORI_RESP_TYPE>\n" +
                "<ORI_RESP_CODE>" + oriCode + "</ORI_RESP_CODE>\n" +
                "<ORI_RESP_MSG>" + oriBankMsg + "</ORI_RESP_MSG>\n" +
                "<RESV>query</RESV>\n" +
                "</TRAN_RESP>";

        return result;
    }
}
