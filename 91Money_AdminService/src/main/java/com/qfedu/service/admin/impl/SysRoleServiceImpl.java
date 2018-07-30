package com.qfedu.service.admin.impl;

import com.qfedu.core.vo.DataGridResult;
import com.qfedu.core.vo.Query;
import com.qfedu.domain.admin.SysRoleMenu;
import com.qfedu.mapper.admin.SysRoleMapper;
import com.qfedu.domain.admin.SysRole;
import com.qfedu.mapper.admin.SysRoleMenuMapper;
import com.qfedu.mapper.admin.SysUserRoleMapper;
import com.qfedu.service.admin.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/7/29 23:36
 */
@Service
public class SysRoleServiceImpl implements SysRoleService{
    @Autowired
    private SysRoleMapper roleMapper;
    @Autowired
    private SysRoleMenuMapper roleMenuMapper;
    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Override
    public DataGridResult getPageList(Query query) {
        Integer offset = (Integer)query.get("offset");
        Integer limit = (Integer)query.get("limit");
        //调用Dao查询分页列表数据
        List<SysRole> rows = roleMapper.queryByPage(offset, limit);
        //调用Dao查询总记录数
        Long total = (Long)roleMapper.queryCount();
        //创建DataGridResult对象
        DataGridResult dataGridResult = new DataGridResult(rows, total);
        return dataGridResult;
    }

    @Override
    public void deleteBatch(Long[] roleIds) {
        roleMapper.deleteBatch(roleIds);
        roleMenuMapper.deleteByRoleIds(roleIds);
        userRoleMapper.deleteByRoleIds(roleIds);
    }

    @Override
    public SysRole getById(Long roleId) {
        SysRole role = roleMapper.selectByPrimaryKey(roleId);

        //查询角色对应的菜单
        List<Long> menuIdList = roleMenuMapper.queryMenuIdList(roleId);
        role.setMenuIdList(menuIdList);

        return role;
    }

    @Override
    public void save(SysRole role) {

        role.setCreateTime(new Date());
        roleMapper.insert(role);

        Long roleId = role.getRoleId();
        List<Long> menuIdList = role.getMenuIdList();
        if(menuIdList.size() == 0){
            return ;
        }

        //保存角色与菜单关系
        for (int i = 0; i < menuIdList.size(); i++) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setMenuId(menuIdList.get(i));
            sysRoleMenu.setRoleId(roleId);
            roleMenuMapper.insert(sysRoleMenu);
        }
    }

    @Override
    public void update(SysRole role) {
        roleMapper.updateByPrimaryKey(role);

        //先删除角色与菜单关系
        Long roleId = role.getRoleId();
        roleMenuMapper.deleteByRoleId(roleId);

        List<Long> menuIdList = role.getMenuIdList();
        if(menuIdList.size() == 0){
            return ;
        }

        //保存角色与菜单关系
        for (int i = 0; i < menuIdList.size(); i++) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setMenuId(menuIdList.get(i));
            sysRoleMenu.setRoleId(roleId);
            roleMenuMapper.insert(sysRoleMenu);
        }
    }

    @Override
    public List<SysRole> findAll() {
        return roleMapper.queryAll();
    }

    @Override
    public List<String> selectRoleNameList(Long userId) {
        return roleMapper.selectRoleNameList(userId);
    }
}
