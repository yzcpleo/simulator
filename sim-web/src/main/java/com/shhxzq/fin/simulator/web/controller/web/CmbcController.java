package com.shhxzq.fin.simulator.web.controller.web;

import com.shhxzq.fin.simulator.biz.service.BankCommandService;
import com.shhxzq.fin.simulator.biz.service.BankTranService;
import com.shhxzq.fin.simulator.biz.service.impl.cmbc2.CmbcUtil;
import com.shhxzq.fin.simulator.biz.util.BankCommandHelp;
import com.shhxzq.fin.simulator.common.util.XmlUtil;
import com.shhxzq.fin.simulator.model.constants.BankEnum;
import com.shhxzq.fin.simulator.model.vo.BankCommand;
import com.shhxzq.fin.simulator.model.vo.BankTran;
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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 民生超网模拟器
 *
 * @author kangyonggan
 * @since 16/5/20
 */
@Controller
@RequestMapping("cmbc")
@Log4j2
public class CmbcController {

    @Autowired
    private BankTranService bankTranService;

    @Autowired
    private BankCommandService bankCommandService;

    /**
     * 民生交易统一入口
     *
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("---------------------------------- 进入民生模拟器 ----------------------------------");
        // 解析请求报文
        String message = HttpUtil.transFromRequest(request);
        log.info("请求报文: {}", message);

        // 解析xml
        Map<String, String> params = XmlUtil.parseXml(message);
        log.info("请求报文-名值对: {}", params);

        // 应答报文模板
        String msgTemplate = "<?xml version=\"1.0\"?><CMBC><responseHeader>%s</responseHeader><xDataBody>%s</xDataBody></CMBC>";
        log.info("应答报文模板: {}", msgTemplate);

        // 交易类型不存在时, 使用此默认返回报文
        List<String> list = new ArrayList();
        String header = "<status><code>0</code><message>ok</message></status>";
        String body = "<statusId><statusCode>0</statusCode><statusErrMsg>交易成功</statusErrMsg></statusId>";
        list.add(header);
        list.add(body);

        // 根据交易类型分发
        boolean isQuery = message.indexOf("trnCode=\"qryXfer\"") > -1;
        log.info("交易类型: {}", isQuery ? "单笔查询" : "赎回");
        if (isQuery) {
            list = query(params);
        } else {
            list = redeem(params);
        }

        log.info("应答消息header: {}", list.get(0));
        log.info("应答消息body: {}", list.get(1));

        // 组装报文
        String xml = String.format(msgTemplate, list.get(0), list.get(1));
        log.info("响应报文: {}", xml);

        response.setCharacterEncoding("gb2312");
        PrintWriter out = response.getWriter();

        out.print(xml);
        out.flush();
        out.close();
        log.info("---------------------------------- 离开民生模拟器 ----------------------------------");
    }

    /**
     * 赎回
     *
     * @param params
     * @return
     */
    public List<String> redeem(Map<String, String> params) {
        log.info("============ 进入赎回接口 ============");

        // 模拟器交易流水号
        log.info("模拟器交易流水号: {}", BankCommandHelp.genSerialNo());

        // 响应码 = 0:交易成功, 2:交易失败, 其他:交易处理中
        BankTran bankTran = bankTranService.findBankTranByBnkCoAndTranCo(BankEnum.CMBC.getBnkCo(), "redeem");
        String code = "0";
        String val = "交易成功";
        if (StringUtils.isNotEmpty(bankTran.getRespCo())) {
            code = bankTran.getRespCo();
            val = bankTran.getRespMsg();
            log.info("强制改变发赎回响应码: {} - {}", code, val);
        } else {
            log.info("不强制改变赎回响应码: {} - {}", code, val);
        }
        // 交易落库
        BankCommand tran = bankCommandService.findBankCommandByMerSerialNo(params.get("trnId"));
        if (tran != null) {
            log.info("交易重复, 基金公司流水号为: {}", tran);
            code = "88888888";
            val = "重复交易";
        } else {
            log.info("交易不重复, 生成交易并入库..");
            // 交易内容
            BankCommand transaction = CmbcUtil.buildTransaction(params, bankTran, BankCommandHelp.genSerialNo(), code, val);
            log.info("交易内容为: {}", transaction);

            // 交易入库
            bankCommandService.saveBankCommand(transaction);
        }

        List<String> list = new ArrayList();
        String header = "<status><code>" + code + "</code><message>" + val + "</message></status>";
        String body = "";
        list.add(header);
        list.add(body);

        log.info("============ 离开赎回接口 ============");
        return list;
    }

    /**
     * 单笔查询
     *
     * @param params
     * @return
     */
    public List<String> query(Map<String, String> params) {
        log.info("============ 进入单笔查询接口 ============");

        // 查询交易
        BankCommand transaction = bankCommandService.findBankCommandByMerSerialNo(params.get("trnId"));
        log.info("查询的交易为: {}", transaction);

        String code = "0";
        String val = "交易成功";
        if (transaction == null) {
            code = "E1602";
            val = "此流水号不存在，请查证!";
            transaction = new BankCommand();
            transaction.setRespCo("");
        }
        log.info(val);

        if ("F".equals(transaction.getTranSt())) {
            transaction.setRespCo("2");
        }

        List<String> list = new ArrayList();
        String header = "<status><code>" + code + "</code><message>" + val + "</message></status>";
        String body = "<statusId><statusCode>" + transaction.getRespCo() + "</statusCode><statusErrMsg>" + val + "</statusErrMsg></statusId>";
        list.add(header);
        list.add(body);

        log.info("============ 离开单笔查询接口 ============");
        return list;
    }


}
