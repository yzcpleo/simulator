package com.shhxzq.fin.simulator.biz.service.impl.ceb;

import com.shhxzq.fin.simulator.biz.service.BankCommandService;
import com.shhxzq.fin.simulator.biz.service.BankTranService;
import com.shhxzq.fin.simulator.biz.util.StreamUtil;
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
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by houjiagang on 16/9/6.
 */
@Service
@Log4j2
public class CEBService {

    private static final String ENCODING = "GBK";

    private static final Pattern PATTERN_REPLACEMENT;

    private static final String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"GBK\"?>\n";

    private XStream requestParser;

    private XStream plainParser;

    private XStream transactionParser;

    @Autowired
    private BankTranService bankTranService;

    @Autowired
    private BankCommandService bankCommandService;

    static {
        PATTERN_REPLACEMENT = Pattern.compile("(?<=<transId>)(.*?)(?=</transId>)");

    }

    public String handle(HttpServletRequest request) throws Exception {
        //init xstream
        requestParser = getXStreamInstance();
        requestParser.processAnnotations(MessageSuit.class);
        plainParser = getXStreamInstance();
        plainParser.processAnnotations(Plain.class);
        //receive request
        byte[] requestBody = StreamUtil.readBytes(request.getInputStream());
        String requestString = new String(requestBody, ENCODING);
        log.info("Receive the request: {}", requestString);
        //convert response by transId
        String respStr = convertResponse(requestString);
        log.info("The response is {}", respStr);
        return respStr;
    }

    public String handleb2e004001(HttpServletRequest request) throws Exception {
        //init xstream
        transactionParser = getXStreamInstance();
        transactionParser.processAnnotations(Transaction.class);
        //receive request
        byte[] requestBody = StreamUtil.readBytes(request.getInputStream());
        String requestString = new String(requestBody, ENCODING);
        log.info("Receive the request: {}", requestString);
        String respStr = processb2e004001(requestString);
        log.info("The response is {}", respStr);
        return respStr;
    }

    public String handleb2e004003(HttpServletRequest request) throws Exception {
        //init xstream
        transactionParser = getXStreamInstance();
        transactionParser.processAnnotations(Transaction.class);
        //receive request
        byte[] requestBody = StreamUtil.readBytes(request.getInputStream());
        String requestString = new String(requestBody, ENCODING);
        log.info("Receive the request: {}", requestString);
        String respStr = processb2e004003(requestString);
        log.info("The response is {}", respStr);
        return respStr;
    }


    private String convertResponse(String requestString) {
        Matcher matcher = PATTERN_REPLACEMENT.matcher(requestString);
        if (!matcher.find()) {
            throw new UnsupportedOperationException("The transId cannot be found in request xml!");
        }
        String transId = matcher.group(0);
        switch (transId) {
            case "OPReq":
                return processOPReq(requestString);
            case "STQReq":
                return processSTQReq(requestString);


        }

        return null;

    }


    /**
     * 光大银行单笔支付(申购)
     *
     * @param requestString
     * @return
     */

