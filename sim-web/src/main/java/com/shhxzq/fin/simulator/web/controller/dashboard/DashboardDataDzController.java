package com.shhxzq.fin.simulator.web.controller.dashboard;

import com.github.pagehelper.PageInfo;
import com.shhxzq.fin.simulator.biz.service.BankChannelService;
import com.shhxzq.fin.simulator.biz.service.DzFileService;
import com.shhxzq.fin.simulator.model.vo.BankChannel;
import com.shhxzq.fin.simulator.model.vo.BankCommand;
import com.shhxzq.fin.simulator.model.vo.DzFile;
import com.shhxzq.fin.simulator.model.vo.User;
import com.shhxzq.fin.simulator.web.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2017/1/11
 */
@Controller
@RequestMapping("dashboard/data/dz")
public class DashboardDataDzController extends BaseController {

    @Autowired
    private DzFileService dzFileService;

    @Autowired
    private BankChannelService bankChannelService;

    /**
     * 对账文件界面
     *
     * @param pageNum
     * @param bnkCo
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("DATA_DZ")
    public String index(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNum,
                        @RequestParam(value = "bnkCo", required = false, defaultValue = "") String bnkCo,
                        Model model) {
        List<BankChannel> bankChannels = bankChannelService.findAllBankChannels();
        List<DzFile> dzFiles = dzFileService.searchDzFiles(pageNum, bnkCo);
        PageInfo<User> page = new PageInfo(dzFiles);

        model.addAttribute("page", page);
        model.addAttribute("bankChannels", bankChannels);
        return getPathList();
    }

}
