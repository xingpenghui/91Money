package com.qfedu.controller;

import com.qfedu.core.vo.DataGridResult;
import com.qfedu.core.vo.Query;
import com.qfedu.core.vo.R;
import com.qfedu.core.vo.RM;
import com.qfedu.domain.admin.SysRole;
import com.qfedu.service.admin.SysRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 *@Author feri
 *@Date Created in 2018/7/30 12:07
 */
@Controller
@RequestMapping("/sys/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping("/{page}")
    public String listPage(@PathVariable String page){
        return "sys/role/"+page;
    }

    @RequestMapping("/list")
    @ResponseBody
    @RequiresPermissions({"sys:role:list"})
    public DataGridResult getPage(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);
        return sysRoleService.getPageList(query);
    }

    @RequestMapping("/delete")
    @ResponseBody
    @RequiresPermissions({"sys:role:delete"})
    public R deleteMore(@RequestBody Long[] ids){
        sysRoleService.deleteBatch(ids);
        return R.setOK("删除成功");
    }

    @RequestMapping("/info/{roleId}")
    @ResponseBody
    @RequiresPermissions({"sys:role:info"})
    public RM info(@PathVariable Long roleId){
        SysRole role = sysRoleService.getById(roleId);
        return RM.ok().put("role", role);
        //return new R(0,"角色详情", role);
    }

    @RequestMapping("/save")
    @ResponseBody
    @RequiresPermissions({"sys:role:save"})
    public R save(@RequestBody SysRole role){
        sysRoleService.save(role);
        return R.setOK("修改成功");
    }

    @RequestMapping("/update")
    @ResponseBody
    @RequiresPermissions({"sys:role:update"})
    public R update(@RequestBody SysRole role){
        sysRoleService.update(role);
        return R.setOK("修改成功");
    }

    /**
     * 角色列表
     */
    @RequiresPermissions({"sys:role:select"})
    @RequestMapping("/select_all")
    @ResponseBody
    public RM select(){
        List<SysRole> roleList = sysRoleService.findAll();
        return RM.ok().put("roleList", roleList);
       // return new R(0,"角色列表", roleList);
    }
}
