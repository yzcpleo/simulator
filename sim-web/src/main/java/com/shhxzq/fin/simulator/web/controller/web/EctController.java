package com.shhxzq.fin.simulator.web.controller.web;

import com.shhxzq.fin.simulator.biz.service.BankCommandService;
import com.shhxzq.fin.simulator.biz.service.BankTranService;
import com.shhxzq.fin.simulator.biz.util.Base64Util;
import com.shhxzq.fin.simulator.common.util.DateUtils;
import com.shhxzq.fin.simulator.common.util.XmlUtil;
import com.shhxzq.fin.simulator.model.constants.BankEnum;
import com.shhxzq.fin.simulator.model.vo.BankCommand;
import com.shhxzq.fin.simulator.model.vo.BankTran;
import com.shhxzq.fin.simulator.web.util.EctUtil;
import com.shhxzq.fin.simulator.web.util.HttpUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * 证通模拟器
 *
 * @author kangyonggan
 * @since 16/5/20
 */
@Controller
@RequestMapping("ect")
@Log4j2
public class EctController {

    @Autowired
    private BankTranService bankTranService;

    @Autowired
    private BankCommandService bankCommandService;

    /**
     * 证通交易统一入口
     *
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public void index(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("---------------------------------- 进入证通模拟器 ----------------------------------");
        // 解析请求报文
        String message = HttpUtil.transFromRequest(request);
        log.info("请求报文-base64: {}", message);

        // base64解码
        message = Base64Util.base64Decode(message);
        log.info("请求报文-明文: {}", message);

        // 解析xml
        Map<String, String> params = XmlUtil.parseXml(message);
        log.info("请求报文-名值对: {}", params);

        // 应答报文模板
        String msgTemplate = "<transation><head></head><response>%s</response><signature></signature></transation>";
        log.info("应答报文模板: {}", msgTemplate);

        // 交易类型不存在时, 使用此默认返回报文
        String msgResponse = "<respCode>99999999</respCode><respMessage>交易类型不存在</respMessage>";

        // 根据交易类型分发交易 transCode = 303:绑卡验证, 102:申购, 101:赎回, 307:签约, 401:单笔查询
        String transCode = params.get("transCode");
        log.info("交易类型: {}", transCode);
        if ("303".equals(transCode)) {
            // verfType = 1:短信验证, 2:账户验证, 3:绑卡确认
            if ("1".equals(params.get("verfType"))) {
                msgResponse = sms(params);
            } else if ("3".equals(params.get("verfType"))) {
                msgResponse = verify(params);
            }
        } else if ("307".equals(transCode)) {
            msgResponse = sign(params);
        } else if ("102".equals(transCode)) {
            msgResponse = pay(params);
        } else if ("101".equals(transCode)) {
            msgResponse = redeem(params);
        } else if ("401".equals(transCode)) {
            msgResponse = query(params);
        }

        log.info("应答消息体: {}", msgResponse);

        // 组装报文
        String xml = String.format(msgTemplate, msgResponse);
        log.info("响应报文-明文: {}", xml);

        // base64
        xml = Base64Util.base64Encode(xml);
        log.info("响应报文-base64: {}", xml);

        // 报文回写
        PrintWriter printWriter = response.getWriter();
        printWriter.write(xml);

        printWriter.flush();
        printWriter.close();
        log.info("---------------------------------- 离开证通模拟器 ----------------------------------");
    }

    /**
     * 发短信
     *
     * @param params
     * @return
     */
    public String sms(Map<String, String> params) {
        log.info("============ 进入发短信接口 ============");

        // 支付云交易流水号
        String serNo = DateUtils.getCurrentTime();
        log.info("支付云交易流水号: {}", serNo);

        // 响应码 = 50050000:交易成功, 50050001:处理中, 其他均为交易失败
        BankTran bankTran = bankTranService.findBankTranByBnkCoAndTranCo(BankEnum.ECT.getBnkCo(), "sendsms");
        String code = "50050000";
        String val = "交易成功";
        if (StringUtils.isNotEmpty(bankTran.getRespCo())) {
            code = bankTran.getRespCo();
            val = bankTran.getRespMsg();
            log.info("强制改变发短信响应码: {} - {}", code, val);
        } else {
            log.info("不强制改变发短信响应码: {} - {}", code, val);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<transDate>").append(params.get("transDate")).append("</transDate>");// 交易日期
        sb.append("<transSeq>").append(params.get("transSeq")).append("</transSeq>");// 交易流水号
        sb.append("<msgSndNo>").append("BS123456789").append("</msgSndNo>");// 短信发送编号
        sb.append("<ectAcct>").append("").append("</ectAcct>");// 证通账号
        sb.append("<signProtocolCode>").append("").append("</signProtocolCode>");// 签约协议号
        sb.append("<respCode>").append(code).append("</respCode>");// 应答码
        sb.append("<respMessage>").append(val).append("</respMessage>");// 应答信息
        sb.append("<errorCode>").append("").append("</errorCode>");// 错误码
        sb.append("<ectTransSeq>").append(serNo).append("</ectTransSeq>");// 支付云交易流水号
        sb.append("<addnField>").append(params.get("addnField")).append("</addnField>");// 附加域

        log.info("============ 离开发短信接口 ============");
        return sb.toString();
    }

    /**
     * 签约
     *
     * @param params
     * @return
     */
    public String sign(Map<String, String> params) {
        log.info("============ 进入签约接口 ============");

        // 支付云交易流水号
        String serNo = DateUtils.getCurrentTime();
        log.info("支付云交易流水号: {}", serNo);

        // 响应码 = 50050000:交易成功, 50050001:处理中, 其他均为交易失败
        BankTran bankTran = bankTranService.findBankTranByBnkCoAndTranCo(BankEnum.ECT.getBnkCo(), "sign");
        String code = "50050000";
        String val = "交易成功";
        if (StringUtils.isNotEmpty(bankTran.getRespCo())) {
            code = bankTran.getRespCo();
            val = bankTran.getRespMsg();
            log.info("强制改变签约应码: {} - {}", code, val);
        } else {
            log.info("不强制改变签约响应码: {} - {}", code, val);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<transDate>").append(params.get("transDate")).append("</transDate>");// 交易日期
        sb.append("<transSeq>").append(params.get("transSeq")).append("</transSeq>");// 交易流水号
        sb.append("<msgSndNo>").append("").append("</msgSndNo>");// 短信发送编号
        sb.append("<ectAcct>").append("").append("</ectAcct>");// 证通账号
        sb.append("<signProtocolCode>").append("").append("</signProtocolCode>");// 签约协议号
        sb.append("<respCode>").append(code).append("</respCode>");// 应答码
        sb.append("<respMessage>").append(val).append("</respMessage>");// 应答信息
        sb.append("<errorCode>").append("").append("</errorCode>");// 错误码
        sb.append("<ectTransSeq>").append(serNo).append("</ectTransSeq>");// 支付云交易流水号
        sb.append("<addnField>").append(params.get("addnField")).append("</addnField>");// 附加域

        log.info("============ 离开签约接口 ============");
        return sb.toString();
    }

    /**
     * 鉴权
     *
     * @param params
     * @return
     */
    public String verify(Map<String, String> params) {
        log.info("============ 进入鉴权接口 ============");

        // 支付云交易流水号
        String serNo = DateUtils.getCurrentTime();
        log.info("支付云交易流水号: {}", serNo);

        // 响应码 = 50050000:交易成功, 50050001:处理中, 其他均为交易失败
        BankTran bankTran = bankTranService.findBankTranByBnkCoAndTranCo(BankEnum.ECT.getBnkCo(), "verify");
        String code = "50050000";
        String val = "交易成功";
        if (StringUtils.isNotEmpty(bankTran.getRespCo())) {
            code = bankTran.getRespCo();
            val = bankTran.getRespMsg();
            log.info("强制改变鉴权应码: {} - {}", code, val);
        } else {
            log.info("不强制改变鉴权响应码: {} - {}", code, val);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<transDate>").append(params.get("transDate")).append("</transDate>");// 交易日期
        sb.append("<transSeq>").append(params.get("transSeq")).append("</transSeq>");// 交易流水号
        sb.append("<msgSndNo>").append("").append("</msgSndNo>");// 短信发送编号
        sb.append("<ectAcct>").append("").append("</ectAcct>");// 证通账号
        sb.append("<signProtocolCode>").append("BS987654321").append("</signProtocolCode>");// 签约协议号
        sb.append("<respCode>").append(code).append("</respCode>");// 应答码
        sb.append("<respMessage>").append(val).append("</respMessage>");// 应答信息
        sb.append("<errorCode>").append("").append("</errorCode>");// 错误码
        sb.append("<ectTransSeq>").append(serNo).append("</ectTransSeq>");// 支付云交易流水号
        sb.append("<addnField>").append(params.get("addnField")).append("</addnField>");// 附加域

        log.info("============ 离开鉴权接口 ============");
        return sb.toString();
    }

    /**
     * 申购
     *
     * @param params
     * @return
     */
    public String pay(Map<String, String> params) {
        log.info("============ 进入申购接口 ============");

        // 支付云交易流水号
        String serNo = DateUtils.getCurrentTime();
        log.info("支付云交易流水号: {}", serNo);


        // 响应码 = 50050000:交易成功, 50050001:处理中, 其他均为交易失败
        BankTran bankTran = bankTranService.findBankTranByBnkCoAndTranCo(BankEnum.ECT.getBnkCo(), "verify");
        String code = "50050000";
        String val = "交易成功";
        if (StringUtils.isNotEmpty(bankTran.getRespCo())) {
            code = bankTran.getRespCo();
            val = bankTran.getRespMsg();
            log.info("强制改变申购应码: {} - {}", code, val);
        } else {
            log.info("不强制改变申购响应码: {} - {}", code, val);
        }

        // 交易落库
        BankCommand bankCommand = bankCommandService.findBankCommandByMerSerialNo(params.get("transSeq"));
        if (bankCommand != null) {
            log.info("交易重复, 基金公司流水号为: {}", bankCommand.getMerSerialNo());
            code = "88888888";
            val = "重复交易";
        } else {
            log.info("交易不重复, 生成交易并入库...");
            // 交易内容
            BankCommand command = EctUtil.buildTransaction(params, bankTran, serNo, code, val);
            log.info("交易内容为: {}", command);

            // 交易入库
            bankCommandService.saveBankCommand(command);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<transDate>").append(params.get("transDate")).append("</transDate>");// 交易日期
        sb.append("<transSeq>").append(params.get("transSeq")).append("</transSeq>");// 交易流水号
        sb.append("<msgSndNo>").append("").append("</msgSndNo>");// 短信发送编号
        sb.append("<ectAcct>").append("").append("</ectAcct>");// 证通账号
        sb.append("<signProtocolCode>").append("").append("</signProtocolCode>");// 签约协议号
        sb.append("<respCode>").append(code).append("</respCode>");// 应答码
        sb.append("<respMessage>").append(val).append("</respMessage>");// 应答信息
        sb.append("<errorCode>").append("").append("</errorCode>");// 错误码
        sb.append("<ectTransSeq>").append(serNo).append("</ectTransSeq>");// 支付云交易流水号
        sb.append("<addnField>").append(params.get("addnField")).append("</addnField>");// 附加域

        log.info("============ 离开申购接口 ============");
        return sb.toString();
    }

    /**
     * 赎回
     *
     * @param params
     * @return
     */
    public String redeem(Map<String, String> params) {
        log.info("============ 进入赎回接口 ============");

        // 支付云交易流水号
        String serNo = DateUtils.getCurrentTime();
        log.info("支付云交易流水号: {}", serNo);

        // 响应码 = 50050000:交易成功, 50050001:处理中, 其他均为交易失败
        BankTran bankTran = bankTranService.findBankTranByBnkCoAndTranCo(BankEnum.ECT.getBnkCo(), "verify");
        String code = "50050000";
        String val = "交易成功";
        if (StringUtils.isNotEmpty(bankTran.getRespCo())) {
            code = bankTran.getRespCo();
            val = bankTran.getRespMsg();
            log.info("强制改变赎回应码: {} - {}", code, val);
        } else {
            log.info("不强制改变赎回响应码: {} - {}", code, val);
        }

        // 交易落库
        BankCommand bankCommand = bankCommandService.findBankCommandByMerSerialNo(params.get("transSeq"));
        if (bankCommand != null) {
            log.info("交易重复, 基金公司流水号为: {}", bankCommand.getMerSerialNo());
            code = "88888888";
            val = "重复交易";
        } else {
            log.info("交易不重复, 生成交易并入库...");
            // 交易内容
            BankCommand command = EctUtil.buildTransaction(params, bankTran, serNo, code, val);
            log.info("交易内容为: {}", command);

            // 交易入库
            bankCommandService.saveBankCommand(command);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<transDate>").append(params.get("transDate")).append("</transDate>");// 交易日期
        sb.append("<transSeq>").append(params.get("transSeq")).append("</transSeq>");// 交易流水号
        sb.append("<respCode>").append(code).append("</respCode>");// 应答码
        sb.append("<respMessage>").append(val).append("</respMessage>");// 应答信息
        sb.append("<errorCode>").append("").append("</errorCode>");// 错误码
        sb.append("<ectTransSeq>").append(serNo).append("</ectTransSeq>");// 支付云交易流水号
        sb.append("<settleDate>").append("").append("</settleDate>");// 清算日期
        sb.append("<addnField>").append(params.get("addnField")).append("</addnField>");// 附加域

        log.info("============ 离开赎回接口 ============");
        return sb.toString();
    }

    /**
     * 单笔查询
     *
     * @param params
     * @return
     */
    public String query(Map<String, String> params) {
        log.info("============ 进入单笔查询接口 ============");

        // 查询交易
        BankCommand bankCommand = bankCommandService.findBankCommandByMerSerialNo(params.get("origTransSeq"));
        log.info("查询的交易为: {}", bankCommand);

        String code = "50050000";
        String val = "交易成功";
        if (bankCommand == null) {
            code = "88888888";
            val = "流水号不存在";
            bankCommand = new BankCommand();
            bankCommand.setRespCo("");
        }
        log.info(val);

        StringBuilder sb = new StringBuilder();
        sb.append("<respCode>").append(code).append("</respCode>");// 应答码
        sb.append("<respMessage>").append(val).append("</respMessage>");// 应答信息
        sb.append("<origRespCode>").append(bankCommand.getRespCo()).append("</origRespCode>");// 原始流水应答码

        log.info("============ 离开单笔查询接口 ============");
        return sb.toString();
    }

}
