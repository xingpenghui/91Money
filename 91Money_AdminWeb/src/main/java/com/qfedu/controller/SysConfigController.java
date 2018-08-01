package com.qfedu.controller;

import com.qfedu.core.vo.DataGridResult;
import com.qfedu.core.vo.Query;
import com.qfedu.core.vo.R;
import com.qfedu.core.vo.RM;
import com.qfedu.domain.admin.SysConfig;
import com.qfedu.service.admin.SysConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 *@Author feri
 *@Date Created in 2018/7/30 12:06
 */
@Controller
@RequestMapping("/sys/config")
public class SysConfigController {
    @Autowired
    private SysConfigService sysConfigService;

    @RequestMapping("/{page}")
    public String index(@PathVariable String page) {
        return "sys/config/" +  page;
    }

    @RequestMapping("/list")
    @ResponseBody
    @RequiresPermissions({"sys:config:list"})
    public DataGridResult getPage(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        return sysConfigService.getPageList(query);
    }

    @RequestMapping("/delete")
    @ResponseBody
    @RequiresPermissions({"sys:config:delete"})
    public R deleteBatch(@RequestBody Long[] ids) {
        sysConfigService.deleteBatch(ids);
        return R.setOK("删除成功");
    }

    @RequestMapping("/save")
    @ResponseBody
    @RequiresPermissions({"sys:config:save"})
    public R save(@RequestBody SysConfig sysConfig) {
        sysConfigService.save(sysConfig);
        return R.setOK("新增成功");
    }

    @RequestMapping("/info/{id}")
    @ResponseBody
    @RequiresPermissions({"sys:config:info"})
    public RM save(@PathVariable Long id) {
        SysConfig sysConfig = sysConfigService.getById(id);
       return RM.ok().put("config", sysConfig);
        //return new R(0,"",sysConfig);
    }

    @RequestMapping("/update")
    @ResponseBody
    @RequiresPermissions({"sys:config:update"})
    public R update(@RequestBody SysConfig sysConfig) {
        sysConfigService.update(sysConfig);
        return R.setOK("修改成功");
    }
}
