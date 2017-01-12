package com.shhxzq.fin.simulator.biz.service.impl.icbc;


import com.shhxzq.fin.simulator.biz.service.BankCommandService;
import com.shhxzq.fin.simulator.biz.service.BankTranService;
import com.shhxzq.fin.simulator.biz.util.BankCommandHelp;
import com.shhxzq.fin.simulator.biz.util.Base64Util;
import com.shhxzq.fin.simulator.common.util.DateUtils;
import com.shhxzq.fin.simulator.model.constants.BankEnum;
import com.shhxzq.fin.simulator.model.vo.BankCommand;
import com.shhxzq.fin.simulator.model.vo.BankTran;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by houjiagang on 16/7/26.
 */
@Component
@Log4j2
public class ICBCService {

    private static final String ENCODING = "GBK";

    private static final String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"GB2312\"?>\n";

    private static final Pattern PATTERN_REPLACEMENT;

    private XStream parser;


    @Autowired
    private BankCommandService bankCommandService;

    @Autowired
    private BankTranService bankTranService;

    static {
        PATTERN_REPLACEMENT = Pattern.compile("(?<=<TransCode>)(.*?)(?=</TransCode>)");
    }

    public String handle(HttpServletRequest request) throws Exception {
        //init xstream
        parser = getXStreamInstance();
        parser.processAnnotations(CMS.class);
        //receive request
        String reqStr = request.getParameter("reqData");
        if (!"<?".equals(reqStr.substring(0, 2))) {
            reqStr = Base64Util.base64Decode(reqStr);
        }
        log.info("Receive the request is {}", reqStr);
        //convert response by transCode
        String respStr = convertResponse(reqStr);
        log.info("Response is {}", respStr.replace("<querys>", "").replace("</querys>", ""));

        return respStr.replace("<querys>", "").replace("</querys>", "");
    }

    private String convertResponse(String reqString) {
        Matcher matcher = PATTERN_REPLACEMENT.matcher(reqString);
        if (!matcher.find()) {
            throw new UnsupportedOperationException("Thre transCode cannot be found in request xml!");
        }
        String transCode = matcher.group(0);
        switch (transCode) {
            case "SZFH_SMSAPPLY":
                return processSMSAPPLY(reqString);
            case "SZFH_SMSCONFIRM":
                return processSMSCONFIRM(reqString);
            case "SZFH_RECFRPER":
                return processRECFRPER(reqString);
            case "SZFH_QRECFRPER":
                return processQRECFRPER(reqString);
            case "SZFH_DELPERAMT":
                return processDELPERAMT(reqString);
            case "PAYPER":
                return processPAYPER(reqString);
            case "QPAYPER":
                return processQPAYPER(reqString);
            case "QPAYPERTM":
                return processQPAYPERTM(reqString);

        }
        return null;
    }

