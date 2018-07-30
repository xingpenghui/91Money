package com.qfedu.controller;

import com.qfedu.core.shiro.ShiroUtil;
import com.qfedu.domain.admin.SysUser;

/**
 *@Author feri
 *@Date Created in 2018/7/29 23:26
 */
public abstract class BaseController {
    protected SysUser getUser() {
        return (SysUser) ShiroUtil.getSession().getAttribute("sysuser");
    }

    protected Long getUserId() {
        return getUser().getUserId();
    }
}
