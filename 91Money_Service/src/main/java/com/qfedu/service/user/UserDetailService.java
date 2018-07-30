package com.qfedu.service.user;

import com.qfedu.core.vo.R;
import com.qfedu.domain.oss.OSSPo;
import com.qfedu.domain.user.UserDetail;
import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/7/27 15:11
 */
public interface UserDetailService {
    //新增
    R save(int uid);
    //修改
    R update(UserDetail detail);
    //查询详情
    UserDetail queryByUid(int uid);
    //查询未审核的用户信息
    List<UserDetail> queryByFlag(int flag);

    R realNameAuth(int uid);
    //保存身份证
    R save(OSSPo po);

}
