package com.qfedu.mapper.admin;

import com.qfedu.core.vo.Query;
import com.qfedu.domain.admin.SysRole;

import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/7/29 23:40
 */
public interface SysRoleMapper {
    int insert(SysRole record);
    SysRole selectByPrimaryKey(Long roleId);
    int updateByPrimaryKey(SysRole record);
    List<SysRole> queryByPage(int index,int count);
    Long queryCount();
    List<SysRole> queryAll();
    List<String> selectRoleNameList(Long userId);
    int deleteBatch(Long[] roleIds);
}
