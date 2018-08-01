package com.qfedu.service.admin.impl;

import com.qfedu.core.vo.DataGridResult;
import com.qfedu.core.vo.Query;
import com.qfedu.mapper.admin.SysMenuMapper;
import com.qfedu.mapper.admin.SysRoleMenuMapper;
import com.qfedu.mapper.admin.SysUserMapper;
import com.qfedu.domain.admin.SysMenu;
import com.qfedu.service.admin.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.*;

/**
 *@Author feri
 *@Date Created in 2018/7/29 23:36*/
@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuMapper menuMapper;

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    @Override
    public DataGridResult getPageList(Query query) {

        Integer offset = (Integer)query.get("offset");
        Integer limit = (Integer)query.get("limit");

        //调用Dao查询分页列表数据
        List<SysMenu> rows = menuMapper.queryByPage(offset, limit);
        //调用Dao查询总记录数
        Long total = (Long)menuMapper.queryCount();
        //创建DataGridResult对象
        DataGridResult dataGridResult = new DataGridResult(rows, total);
        return dataGridResult;
    }

    @Override
    public void deleteBatch(Long[] menuIds) {
        menuMapper.deleteBatch(menuIds);
        roleMenuMapper.deleteByMenuIds(menuIds);
    }

    @Override
    public SysMenu getById(Long menuId) {
        return menuMapper.selectByPrimaryKey(menuId);
    }

    @Override
    public void save(SysMenu menu) {
        if(menu.getParentMenu() != null && menu.getParentMenu().getMenuId() == null ) {
            menu.setParentMenu(null);
        }
        menuMapper.insert(menu);
    }

    @Override
    public void update(SysMenu menu) {
        menuMapper.updateByPrimaryKey(menu);
    }

    @Override
    public List<SysMenu> getNotButtonMenuList() {
        return menuMapper.queryNotButtonList();
    }

    @Override
    public List<SysMenu> findAll() {
        return menuMapper.queryListAll();
    }

    @Override
    public List<String> getUserPermsList(Long userId) {
        List<String> userPermsList = menuMapper.queryAllPerms(userId);
        List<String> finalPermsList = new ArrayList<String>();
        for (int i = 0; i < userPermsList.size(); i++) {
            String perms = userPermsList.get(i);
            if(StringUtils.isEmpty(perms)) {
                continue;
            }
            finalPermsList.addAll(Arrays.asList(perms.split(",")));
        }
        System.out.println("权限："+finalPermsList);
        return finalPermsList;
    }

    @Override
    public List<SysMenu> getTopMenuList() {
        return menuMapper.queryTopMenuList();
    }

    @Override
    public List<SysMenu> findUserMenuList(Long userId) {
        List<SysMenu> menuList = new ArrayList<SysMenu>();
        //系统管理员，拥有最高权限
        if(userId == 1){
            menuList = menuMapper.queryAllTop();
            System.err.println("菜单："+menuList);
            for (int i = 0; i < menuList.size(); i++) {
                List<SysMenu> subMenu = menuMapper.queryListParentId(menuList.get(i).getMenuId());
                System.err.println("菜单子祥："+menuList);
                menuList.get(i).setChildren(subMenu);
            }
            return menuList;
        }
        //用户菜单列表
        List<Long> menuIdList = menuMapper.queryAllMenuId(userId);
        menuList = menuMapper.queryUserTop(menuIdList);
        for (int i = 0; i < menuList.size(); i++) {
            List<SysMenu> subMenu = menuMapper.queryUserMenuByParentId(menuList.get(i).getMenuId(), menuIdList);
            menuList.get(i).setChildren(subMenu);
        }
        return menuList;
    }
}
