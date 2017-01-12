package com.shhxzq.fin.simulator.web.controller.web;

import com.shhxzq.fin.simulator.biz.service.impl.boc.BOCService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 中行
 * Created by houjiagang on 16/8/3.
 */
@Controller
@Log4j2
public class BOCController {

    @Autowired
    private BOCService bocService;

    /**
     * preSign
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "MCPPreSignAgreement.do" )
    public void preSign(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String respStr = bocService.handle(request);
        response.setContentType("text/xml");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(respStr);

    }

    /**
     * sign
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "MCPSignAgreement.do" )
    public void sign(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String respStr = bocService.handle(request);
        response.setContentType("text/xml");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(respStr);

    }

    /**
     * pay
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "MCPPayTran.do" )
    public void pay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String respStr = bocService.handle(request);
        response.setContentType("text/xml");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(respStr);

    }

    /**
     * query
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "MCPQueryTran.do" )
    public void query(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String respStr = bocService.handle(request);
        response.setContentType("text/xml");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(respStr);

    }



}