    private String processQPAYPERTM(String reqString) {
        log.info("============ 工行银企赎回对账(QPAYPERTM)开始 ============");
        CMS request = (CMS) parser.fromXML(reqString);
        String workDay = request.getEBody().getIn().getBeginTime();
        List<BankCommand> transactions = bankCommandService.findBankCommands4Dz("redeem", BankEnum.ICBC.getBnkCo(), workDay.substring(0, 8));
        List<Query> querys = new ArrayList();
        for (BankCommand tran : transactions) {
            Query query = new Query();
            query.setSerialNo(tran.getBnkSerialNo());
            query.setOrderNo("1");
            query.setFSeqno(tran.getMerSerialNo());
            query.setISeqno(tran.getMerSerialNo());
            query.setOnlBatF("1");
            query.setSettleMode("0");
            query.setPayType("1");
            query.setAccNo(tran.getRcvrAccoNo());
            query.setAccNameCN(tran.getRcvrAccoNo());
            query.setRecAccNo(tran.getRcvrAccoNo());
            query.setRecAccNameCN(tran.getRcvrAccoNo());
            query.setSysIOFlg("1");
            query.setIsSameCity("1");
            query.setRecICBCCode("4000");
            query.setRecBankName("1234");
            query.setCurrType("001");
            query.setPayAmt(tran.getAmount());
            query.setUseCN("mock");
            query.setPostScript(tran.getMerSerialNo());
            query.setIRetCode("0");
            query.setResult(tran.getRespCo());
            query.setBankRetTime(DateUtils.getCurrentDate());
            query.setInstrRetCode("0");
            String msg = "成功";
            if (!"Y".equals(tran.getTranSt())) {
                msg = "失败";
            }
            query.setInstrRetMsg(msg);
            querys.add(query);
        }

        Out out = new Out();
        out.setBeginTime(request.getEBody().getIn().getBeginTime());
        out.setEndTime(request.getEBody().getIn().getEndTime());
        out.setResultType("");
        out.setNextTag("");
        out.setRepReserved1("");
        out.setRepReserved2("");
        out.setQuerys(querys);

        Pub pub = request.getEBody().getPub();
        pub.setRetCode("");
        pub.setRetMsg("");

        EBody eBody = new EBody();
        eBody.setPub(pub);
        eBody.setOut(out);

        CMS response = new CMS();
        response.setEBody(eBody);

        String respStr = XML_DECLARATION + parser.toXML(response);

        return respStr;


    }

    private String processQPAYPER(String reqString) {
        log.info("============ 工行银企赎回查询(QPAYPER)开始 ============");
        CMS request = (CMS) parser.fromXML(reqString);

        BankCommand bankCommand = bankCommandService.findBankCommandByMerSerialNo(request.getEBody().getIn().getQryfSeqno());
        log.info("查询的交易为: {}", bankCommand);
        String retCode = "7";
        String retMsg = "交易成功";
        if (bankCommand == null) {
            retCode = "6";
            retMsg = "流水号不存在";
            bankCommand = new BankCommand();
            bankCommand.setRcvrAccoNo("");
            bankCommand.setAmount("");
        } else {
            retCode = bankCommand.getRespCo();
        }

        //拼装返回response
        Query rd = new Query();
        rd.setISeqno("1");
        rd.setQryiSeqno("1");
        rd.setQryOrderNo("1");
        rd.setPayType("1");
        rd.setPayAccNo("4000023029200124946");
        rd.setPayAccNameCN("mock");
        rd.setRecAccNo(bankCommand.getRcvrAccoNo());
        rd.setRecAccNameCN(bankCommand.getRcvrAccoNo());
        rd.setSysIOFlg("1");
        rd.setIsSameCity("1");
        rd.setRecICBCCode("4000");
        rd.setCurrType("001");
        rd.setPayAmt(bankCommand.getAmount());
        rd.setUseCode("22");
        rd.setUseCN("基金赎回");
        rd.setBusCode("953");
        rd.setIRetCode("0");
        rd.setResult(retCode);
        rd.setInstrRetCode("0");
        rd.setInstrRetMsg(retMsg);
        rd.setBankRetTime(RandomStringUtils.randomNumeric(14));
        List<Query> rds = new ArrayList<Query>();
        rds.add(rd);

        Out out = new Out();
        out.setQryfSeqno(request.getEBody().getIn().getQryfSeqno());
        out.setQrySerialNo(request.getEBody().getPub().getFSeqno());
        out.setOnlBatF("1");
        out.setBusType("");
        out.setRepReserved1("");
        out.setRepReserved2("");

        out.setQuerys(rds);

        Pub pub = request.getEBody().getPub();
        pub.setRetCode("0");
        pub.setRetMsg("");

        EBody eBody = new EBody();
        eBody.setPub(pub);
        eBody.setOut(out);

        CMS response = new CMS();
        response.setEBody(eBody);

        String respStr = XML_DECLARATION + parser.toXML(response);

        return respStr;
    }

