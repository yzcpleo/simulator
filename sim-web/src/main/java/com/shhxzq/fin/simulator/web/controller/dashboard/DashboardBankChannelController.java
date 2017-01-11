package com.shhxzq.fin.simulator.web.controller.dashboard;

import com.github.pagehelper.PageInfo;
import com.shhxzq.fin.simulator.biz.service.BankChannelService;
import com.shhxzq.fin.simulator.model.vo.BankChannel;
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
@RequestMapping("dashboard/bank/channel")
public class DashboardBankChannelController extends BaseController {

    @Autowired
    private BankChannelService bankChannelService;

    /**
     * 银行通道界面
     *
     * @param pageNum
     * @param bnkNm
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("BANK_CHANNEL")
    public String index(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNum,
                        @RequestParam(value = "bnkNm", required = false, defaultValue = "") String bnkNm,
                        Model model) {
        List<BankChannel> bankChannels = bankChannelService.searchBankChannels(pageNum, bnkNm);
        PageInfo<User> page = new PageInfo(bankChannels);

        model.addAttribute("page", page);
        return getPathList();
    }

    /**
     * 添加银行通道
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    @RequiresPermissions("BANK_CHANNEL")
    public String create(Model model) {
        model.addAttribute("bankChannel", new BankChannel());
        return getPathFormModal();
    }

    /**
     * 保存银行通道
     *
     * @param bankChannel
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("BANK_CHANNEL")
    public Map<String, Object> save(@ModelAttribute("bankChannel") @Valid BankChannel bankChannel, BindingResult result) {
        Map<String, Object> resultMap = getResultMap();
        if (!result.hasErrors()) {
            bankChannelService.saveBankChannel(bankChannel);
        } else {
            setResultMapFailure(resultMap);
        }

        return resultMap;
    }

    /**
     * 编辑银行通道
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/edit", method = RequestMethod.GET)
    @RequiresPermissions("BANK_CHANNEL")
    public String create(@PathVariable("id") Long id, Model model) {
        model.addAttribute("bankChannel", bankChannelService.findBankChannelById(id));
        return getPathFormModal();
    }

    /**
     * 更新银行通道
     *
     * @param bankChannel
     * @param result
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("BANK_CHANNEL")
    public Map<String, Object> update(@ModelAttribute("bankChannel") @Valid BankChannel bankChannel, BindingResult result) {
        Map<String, Object> resultMap = getResultMap();
        if (!result.hasErrors()) {
            bankChannelService.updateBankChannel(bankChannel);
        } else {
            setResultMapFailure(resultMap);
        }

        return resultMap;
    }

}
