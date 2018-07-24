package com.qfedu.sso.controller;

import com.qfedu.core.vo.R;
import com.qfedu.domain.user.User;
import com.qfedu.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *@Author feri
 *@Date Created in 2018/7/24 14:51
 */
@RestController
public class UserController {

    @Autowired
    private UserService service;
    @PostMapping("/user")
    public R save(@RequestBody User user){
        return service.save(user);
    }
}