    private String processOPReq(String requestString) {
        log.info("============ 光大银行申购(OPReq)开始 ============");
        MessageSuit messageSuit = (MessageSuit) requestParser.fromXML(requestString);

        BankTran bankTran = bankTranService.findBankTranByBnkCoAndTranCo(BankEnum.CEB.getBnkCo(), "pay");
        String originSerialNo = messageSuit.getMessage().getPlain().getSerialNo();
        String retCode = "";
        String retMsg = "";
        if (StringUtils.isNotEmpty(bankTran.getRespCo())) {
            retCode = bankTran.getRespCo();
            retMsg = bankTran.getRespMsg();
            log.info("强制改变申购响应吗为: {} - {}", retCode, retMsg);
        } else {
            log.info("不强制改变响应吗, 正常申购...");
        }

        BankCommand tran = bankCommandService.findBankCommandByMerSerialNo(originSerialNo);
        if (tran != null) {
            log.info("重复交易,流水号为: {}", tran);
        } else {
            log.info("交易不重复,生成交易并入库...");
            BankCommand transaction = CebUtil.buildTransaction(messageSuit, bankTran, DateUtils.getCurrentFullDateTime(), retCode);
            bankCommandService.saveBankCommand(transaction);
        }

        Plain plain = new Plain();
        if (!"".equals(retCode)) {
            //失败场景
            plain.setTransId("Error");
            plain.setMerId(messageSuit.getMessage().getPlain().getMerId());
            plain.setErrorCode(retCode);
            plain.setErrorMessage(retMsg);
            plain.setErrorDetail(retMsg);
        } else {
            //成功场景
            plain.setId("OPRes");
            plain.setTransId("OPRes");
            plain.setSerialNo(originSerialNo);
            plain.setSignNo(RandomStringUtils.randomNumeric(20));
            plain.setAmount(messageSuit.getMessage().getPlain().getAmount());
            plain.setCleatDate(DateUtils.getCurrentDate());
        }

        String plainString = plainParser.toXML(plain);

        String respStr = "<MessageSuit>\n" +
                "    <Message id=\"" + originSerialNo + "\">"
                + plainString
                + "<ds:Signature\n" +
                "            xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
                "            <ds:SignedInfo>\n" +
                "                <ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"></ds:CanonicalizationMethod>\n" +
                "                <ds:SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"></ds:SignatureMethod>\n" +
                "                <ds:Reference URI=\"#OPRes\">\n" +
                "                    <ds:Transforms>\n" +
                "                        <ds:Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"></ds:Transform>\n" +
                "                    </ds:Transforms>\n" +
                "                    <ds:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"></ds:DigestMethod>\n" +
                "                    <ds:DigestValue>6CB0wBsaA3YSSgj4mvpL47s4POk=</ds:DigestValue>\n" +
                "                </ds:Reference>\n" +
                "            </ds:SignedInfo>\n" +
                "            <ds:SignatureValue>\n" +
                "\t\t\t\tasHHwddor9OOdzgUV+qBiT7n9FrGQrUN7Af5L7mFonXnFUlHhRAnE4xMJ9EAaWdiu1rTsFjbgirS\n" +
                "+RJ8TFDwwGbNgqs894edXWBxdC8Go8b5S05wvEXO0LXHuZljxjdcXkTE3Wzc+xNfevGCr8V19WEj\n" +
                "ruPcsxIRq8ktnROmiyE=\n" +
                "\t\t\t</ds:SignatureValue>\n" +
                "        </ds:Signature>\n" +
                "    </Message>\n" +
                "</MessageSuit>";

        log.info("============ 光大银行申购(OPReq)结束 ============");
        return XML_DECLARATION + respStr;

    }


