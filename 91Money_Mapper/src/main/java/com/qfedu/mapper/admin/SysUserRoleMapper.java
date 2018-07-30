package com.qfedu.mapper.admin;

import com.qfedu.domain.admin.SysUserRole;

import java.io.Serializable;
import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/7/29 23:41
 */
public interface SysUserRoleMapper {
    int insert(SysUserRole record);
    SysUserRole selectByPrimaryKey(Long id);
    int updateByPrimaryKey(SysUserRole record);
    List<Long> queryRoleIdList(long userId);
    Integer deleteByUserId(long userId);
    Integer deleteByRoleIds(Long[] roleIds);
    Integer deleteByUserIds(Long[] userIds);
}
