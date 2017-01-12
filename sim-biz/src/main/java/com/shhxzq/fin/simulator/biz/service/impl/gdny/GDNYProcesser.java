package com.shhxzq.fin.simulator.biz.service.impl.gdny;

import com.shhxzq.fin.simulator.biz.service.BankCommandService;
import com.shhxzq.fin.simulator.biz.service.BankTranService;
import com.shhxzq.fin.simulator.biz.util.BankCommandHelp;
import com.shhxzq.fin.simulator.model.constants.BankEnum;
import com.shhxzq.fin.simulator.model.vo.BankCommand;
import com.shhxzq.fin.simulator.model.vo.BankTran;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by houjiagang on 16/7/12.
 */
@Log4j2
@Component
public class GDNYProcesser {

    private static final String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"GB2312\"?>";
    private final static String AUTHENTICATION = "authentication";
    private final static String CONSUME = "consume";
    private final static String SPAYMENT = "spayment";
    private final static String ORDERQUERY = "orderquery";

    @Autowired
    private BankTranService bankTranService;

    @Autowired
    private BankCommandService bankCommandService;

    public String handleMessage(String message) throws Exception {
        String[] headParams = {"SEQ_NO", "TRANS_TIME", "TRANS_CODE", "CHANNEL"};
        String[] bodyParams = {"CARD_NO", "TX_AMT", "TRANS_TYPE", "OLD_SEQ_NO", "ACCT_NO"};
        HashMap<String, String> map = analyseXml(message, headParams, bodyParams);
        String transCode = map.get("TRANS_CODE");
        if (transCode.equals(AUTHENTICATION)){
            return handleVerify(map);
        } else if(transCode.equals(CONSUME)){
            return handlePay(map);
        } else if(transCode.equals(SPAYMENT)){
            return handleRedeem(map);
        } else if(transCode.equals(ORDERQUERY)){
            return handleQuery(map);
        }

        return null;
    }


    /**
     * 查询
     *
     *
     *
     */
    private String handleQuery(HashMap<String, String> map){
        log.info("\"============ 广东南粤查询开始 ============\"");
        BankCommand transaction = bankCommandService.findBankCommandByMerSerialNo(map.get("SEQ_NO"));
        log.info("查询的交易为: {}", transaction);
        String retCode = "00";
        String retMsg = "交易成功";
        if (transaction == null) {
            retCode = "0003";
            retMsg = "找不到原交易";
        } else {
            retCode = transaction.getRespCo();
            retMsg = transaction.getRespMsg();
        }

        StringBuilder msg = new StringBuilder();
        msg.append(RandomStringUtils.randomNumeric(8));
        msg.append(XML_DECLARATION);
        msg.append("<NYYH>");
        msg.append("<HEAD>");
        msg.append("<SEQ_NO>").append(map.get("SEQ_NO")).append("</SEQ_NO>");
        msg.append("<TRANS_TIME>").append(map.get("TRANS_TIME")).append("</TRANS_TIME>");
        msg.append("<TRANS_CODE>").append(map.get("TRANS_CODE")).append("</TRANS_CODE>");
        msg.append("<CHANNEL>").append(map.get("CHANNEL")).append("</CHANNEL>");
        msg.append("<BANK_SEQ_NO>").append(RandomStringUtils.randomNumeric(16)).append("</BANK_SEQ_NO>");
        msg.append("<ORDER_NO/>");
        msg.append("<RET_CODE>").append("00").append("</RET_CODE>");
        msg.append("<RET_MSG>").append("交易成功").append("</RET_MSG>");
        msg.append("</HEAD>");
        msg.append("<BODY>");
        msg.append("<TRANS_TYPE>").append(map.get("TRANS_TYPE")).append("</TRANS_TYPE>");
        msg.append("<OLD_SEQ_NO>").append(map.get("OLD_SEQ_NO")).append("</OLD_SEQ_NO>");
        if ("0003".equals(retCode)){
            msg.append("<OLD_BANK_SEQ_NO/>");
            msg.append("<ORDER_DATE>").append(map.get("TRANS_TIME").substring(0,8)).append("</ORDER_DATE>");
            msg.append("<CARD_NO/>");
            msg.append("<PAYEE_ACCT_NAME/>");
            msg.append("<PAYEE_ACCT_NO/>");
            msg.append("<OLD_RET_CODE>").append(retCode).append("</OLD_RET_CODE>");
            msg.append("<OLD_RET_MSG/>");
            msg.append("<TX_AMT/>");
        } else {
            msg.append("<OLD_BANK_SEQ_NO>").append(map.get("SEQ_NO")).append("</OLD_BANK_SEQ_NO>");
            msg.append("<ORDER_DATE>").append(map.get("TRANS_TIME").substring(0,8)).append("</ORDER_DATE>");
            msg.append("<CARD_NO>").append(transaction.getRcvrAccoNo()).append("</CARD_NO>");
            msg.append("<PAYEE_ACCT_NAME>").append("mockserver").append("</PAYEE_ACCT_NAME>");
            msg.append("<PAYEE_ACCT_NO>").append("12345672234567").append("</PAYEE_ACCT_NO>");
            msg.append("<OLD_RET_CODE>").append(retCode).append("</OLD_RET_CODE>");
            msg.append("<OLD_RET_MSG>").append(retMsg).append("</OLD_RET_MSG>");
            msg.append("<TX_AMT>").append(transaction.getAmount()).append("</TX_AMT>");
        }
        msg.append("<RESERVE1/>");
        msg.append("<RESERVE2/>");
        msg.append("</BODY>");
        msg.append("</NYYH>");
        msg.append(RandomStringUtils.randomNumeric(16));
        return msg.toString();



    }


