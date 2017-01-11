package com.shhxzq.fin.simulator.web.controller.dashboard;

import com.shhxzq.fin.simulator.biz.service.UserService;
import com.shhxzq.fin.simulator.model.vo.ShiroUser;
import com.shhxzq.fin.simulator.model.vo.User;
import com.shhxzq.fin.simulator.web.controller.BaseController;
import com.shhxzq.fin.simulator.web.util.FileUpload;
import com.shhxzq.fin.simulator.web.util.Images;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author kangyonggan
 * @since 2016/12/10
 */
@Controller
@RequestMapping("dashboard/user")
public class DashboardUserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 修改密码
     *
     * @return
     */
    @RequestMapping(value = "password", method = RequestMethod.GET)
    @RequiresPermissions("USER_PASSWORD")
    public String password() {
        return getPathRoot() + "/password";
    }

    /**
     * 修改密码
     *
     * @param password
     * @return
     */
    @RequestMapping(value = "password", method = RequestMethod.POST)
    @RequiresPermissions("USER_PASSWORD")
    @ResponseBody
    public Map<String, Object> password(@RequestParam("password") String password) {
        User user = userService.findUserById(userService.getShiroUser().getId());
        user.setPassword(password);
        userService.updateUserPassword(user);

        return getResultMap();
    }

    /**
     * 个人资料
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "profile", method = RequestMethod.GET)
    @RequiresPermissions("USER_PROFILE")
    public String profile(Model model) {
        ShiroUser user = userService.getShiroUser();

        model.addAttribute("user", userService.findUserById(user.getId()));
        return getPathRoot() + "/profile";
    }

    /**
     * 个人资料
     *
     * @param user
     * @param result
     * @param avatar
     * @return
     * @throws FileUploadException
     */
    @RequestMapping(value = "profile", method = RequestMethod.POST)
    @RequiresPermissions("USER_PROFILE")
    @ResponseBody
    public Map<String, Object> profile(@ModelAttribute("user") @Valid User user, BindingResult result,
                                       @RequestParam(value = "avatar", required = false) MultipartFile avatar) throws FileUploadException {
        ShiroUser shiroUser = userService.getShiroUser();
        user.setId(shiroUser.getId());
        Map<String, Object> resultMap = getResultMap();

        if (!result.hasErrors()) {
            if (avatar != null && !avatar.isEmpty()) {
                String fileName = FileUpload.upload(avatar);

                String large = Images.large(fileName);
                user.setLargeAvatar(large);
                String middle = Images.middle(fileName);
                user.setMediumAvatar(middle);
                String small = Images.small(fileName);
                user.setSmallAvatar(small);
            }

            userService.updateUser(user);
            resultMap.put("user", userService.findUserById(shiroUser.getId()));
        } else {
            setResultMapFailure(resultMap);
        }

        return resultMap;
    }

}
