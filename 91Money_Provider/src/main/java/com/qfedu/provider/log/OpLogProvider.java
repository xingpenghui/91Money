package com.qfedu.provider.log;

import com.qfedu.core.util.ExecuteUtils;
import com.qfedu.core.vo.R;
import com.qfedu.domain.user.OpLog;
import com.qfedu.mapper.user.OpLogMapper;
import com.qfedu.service.log.OpLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/7/27 15:46
 */
@Service
public class OpLogProvider implements OpLogService {


    @Autowired
    private OpLogMapper mapper;
    @Override
    public R save(OpLog log)
    {
        return ExecuteUtils.getR(mapper.insert(log),"新增日志成功");
    }

    @Override
    public List<OpLog> queryAll(int type) {
        return mapper.selectByType(type);
    }
}
