package com.qfedu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *@Author feri
 *@Date Created in 2018/7/31 00:33
 */
@Controller
@RequestMapping("/sys/auth")
public class UserAuthController {
    @RequestMapping("/{page}")
    public String listPage(@PathVariable String page){
        return "sys/auth/"+page;
    }

}
