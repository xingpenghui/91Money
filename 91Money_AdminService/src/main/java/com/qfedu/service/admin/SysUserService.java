package com.qfedu.service.admin;

import com.qfedu.domain.admin.SysUser;

import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/7/29 23:41
 */
public interface SysUserService {
    /**
     * 根据用户名，查询系统用户
     */
    SysUser queryByUserName(String username);

    /**
     * 查询用户的所有权限
     * @param userId  用户ID
     */
    List<String> queryAllPerms(Long userId);
}
