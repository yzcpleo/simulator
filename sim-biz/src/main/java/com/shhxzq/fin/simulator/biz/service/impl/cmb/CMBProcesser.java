package com.shhxzq.fin.simulator.biz.service.impl.cmb;

import com.shhxzq.fin.simulator.biz.service.BankCommandService;
import com.shhxzq.fin.simulator.biz.service.BankTranService;
import com.shhxzq.fin.simulator.biz.util.BankCommandHelp;
import com.shhxzq.fin.simulator.common.util.DateUtils;
import com.shhxzq.fin.simulator.model.constants.BankEnum;
import com.shhxzq.fin.simulator.model.vo.BankCommand;
import com.shhxzq.fin.simulator.model.vo.BankTran;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by houjiagang on 16/8/9.
 */
@Component
@Log4j2
public class CMBProcesser {

    @Autowired
    private BankTranService bankTranService;

    @Autowired
    private BankCommandService bankCommandService;

    public String handle(String reqString) throws Exception {
        //receive request
        log.info("Receive the request is {}", reqString);
        PkgHeader header = new PkgHeader(reqString);
        //convert response by messageId
        String respStr = convertResponse(reqString, header);
        log.info("Response is {}", respStr);
        return respStr;
    }

    private String convertResponse(String reqString, PkgHeader header) throws Exception {
        switch (header.getTransCode()) {
            case "CMDK":
                return processCMDK(header);
            case "CMDM":
                return processCMDM(header);
//            case "BKQD":
//                return processBKQD(reqString, header);
//            case "CMQY":
//                return processCMQY(reqString, header);
            case "CMSK":
                return processCMSK(reqString, header);
            case "CMDB":
                return processCMDB(reqString, header);

//            case "CMYZ":
//                return processCMYZ(reqString, header);
//            case "CMQX":
//                return processCMQX(reqString, header);
//            case "CMFK":
//                return processCMFK(reqString, header);


        }
        return null;

    }

    private String processCMDK(PkgHeader requestHeader) throws Exception {
        PkgBodyCMDKRep bodyResponse = new PkgBodyCMDKRep();
        bodyResponse.setRetCode("CMBMB99");
        bodyResponse.setDeKey("0AB3FA3DB24CE4DC");
        bodyResponse.setTransDetail("申请传输密钥成功");
        String bodyString = bodyResponse.getPackage();

        PkgHeader header = new PkgHeader();
        header.setPkgFlag("CMBA");
        header.setPkgLen(String.valueOf(bodyString.getBytes().length));
        header.setTransCode("CMDK");
        header.setBnkDate(DateUtils.getCurrentDate());
        header.setBnkTime("120000");
        header.setBnkFlowNo(RandomStringUtils.randomNumeric(16));
        header.setCorpDate(DateUtils.getCurrentDate());
        header.setCorpTime("120000");
        header.setCorpFlowNo(requestHeader.getCorpFlowNo());
        header.setReqCorpNo("000216");
        header.setAckCode("");

        String headerString = header.getPackage();
        String returnString = headerString + bodyString;
        return returnString;

    }

    private String processCMDM(PkgHeader requestHeader) throws Exception {
        PkgBodyCMDMRep bodyResponse = new PkgBodyCMDMRep();
        bodyResponse.setRetCode("CMBMB99");
        bodyResponse.setPinKey(RandomStringUtils.randomNumeric(8));
        bodyResponse.setMacKey(RandomStringUtils.randomNumeric(8));
        bodyResponse.setTransDetail("申请数据密钥成功");
        String bodyString = bodyResponse.getPackage();

        PkgHeader header = new PkgHeader();
        header.setPkgFlag("CMBA");
        header.setPkgLen(String.valueOf(bodyString.getBytes().length));
        header.setTransCode("CMDM");
        header.setBnkDate(DateUtils.getCurrentDate());
        header.setBnkTime("120000");
        header.setBnkFlowNo(RandomStringUtils.randomNumeric(16));
        header.setCorpDate(DateUtils.getCurrentDate());
        header.setCorpTime("120000");
        header.setCorpFlowNo(requestHeader.getCorpFlowNo());
        header.setReqCorpNo("000216");
        header.setAckCode("");

        String headerString = header.getPackage();
        String returnString = headerString + bodyString;
        return returnString;

    }

