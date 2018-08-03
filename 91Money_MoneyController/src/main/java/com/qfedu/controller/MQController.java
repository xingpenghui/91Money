package com.qfedu.controller;

import com.qfedu.core.vo.R;
import com.qfedu.service.MqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *@Author feri
 *@Date Created in 2018/8/3 10:31
 */
@RestController
public class MQController {

    @Autowired
    private MqService service;
    @GetMapping("/sendmsg")
    public R send(int type,String msg){
        return service.sendMsg(type,msg);
    }

}
