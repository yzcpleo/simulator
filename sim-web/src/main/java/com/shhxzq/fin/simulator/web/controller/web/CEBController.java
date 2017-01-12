package com.shhxzq.fin.simulator.web.controller.web;

import com.shhxzq.fin.simulator.biz.service.impl.ceb.CEBService;
import com.shhxzq.fin.simulator.biz.service.impl.ceb.HttpClientSend;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by houjiagang on 16/9/5.
 */

@Controller
@Log4j2
public class CEBController {


    @Autowired
    private CEBService cebService;

    @RequestMapping(value = "pwap/AgreeEpaySignPre.do")
    public String signPre(HttpServletRequest request, ModelMap model){
        String certNo = request.getParameter("certNo");
        String certType = request.getParameter("certType");
        String plain = request.getParameter("Plain");
        String transName = request.getParameter("transName");
        String signature = request.getParameter("Signature");
        String name = request.getParameter("name");
        String merId = request.getParameter("merId");
        String cell = request.getParameter("cell");
        String email = request.getParameter("email");
        String url = request.getParameter("url");

        model.put("url", url);
        model.put("certNo", certNo);
        model.put("certType", certType);
        model.put("name", name);
        model.put("merId", merId);

        return "web/ceb/signPre";


    }


    @RequestMapping(value = "ceb/doPost")
    public String doPost(HttpServletRequest request, ModelMap model) throws Exception {
        String cardNo = request.getParameter("cardNo");
        String certNo = request.getParameter("certNo");
        String certType = request.getParameter("certType");
        String name = request.getParameter("name");
        String merId = request.getParameter("merId");
        String url = request.getParameter("url");

        String postData = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<MessageSuit>\n" +
                "    <Message id=\"E" + RandomStringUtils.randomNumeric(11) + "\">\n" +
                "        <Plain id=\"TSReq\">\n" +
                "            <transId>TSReq</transId>\n" +
                "            <merId>" + merId + "</merId>\n" +
                "            <signNo>" + RandomStringUtils.randomNumeric(20) + "</signNo>\n" +
                "            <cardNo>" + cardNo + "</cardNo>\n" +
                "            <cardType>D</cardType>\n" +
                "            <name>" + name + "</name>\n" +
                "            <certType>" + certType + "</certType>\n" +
                "            <certNo>" + certNo + "</certNo>\n" +
                "            <email />\n" +
                "            <cell>" + certNo + "</cell>\n" +
                "            <channelId>05</channelId>\n" +
                "        </Plain>\n" +
                "        <ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
                "            <ds:SignedInfo>\n" +
                "                <ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\" />\n" +
                "                <ds:SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\" />\n" +
                "                <ds:Reference URI=\"#TSReq\">\n" +
                "                    <ds:Transforms>\n" +
                "                        <ds:Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\" />\n" +
                "                    </ds:Transforms>\n" +
                "                    <ds:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\" />\n" +
                "                    <ds:DigestValue>UgCNIZl+JrVS6ScSQVyxLVbeNec=</ds:DigestValue>\n" +
                "                </ds:Reference>\n" +
                "            </ds:SignedInfo>\n" +
                "            <ds:SignatureValue>Js9oBYc6thtgWlwWi07RIy1R2MHn77Z28Vh0c6koxiQ3SKtSCxxhbjceaUTZtei/q24yPa09Cc6q qy6dTo9tLNIkNoKmRGVxpcSfcQgwIEwmu7vGPt/t9CHRdemC86BFJIm2Pnjlzjg0LHkxkZBH3wLu FzE9qEgfvaN/2lPEKPU=</ds:SignatureValue>\n" +
                "        </ds:Signature>\n" +
                "    </Message>\n" +
                "</MessageSuit>";

        //光大银行后台回调地址
        String postUrl = "http://10.199.101.212:8080/bankEngine/authenticate/cebAuthGetResponsion";
        HttpClientSend http = new HttpClientSend();
        String response = http.post(postData, postUrl);
        log.info("The response is {}", response);

        model.put("url", url);
        return "web/ceb/doPost";

    }


    @RequestMapping(value = "ceb/test")
    public String test(HttpServletRequest request, HttpServletResponse response){
        return "web/ceb/test";

    }

    @RequestMapping(value = "agreeEpayper/connect.do")
    public void ceb(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String respStr = cebService.handle(request);
        response.setContentType("text/xml");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(respStr);
    }

    @RequestMapping(value = "ent/b2e004001.do")
    public void b2e004001(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String respStr = cebService.handleb2e004001(request);
        response.setContentType("text/xml");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(respStr);

    }

    @RequestMapping(value = "ent/b2e004003.do")
    public void b2e004003(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String respStr = cebService.handleb2e004003(request);
        response.setContentType("text/xml");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(respStr);

    }


}