    private String processCMYZ(String reqString, PkgHeader requestHeader) throws Exception {
        log.info("============ 招行快捷签约(CMYZ)开始 ============");

        BankTran bankTran = bankTranService.findBankTranByBnkCoAndTranCo(BankEnum.CMB.getBnkCo(), "verify");
        String retCode = "CMBMB99";
        String retMsg = "验证通过";
        if(StringUtils.isNotEmpty(bankTran.getRespCo())) {
            retCode = bankTran.getRespCo();
            retMsg = bankTran.getRespMsg();
            log.info("强制改变鉴权响应码为: {} - {}", retCode, retMsg);
        } else {
            log.info("不强制改变响应码, 正常鉴权...");
        }

        PkgBodyCMYZReq requestBody = new PkgBodyCMYZReq(reqString.substring(126));
        PkgBodyCMYZRep body = new PkgBodyCMYZRep();
        body.setRetCode(retCode);
        body.setBankDate("");
        body.setProtocolNo(requestBody.getProtocolNo());
        body.setAcctno("");
        body.setAreaCode("");
        body.setCurCode("");
        body.setAmount("00000000000000000000");
        body.setTransDetail(retMsg);
        String bodyString = body.getPackage();

        PkgHeader header = new PkgHeader();
        header.setPkgFlag("CMBA");
        header.setPkgLen(String.valueOf(bodyString.getBytes().length));
        header.setTransCode("CMYZ");
        header.setBnkDate(DateUtils.getCurrentDate());
        header.setBnkTime("120000");
        header.setBnkFlowNo(RandomStringUtils.randomNumeric(16));
        header.setCorpDate(DateUtils.getCurrentDate());
        header.setCorpTime("120000");
        header.setCorpFlowNo(requestHeader.getCorpFlowNo());
        header.setReqCorpNo("000216");
        header.setAckCode(RandomStringUtils.randomNumeric(16));

        String headerString = header.getPackage();
        String returnString = headerString + bodyString;
        return returnString;

    }

//    private String processBKQD(String reqString, PkgHeader requestHeader) throws Exception {
//        log.info("============ 招行快捷签约(BKQD)开始 ============");
//        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_CMB);
//        Config config = configService.findBankSelectConfig(bank.getCode(), "verify");
//        String retCode = "CMBMB99";
//        String retMsg = "签署协议成功";
//        if(config != null) {
//            retCode = config.getK();
//            retMsg = config.getVal();
//            log.info("强制改变鉴权响应码为: {} - {}", retCode, retMsg);
//        } else {
//            log.info("不强制改变响应码, 正常鉴权...");
//        }
//
//        PkgBodyBKQDReq requestBody = new PkgBodyBKQDReq(reqString.substring(126));
//        PkgBodyBKQDRep body = new PkgBodyBKQDRep();
//        body.setRetCode(retCode);
//        body.setCustCode(requestBody.getCustCode());
//        body.setSubCorpNo(requestBody.getSubCorpNo());
//        body.setTransDetail(retMsg);
//        String bodyString = body.getPackage();
//
//        PkgHeader header = new PkgHeader();
//        header.setPkgFlag("CMBA");
//        header.setPkgLen(String.valueOf(bodyString.getBytes().length));
//        header.setTransCode("BKQD");
//        header.setBnkDate(DateUtil.format8Date(new Date()));
//        header.setBnkTime("120000");
//        header.setBnkFlowNo(RandomStringUtils.randomNumeric(16));
//        header.setCorpDate(DateUtil.format8Date(new Date()));
//        header.setCorpTime("120000");
//        header.setCorpFlowNo(requestHeader.getCorpFlowNo());
//        header.setReqCorpNo("000216");
//        header.setAckCode(RandomStringUtils.randomNumeric(16));
//
//        String headerString = header.getPackage();
//        String returnString = headerString + bodyString;
//        return returnString;
//
//    }

    private String processCMQY(String reqString, PkgHeader requestHeader) throws Exception {
        log.info("============ 招行快捷签约启动(CMQY)开始 ============");
        String retCode = "CMBMB99";
        String retMsg = "启用协议成功";

        PkgBodyCMQYReq requestBody = new PkgBodyCMQYReq(reqString.substring(126));
        PkgBodyCMQYRep body = new PkgBodyCMQYRep();
        body.setRetCode(retCode);
        body.setCustCode(requestBody.getCustCode());
        body.setSubCorpNo(requestBody.getSubCorpNo());
        body.setTransDetail(retMsg);
        String bodyString = body.getPackage();

        PkgHeader header = new PkgHeader();
        header.setPkgFlag("CMBA");
        header.setPkgLen(String.valueOf(bodyString.getBytes().length));
        header.setTransCode("CMQY");
        header.setBnkDate(DateUtils.getCurrentDate());
        header.setBnkTime("120000");
        header.setBnkFlowNo(RandomStringUtils.randomNumeric(16));
        header.setCorpDate(DateUtils.getCurrentDate());
        header.setCorpTime("120000");
        header.setCorpFlowNo(requestHeader.getCorpFlowNo());
        header.setReqCorpNo("000216");
        header.setAckCode(RandomStringUtils.randomNumeric(16));

        String headerString = header.getPackage();
        String returnString = headerString + bodyString;
        return returnString;

    }