    private String processPAYPER(String reqString) {
        log.info("============ 工行银企赎回(PAYPER)开始 ============");
        CMS request = (CMS) parser.fromXML(reqString);

        BankTran bankTran = bankTranService.findBankTranByBnkCoAndTranCo(BankEnum.ICBC.getBnkCo(), "redeem");
        String retCode = "7";
        String retMsg = "处理成功";
        if (StringUtils.isNotEmpty(bankTran.getRespCo())) {
            retCode = bankTran.getRespCo();
            retMsg = bankTran.getRespMsg();
            log.info("强制改变赎回响应码为: {} - {}", retCode, retMsg);
        } else {
            log.info("不强制改变申购响应码, 正常赎回...");
        }
        BankCommand tran = bankCommandService.findBankCommandByMerSerialNo(request.getEBody().getPub().getFSeqno());

        if (tran != null) {
            log.info("重复交易,流水号为: {}", tran);
            retCode = "7";
            retMsg = "重复交易";
        } else {
            log.info("交易不重复,生成交易并入库...");
            BankCommand transaction = IcbcUtil.buildRedeemTransaction(request, bankTran, BankCommandHelp.genSerialNo(), retCode);
            bankCommandService.saveBankCommand(transaction);
        }
        //拼装response
        Pub pub = request.getEBody().getPub();
        pub.setRetCode("0");
        pub.setRetMsg("成功");

        Query rd = request.getEBody().getIn().getQuery();
        rd.setOrderNo("1");
        rd.setResult(retCode);
        rd.setIRetCode("0");
        rd.setIRetMsg(retMsg);
        List<Query> rds = new ArrayList<Query>();
        rds.add(rd);

        Out out = new Out();
        out.setOnlBatF("1");
        out.setSettleMode("0");
        out.setTotalNum("1");
        out.setTotalAmount(request.getEBody().getIn().getTotalAmount());
        out.setRepReserved1("");
        out.setRepReserved2("");
        out.setQuerys(rds);

        EBody eBody = new EBody();
        eBody.setPub(pub);
        eBody.setOut(out);

        CMS response = new CMS();
        response.setEBody(eBody);

        String respStr = XML_DECLARATION + parser.toXML(response);

        return respStr;
    }

    private String processDELPERAMT(String reqString) {
        log.info("============ 工行快捷解约(SZFH_DELPERAMT)开始 ============");
        CMS request = (CMS) parser.fromXML(reqString);
        //拼装response
        Out out = new Out();
        out.setCorpAccNo(request.getEBody().getIn().getCorpAccNo());
        out.setAccNo("");
        out.setAccName("");
        out.setMobilePhone("");
        out.setCorpNo(request.getEBody().getIn().getCorpNo());
        out.setPersonNo(request.getEBody().getIn().getPersonNo());
        out.setReqReserved1("");
        out.setReqReserved2("");
        out.setReqReserved3("");
        out.setReqReserved4("");
        out.setBankSeq("");
        out.setDelTime("");
        out.setRepReserved1("");
        out.setRepReserved2("");
        out.setRepReserved3("");
        out.setRepReserved4("");

        Pub pub = request.getEBody().getPub();
        pub.setRetCode("00000");
        pub.setRetMsg("交易成功");

        EBody eBody = new EBody();
        eBody.setPub(pub);
        eBody.setOut(out);

        CMS response = new CMS();
        response.setEBody(eBody);

        String respStr = XML_DECLARATION + parser.toXML(response);

        return respStr;

    }


    private String processQRECFRPER(String reqString) {
        CMS request = (CMS) parser.fromXML(reqString);
        if ("".equals(request.getEBody().getIn().getQryfSeqno())) {
            return processDz(request);
        } else {
            return processQuery(request);
        }

    }