    private String processSTQReq(String requestString) {
        log.info("============ 光大银行单笔查询(STQReq)开始 ============");
        MessageSuit messageSuit = (MessageSuit) requestParser.fromXML(requestString);
        BankCommand transaction = bankCommandService.findBankCommandByMerSerialNo(messageSuit.getMessage().getPlain().getSerialNo());
        String retCode = "0001";
        if (transaction == null) {
            retCode = "0003";
        } else {
            retCode = transaction.getRespCo();
            if (!"".equals(retCode)) {
                retCode = "0000";
            } else {
                retCode = "0001";
            }
        }


        String originSerialNo = messageSuit.getMessage().getPlain().getSerialNo();
        Plain plain = new Plain();
        plain.setId("STQRes");
        plain.setTransId("STQRes");
        plain.setType("1");
        plain.setSerialNo(originSerialNo);
        plain.setDate(DateUtils.getCurrentFullDateTime());
        plain.setSignNo(RandomStringUtils.randomNumeric(20));
        plain.setAmount(messageSuit.getMessage().getPlain().getAmount());
        plain.setCharge("0");
        plain.setCurrency("156");
        plain.setOriginalSerialNo(originSerialNo);
        plain.setOriginalDate(transaction.getWorkDay() + " 00:00:00");
        plain.setStatus(retCode);
        plain.setCleatDate(DateUtils.getCurrentDate());

        String plainString = plainParser.toXML(plain);

        String respStr = "<MessageSuit>\n" +
                "    <Message id=\"" + originSerialNo + "\">"
                + plainString
                + "<ds:Signature\n" +
                "            xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
                "            <ds:SignedInfo>\n" +
                "                <ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"></ds:CanonicalizationMethod>\n" +
                "                <ds:SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"></ds:SignatureMethod>\n" +
                "                <ds:Reference URI=\"#STQRes\">\n" +
                "                    <ds:Transforms>\n" +
                "                        <ds:Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"></ds:Transform>\n" +
                "                    </ds:Transforms>\n" +
                "                    <ds:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"></ds:DigestMethod>\n" +
                "                    <ds:DigestValue>6CB0wBsaA3YSSgj4mvpL47s4POk=</ds:DigestValue>\n" +
                "                </ds:Reference>\n" +
                "            </ds:SignedInfo>\n" +
                "            <ds:SignatureValue>\n" +
                "\t\t\t\tasHHwddor9OOdzgUV+qBiT7n9FrGQrUN7Af5L7mFonXnFUlHhRAnE4xMJ9EAaWdiu1rTsFjbgirS\n" +
                "+RJ8TFDwwGbNgqs894edXWBxdC8Go8b5S05wvEXO0LXHuZljxjdcXkTE3Wzc+xNfevGCr8V19WEj\n" +
                "ruPcsxIRq8ktnROmiyE=\n" +
                "\t\t\t</ds:SignatureValue>\n" +
                "        </ds:Signature>\n" +
                "    </Message>\n" +
                "</MessageSuit>";

        log.info("============ 光大银行单笔查询(STQRes)结束 ============");
        return XML_DECLARATION + respStr;


    }


    public String processb2e004001(String requestString) {
        log.info("============ 光大银企赎回(b2e004001)开始 ============");
        Transaction request = (Transaction) transactionParser.fromXML(requestString);

        BankTran config = bankTranService.findBankTranByBnkCoAndTranCo(BankEnum.CEB.getBnkCo(), "redeem");
        String batchId = request.getTransHead().getBatchID();
        String retCode = "0000";
        String retMsg = "本次交易成功！";
        if (StringUtils.isNotEmpty(config.getRespCo())) {
            retCode = config.getRespCo();
            if ("00".equals(retCode)) {
                retCode = "0000";
            }
            retMsg = config.getRespMsg();
            log.info("强制改变赎回响应码为: {} - {}", retCode, retMsg);
        } else {
            log.info("不强制改变响应码, 正常赎回...");
        }
        BankCommand tran = bankCommandService.findBankCommandByMerSerialNo(batchId);
        if (tran != null) {
            log.info("重复交易, 流水号为: {}", tran);
        } else {
            log.info("交易不重复,生成交易并入库");
            BankCommand transaction = CebUtil.buildredeemTransaction(request, config, DateUtils.getCurrentFullDateTime(), retCode);
            bankCommandService.saveBankCommand(transaction);
        }

        SystemHead systemHead = new SystemHead();
        systemHead.setLanguage(request.getSystemHead().getLanguage());
        systemHead.setCifNo(request.getSystemHead().getCifNo());
        systemHead.setUserID(request.getSystemHead().getUserID());

        TransHead transHead = new TransHead();
        transHead.setTransCode("b2e004001");
        transHead.setBatchID(request.getTransHead().getBatchID());
        transHead.setJnlDate(request.getTransHead().getJnlDate());
        transHead.setJnlTime(request.getTransHead().getJnlTime());

        TransContent.ReqData respData = new TransContent.ReqData();
        respData.setAccountNo(request.getTransContent().getReqData().getAccountNo());
        respData.setTransferType(request.getTransContent().getReqData().getTransferType());
        respData.setToAccountName(request.getTransContent().getReqData().getToAccountName());
        respData.setToAccountNo(request.getTransContent().getReqData().getToAccountNo());
        respData.setToBank(request.getTransContent().getReqData().getToBank());
        respData.setToLocation(request.getTransContent().getReqData().getToLocation());
        respData.setAmount(request.getTransContent().getReqData().getAmount());
        respData.setCheckNo(request.getTransContent().getReqData().getCheckNo());
        respData.setCheckPassword(request.getTransContent().getReqData().getCheckPassword());
        respData.setIsUrgent(request.getTransContent().getReqData().getIsUrgent());
        respData.setPerOrEnt(request.getTransContent().getReqData().getPerOrEnt());
        respData.setTransDateTime("2016-09-09T00:00:00");
        respData.setClientPatchID(batchId);
        respData.setIsAudit(request.getTransContent().getReqData().getIsAudit());
        respData.setMatchRule(request.getTransContent().getReqData().getMatchRule());

        TransContent transContent = new TransContent();
        transContent.setReturnCode(retCode);
        transContent.setReturnMsg(retMsg);
        transContent.setRespData(respData);

        Transaction respTransaction = new Transaction();
        respTransaction.setSystemHead(systemHead);
        respTransaction.setTransHead(transHead);
        respTransaction.setTransContent(transContent);

        String respStr = transactionParser.toXML(respTransaction);

        return XML_DECLARATION + respStr;
    }


