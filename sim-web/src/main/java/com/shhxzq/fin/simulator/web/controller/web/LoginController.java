package com.shhxzq.fin.simulator.web.controller.web;

import com.shhxzq.fin.simulator.model.constants.AppConstants;
import com.shhxzq.fin.simulator.model.vo.User;
import com.shhxzq.fin.simulator.web.controller.BaseController;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author kangyonggan
 * @since 2017/1/8
 */
@Controller
@RequestMapping("/")
@Log4j2
public class LoginController extends BaseController {

    /**
     * 登录界面
     *
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        return getPathIndex();
    }

    /**
     * 登录
     *
     * @param user
     * @param captcha
     * @param request
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(@RequestParam(value = "captcha") String captcha, User user, HttpServletRequest request) {
        Map<String, Object> resultMap = getResultMap();

        HttpSession session = request.getSession();
        String realCaptcha = (String) session.getAttribute(AppConstants.KEY_CAPTCHA);
        log.info("session中的验证码为：{}", realCaptcha);
        log.info("用户上送的验证码为：{}", captcha);

        if (!captcha.equalsIgnoreCase(realCaptcha)) {
            resultMap.put(ERR_CODE, FAILURE);
            resultMap.put(ERR_MSG, "验证码错误或已失效");
            return resultMap;
        }

        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        final Subject subject = SecurityUtils.getSubject();

        try {
            session.setMaxInactiveInterval(30 * 24 * 60 * 60);// 30天
            subject.login(token);
        } catch (UnknownAccountException uae) {
            log.error("未知用户名", uae);
            setResultMapFailure(resultMap, "未知用户名");
            return resultMap;
        } catch (IncorrectCredentialsException ice) {
            log.error("用户名或密码错误", ice);
            setResultMapFailure(resultMap, "用户名或密码错误");
            return resultMap;
        } catch (LockedAccountException lae) {
            log.error("账号被锁", lae);
            setResultMapFailure(resultMap, "账号被锁, 请联系管理员");
            return resultMap;
        } catch (DisabledAccountException dae) {
            log.error("账号已禁用", dae);
            setResultMapFailure(resultMap, "账号已禁用, 请联系管理员");
            return resultMap;
        } catch (Exception e) {
            log.error("未知异常", e);
            setResultMapFailure(resultMap);
            return resultMap;
        }

        String redirectUrl = "/dashboard";

        SavedRequest savedRequest = WebUtils.getSavedRequest(request);
        // 获取之前访问的URL
        if (savedRequest != null && savedRequest.getRequestUrl() != null) {
            redirectUrl = savedRequest.getRequestUrl();
        }
        resultMap.put(ERR_MSG, redirectUrl);

        // 清除验证码
        session.removeAttribute(AppConstants.KEY_CAPTCHA);
        return resultMap;
    }

    /**
     * 注销
     *
     * @return
     */
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout() {
        final Subject subject = SecurityUtils.getSubject();
        log.info("logout {}", subject.getPrincipal());
        subject.logout();
        return "redirect:/";
    }

}