    private String processDz(CMS request) {
        log.info("============ 工行快捷申购对账(SZFH_QRECFRPER)开始 ============");
        String workDay = request.getEBody().getIn().getBeginDate();
        List<BankCommand> transactions = bankCommandService.findBankCommands4Dz("pay", BankEnum.ICBC.getBnkCo(), workDay);
        List<Query> querys = new ArrayList<Query>();
        for (BankCommand tran : transactions) {
            Query query = new Query();
            query.setBankSeq(tran.getBnkSerialNo());
            query.setCurfSeqno(tran.getMerSerialNo());
            query.setPcode("MPREPAY_PAY");
            query.setUserAcc(tran.getRcvrAccoNo());
            query.setUserName(tran.getRcvrAcctNm());
            query.setIdType("0");
            query.setIdCode("12345678910");
            query.setMobilePhone("12345678910");
            query.setTotalAmount(tran.getAmount());
            query.setCurrType("001");
            query.setSummary("");
            String status = "2";
            if (!"Y".equals(tran.getTranSt())) {
                status = "3";
            }
            query.setStatus(status);
            query.setBankRem("");
            query.setRepReserved1("BDP800008");
            query.setRepReserved2("CM103432016060115");
            query.setRepReserved3("");
            query.setRepReserved4("");
            querys.add(query);
        }

        Out out = new Out();
        out.setCorpAccNo(request.getEBody().getIn().getCorpAccNo());
        out.setBeginDate(request.getEBody().getIn().getBeginDate());
        out.setEndDate(request.getEBody().getIn().getEndDate());
        out.setQryfSeqno("");
        out.setQryBankSeq("");
        out.setNextTag("");
        out.setReqReserved1("");
        out.setReqReserved2("");
        out.setReqReserved3("");
        out.setReqReserved4("");
        out.setQuerys(querys);

        Pub pub = request.getEBody().getPub();
        pub.setRetCode("00000");
        pub.setRetMsg("交易成功");

        EBody eBody = new EBody();
        eBody.setPub(pub);
        eBody.setOut(out);

        CMS response = new CMS();
        response.setEBody(eBody);

        String respStr = XML_DECLARATION + parser.toXML(response);

        return respStr;

    }

    private String processQuery(CMS request) {
        log.info("============ 工行快捷申购查询(SZFH_QRECFRPER)开始 ============");
        BankCommand transaction = bankCommandService.findBankCommandByMerSerialNo(request.getEBody().getIn().getQryfSeqno());
        log.info("查询的交易为: {}", transaction);

        //拼装返回response
        Query rd = new Query();
        rd.setBankSeq("");
        rd.setCurfSeqno("");
        rd.setPcode("");
        rd.setUserAcc("");
        rd.setUserName("");
        rd.setIdType("");
        rd.setIdCode("");
        rd.setMobilePhone("");
        rd.setTotalAmount("");
        rd.setCurrType("");
        rd.setSummary("");
        String status = "2";
        if (!"Y".equals(transaction.getTranSt())) {
            status = "3";
        }
        rd.setStatus(status);
        rd.setBankRem("");
        rd.setRepReserved1("");
        rd.setRepReserved2("");
        rd.setRepReserved3("");
        rd.setRepReserved4("");
        List<Query> rds = new ArrayList<Query>();
        rds.add(rd);

        Out out = new Out();
        out.setCorpAccNo(request.getEBody().getIn().getCorpAccNo());
        out.setBeginDate(request.getEBody().getIn().getBeginDate());
        out.setEndDate(request.getEBody().getIn().getEndDate());
        out.setQryfSeqno(request.getEBody().getIn().getQryfSeqno());
        out.setQryBankSeq(request.getEBody().getIn().getQryBankSeq());
        out.setNextTag(request.getEBody().getIn().getNextTag());
        out.setReqReserved1("");
        out.setReqReserved2("");
        out.setReqReserved3("");
        out.setReqReserved4("");


        out.setQuerys(rds);

        Pub pub = request.getEBody().getPub();
        pub.setRetCode("00000");
        pub.setRetMsg("交易成功");

        EBody eBody = new EBody();
        eBody.setPub(pub);
        eBody.setOut(out);

        CMS response = new CMS();
        response.setEBody(eBody);

        String respStr = XML_DECLARATION + parser.toXML(response);

        return respStr;

    }


