package com.qfedu.service.admin.impl;

import com.qfedu.mapper.admin.SysLogMapper;
import com.qfedu.domain.admin.SysLog;
import com.qfedu.service.admin.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *@Author feri
 *@Date Created in 2018/7/29 23:36
 */
@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public void save(SysLog sysLog) {
        sysLogMapper.insertSelective(sysLog);
    }
}
