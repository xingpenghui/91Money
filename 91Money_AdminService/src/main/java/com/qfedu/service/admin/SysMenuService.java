package com.qfedu.service.admin;

import com.qfedu.core.vo.DataGridResult;
import com.qfedu.core.vo.Query;
import com.qfedu.domain.admin.SysMenu;

import java.util.List;
import java.util.Set;

/**
 *@Author feri
 *@Date Created in 2018/7/29 23:39
 */
public interface SysMenuService {
    //分页业务方法
    DataGridResult getPageList(Query query);

    void deleteBatch(Long[] ids);

    SysMenu getById(Long menuId);

    void save(SysMenu menu);

    void update(SysMenu menu);

    List<SysMenu> getNotButtonMenuList();

    List<SysMenu> findAll();

    List<String> getUserPermsList(Long userId);

    List<SysMenu> getTopMenuList();

    List<SysMenu> findUserMenuList(Long userId);
}