    private String processRECFRPER(String reqString) {
        log.info("============ 工行快捷申购(SZFH_RECFRPER)开始 ============");
        CMS request = (CMS) parser.fromXML(reqString);

        BankTran bankTran = bankTranService.findBankTranByBnkCoAndTranCo(BankEnum.ICBC.getBnkCo(), "pay");
        String retCode = "00000";
        String retMsg = "交易成功";
        if (StringUtils.isNotEmpty(bankTran.getRespCo())) {
            retCode = bankTran.getRespCo();
            retMsg = bankTran.getRespMsg();
            log.info("强制改变申购响应码为: {} - {}", retCode, retMsg);
        } else {
            log.info("不强制改变申购响应码, 正常申购...");
        }
        BankCommand tran = bankCommandService.findBankCommandByMerSerialNo(request.getEBody().getPub().getFSeqno());
        if (tran != null) {
            log.info("重复交易,流水号为: {}", tran);
            retCode = "00100";
            retMsg = "重复交易";
        } else {
            log.info("交易不重复,生成交易并入库...");
            BankCommand transaction = IcbcUtil.buildTransaction(request, bankTran, BankCommandHelp.genSerialNo(), retCode);
            bankCommandService.saveBankCommand(transaction);
        }
        //拼装response
        Pub pub = request.getEBody().getPub();
        pub.setRetCode(retCode);
        pub.setRetMsg(retMsg);

        Out out = new Out();
        out.setCorpAccNo(request.getEBody().getIn().getCorpAccNo());
        out.setPcode(request.getEBody().getIn().getPcode());
        out.setUserAcc(request.getEBody().getIn().getUserAcc());
        out.setUserName(request.getEBody().getIn().getUserName());
        out.setIdType(request.getEBody().getIn().getIdType());
        out.setIdCode(request.getEBody().getIn().getIdCode());
        out.setMobilePhone(request.getEBody().getIn().getMobilePhone());
        out.setTotalAmount(request.getEBody().getIn().getTotalAmount());
        out.setCurrType(request.getEBody().getIn().getCurrType());
        out.setSummary(request.getEBody().getIn().getSummary());
        out.setUserinfo1("");
        out.setUserinfo2("");
        out.setUserinfo3("");
        out.setUserinfo4(request.getEBody().getIn().getUserinfo4());
        out.setUserinfo5(request.getEBody().getIn().getUserinfo5());
        out.setUserinfo6(request.getEBody().getIn().getUserinfo6());
        out.setUserinfo7(request.getEBody().getIn().getUserinfo7());
        out.setUserinfo8("");
        out.setUserinfo9("");
        out.setUserinfo10("");
        out.setReqReserved1(request.getEBody().getIn().getReqReserved1());
        out.setReqReserved2(request.getEBody().getIn().getReqReserved2());
        out.setReqReserved3(request.getEBody().getIn().getReqReserved3());
        out.setReqReserved4(request.getEBody().getIn().getReqReserved4());
        out.setBankSeq(RandomStringUtils.randomNumeric(20));
        out.setHostDate("");
        out.setRepReserved1("");
        out.setRepReserved2("");
        out.setRepReserved3("");
        out.setRepReserved4("");

        EBody eBody = new EBody();
        eBody.setPub(pub);
        eBody.setOut(out);

        CMS response = new CMS();
        response.setEBody(eBody);

        String respStr = XML_DECLARATION + parser.toXML(response);

        return respStr;
    }

