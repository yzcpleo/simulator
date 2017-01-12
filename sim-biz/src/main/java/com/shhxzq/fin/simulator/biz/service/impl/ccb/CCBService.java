package com.shhxzq.fin.simulator.biz.service.impl.ccb;

import com.shhxzq.fin.simulator.biz.service.BankCommandService;
import com.shhxzq.fin.simulator.biz.service.BankTranService;
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
 * Created by houjiagang on 16/7/25.
 */

@Service
@Log4j2
public class CCBService {

    private static final String ENCODING = "UTF-8";
    private static final Pattern PATTERN_REPLACEMENT;
    private static final String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

    private XStream tranParser;

    @Autowired
    private BankCommandService bankCommandService;

    @Autowired
    private BankTranService bankTranService;

    static {
        PATTERN_REPLACEMENT = Pattern.compile("(?<=<txcode>)(.*?)(?=</txcode>)");
    }

    public String handle(HttpServletRequest request) throws Exception {
        //init xstream
        tranParser = getXStreamInstance();
        tranParser.processAnnotations(Tran.class);
        //receive request
        String reqStr = request.getParameter("xml");
        log.info("Receive the request is {}", reqStr);
        //convert response by txcode
        String respStr = convertResponse(reqStr).replace("&lt;", "<").replace("&gt;", ">");
        log.info("Response is {}", respStr);
        return respStr;
    }

    private String convertResponse(String reqString) {
        Matcher matcher = PATTERN_REPLACEMENT.matcher(reqString);
        if (!matcher.find()) {
            throw new UnsupportedOperationException("The txcode cannot be found in request xml!");
        }
        String txCode = matcher.group(0);
        switch (txCode) {
            case "AL0001":
                return processAL0001(reqString);
            case "AL0002":
                return processAL0002(reqString);
            case "AL0003":
                return processAL0003(reqString);
            case "AL0005":
                return processAL0005(reqString);
        }
        return null;
    }

    private String processAL0003(String reqString) {
        log.info("============ 建行快捷充值查询(AL0003)开始 ============");
        Tran request = (Tran) tranParser.fromXML(reqString);
        BankCommand transaction = bankCommandService.findBankCommandByMerSerialNo(request.getBody().getOrderNo());
        String retCode = "";
        String retMsg = "SUCCESS";
        if (transaction == null) {
            retCode = "YDCP1KJZ0010";
            retMsg = "原支付流水不存在";
            transaction = new BankCommand();
            transaction.setWorkDay(DateUtils.getCurrentDate());
            transaction.setAmount("0");
            transaction.setTranSt("F");
        } else {
            retCode = transaction.getRespCo();
        }

        //拼装返回Tran
        Header.TranResponse tranResponse = new Header.TranResponse();
        if ("YDCP1KJZ0010".equals(retCode)) {
            tranResponse.setStatus("<![CDATA[FAIL]]>");
            tranResponse.setCode("<![CDATA[" + retCode + "]]>");
            tranResponse.setDesc("<![CDATA[" + retMsg + "]]>");
        } else {
            tranResponse.setStatus("<![CDATA[SUCCESS]]>");

        }

        Header header = new Header();
        header.setTxcode("<![CDATA[" + request.getHeader().getTxcode() + "]]>");
        header.setTxseq("<![CDATA[" + request.getHeader().getTxseq() + "]]>");
        header.setTxdate("<![CDATA[" + request.getHeader().getTxdate() + "]]>");
        header.setTxsign("<![CDATA[" + request.getHeader().getTxsign() + "]]>");
        header.setTranResponse(tranResponse);

        Body.Record record = new Body.Record();
        record.setPType("G"); //attribute
        if ("F".equals(transaction.getTranSt())) {
            record.setTranStatus("<![CDATA[0]]>");
        } else {
            record.setTranStatus("<![CDATA[1]]>");
        }
        record.setBackTradDate("<![CDATA[" + transaction.getWorkDay() + "]]>");
        record.setCurrCod("<![CDATA[01]]>");
        record.setAmount("<![CDATA[" + transaction.getAmount() + "]]>");
        record.setOriTxseq("<![CDATA[" + request.getBody().getOrderNo() + "]]>");


        Body.Response response = new Body.Response();
        response.setShopNo("<![CDATA[" + request.getBody().getShopNo() + "]]>");
        response.setOrderNo("<![CDATA[" + request.getBody().getOrderNo() + "]]>");
        response.setRecCounts("<![CDATA[1]]>");
        response.setRecord(record);

        Body body = new Body();
        body.setResponse(response);

        Tran respTran = new Tran();
        respTran.setHeader(header);
        respTran.setBody(body);

        String respStr = XML_DECLARATION + tranParser.toXML(respTran);
        return respStr;
    }


