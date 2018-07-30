package com.qfedu.service.admin;

import com.qfedu.domain.admin.SysConfig;

import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/7/29 23:38
 */
public interface SysConfigService {
    /**
     * 根据主键前缀查询配置列表
     * @param keyPrefix
     * @return
     */
    List<SysConfig> queryByKeyPrefix(String keyPrefix);
}
