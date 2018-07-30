package com.qfedu.service.admin;

import com.qfedu.core.vo.DataGridResult;
import com.qfedu.core.vo.Query;
import com.qfedu.domain.admin.SysUser;

import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/7/29 23:41
 */
public interface SysUserService {
    //分页业务方法
    DataGridResult getPageList(Query query);

    void deleteBatch(Long[] ids);

    SysUser getById(Long userId);

    void save(SysUser user);

    void update(SysUser user);

    SysUser getByUsername(String username);
}