    private String processAL0002(String reqString) {
        log.info("============ 建行快捷充值(AL0002)开始 ============");
        Tran request = (Tran) tranParser.fromXML(reqString);

        BankTran bankTran = bankTranService.findBankTranByBnkCoAndTranCo(BankEnum.CCB.getBnkCo(), "pay");
        String retCode = "";
        String retMsg = "SUCCESS";
        if (StringUtils.isNotEmpty(bankTran.getRespCo())) {
            retCode = bankTran.getRespCo();
            retMsg = bankTran.getRespMsg();
            log.info("强制改变充值响应码为: {} - {}", retCode, retMsg);
        } else {
            log.info("不强制改变响应码, 正常充值...");
        }


        BankCommand tran = bankCommandService.findBankCommandByMerSerialNo(request.getHeader().getTxseq());
        if (tran != null) {
            log.info("重复交易,流水号为: {}", tran);
            retCode = "0";
        } else {
            log.info("交易不重复,生成交易并入库...");
            BankCommand transaction = CcbUtil.buildTransaction(request, bankTran, DateUtils.getCurrentFullDateTime(), retCode);
            bankCommandService.saveBankCommand(transaction);
        }

        //拼装返回Tran
        Header.TranResponse tranResponse = new Header.TranResponse();
        if (!"".equals(retCode)) {
            tranResponse.setStatus("<![CDATA[FAIL]]>");
            tranResponse.setCode("<![CDATA[" + retCode + "]]>");
            tranResponse.setDesc("<![CDATA[" + retMsg + "]]>");
        } else {
            tranResponse.setStatus("<![CDATA[SUCCESS]]>");

        }

        Header header = new Header();
        header.setTxcode("<![CDATA[" + request.getHeader().getTxcode() + "]]>");
        header.setTxseq("<![CDATA[" + request.getHeader().getTxseq() + "]]>");
        header.setTxdate("<![CDATA[" + request.getHeader().getTxdate() + "]]>");
        header.setTxsign("<![CDATA[" + request.getHeader().getTxsign() + "]]>");
        header.setTranResponse(tranResponse);

        Body.Response response = new Body.Response();
        response.setTxDt("<![CDATA[" + request.getHeader().getTxdate() + "]]>");
        response.setBankFlow("<![CDATA[" + RandomStringUtils.randomNumeric(27) + "]]>");
        response.setCurrCod("<![CDATA[" + request.getBody().getCurrCod() + "]]>");
        response.setAmount("<![CDATA[" + request.getBody().getAmount() + "]]>");
        response.setShopNo("<![CDATA[" + request.getBody().getShopNo() + "]]>");
        response.setOrderNo("<![CDATA[" + request.getBody().getOrderNo() + "]]>");

        Body body = new Body();
        body.setResponse(response);

        Tran respTran = new Tran();
        respTran.setHeader(header);
        respTran.setBody(body);

        String respStr = XML_DECLARATION + tranParser.toXML(respTran);
        return respStr;
    }

