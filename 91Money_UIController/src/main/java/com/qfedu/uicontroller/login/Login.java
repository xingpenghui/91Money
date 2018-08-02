package com.qfedu.uicontroller.login;

import com.alibaba.fastjson.JSON;
import com.qfedu.core.redis.JedisUtil;
import com.qfedu.core.util.CookieUtil;
import com.qfedu.domain.user.User;

import javax.servlet.http.HttpServletRequest;

/**
 *@Author feri
 *@Date Created in 2018/7/27 17:33
 */
public class Login {
    public static User getU(JedisUtil jedisUtil, HttpServletRequest request){

        String json=jedisUtil.getStr("usertoken:"+CookieUtil.getCk(request,"userauth"));
        System.out.println("json---->"+json);
        return JSON.parseObject(json,User.class);
    }
}
