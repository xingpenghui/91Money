package com.qfedu.sso.controller;

import com.qfedu.core.util.CookieUtil;
import com.qfedu.core.vo.R;
import com.qfedu.domain.user.User;
import com.qfedu.sso.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/7/24 14:51
 */
@RestController
public class UserController {

    //创建日志对象
    private Logger logger=LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService service;
    //注册
    @PostMapping("/user")
    public R save(User user)
    {
        logger.info("新增用户：",user);
        return service.save(user);
    }

    //查询
    @GetMapping("/users")
    public List<User> queryAll(){
        return service.query();
    }

    //登录
    @PostMapping("/userlogin")
    public R login(String name, String password,HttpServletRequest request, HttpServletResponse response){
        R r =service.ssoLogin(name,password);
        System.out.println(r.getCode()+"-->"+r.getMsg());
        if(r.getCode()==0){
            CookieUtil.setCK(response,"userauth",r.getMsg());
        }
        return r;
    }
    //检查登录
    @GetMapping("/usercheck")
    public R checkLogin(HttpServletRequest request,HttpServletResponse response){
        String tk=CookieUtil.getCk(request,"userauth");
        R r= service.ssoCheck(tk);
        if(r.getCode()!=0){
            CookieUtil.delCK(response,"userauth");
        }
        return r;
    }
    //注销
    @GetMapping("/userout")
    public R loginout(HttpServletRequest request,HttpServletResponse response){
        String tk=CookieUtil.getCk(request,"userauth");
        R r= service.loginOut(tk);
        if(r.getCode()==0){
            CookieUtil.delCK(response,"userauth");
        }
        return r;
    }
}
