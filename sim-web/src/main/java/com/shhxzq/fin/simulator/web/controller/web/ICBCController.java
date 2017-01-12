package com.shhxzq.fin.simulator.web.controller.web;

import com.shhxzq.fin.simulator.biz.service.impl.icbc.ICBCService;
import com.shhxzq.fin.simulator.biz.util.Base64Util;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by houjiagang on 16/7/26.
 */
@Controller
@Log4j2
public class ICBCController {

    @Autowired
    private ICBCService icbcService;


    @RequestMapping(value = "servlet/ICBCCMPAPIReqServlet" )
    public void icbc(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String respStr = icbcService.handle(request);
        response.setContentType("text/xml");
        response.setCharacterEncoding("GBK");
        PrintWriter out = response.getWriter();
        respStr = Base64Util.base64Encode(respStr + ">");
        log.info("After Base64 encode {}", "reqData" + respStr);
        out.print("reqData=" + respStr);


    }

    @RequestMapping(value = "test" , produces = {"application/json"})
    @ResponseBody
    public String test(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String s = "{\n" +
                "  \"ANSWERS\": [\n" +
                "    {\n" +
                "      \"ANS_COMM_DATA\": [\n" +
                "        {\n" +
                "          \"ACCOUNT\": \"0017976335\",\n" +
                "          \"CURRENCY\": \"0\",\n" +
                "          \"DCL_FLAG\": \"1\",\n" +
                "          \"EXT_SERIAL_NO\": \"\",\n" +
                "          \"IS_WITHDRAW\": \"0\",\n" +
                "          \"KEY_STR\": \"1\",\n" +
                "          \"MARKET\": \"0\",\n" +
                "          \"MATCHED_AMT\": \"0.000\",\n" +
                "          \"MATCHED_QTY\": \"0\",\n" +
                "          \"ORDER_AMT\": \"1350.000\",\n" +
                "          \"ORDER_DATE\": \"20150701\",\n" +
                "          \"ORDER_ID\": \"125\",\n" +
                "          \"ORDER_TIME\": \"13:36:41\",\n" +
                "          \"PRICE\": \"13.500\",\n" +
                "          \"QTY\": \"100\",\n" +
                "          \"SECU_ACC\": \"0168432251\",\n" +
                "          \"SECU_CODE\": \"000088\",\n" +
                "          \"SECU_NAME\": \"盐 田 港\",\n" +
                "          \"TRD_DATE\": \"20150701\",\n" +
                "          \"TRD_ID\": \"0B\",\n" +
                "          \"WITHDRAWN_QTY\": \"0\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"ACCOUNT\": \"0017976335\",\n" +
                "          \"CURRENCY\": \"0\",\n" +
                "          \"DCL_FLAG\": \"1\",\n" +
                "          \"EXT_SERIAL_NO\": \"\",\n" +
                "          \"IS_WITHDRAW\": \"0\",\n" +
                "          \"KEY_STR\": \"1\",\n" +
                "          \"MARKET\": \"0\",\n" +
                "          \"MATCHED_AMT\": \"0.000\",\n" +
                "          \"MATCHED_QTY\": \"0\",\n" +
                "          \"ORDER_AMT\": \"1350.000\",\n" +
                "          \"ORDER_DATE\": \"20150701\",\n" +
                "          \"ORDER_ID\": \"125\",\n" +
                "          \"ORDER_TIME\": \"13:36:41\",\n" +
                "          \"PRICE\": \"13.500\",\n" +
                "          \"QTY\": \"100\",\n" +
                "          \"SECU_ACC\": \"0168432251\",\n" +
                "          \"SECU_CODE\": \"000088\",\n" +
                "          \"SECU_NAME\": \"盐 田 港\",\n" +
                "          \"TRD_DATE\": \"20150701\",\n" +
                "          \"TRD_ID\": \"0B\",\n" +
                "          \"WITHDRAWN_QTY\": \"0\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"ANS_MSG_HDR\": {\n" +
                "        \"MSG_CODE\": \"0\",\n" +
                "        \"MSG_TEXT\": \"调用成功！\"\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        return s;
    }
}
