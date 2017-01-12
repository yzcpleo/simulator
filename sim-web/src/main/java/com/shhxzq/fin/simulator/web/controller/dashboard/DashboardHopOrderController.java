package com.shhxzq.fin.simulator.web.controller.dashboard;

import com.github.pagehelper.PageInfo;
import com.shhxzq.fin.simulator.biz.service.SimOrderService;
import com.shhxzq.fin.simulator.model.vo.SimOrder;
import com.shhxzq.fin.simulator.web.controller.BaseController;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by houjiagang on 2016/12/19.
 */
@Log4j2
@Controller
@RequestMapping("dashboard/hop/order")
public class DashboardHopOrderController extends BaseController {

    @Autowired
    private SimOrderService simOrderService;

    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("HOP_ORDER")
    public String index(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNow,
                        @RequestParam(value = "orderType", required = false, defaultValue = "") String orderType,
                        Model model) {
        List<SimOrder> orders = simOrderService.findOrders(pageNow, orderType);
        PageInfo<SimOrder> page = new PageInfo<>(orders);
        model.addAttribute("page", page);

        return getPathIndex();

    }


    @RequestMapping(value = "{id:[\\d]+}", method = RequestMethod.GET)
    @RequiresPermissions("HOP_ORDER")
    public String detail(@PathVariable("id") Long id, Model model) {
        SimOrder order = simOrderService.findOrder(id);
        model.addAttribute("order", order);
        return getPathDetailModal();

    }

    @RequestMapping(value = "{id:[\\d]+}/edit", method = RequestMethod.GET)
    @RequiresPermissions("HOP_ORDER")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("order", simOrderService.findOrder(id));
        return getPathFormModal();
    }


    @RequestMapping(value = "{id:[\\d]+}/update", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("HOP_ORDER")
    public Map<String, Object> update(@ModelAttribute("transaction") @Valid SimOrder order, BindingResult result) {
        Map<String, Object> resultMap = getResultMap();

        if (result.hasErrors()) {
            setResultMapFailure(resultMap);
        } else {
            simOrderService.updateOrder(order);
        }
        return resultMap;
    }

    @RequestMapping(value = "{id:[\\d]+}/delete", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions("HOP_ORDER")
    public Map<String, Object> delete(@PathVariable Long id) {
        simOrderService.deleteOrder(id);
        return getResultMap();
    }

    @RequestMapping(value = "createOpenAcct", method = RequestMethod.GET)
    @RequiresPermissions("HOP_ORDER")
    public String createOpenAcct(Model model) {
        model.addAttribute("order", new SimOrder());
        return getPathRoot() + "/createOpenAcct-modal";
    }

    @RequestMapping(value = "createTransaction", method = RequestMethod.GET)
    @RequiresPermissions("HOP_ORDER")
    public String createTransaction(Model model) {
        model.addAttribute("order", new SimOrder());
        return getPathRoot() + "/createTransaction-modal";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("HOP_ORDER")
    public Map<String, Object> save(HttpServletRequest request, @ModelAttribute("transaction") @Valid SimOrder order, BindingResult result) {
        Map<String, Object> resultMap = getResultMap();

        String orderType = request.getParameter("orderType");
        order.setOrderType(orderType);
        if (result.hasErrors()) {
            setResultMapFailure(resultMap);
        } else {
            simOrderService.saveOrder(order);
        }

        return resultMap;
    }

    @RequestMapping(value = "file", method = RequestMethod.GET)
    @RequiresPermissions("HOP_ORDER")
    public String file(Model model) {
        return getPathRoot() + "/fileCreate-modal";
    }


    @RequestMapping(value = "genFile", method = RequestMethod.POST)
    @RequiresPermissions("HOP_ORDER")
    @ResponseBody
    public Map<String, Object> genFile(HttpServletRequest request) {
        String fileNmae = simOrderService.genFile(request.getParameter("orderType"));

        return getResultMap();
    }
}
