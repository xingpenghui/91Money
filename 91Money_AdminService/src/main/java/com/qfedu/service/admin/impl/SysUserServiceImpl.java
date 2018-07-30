package com.qfedu.service.admin.impl;

import com.qfedu.mapper.admin.SysMenuMapper;
import com.qfedu.mapper.admin.SysUserMapper;
import com.qfedu.domain.admin.SysUser;
import com.qfedu.service.admin.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/7/29 23:36
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public SysUser queryByUserName(String username) {
        return sysUserMapper.queryByUserName(username);
    }

    @Override
    public List<String> queryAllPerms(Long userId) {
        return sysMenuMapper.queryAllPerms(userId);
    }
}
