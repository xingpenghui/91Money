package com.qfedu.sso.service;

import com.qfedu.core.vo.R;
import com.qfedu.domain.user.User;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/7/24 14:37
 */
public interface UserService {
    //新增
    R save(User user);

    List<User> query();

    User queryBy(String name);

    //登录和检查
    R ssoLogin(String token, String name, String password, HttpServletResponse response, HttpSession session);

    //单点登录之登录
    R ssoLogin(String name, String password, HttpServletResponse response);

    //单点登录之检查是否登录
    R ssoCheck(String token,HttpServletResponse response);

    R loginOut(String token,HttpServletResponse response);

}
