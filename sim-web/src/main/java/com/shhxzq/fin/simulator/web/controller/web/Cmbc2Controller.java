package com.shhxzq.fin.simulator.web.controller.web;

import com.shhxzq.fin.simulator.biz.service.impl.cmbc2.CmbcService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by houjiagang on 16/7/19.
 */
@Controller
@Log4j2
public class Cmbc2Controller {

    private static final String ENCODING = "UTF-8";

    @Autowired
    private CmbcService cmbcServiceImpl;

    @RequestMapping(value = "epay/connect.do" , consumes = {"application/xml", "text/xml", "text/plain"})
    public void cmbc(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("=========== 民生快捷Mock开始 ============");
        String returnResponse = cmbcServiceImpl.handle(request);
        response.setContentType("text/xml");
        response.setCharacterEncoding(ENCODING);
        PrintWriter out = response.getWriter();
        out.print(returnResponse);

    }


}
