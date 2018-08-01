package com.qfedu.controller;

import com.qfedu.core.util.EncrypUtil;
import com.qfedu.core.vo.R;
import com.qfedu.service.admin.SysLogService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 *@Author feri
 *@Date Created in 2018/7/30 14:26
 */
@Controller
public class SysLoginController {
    @RequestMapping("/sys/login")
    @ResponseBody
    public R login(@RequestBody Map<String, String> userinfo) {
        String username = userinfo.get("username");
        String password = userinfo.get("password");
        String rememberMeStr = userinfo.get("rememberMe");
        Boolean rememberMe = false;
        if(rememberMeStr != null) {
            rememberMe = true;
        }
        try {
            Subject subject = SecurityUtils.getSubject();
            password = EncrypUtil.md5Pass(password);
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(rememberMe);
            subject.login(token);
        } catch (UnknownAccountException e) {
            return R.setError(e.getMessage());
        }catch (IncorrectCredentialsException e) {
            return R.setError(e.getMessage());
        }catch (LockedAccountException e) {
            return R.setError(e.getMessage());
        }catch (AuthenticationException e) {
            return R.setError(e.getMessage());
        }
        return R.setOK("登录成功");
    }

    @RequestMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:login.html";
    }

}
