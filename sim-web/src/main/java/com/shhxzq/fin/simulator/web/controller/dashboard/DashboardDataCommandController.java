package com.shhxzq.fin.simulator.web.controller.dashboard;

import com.github.pagehelper.PageInfo;
import com.shhxzq.fin.simulator.biz.service.BankChannelService;
import com.shhxzq.fin.simulator.biz.service.BankCommandService;
import com.shhxzq.fin.simulator.model.vo.BankChannel;
import com.shhxzq.fin.simulator.model.vo.BankCommand;
import com.shhxzq.fin.simulator.model.vo.User;
import com.shhxzq.fin.simulator.web.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author kangyonggan
 * @since 2017/1/10
 */
@Controller
@RequestMapping("dashboard/data/command")
public class DashboardDataCommandController extends BaseController {

    @Autowired
    private BankCommandService bankCommandService;

    @Autowired
    private BankChannelService bankChannelService;

    /**
     * 交易管理界面
     *
     * @param pageNum
     * @param bnkCo
     * @param merSerialNo
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("DATA_COMMAND")
    public String index(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNum,
                        @RequestParam(value = "bnkCo", required = false, defaultValue = "") String bnkCo,
                        @RequestParam(value = "merSerialNo", required = false, defaultValue = "") String merSerialNo,
                        Model model) {
        List<BankChannel> bankChannels = bankChannelService.findAllBankChannels();
        List<BankCommand> bankCommands = bankCommandService.searchBankCommands(pageNum, bnkCo, merSerialNo);
        PageInfo<User> page = new PageInfo(bankCommands);

        model.addAttribute("page", page);
        model.addAttribute("bankChannels", bankChannels);
        return getPathList();
    }

    /**
     * 编辑交易流水
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/edit", method = RequestMethod.GET)
    @RequiresPermissions("DATA_COMMAND")
    public String create(@PathVariable("id") Long id, Model model) {
        model.addAttribute("bankCommand", bankCommandService.findBankCommandById(id));
        return getPathFormModal();
    }

    /**
     * 更新交易流水
     *
     * @param bankCommand
     * @param result
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("DATA_COMMAND")
    public Map<String, Object> update(@ModelAttribute("bankCommand") @Valid BankCommand bankCommand, BindingResult result) {
        Map<String, Object> resultMap = getResultMap();
        if (!result.hasErrors()) {
            bankCommandService.updateBankCommand(bankCommand);
        } else {
            setResultMapFailure(resultMap);
        }

        return resultMap;
    }

}
