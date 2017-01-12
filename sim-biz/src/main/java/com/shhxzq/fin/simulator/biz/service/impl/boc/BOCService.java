package com.shhxzq.fin.simulator.biz.service.impl.boc;

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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by houjiagang on 16/8/3.
 */
@Component
@Log4j2
public class BOCService {

    private static final String ENCODING = "UTF-8";

    private static final String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";

    private XStream requestParser;

    private XStream responseParser;

    @Autowired
    private BankCommandService bankCommandService;

    @Autowired
    private BankTranService bankTranService;

    public String handle(HttpServletRequest request) throws Exception {
        //init xstream
        requestParser = getXStreamInstance();
        requestParser.processAnnotations(Request.class);
        responseParser = getXStreamInstance();
        responseParser.processAnnotations(Response.class);
        //receive request&messageId
        String messageId = request.getParameter("messageId");
        messageId = Base64Util.base64Decode(messageId, "UTF-8");
        String reqStr = request.getParameter("signature");
        log.info("The messageId is {}", messageId);
        log.info("Receive the request is {}", reqStr);
        //convert response by messageId
        String respStr = convertResponse(messageId, reqStr);
        log.info("Response is {}", respStr);
        return respStr;
    }

    private String convertResponse(String messageID, String reqString){

        switch (messageID) {
            case "215101":
                return processPreSign(reqString);
            case "215102":
                return processSign(reqString);
            case "215105":
                return processPay(reqString);
            case "215108":
                return processPayQuery(reqString);
        }
        return null;

    }

    private String processPayQuery(String reqString){
        log.info("============ 中行快捷订单支付查询(215108)开始 ============");
        Request request = (Request) requestParser.fromXML(reqString);
        BankCommand transaction = bankCommandService.findBankCommandByMerSerialNo(request.getBody().getTraceNo());
        String retCode = "OK";
        if (transaction == null) {
            retCode = "E15000047";
            transaction = new BankCommand();
            transaction.setAmount("0");
            transaction.setTranSt("F");
            transaction.setRespMsg("");
        } else {
            retCode = transaction.getRespCo();
        }

        Head head = new Head();
        if ("E15000047".equals(retCode)) {
            head.setResponseCode(retCode);
            head.setResponseInfo(transaction.getRespMsg());
        } else {
            head.setResponseCode("OK");
            head.setResponseInfo("");
        }

        Body body = new Body();
        body.setMerchantNo("80000210001010");
        body.setTraceNo(request.getBody().getTraceNo());
        body.setCardNo(request.getBody().getCardNo());
        body.setTranCode(request.getBody().getTranCode());
        body.setCurrency("156");
        body.setAmount(transaction.getAmount());
        body.setTranStatus("1");
        if(!"OK".equals(retCode)){
            body.setTranStatus("2");
        }
        body.setRecvTime(DateUtils.getCurrentDateTime());
        body.setTranTime(DateUtils.getCurrentDateTime());

        Response response = new Response();
        response.setHead(head);
        if(!"E15000047".equals(retCode)) {
            response.setBody(body);
        }
        String respStr =  XML_DECLARATION + responseParser.toXML(response);
        log.info("============ 中行快捷订单支付查询(215108)结束 ============");
        return respStr;

    }

