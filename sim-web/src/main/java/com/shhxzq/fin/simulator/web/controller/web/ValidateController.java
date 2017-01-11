package com.shhxzq.fin.simulator.web.controller.web;

import com.shhxzq.fin.simulator.biz.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kangyonggan
 * @since 2016/12/3
 */
@RestController
@RequestMapping("validate")
public class ValidateController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private BankChannelService bankChannelService;

    @Autowired
    private BankTranService bankTranService;

    @Autowired
    private BankRespService bankRespService;

    /**
     * 校验用户名是否可用
     *
     * @param username
     * @param oldUsername
     * @return
     */
    @RequestMapping(value = "user", method = RequestMethod.POST)
    public boolean validateUsername(@RequestParam("username") String username,
                                    @RequestParam("oldUsername") String oldUsername) {
        if (username.equals(oldUsername)) {
            return true;
        }

        return !userService.existsUsername(username);
    }

    /**
     * 校验角色代码是否可用
     *
     * @param code
     * @param oldCode
     * @return
     */
    @RequestMapping(value = "role", method = RequestMethod.POST)
    public boolean validateRoleCode(@RequestParam("code") String code,
                                    @RequestParam(value = "oldCode", required = false, defaultValue = "") String oldCode) {
        if (code.equals(oldCode)) {
            return true;
        }

        return !roleService.existsRoleCode(code);
    }

    /**
     * 校验菜单代码是否可用
     *
     * @param code
     * @param oldCode
     * @return
     */
    @RequestMapping(value = "menu", method = RequestMethod.POST)
    public boolean validateMenuCode(@RequestParam("code") String code,
                                    @RequestParam(value = "oldCode", required = false, defaultValue = "") String oldCode) {
        if (code.equals(oldCode)) {
            return true;
        }

        return !menuService.existsMenuCode(code);
    }

    /**
     * 校验银行代码是否可用
     *
     * @param bnkCo
     * @param oldBnkCo
     * @return
     */
    @RequestMapping(value = "bank", method = RequestMethod.POST)
    public boolean validateBankCo(@RequestParam("bnkCo") String bnkCo,
                                  @RequestParam(value = "oldBnkCo", required = false, defaultValue = "") String oldBnkCo) {
        if (bnkCo.equals(oldBnkCo)) {
            return true;
        }

        return !bankChannelService.existsBnkCo(bnkCo);
    }

    /**
     * 校验某银行交易码是否可用
     *
     * @param bnkCo
     * @param tranCo
     * @param oldTranCo
     * @return
     */
    @RequestMapping(value = "tran", method = RequestMethod.POST)
    public boolean validateBankTranCo(@RequestParam("bnkCo") String bnkCo,
                                      @RequestParam("tranCo") String tranCo,
                                      @RequestParam(value = "oldTranCo", required = false, defaultValue = "") String oldTranCo) {
        if (tranCo.equals(oldTranCo)) {
            return true;
        }

        return !bankTranService.existsTranCo(bnkCo, tranCo);
    }

    /**
     * 校验某银行错误码是否可用
     *
     * @param bnkCo
     * @param respCo
     * @param oldRespCo
     * @return
     */
    @RequestMapping(value = "resp", method = RequestMethod.POST)
    public boolean validateBankRespCo(@RequestParam("bnkCo") String bnkCo,
                                      @RequestParam("respCo") String respCo,
                                      @RequestParam(value = "oldRespCo", required = false, defaultValue = "") String oldRespCo) {
        if (respCo.equals(oldRespCo)) {
            return true;
        }

        return !bankRespService.existsRespCo(bnkCo, respCo);
    }


}