package com.qfedu.service.log;

import com.qfedu.core.vo.R;
import com.qfedu.domain.user.OpLog;

import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/7/27 15:46
 */
public interface OpLogService {
    //新增
    R save(OpLog log);
    //查询
    List<OpLog> queryAll(int type);
}