    private String processSMSCONFIRM(String reqString) {
        log.info("============ 工行快捷鉴权(SZFH_SMSCONFIRM)开始 ============");
        CMS request = (CMS) parser.fromXML(reqString);

        BankTran bankTran = bankTranService.findBankTranByBnkCoAndTranCo(BankEnum.ICBC.getBnkCo(), "verify");
        String retCode = "1";
        String retMsg = "成功";
        if (StringUtils.isNotEmpty(bankTran.getRespCo())) {
            retCode = bankTran.getRespCo();
            retMsg = bankTran.getRespMsg();
            log.info("强制改变鉴权响应码为: {} - {}", retCode, retMsg);
        } else {
            log.info("不强制改变响应码, 正常鉴权...");
        }
        //拼装response
        Pub pub = request.getEBody().getPub();
        pub.setRetCode("00000");
        pub.setRetMsg("交易成功");

        Out out = new Out();
        out.setCorpAccNo(request.getEBody().getIn().getCorpAccNo());
        out.setCorpNo(request.getEBody().getIn().getCorpNo());
        out.setPersonNo(request.getEBody().getIn().getPersonNo());
        out.setSmsSendNo(request.getEBody().getIn().getSmsSendNo());
        out.setVeriCode("");
        out.setReqReserved1("");
        out.setReqReserved2("");
        out.setReqReserved3("");
        out.setReqReserved4("");
        out.setBankSeq("");
        out.setVfRetCode(retCode);
        out.setVfRetMsg(retMsg);
        out.setRepReserved1("");
        out.setRepReserved2("");
        out.setRepReserved3("");
        out.setRepReserved4("");

        EBody eBody = new EBody();
        eBody.setPub(pub);
        eBody.setOut(out);

        CMS response = new CMS();
        response.setEBody(eBody);

        String respStr = XML_DECLARATION + parser.toXML(response);

        return respStr;
    }

    private String processSMSAPPLY(String reqString) {
        log.info("============ 工行快捷发短信(SZFH_SMSAPPLY)开始 ============");
        CMS request = (CMS) parser.fromXML(reqString);
        BankTran bankTran = bankTranService.findBankTranByBnkCoAndTranCo(BankEnum.ICBC.getBnkCo(), "sendsms");

        String retCode = "00000";
        String retMsg = "交易成功";
        if (StringUtils.isNotEmpty(bankTran.getRespCo())) {
            retCode = bankTran.getRespCo();
            retMsg = bankTran.getRespMsg();
            log.info("强制改变短信响应码为: {} - {}", retCode, retMsg);
        } else {
            log.info("不强制改变响应码, 正常发短信...");
        }
        //拼装response
        Pub pub = request.getEBody().getPub();
        pub.setRetCode(retCode);
        pub.setRetMsg(retMsg);

        Out out = new Out();
        out.setCorpAccNo(request.getEBody().getIn().getCorpAccNo());
        out.setAccNo(request.getEBody().getIn().getAccNo());
        out.setSupType("0");
        out.setAccName(request.getEBody().getIn().getAccName());
        out.setIdType(request.getEBody().getIn().getIdType());
        out.setIdCode(request.getEBody().getIn().getIdCode());
        out.setMobilePhone(request.getEBody().getIn().getMobilePhone());
        out.setCorpNo(request.getEBody().getIn().getCorpNo());
        out.setPersonNo(request.getEBody().getIn().getPersonNo());
        out.setDeadLine(request.getEBody().getIn().getDeadLine());
        out.setReqReserved1("");
        out.setReqReserved2("");
        out.setReqReserved3("");
        out.setReqReserved4("");
        out.setBankSeq(RandomStringUtils.randomNumeric(20));
        out.setAccType("3");
        out.setAreaCode("4000");
        out.setSmsSendFlag("");
        out.setSmsSendNo(RandomStringUtils.randomNumeric(8));
        out.setRepReserved1("");
        out.setRepReserved2("");
        out.setRepReserved3("");
        out.setRepReserved4("");

        EBody eBody = new EBody();
        eBody.setPub(pub);
        eBody.setOut(out);

        CMS response = new CMS();
        response.setEBody(eBody);

        String respStr = XML_DECLARATION + parser.toXML(response);

        return respStr;
    }

    private XStream getXStreamInstance() {
        return new XStream(new DomDriver(ENCODING, new XmlFriendlyNameCoder("_-", "_")));
    }
}
