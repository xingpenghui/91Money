package com.qfedu.service.admin.impl;

import com.qfedu.mapper.admin.SysConfigMapper;
import com.qfedu.domain.admin.SysConfig;
import com.qfedu.service.admin.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/7/29 23:36
 */
@Service
public class SysConfigServiceImpl implements SysConfigService {

    @Autowired
    private SysConfigMapper sysConfigMapper;

    public void setSysConfigMapper(SysConfigMapper sysConfigMapper) {
        this.sysConfigMapper = sysConfigMapper;
    }

    @Override
    public List<SysConfig> queryByKeyPrefix(String keyPrefix) {
        return sysConfigMapper.queryByKeyPrefix(keyPrefix);
    }
}
