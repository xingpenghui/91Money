package com.qfedu.service.log;

import com.qfedu.core.vo.R;
import com.qfedu.domain.user.LoginLog;

import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/7/27 15:45
 */
public interface LoginLogService {
    //新增
    R save(LoginLog log);
    //查询
    List<LoginLog> queryAll();
}
