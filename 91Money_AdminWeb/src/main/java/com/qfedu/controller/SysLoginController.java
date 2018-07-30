package com.qfedu.controller;

import com.qfedu.core.shiro.ShiroUtil;
import com.qfedu.core.util.EncrypUtil;
import com.qfedu.core.vo.R;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.IOException;
import java.util.Map;

/**
 *@Author feri
 *@Date Created in 2018/7/30 00:05
 */
@Controller
public class SysLoginController {
    /**
     * 登录
     */
    @ResponseBody
    @PostMapping("/sysuserlogin")
    public R login(@RequestBody Map<String, String> map)throws IOException {
        String username = map.get("username");
        String password = map.get("password");
        String rememberMe = map.get("rememberMe");
        Boolean remember = false;
        if(rememberMe != null) {
            remember = true;
        }
        try{
            Subject subject = ShiroUtil.getSubject();
            //加密用户输入的密码
            password = EncrypUtil.md5Pass(password);
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(remember);
            subject.login(token);
        }catch (UnknownAccountException e) {
            return R.setError(e.getMessage());
        }catch (IncorrectCredentialsException e) {
            return R.setError(e.getMessage());
        }catch (LockedAccountException e) {
            return R.setError(e.getMessage());
        }catch (AuthenticationException e) {
            return R.setError("账户验证失败");
        }
        return R.setOK("登录成功");
    }

    /**
     * 退出
     */
    @GetMapping("logout")
    public String logout() {
        ShiroUtil.logout();
        return "redirect:login.html";
    }
}