    public String processb2e004003(String requestString) {
        log.info("============ 光大银企单笔查询(b2e004003)开始 ============");
        Transaction request = (Transaction) transactionParser.fromXML(requestString);

        BankCommand transaction = bankCommandService.findBankCommandByMerSerialNo(request.getTransContent().getReqData().getClientBchID());
        String retCode = "00";
        if (transaction == null) {
            retCode = "02";
        } else {
            retCode = transaction.getRespCo();
        }

        if ("0000".equals(retCode)) {
            retCode = "00";
        }

        SystemHead systemHead = new SystemHead();
        systemHead.setLanguage(request.getSystemHead().getLanguage());
        systemHead.setCifNo(request.getSystemHead().getCifNo());
        systemHead.setUserID(request.getSystemHead().getUserID());

        TransHead transHead = new TransHead();
        transHead.setTransCode("b2e004003");
        transHead.setBatchID(request.getTransHead().getBatchID());
        transHead.setJnlDate(request.getTransHead().getJnlDate());
        transHead.setJnlTime(request.getTransHead().getJnlTime());

        TransContent.ReqData respData = new TransContent.ReqData();
        respData.setClientPatchID(request.getTransContent().getReqData().getClientPatchID());
        respData.setNowFlag("00");
        respData.setTranferFlag(retCode);
        respData.setTransferType("2120");
        respData.setAccountNo(transaction.getRcvrAccoNo());
        respData.setToAccountName(transaction.getRcvrAccoNo());
        respData.setToAccountNo(transaction.getRcvrAccoNo());
        respData.setAmount(transaction.getAmount());
        respData.setIsUrgent("0");
        respData.setPerOrEnt("0");
        respData.setIsAudit("1");
        respData.setMatchRule("0");

        TransContent transContent = new TransContent();
        transContent.setRespData(respData);

        Transaction respTransaction = new Transaction();
        respTransaction.setSystemHead(systemHead);
        respTransaction.setTransHead(transHead);
        respTransaction.setTransContent(transContent);

        String respStr = transactionParser.toXML(respTransaction);

        return XML_DECLARATION + respStr;
    }

    private XStream getXStreamInstance() {
        return new XStream(new DomDriver(ENCODING, new XmlFriendlyNameCoder("_-", "_")));
    }
}
