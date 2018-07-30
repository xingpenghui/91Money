package com.qfedu.core.util;

import com.qfedu.core.redis.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 *@Author feri
 *@Date Created in 2018/7/27 17:05
 */
public class LoginUtil {
    @Autowired
    private JedisUtil jedisUtil;

    public String getLogin(HttpServletRequest request){
        String tk=CookieUtil.getCk(request,"userauth");
        return jedisUtil.getStr("usertoken:"+tk);
    }
}