    /**
     *
     * 赎回(代付)
     *
     * @param map
     * @return
     */
    private String handleRedeem(HashMap<String, String> map){
        log.info("============ 广东南粤赎回开始 ============");

        BankTran bankTran = bankTranService.findBankTranByBnkCoAndTranCo(BankEnum.GDNY.getBnkCo(), "redeem");

        String retCode = "00";
        String retMsg = "交易成功";
        if (StringUtils.isNotEmpty(bankTran.getRespCo())){
            retCode = bankTran.getRespCo();
            retMsg = bankTran.getRespMsg();
            log.info("强制改变赎回响应吗为: {} - {}", retCode, retMsg);
        } else {
            log.info("不强制改变赎回响应吗,正常赎回...");
        }
        BankCommand tran = bankCommandService.findBankCommandByMerSerialNo(map.get("SEQ_NO"));
        if (tran != null){
            log.info("重复交易,流水号为: {}", tran);
            retCode = "07";
            retMsg = "重复的交易";
        } else {
            log.info("交易不重复,生成交易并入库...");
            BankCommand transaction = GDNYUtil.request2Transaction(map, bankTran, BankCommandHelp.genSerialNo(), retCode);
            log.info("交易内容为: {}", transaction);

            bankCommandService.saveBankCommand(transaction);
        }

        StringBuilder msg = new StringBuilder();
        msg.append(RandomStringUtils.randomNumeric(8));
        msg.append(XML_DECLARATION);
        msg.append("<NYYH>");
        msg.append("<HEAD>");
        msg.append("<SEQ_NO>").append(map.get("SEQ_NO")).append("</SEQ_NO>");
        msg.append("<TRANS_TIME>").append(map.get("TRANS_TIME")).append("</TRANS_TIME>");
        msg.append("<TRANS_CODE>").append(map.get("TRANS_CODE")).append("</TRANS_CODE>");
        msg.append("<CHANNEL>").append(map.get("CHANNEL")).append("</CHANNEL>");
        msg.append("<BANK_SEQ_NO>").append(RandomStringUtils.randomNumeric(16)).append("</BANK_SEQ_NO>");
        msg.append("<ORDER_NO/>");
        msg.append("<RET_CODE>").append(retCode).append("</RET_CODE>");
        msg.append("<RET_MSG>").append(retMsg).append("</RET_MSG>");
        msg.append("</HEAD>");
        msg.append("<BODY>");
        msg.append("<CLEAR_DATE>").append(map.get("TRANS_TIME").substring(0,8)).append("</CLEAR_DATE>");
        msg.append("<RESERVE1/>");
        msg.append("<RESERVE2/>");
        msg.append("</BODY>");
        msg.append("</NYYH>");
        msg.append(RandomStringUtils.randomNumeric(16));
        return msg.toString();

    }

