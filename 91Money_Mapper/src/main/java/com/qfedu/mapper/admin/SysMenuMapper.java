package com.qfedu.mapper.admin;

import com.qfedu.core.vo.Query;
import com.qfedu.domain.admin.SysMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/7/29 23:39
 */
public interface SysMenuMapper {
    int insert(SysMenu record);
    SysMenu selectByPrimaryKey(Long menuId);
    int updateByPrimaryKey(SysMenu record);
    List<SysMenu> queryListAll();
    List<SysMenu> queryByPage(@Param("index") int index, @Param("count") int count);
    long queryCount();
    int deleteBatch(Long[] menuIds);
    /**
     * 获取不包含按钮的菜单列表
     */
    List<SysMenu> queryNotButtonList();
    List<SysMenu> queryTopMenuList();
    List<SysMenu> queryAllTop();
    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     */
    List<SysMenu> queryListParentId(Long parentId);
    /**
     * 查询用户的所有权限
     * @param userId  用户ID
     */
    List<String> queryAllPerms(Long userId);
    /**
     * 查询用户的所有菜单ID
     */
    List<Long> queryAllMenuId(Long userId);
    List<SysMenu> queryUserTop(List<Long> menuIdList);
    List<SysMenu> queryUserMenuByParentId(@Param("mid")Long menuId, @Param("mlist") List<Long> menuIdList);
}
