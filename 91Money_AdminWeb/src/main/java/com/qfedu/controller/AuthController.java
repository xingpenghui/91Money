package com.qfedu.controller;

import com.qfedu.core.vo.DataGridResult;
import com.qfedu.core.vo.Query;
import com.qfedu.core.vo.R;
import com.qfedu.service.admin.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 *@Author feri
 *@Date Created in 2018/8/2 00:35
 */
@Controller
@RequestMapping("/sys/auth")
public class AuthController {
    @Autowired
    private AuthService service;

    @GetMapping("/list")
    @ResponseBody
    public DataGridResult list(@RequestParam Map<String, Object> params){
        Query query=new Query(params);
        return service.getPageList(query);
    }
    //2 审核通过 3 审核拒绝
    @GetMapping("/update")
    @ResponseBody
    public R update(int flag,int id){
        return service.update(flag,id);
    }
}