    private String processAL0005(String reqString) {
        log.info("============ 建行快捷鉴权(AL0005)开始 ============");
        Tran request = (Tran) tranParser.fromXML(reqString);

        BankTran bankTran = bankTranService.findBankTranByBnkCoAndTranCo(BankEnum.CCB.getBnkCo(), "verify");
        String retCode = "";
        String retMsg = "SUCCESS";
        if (StringUtils.isNotEmpty(bankTran.getRespCo())) {
            retCode = bankTran.getRespCo();
            retMsg = bankTran.getRespMsg();
            log.info("强制改变鉴权响应码为: {} - {}", retCode, retMsg);
        } else {
            log.info("不强制改变响应码, 正常鉴权...");
        }
        //拼装返回Tran
        Header.TranResponse tranResponse = new Header.TranResponse();
        if (!"".equals(retCode)) {
            tranResponse.setStatus("<![CDATA[FAIL]]>");
            tranResponse.setCode("<![CDATA[" + retCode + "]]>");
            tranResponse.setDesc("<![CDATA[" + retMsg + "]]>");
        } else {
            tranResponse.setStatus("<![CDATA[SUCCESS]]>");

        }

        Header header = new Header();
        header.setTxcode("<![CDATA[" + request.getHeader().getTxcode() + "]]>");
        header.setTxseq("<![CDATA[" + request.getHeader().getTxseq() + "]]>");
        header.setTxdate("<![CDATA[" + request.getHeader().getTxdate() + "]]>");
        header.setTxsign("<![CDATA[" + request.getHeader().getTxsign() + "]]>");
        header.setTranResponse(tranResponse);

        Body.Response response = new Body.Response();

        Body body = new Body();
        body.setResponse(response);

        Tran tran = new Tran();
        tran.setHeader(header);
        tran.setBody(body);

        String respStr = XML_DECLARATION + tranParser.toXML(tran);
        return respStr;
    }

    private String processAL0001(String reqString) {
        log.info("============ 建行快捷发短信(AL0001)开始 ============");
        Tran request = (Tran) tranParser.fromXML(reqString);

        BankTran bankTran = bankTranService.findBankTranByBnkCoAndTranCo(BankEnum.CCB.getBnkCo(), "sendsms");
        String retCode = "";
        String retMsg = "SUCCESS";
        if (StringUtils.isNotEmpty(bankTran.getRespCo())) {
            retCode = bankTran.getRespCo();
            retMsg = bankTran.getRespMsg();
            log.info("强制改变短信响应码为: {} - {}", retCode, retMsg);
        } else {
            log.info("不强制改变响应码, 正常发短信...");
        }
        //拼装返回Tran
        Header.TranResponse tranResponse = new Header.TranResponse();
        if (!"".equals(retCode)) {
            tranResponse.setStatus("<![CDATA[FAIL]]>");
            tranResponse.setCode("<![CDATA[" + retCode + "]]>");
            tranResponse.setDesc("<![CDATA[" + retMsg + "]]>");
        } else {
            tranResponse.setStatus("<![CDATA[SUCCESS]]>");

        }


        Header header = new Header();
        header.setTxcode("<![CDATA[" + request.getHeader().getTxcode() + "]]>");
        header.setTxseq("<![CDATA[" + request.getHeader().getTxseq() + "]]>");
        header.setTxdate("<![CDATA[" + request.getHeader().getTxdate() + "]]>");
        header.setTxsign("<![CDATA[" + request.getHeader().getTxsign() + "]]>");
        header.setTranResponse(tranResponse);

        Body.Response response = new Body.Response();
        response.setValidFlag("<![CDATA[" + "1" + "]]>");

        Body body = new Body();
        body.setResponse(response);

        Tran tran = new Tran();
        tran.setHeader(header);
        tran.setBody(body);

        String respStr = XML_DECLARATION + tranParser.toXML(tran);
        return respStr;
    }


    private XStream getXStreamInstance() {
        return new XStream(new DomDriver(ENCODING, new XmlFriendlyNameCoder("_-", "_")));
    }

}
