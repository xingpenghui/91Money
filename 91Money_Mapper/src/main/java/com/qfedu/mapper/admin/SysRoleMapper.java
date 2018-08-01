package com.qfedu.mapper.admin;

import com.qfedu.core.vo.Query;
import com.qfedu.domain.admin.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/7/29 23:40
 */
public interface SysRoleMapper {
    int insert(SysRole record);
    SysRole selectByPrimaryKey(Long roleId);
    int updateByPrimaryKey(SysRole record);
    List<SysRole> queryByPage(@Param("index") int index, @Param("count") int count);
    Long queryCount();
    List<SysRole> queryAll();
    List<String> selectRoleNameList(Long userId);
    int deleteBatch(Long[] roleIds);
}
