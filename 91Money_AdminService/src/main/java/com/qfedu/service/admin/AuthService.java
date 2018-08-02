package com.qfedu.service.admin;

import com.qfedu.core.vo.DataGridResult;
import com.qfedu.core.vo.Query;
import com.qfedu.core.vo.R;

/**
 *@Author feri
 *@Date Created in 2018/8/2 00:37
 */
public interface AuthService {
    //分页业务方法
    DataGridResult getPageList(Query query);
    R update(int flag,int id);

}
