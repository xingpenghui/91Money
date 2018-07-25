package com.qfedu.sso.controller;

import com.qfedu.core.vo.R;
import com.qfedu.domain.user.User;
import com.qfedu.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/7/24 14:51
 */
@RestController
public class UserController {

    @Autowired
    private UserService service;
    //注册
    @PostMapping("/user")
    public R save(User user){
        return service.save(user);
    }

    //查询
    @GetMapping("/users")
    public List<User> queryAll(){
        return service.query();
    }
}
