package com.qfedu.mapper.admin;

import com.qfedu.domain.admin.SysRoleMenu;

import java.io.Serializable;
import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/7/29 23:40
 */
public interface SysRoleMenuMapper {
    int insert(SysRoleMenu record);
    SysRoleMenu selectByPrimaryKey(Long id);
    int updateByPrimaryKey(SysRoleMenu record);
    List<Long> queryMenuIdList(long rid);
    //根据菜单删除所有关联的角色
    Integer deleteByMenuIds(Long[] mids);
    //根据角色删除所有关联的菜单
    Integer deleteByRoleIds(Long[] roleIds);
    //删除单个
    int deleteByRoleId(long rid);
}