    private String processCMQX(String reqString, PkgHeader requestHeader) throws Exception {
        log.info("============ 招行快捷客户取消电子协议(CMQX)开始 ============");
        String retCode = "CMBMB99";
        String retMsg = "取消成功";

        PkgBodyCMQXReq requestBody = new PkgBodyCMQXReq(reqString.substring(126));
        PkgBodyCMQXRep body = new PkgBodyCMQXRep();
        body.setRetCode(retCode);
        body.setBankDate("");
        body.setProtocolNo(requestBody.getCustCode());
        body.setAccount("");
        body.setAccountArea("");
        body.setCurCode("");
        body.setAmount("00000000000000000000");
        body.setTransDetail(retMsg);
        String bodyString = body.getPackage();

        PkgHeader header = new PkgHeader();
        header.setPkgFlag("CMBA");
        header.setPkgLen(String.valueOf(bodyString.getBytes().length));
        header.setTransCode("CMQX");
        header.setBnkDate(DateUtils.getCurrentDate());
        header.setBnkTime("120000");
        header.setBnkFlowNo(RandomStringUtils.randomNumeric(16));
        header.setCorpDate(DateUtils.getCurrentDate());
        header.setCorpTime("120000");
        header.setCorpFlowNo(requestHeader.getCorpFlowNo());
        header.setReqCorpNo("000216");
        header.setAckCode(RandomStringUtils.randomNumeric(16));

        String headerString = header.getPackage();
        String returnString = headerString + bodyString;
        return returnString;

    }

    private String processCMSK(String reqString, PkgHeader requestHeader) throws Exception {
        log.info("============ 招行快捷委托收款(CMSK)开始 ============");
        PkgBodyCMSKReq requestBody = new PkgBodyCMSKReq(reqString.substring(126));

        BankTran bankTran = bankTranService.findBankTranByBnkCoAndTranCo(BankEnum.CMB.getBnkCo(), "pay");
        String retCode = "CMBMB99";
        String retMsg = "收款交易成功!";
        if(StringUtils.isNotEmpty(bankTran.getRespCo())) {
            retCode = bankTran.getRespCo();
            retMsg = bankTran.getRespMsg();
            log.info("强制改变申购响应码为: {} - {}", retCode, retMsg);
        } else {
            log.info("不强制改变响应码, 正常申购...");
        }
        BankCommand tran = bankCommandService.findBankCommandByMerSerialNo(requestHeader.getCorpFlowNo());
        if(tran != null) {
            log.info("重复交易,流水号为: {}", tran);
            retCode = "CMBMB96";
            retMsg = "交易重复";
        } else {
            log.info("交易不重复,生成交易并入库...");
            BankCommand transaction = CMBUtil.buildTransaction(requestHeader.getCorpFlowNo(), requestBody.getAmount(), bankTran, BankCommandHelp.genSerialNo(), retCode);
            bankCommandService.saveBankCommand(transaction);
        }

        PkgBodyCMSKRep body = new PkgBodyCMSKRep();
        body.setRetCode(retCode);
        body.setBankDate(DateUtils.getCurrentDate());
        body.setCustCode(requestBody.getCustCode());
        body.setAcctno("");
        body.setAreaCode("");
        body.setCurCode("RMB");
        body.setAmount(requestBody.getAmount());
        body.setTransDetail(retMsg);

        String bodyString = body.getPackage();

        PkgHeader header = new PkgHeader();
        header.setPkgFlag("CMBA");
        header.setPkgLen(String.valueOf(bodyString.getBytes().length));
        header.setTransCode("CMSK");
        header.setBnkDate(DateUtils.getCurrentDate());
        header.setBnkTime("120000");
        header.setBnkFlowNo(RandomStringUtils.randomNumeric(16));
        header.setCorpDate(DateUtils.getCurrentDate());
        header.setCorpTime("120000");
        header.setCorpFlowNo(requestHeader.getCorpFlowNo());
        header.setReqCorpNo("000216");
        header.setAckCode(RandomStringUtils.randomNumeric(16));

        String headerString = header.getPackage();
        String returnString = headerString + bodyString;
        return returnString;

    }


