package com.shhxzq.fin.simulator.web.controller.dashboard;

import com.github.pagehelper.PageInfo;
import com.shhxzq.fin.simulator.biz.service.BankChannelService;
import com.shhxzq.fin.simulator.biz.service.BankRespService;
import com.shhxzq.fin.simulator.model.vo.BankChannel;
import com.shhxzq.fin.simulator.model.vo.BankResp;
import com.shhxzq.fin.simulator.model.vo.User;
import com.shhxzq.fin.simulator.web.controller.BaseController;
import com.shhxzq.fin.simulator.web.util.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author kangyonggan
 * @since 2017/1/10
 */
@Controller
@RequestMapping("dashboard/bank/resp")
public class DashboardBankRespController extends BaseController {

    @Autowired
    private BankRespService bankRespService;

    @Autowired
    private BankChannelService bankChannelService;

    /**
     * 错误码配置界面
     *
     * @param pageNum
     * @param bnkCo
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("BANK_RESP")
    public String index(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNum,
                        @RequestParam(value = "bnkCo", required = false, defaultValue = "") String bnkCo,
                        Model model) {
        List<BankChannel> bankChannels = bankChannelService.findAllBankChannels();
        List<BankResp> bankResps = bankRespService.searchBankResps(pageNum, bnkCo);
        PageInfo<User> page = new PageInfo(bankResps);

        model.addAttribute("page", page);
        model.addAttribute("bankChannels", bankChannels);
        return getPathList();
    }

    /**
     * 添加错误码
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    @RequiresPermissions("BANK_RESP")
    public String create(Model model) {
        List<BankChannel> bankChannels = bankChannelService.findAllBankChannels();

        model.addAttribute("bankChannels", bankChannels);
        model.addAttribute("bankResp", new BankResp());
        return getPathFormModal();
    }

    /**
     * 保存错误码
     *
     * @param bankResp
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("BANK_RESP")
    public Map<String, Object> save(@ModelAttribute("bankResp") @Valid BankResp bankResp, BindingResult result) {
        Map<String, Object> resultMap = getResultMap();
        if (!result.hasErrors()) {
            bankRespService.saveBankResp(bankResp);
        } else {
            setResultMapFailure(resultMap);
        }

        return resultMap;
    }

    /**
     * 编辑错误码
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/edit", method = RequestMethod.GET)
    @RequiresPermissions("BANK_RESP")
    public String create(@PathVariable("id") Long id, Model model) {
        List<BankChannel> bankChannels = bankChannelService.findAllBankChannels();

        model.addAttribute("bankChannels", bankChannels);
        model.addAttribute("bankResp", bankRespService.findBankRespById(id));
        return getPathFormModal();
    }

    /**
     * 更新错误码
     *
     * @param bankResp
     * @param result
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("BANK_RESP")
    public Map<String, Object> update(@ModelAttribute("bankResp") @Valid BankResp bankResp, BindingResult result) {
        Map<String, Object> resultMap = getResultMap();
        if (!result.hasErrors()) {
            bankRespService.updateBankResp(bankResp);
        } else {
            setResultMapFailure(resultMap);
        }

        return resultMap;
    }

    /**
     * 物理删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/delete", method = RequestMethod.GET)
    @ResponseBody
    public void delete(@PathVariable("id") Long id) {
        bankRespService.deleteBankRespById(id);
    }

    /**
     * 导入错误码界面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "import", method = RequestMethod.GET)
    @RequiresPermissions("BANK_RESP")
    public String importResps(Model model) {
        List<BankChannel> bankChannels = bankChannelService.findAllBankChannels();

        model.addAttribute("bankChannels", bankChannels);
        return getPathRoot() + "/import-modal";
    }

    /**
     * 导入错误码
     *
     * @param bnkCo
     * @param excel
     * @return
     * @throws FileUploadException
     */
    @RequestMapping(value = "import", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("BANK_RESP")
    public Map<String, Object> importResps(@RequestParam("bnkCo") String bnkCo,
                                           @RequestParam("excel") MultipartFile excel) throws FileUploadException {
        Map<String, Object> resultMap = getResultMap();

        if (excel != null && !excel.isEmpty()) {
            String fileName = FileUpload.upload(excel);

            bankRespService.saveBankResps(bnkCo, fileName);
        } else {
            setResultMapFailure(resultMap);
        }

        return resultMap;
    }
}
