package com.qfedu.controller;

import com.qfedu.core.vo.R;
import com.qfedu.domain.admin.SysMenu;
import com.qfedu.service.admin.SysMenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *@Author feri
 *@Date Created in 2018/7/30 00:08
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends BaseController{
    @Autowired
    private SysMenuService sysMenuService;


    @GetMapping("/listall")
    public List<SysMenu> getAll(){
        //查询列表数据
        return sysMenuService.queryListAll();
    }

//    @GetMapping("/list")
//    @RequiresPermissions(value={"sys:menu:list"})
//    public DataGridResult getPage(@RequestParam Map<String, Object> params){
//        //查询列表数据
//        Query query = new Query(params);//进一步处理参数
//        return sysMenuService.getPageList(query);
//    }

    @PostMapping("/del")
    @RequiresPermissions(value={"sys:menu:delete"})
    public R deleteBatch(@RequestBody Long[] menuIds) {
        for(Long menuId : menuIds){
            if(menuId.longValue() <= 31){
                return R.setError("系统菜单，不能删除");
            }
        }
        sysMenuService.deleteBatch(menuIds);
        return R.setOK("删除成功");
    }

    /**
     * 选择菜单(添加、修改菜单)
     */
    @GetMapping("/select")
    @RequiresPermissions(value={"sys:menu:select"})
    public R select(){
        //查询列表数据
        List<SysMenu> menuList = sysMenuService.queryNotButtonList();

        //添加顶级菜单
        SysMenu root = new SysMenu();
        root.setMenuId(0L);
        root.setName("一级菜单");
        root.setParentId(-1L);
        root.setOpen(true);
        menuList.add(root);
        return new R(0,"", menuList);
    }

    @PostMapping("/save")
    @RequiresPermissions(value={"sys:menu:save"})
    public R save(@RequestBody SysMenu menu){
        sysMenuService.save(menu);
        return R.setOK("新增成功");
    }

    /**
     * 菜单信息
     */
    @GetMapping("/info/{menuId}")
    @RequiresPermissions(value={"sys:menu:info"})
    public R info(@PathVariable("menuId") Long menuId){
        SysMenu menu = sysMenuService.queryObject(menuId);
//        return R.ok().put("menu", menu);
        return new R(0,"",menu);
    }

    @PostMapping("/update")
    @RequiresPermissions(value={"sys:menu:update"})
    public R update(@RequestBody SysMenu menu){

        sysMenuService.update(menu);
        return R.setOK("修改成功");
    }

    /**
     * 用户菜单列表
     */
    @GetMapping("/user")
    public R user(){
        Long userId = getUserId();
        List<SysMenu> menuList = sysMenuService.getUserMenuList(userId);
        Set<String> permissions = sysMenuService.getUserPermissions(userId);
//        return R.setOK("").put("menuList", menuList).put("permissions", permissions);
        return new R(0,"",menuList);
    }
}
