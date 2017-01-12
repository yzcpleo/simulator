package com.shhxzq.fin.simulator.web.controller.dashboard;

import com.github.pagehelper.PageInfo;
import com.shhxzq.fin.simulator.biz.service.BankChannelService;
import com.shhxzq.fin.simulator.biz.service.BankTranService;
import com.shhxzq.fin.simulator.biz.service.DzFileService;
import com.shhxzq.fin.simulator.model.vo.BankChannel;
import com.shhxzq.fin.simulator.model.vo.BankTran;
import com.shhxzq.fin.simulator.model.vo.DzFile;
import com.shhxzq.fin.simulator.model.vo.User;
import com.shhxzq.fin.simulator.web.controller.BaseController;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author kangyonggan
 * @since 2017/1/11
 */
@Controller
@RequestMapping("dashboard/data/dz")
@Log4j2
public class DashboardDataDzController extends BaseController {

    @Autowired
    private DzFileService dzFileService;

    @Autowired
    private BankChannelService bankChannelService;

    @Autowired
    private BankTranService bankTranService;

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

    /**
     * 手动生成对账文件
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "gen", method = RequestMethod.GET)
    public String gen(Model model) {
        List<BankTran> bankTrans = bankTranService.findbankTran4Gen();

        model.addAttribute("bankTrans", bankTrans);
        return getPathRoot() + "/gen-modal";
    }

    /**
     * 手动生成对账文件提交
     *
     * @param id
     * @param workDay
     * @return
     */
    @RequestMapping(value = "gen", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> gen(@RequestParam("id") Long id, @RequestParam("workDay") String workDay) {
        dzFileService.saveDzFile(id, workDay);
        return getResultMap();
    }

    /**
     * 手动生成所有的对账文件
     *
     * @return
     */
    @RequestMapping(value = "genall", method = RequestMethod.GET)
    public String genall() {
        return getPathRoot() + "/genall-modal";
    }

    /**
     * 手动生成所有的对账文件提交
     *
     * @param workDay
     * @return
     */
    @RequestMapping(value = "genall", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> genall(@RequestParam("workDay") String workDay) {
        dzFileService.saveDzFiles(workDay);
        return getResultMap();
    }

    /**
     * 手动推送
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/push", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> push(@PathVariable("id") Long id) {
        Map<String, Object> resultMap = getResultMap();
        try {
            dzFileService.pushDzFile(id);
        } catch (Exception e) {
            log.error("对账文件推送失败", e);
            setResultMapFailure(resultMap, "对账文件推送失败，请联系管理员!");
        }
        return resultMap;
    }

    /**
     * 手动批量推送
     *
     * @return
     */
    @RequestMapping(value = "pushall", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> pushall() {
        Map<String, Object> resultMap = getResultMap();
        try {
            dzFileService.pushDzFiles();
        } catch (Exception e) {
            log.error("对账文件批量推送失败", e);
            setResultMapFailure(resultMap, "对账文件批量推送失败，请联系管理员!");
        }
        return resultMap;
    }

}