    /**
     *
     * 申购(消费)
     *
     * @param map
     * @return
     */
    private String handlePay(HashMap<String, String> map){
        log.info("============ 广东南粤申购开始 ============");

        BankTran bankTran = bankTranService.findBankTranByBnkCoAndTranCo(BankEnum.GDNY.getBnkCo(), "pay");

        String retCode = "00";
        String retMsg = "交易成功";
        if (StringUtils.isNotEmpty(bankTran.getRespCo())){
            retCode = bankTran.getRespCo();
            retMsg = bankTran.getRespMsg();
            log.info("强制改变申购响应码为: {} - {}", retCode, retMsg);
        } else {
            log.info("不强制改变申购响应码,正常申购...");
        }

        BankCommand tran = bankCommandService.findBankCommandByMerSerialNo(map.get("SEQ_NO"));
        if (tran != null){
            log.info("重复交易,流水号为: {}", tran);
            retCode = "07";
            retMsg = "重复的交易";
        } else {
            log.info("交易不重复,生成交易并入库...");
            BankCommand transaction = GDNYUtil.request2Transaction(map, bankTran, BankCommandHelp.genSerialNo(), retCode);
            log.info("交易内容为: {}", transaction);

            bankCommandService.saveBankCommand(transaction);
        }

        StringBuilder msg = new StringBuilder();
        msg.append(RandomStringUtils.randomNumeric(8));
        msg.append(XML_DECLARATION);
        msg.append("<NYYH>");
        msg.append("<HEAD>");
        msg.append("<SEQ_NO>").append(map.get("SEQ_NO")).append("</SEQ_NO>");
        msg.append("<TRANS_TIME>").append(map.get("TRANS_TIME")).append("</TRANS_TIME>");
        msg.append("<TRANS_CODE>").append(map.get("TRANS_CODE")).append("</TRANS_CODE>");
        msg.append("<CHANNEL>").append(map.get("CHANNEL")).append("</CHANNEL>");
        msg.append("<BANK_SEQ_NO>").append(RandomStringUtils.randomNumeric(16)).append("</BANK_SEQ_NO>");
        msg.append("<ORDER_NO/>");
        msg.append("<RET_CODE>").append(retCode).append("</RET_CODE>");
        msg.append("<RET_MSG>").append(retMsg).append("</RET_MSG>");
        msg.append("</HEAD>");
        msg.append("<BODY>");
        msg.append("<CLEAR_DATE>").append(map.get("TRANS_TIME").substring(0,8)).append("</CLEAR_DATE>");
        msg.append("<RESERVE1/>");
        msg.append("<RESERVE2/>");
        msg.append("</BODY>");
        msg.append("</NYYH>");
        msg.append(RandomStringUtils.randomNumeric(16));
        return msg.toString();

    }


    /**
     *
     * 鉴权
     *
     * @param map
     * @return
     */
    private String handleVerify(Map map){
        log.info("============ 广东南粤鉴权开始 ============");

        BankTran bankTran = bankTranService.findBankTranByBnkCoAndTranCo(BankEnum.GDNY.getBnkCo(), "verify");

        String retCode = "00";
        String retMsg = "交易成功";
        if (StringUtils.isNotEmpty(bankTran.getRespCo())){
            retCode = bankTran.getRespCo();
            retMsg = bankTran.getRespMsg();
            log.info("强制改变鉴权响应码为: {} - {}", retCode, retMsg);
        } else {
            log.info("不强制改变鉴权响应码, 正常鉴权...");
        }


        StringBuilder msg = new StringBuilder();
        msg.append(RandomStringUtils.randomNumeric(8));
        msg.append(XML_DECLARATION);
        msg.append("<NYYH>");
        msg.append("<HEAD>");
        msg.append("<SEQ_NO>").append(map.get("SEQ_NO")).append("</SEQ_NO>");
        msg.append("<TRANS_TIME>").append(map.get("TRANS_TIME")).append("</TRANS_TIME>");
        msg.append("<TRANS_CODE>").append(map.get("TRANS_CODE")).append("</TRANS_CODE>");
        msg.append("<CHANNEL>").append(map.get("CHANNEL")).append("</CHANNEL>");
        msg.append("<BANK_SEQ_NO>").append(RandomStringUtils.randomNumeric(16)).append("</BANK_SEQ_NO>");
        msg.append("<ORDER_NO/>");
        msg.append("<RET_CODE>").append(retCode).append("</RET_CODE>");
        msg.append("<RET_MSG>").append(retMsg).append("</RET_MSG>");
        msg.append("</HEAD>");
        msg.append("<BODY>");
        msg.append("<RESERVE1/>");
        msg.append("<RESERVE2/>");
        msg.append("</BODY>");
        msg.append("</NYYH>");
        msg.append(RandomStringUtils.randomNumeric(16));
        return msg.toString();
    }


    private HashMap<String, String> analyseXml(String responseXml, String[] headParams, String[] bodyParams) throws Exception {
        HashMap<String, String> resultMap = new HashMap<String, String>();
        /**
         * 把前8位和后16位截取掉
         */
        responseXml = responseXml.substring(8, responseXml.length()-16);
        /**
         * dom4j解析xml
         */
        Reader r = new StringReader(responseXml);
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(r);
        Element root = doc.getRootElement();
        Element head = root.getChild("HEAD");
        Element body = root.getChild("BODY");
        String logString = StringUtils.EMPTY;//日志输出
        /**
         * 循环参数数组解析
         */
        for(String param : headParams){
            String value = head.getChild(param) == null ? StringUtils.EMPTY :
                    StringUtils.trim(head.getChild(param).getText());
            resultMap.put(param, value);
            if(StringUtils.isNotBlank(logString)){
                logString += ",";
            }
            logString += param + "=" + value;
        }
        for(String param : bodyParams){
            String value = body.getChild(param) == null ? StringUtils.EMPTY :
                    StringUtils.trim(body.getChild(param).getText());
            resultMap.put(param, value);
            if(StringUtils.isNotBlank(logString)){
                logString += ",";
            }
            logString += param + "=" + value;
        }
        log.info("返回参数：" + logString);
        return resultMap;
    }

}
