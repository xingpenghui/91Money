package com.qfedu.service.admin;

import com.qfedu.domain.admin.SysRole;

/**
 *@Author feri
 *@Date Created in 2018/7/29 23:40
 */
public interface SysRoleService {
   // DataGridResult getPageList(Query query);

    void deleteBatch(Long[] id);
}