    private String processPay(String reqString){
        log.info("============ 中行快捷订单支付(215105)开始 ============");
        Request request = (Request) requestParser.fromXML(reqString);

        BankTran bankTran = bankTranService.findBankTranByBnkCoAndTranCo(BankEnum.BOC.getBnkCo(), "pay");
        String retCode = "OK";
        String retMsg = "成功";
        if(StringUtils.isNotEmpty(bankTran.getRespCo())) {
            retCode = bankTran.getRespCo();
            retMsg = bankTran.getRespMsg();
            log.info("强制改变申购响应码为: {} - {}", retCode, retMsg);
        } else {
            log.info("不强制改变响应码, 正常申购...");
        }
//        Config configStat = configService.findBankSelectConfig(bank.getCode(), "payStat");
//        String tranStat = "1";
//        if(configStat != null) {
//            tranStat = configStat.getK();
//            log.info("强制改变申购状态码为: {}", tranStat);
//        } else {
//            log.info("不强制改变申购状态码, 正常申购...");
//        }

        BankCommand tran = bankCommandService.findBankCommandByMerSerialNo(request.getBody().getTraceNo());
        if(tran != null) {
            log.info("重复交易,流水号为: {}", tran);
            retCode = "E15000002";
            retMsg = "商户交易流水号重复";
        } else {
            log.info("交易不重复,生成交易并入库...");
            BankCommand transaction = BocUtil.buildTransaction(request, bankTran, BankCommandHelp.genSerialNo(), retCode);
            bankCommandService.saveBankCommand(transaction);
        }

        //拼装response
        Head head = new Head();
        head.setResponseCode(retCode);
        head.setResponseInfo(retMsg);
        head.setRequestSeq(request.getHead().getRequestSeq());

        Body body = new Body();
        body.setMerchantNo("80000210001010");
        body.setTraceNo(request.getBody().getTraceNo());
        body.setCardNo(request.getBody().getCardNo());
        body.setCurrency("156");
        body.setAmount(request.getBody().getAmount());
        body.setRecvTime(DateUtils.getCurrentDateTime());
        body.setTranTime(DateUtils.getCurrentDateTime());

        Response response = new Response();
        response.setHead(head);
        if ("OK".equals(retCode)) {
            response.setBody(body);
        }

        String respStr = XML_DECLARATION + responseParser.toXML(response);
        log.info("============ 中行快捷订单支付(215105)结束 ============");
        return respStr;

    }

    private String processSign(String reqString){
        log.info("============ 中行快捷支付签约(215102)开始 ============");
        Request request = (Request) requestParser.fromXML(reqString);

        BankTran bankTran = bankTranService.findBankTranByBnkCoAndTranCo(BankEnum.BOC.getBnkCo(), "verify");
        String retCode = "OK";
        String retMsg = "成功";
        if(StringUtils.isNotEmpty(bankTran.getRespCo())) {
            retCode = bankTran.getRespCo();
            retMsg = bankTran.getRespMsg();
            log.info("强制改变鉴权响应码为: {} - {}", retCode, retMsg);
        } else {
            log.info("不强制改变响应码, 正常鉴权...");
        }
        //拼装response
        Head head = new Head();
        head.setResponseCode(retCode);
        head.setResponseInfo(retMsg);
        head.setRequestSeq(request.getHead().getRequestSeq());

        Response response = new Response();
        response.setHead(head);

        String respStr = XML_DECLARATION + responseParser.toXML(response);
        log.info("============ 中行快捷支付签约(215102)结束 ============");
        return respStr;
    }

    private String processPreSign(String reqString){
        log.info("============ 中行快捷预签约(215101)开始 ============");
        Request request = (Request) requestParser.fromXML(reqString);
        BankTran bankTran = bankTranService.findBankTranByBnkCoAndTranCo(BankEnum.BOC.getBnkCo(), "sendsms");
        String retCode = "OK";
        String retMsg = "成功";
        if(StringUtils.isNotEmpty(bankTran.getRespCo())) {
            retCode = bankTran.getRespCo();
            retMsg = bankTran.getRespMsg();
            log.info("强制改变短信响应码为: {} - {}", retCode, retMsg);
        } else {
            log.info("不强制改变响应码, 正常发短信...");
        }
        //拼装response
        Head head = new Head();
        head.setResponseCode(retCode);
        head.setResponseInfo(retMsg);
        head.setRequestSeq(request.getHead().getRequestSeq());

        Body body = new Body();
        body.setUniqueCode("888");

        Response response = new Response();
        response.setHead(head);
        if ("OK".equals(retCode)) {
            response.setBody(body);
        }

        String respStr = XML_DECLARATION + responseParser.toXML(response);
        log.info("============ 中行快捷预签约(215101)结束 ============");
        return respStr;
    }




    private XStream getXStreamInstance() {
        return new XStream(new DomDriver(ENCODING, new XmlFriendlyNameCoder("_-", "_")));
    }
}