    private String processCMFK(String reqString, PkgHeader requestHeader) throws Exception {
        log.info("============ 招行快捷委托付款(CMFK)开始 ============");
        PkgBodyCMSKReq requestBody = new PkgBodyCMSKReq(reqString.substring(126));

        BankTran bankTran = bankTranService.findBankTranByBnkCoAndTranCo(BankEnum.CMB.getBnkCo(), "redeem");
        String retCode = "CMBMB99";
        String retMsg = "收款交易成功!";
        if(StringUtils.isNotEmpty(bankTran.getRespCo())) {
            retCode =bankTran.getRespCo();
            retMsg = bankTran.getRespMsg();
            log.info("强制改变赎回响应码为: {} - {}", retCode, retMsg);
        } else {
            log.info("不强制改变响应码, 正常赎回...");
        }
        BankCommand tran = bankCommandService.findBankCommandByMerSerialNo(requestHeader.getCorpFlowNo());
        if(tran != null) {
            log.info("重复交易,流水号为: {}", tran);
            retCode = "CMBMB96";
            retMsg = "交易重复";
        } else {
            log.info("交易不重复,生成交易并入库...");
            BankCommand transaction = CMBUtil.buildTransaction(requestHeader.getCorpFlowNo(), requestBody.getAmount(), bankTran, BankCommandHelp.genSerialNo(), retCode);
            bankCommandService.saveBankCommand(transaction);
        }

        PkgBodyCMSKRep body = new PkgBodyCMSKRep();
        body.setRetCode(retCode);
        body.setBankDate(DateUtils.getCurrentDate());
        body.setCustCode(requestBody.getCustCode());
        body.setAcctno("");
        body.setAreaCode("");
        body.setCurCode("RMB");
        body.setAmount(requestBody.getAmount());
        body.setTransDetail(retMsg);

        String bodyString = body.getPackage();

        PkgHeader header = new PkgHeader();
        header.setPkgFlag("CMBA");
        header.setPkgLen(String.valueOf(bodyString.getBytes().length));
        header.setTransCode("CMFK");
        header.setBnkDate(DateUtils.getCurrentDate());
        header.setBnkTime("120000");
        header.setBnkFlowNo(RandomStringUtils.randomNumeric(16));
        header.setCorpDate(DateUtils.getCurrentDate());
        header.setCorpTime("120000");
        header.setCorpFlowNo(requestHeader.getCorpFlowNo());
        header.setReqCorpNo("000216");
        header.setAckCode(RandomStringUtils.randomNumeric(16));

        String headerString = header.getPackage();
        String returnString = headerString + bodyString;
        return returnString;

    }

    private String processCMDB(String reqString, PkgHeader requestHeader) throws Exception {
        log.info("============ 招行快捷单笔对账(CMDB)开始 ============");
        PkgBodyCMDBReq requestBody = new PkgBodyCMDBReq(reqString.substring(126));
        BankCommand transaction = bankCommandService.findBankCommandByMerSerialNo(requestBody.getOrgCorpFlowNo());
        String retCode = "CMBMB99";
        String retMsg = "原交易成功:";
        if(transaction == null){
            retCode = "CMBMB95";
            retMsg = "原交易不存在.";
            transaction = new BankCommand();
            transaction.setWorkDay("");
            transaction.setMerSerialNo("");
        } else {
            retCode = transaction.getRespCo();
            retMsg = transaction.getRespMsg();
        }

        PkgBodyCMDBRep body = new PkgBodyCMDBRep();
        body.setRetCode("CMBMB99");
        body.setOrgBnkDate(transaction.getWorkDay());
        body.setOrgBnkFlowNo(transaction.getMerSerialNo());
        body.setOrgRetCode(retCode);
        body.setTransDetail(retMsg);

        String bodyString = body.getPackage();

        PkgHeader header = new PkgHeader();
        header.setPkgFlag("CMBA");
        header.setPkgLen(String.valueOf(bodyString.getBytes().length));
        header.setTransCode("CMDB");
        header.setBnkDate(DateUtils.getCurrentDate());
        header.setBnkTime("120000");
        header.setBnkFlowNo(RandomStringUtils.randomNumeric(16));
        header.setCorpDate(DateUtils.getCurrentDate());
        header.setCorpTime("120000");
        header.setCorpFlowNo(requestHeader.getCorpFlowNo());
        header.setReqCorpNo("000216");
        header.setAckCode(RandomStringUtils.randomNumeric(16));

        String headerString = header.getPackage();
        String returnString = headerString + bodyString;
        return returnString;

    }

}
