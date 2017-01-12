package com.shhxzq.fin.simulator.web.controller.web;

import com.shhxzq.fin.simulator.biz.service.impl.spdb.SpdbService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 *
 * 浦发快捷+银企 mockserver
 * Created by houjiagang on 16/7/15.
 */

@Controller
@Log4j2
public class SpdbController {

    private static final String ENCODING = "GBK";

    @Autowired
    private SpdbService spdbService;


    /**
     * payment/main 浦发快捷
     *
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "payment/main" , consumes = {"application/xml", "text/xml", "text/plain"})
    public void spdb(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("=========== 浦发快捷Mock开始 ============");
        String returnResponse = spdbService.handle(request);

        response.setContentType("text/xml");
        response.setCharacterEncoding(ENCODING);
        PrintWriter out = response.getWriter();
        out.print(returnResponse);

    }


    /**
     * payment/main1 浦发银企
     *
     *
     *
     */
    @RequestMapping(value = "payment/main1")
    public void spdb1(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("=========== 浦发银企Mock开始 ============");
        String returnResponse = spdbService.handleCompany(request);

        response.setContentType("text/xml");
        response.setCharacterEncoding(ENCODING);
        PrintWriter out = response.getWriter();
        out.print(returnResponse);

    }


}
