package com.qfedu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *@Author feri
 *@Date Created in 2018/7/30 12:06
 */
@Controller
public class IndexController {
    @RequestMapping("/index")
    public String index() {
        System.out.println("主页");
        return "index";
    }

    @RequestMapping("/home")
    public String home() {
        return "home";
    }
}
