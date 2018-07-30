package com.qfedu.controller;

import com.qfedu.core.vo.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *@Author feri
 *@Date Created in 2018/7/30 00:12
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseController{
    /**
     * 获取登录的用户信息
     */
    @GetMapping("/info")
    public R info(){
        //return R.ok().put("user", getUser());
        return new R(0,"",getUser());
    }
}
