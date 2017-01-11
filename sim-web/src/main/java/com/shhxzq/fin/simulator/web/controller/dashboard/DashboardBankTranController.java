package com.shhxzq.fin.simulator.web.controller.dashboard;

import com.github.pagehelper.PageInfo;
import com.shhxzq.fin.simulator.biz.service.BankChannelService;
import com.shhxzq.fin.simulator.biz.service.BankRespService;
import com.shhxzq.fin.simulator.biz.service.BankTranService;
import com.shhxzq.fin.simulator.model.vo.BankChannel;
import com.shhxzq.fin.simulator.model.vo.BankResp;
import com.shhxzq.fin.simulator.model.vo.BankTran;
import com.shhxzq.fin.simulator.model.vo.User;
import com.shhxzq.fin.simulator.web.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping("dashboard/bank/tran")
public class DashboardBankTranController extends BaseController {

    @Autowired
    private BankTranService bankTranService;

    @Autowired
    private BankChannelService bankChannelService;

    @Autowired
    private BankRespService bankRespService;

    /**
     * 交易管理界面
     *
     * @param pageNum
     * @param bnkCo
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("BANK_TRAN")
    public String index(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNum,
                        @RequestParam(value = "bnkCo", required = false, defaultValue = "") String bnkCo,
                        Model model) {
        List<BankChannel> bankChannels = bankChannelService.findAllBankChannels();
        List<BankTran> bankTrans = bankTranService.searchBankTrans(pageNum, bnkCo);
        PageInfo<User> page = new PageInfo(bankTrans);

        model.addAttribute("page", page);
        model.addAttribute("bankChannels", bankChannels);
        return getPathList();
    }

    /**
     * 添加交易类型
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    @RequiresPermissions("BANK_TRAN")
    public String create(Model model) {
        List<BankChannel> bankChannels = bankChannelService.findAllBankChannels();

        model.addAttribute("bankChannels", bankChannels);
        model.addAttribute("bankTran", new BankTran());
        return getPathFormModal();
    }

    /**
     * 保存交易类型
     *
     * @param bankTran
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("BANK_TRAN")
    public Map<String, Object> save(@ModelAttribute("bankTran") @Valid BankTran bankTran, BindingResult result) {
        Map<String, Object> resultMap = getResultMap();
        if (!result.hasErrors()) {
            bankTranService.saveBankTran(bankTran);
        } else {
            setResultMapFailure(resultMap);
        }

        return resultMap;
    }

    /**
     * 编辑交易类型
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/edit", method = RequestMethod.GET)
    @RequiresPermissions("BANK_TRAN")
    public String create(@PathVariable("id") Long id, Model model) {
        List<BankChannel> bankChannels = bankChannelService.findAllBankChannels();

        model.addAttribute("bankChannels", bankChannels);
        model.addAttribute("bankTran", bankTranService.findBankTranById(id));
        return getPathFormModal();
    }

    /**
     * 更新交易类型
     *
     * @param bankTran
     * @param result
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("BANK_TRAN")
    public Map<String, Object> update(@ModelAttribute("bankTran") @Valid BankTran bankTran, BindingResult result) {
        Map<String, Object> resultMap = getResultMap();
        if (!result.hasErrors()) {
            bankTranService.updateBankTran(bankTran);
        } else {
            setResultMapFailure(resultMap);
        }

        return resultMap;
    }

    /**
     * 配置错误码
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/resp", method = RequestMethod.GET)
    @RequiresPermissions("BANK_TRAN")
    public String resp(@PathVariable("id") Long id, Model model) {
        BankTran bankTran = bankTranService.findBankTranById(id);
        List<BankResp> bankResps = bankRespService.findAllBankRespsByBnkCo(bankTran.getBnkCo());

        model.addAttribute("bankResps", bankResps);
        model.addAttribute("bankTran", bankTran);
        return getPathRoot() + "/resp-modal";
    }

    /**
     * 配置错误码
     *
     * @param id
     * @param respCo
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/resp", method = RequestMethod.POST)
    @RequiresPermissions("BANK_TRAN")
    @ResponseBody
    public Map<String, Object> resp(@PathVariable("id") Long id,
                                    @RequestParam(value = "respCo", required = false, defaultValue = "") String respCo) {
        BankTran bankTran = bankTranService.findBankTranById(id);
        if (StringUtils.isNotEmpty(respCo)) {
            BankResp bankResp = bankRespService.findBankRespByRespCoAndBakCo(respCo, bankTran.getBnkCo());
            bankTran.setRespCo(respCo);
            bankTran.setRespMsg(bankResp.getRespMsg());
        } else {
            bankTran.setRespCo("");
            bankTran.setRespMsg("");
        }

        bankTranService.updateBankTran(bankTran);
        return getResultMap();
    }

}
