package com.qfedu.controller;

import com.qfedu.service.admin.SysConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *@Author feri
 *@Date Created in 2018/7/30 00:05
 */
@RestController
@RequestMapping("/sys/config")
public class SysConfigController {
    @Autowired
    private SysConfigService sysConfigService;
}
