package com.qfedu.provider.log;

import com.qfedu.core.util.ExecuteUtils;
import com.qfedu.core.vo.R;
import com.qfedu.domain.user.LoginLog;
import com.qfedu.mapper.user.LoginLogMapper;
import com.qfedu.service.log.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/7/27 15:45
 */
public class LoginLogProvider implements LoginLogService {

    @Autowired
    private LoginLogMapper mapper;
    @Override
    public R save(LoginLog log) {
        return ExecuteUtils.getR(mapper.insert(log),"新增日志成功");
    }

    @Override
    public List<LoginLog> queryAll() {
        return mapper.selctAll();
    }
}
