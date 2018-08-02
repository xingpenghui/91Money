package com.qfedu.service.admin.impl;

import com.qfedu.core.util.ExecuteUtils;
import com.qfedu.core.vo.DataGridResult;
import com.qfedu.core.vo.Query;
import com.qfedu.core.vo.R;
import com.qfedu.domain.admin.SysMenu;
import com.qfedu.domain.user.UserDetail;
import com.qfedu.mapper.user.UserDetailMapper;
import com.qfedu.service.admin.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/8/2 00:37
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserDetailMapper mapper;
    @Override
    public DataGridResult getPageList(Query query) {
        Integer offset = (Integer)query.get("offset");
        Integer limit = (Integer)query.get("limit");
        //调用Dao查询分页列表数据
        List<UserDetail> rows = mapper.queryByPage(offset, limit);
        //调用Dao查询总记录数
        Long total = (Long)mapper.queryCount();
        //创建DataGridResult对象
        DataGridResult dataGridResult = new DataGridResult(rows, total);
        return dataGridResult;
    }

    @Override
    public R update(int flag,int id) {
        return ExecuteUtils.getR(mapper.updateId(flag,id),"实名认证审核");
    }
}
