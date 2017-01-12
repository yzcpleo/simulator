package com.shhxzq.fin.simulator.web.controller.web;

import com.shhxzq.fin.simulator.biz.service.impl.ccb.CCBService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 建行
 * Created by houjiagang on 16/7/25.
 */

@Controller
@Log4j2
public class CCBController {

    @Autowired
    private CCBService ccbService;

    @RequestMapping(value = "FastPayment_adapter/FastPayChannelServlet/FastPay")
    public void ccb(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String respStr = ccbService.handle(request);
        response.setContentType("text/xml");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(respStr);


    }


}
