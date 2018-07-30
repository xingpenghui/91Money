package com.qfedu.sso.service;

import com.qfedu.core.vo.R;
import com.qfedu.domain.user.User;

import javax.servlet.http.HttpServletRequest;
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
    //查询所有
    List<User> query();
    //查询
    User queryBy(String name);
    //单点登录之登录
    R ssoLogin(String name, String password);
    //单点登录之检查是否登录
    R ssoCheck(String token);
    //退出
    R loginOut(String token);
}
