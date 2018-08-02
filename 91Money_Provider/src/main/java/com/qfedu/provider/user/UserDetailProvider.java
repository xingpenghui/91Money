package com.qfedu.provider.user;

import com.qfedu.core.util.ExecuteUtils;
import com.qfedu.core.vo.R;
import com.qfedu.domain.oss.OSSPo;
import com.qfedu.domain.user.OpLog;
import com.qfedu.domain.user.UserDetail;
import com.qfedu.mapper.oss.OSSPoMapper;
import com.qfedu.mapper.user.OpLogMapper;
import com.qfedu.mapper.user.UserDetailMapper;
import com.qfedu.service.user.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/7/27 15:11
 */
@Service
public class UserDetailProvider implements UserDetailService {

    @Autowired
    private UserDetailMapper mapper;
    @Autowired
    private OSSPoMapper ossPoMapper;
    @Autowired
    private OpLogMapper logMapper;

    @Override
    public R save(int uid) {
        return ExecuteUtils.getR(mapper.insert(uid),"初始化用户详情完成");
    }

    @Override
    public R update(UserDetail detail) {
        System.err.println("ud--->"+detail);
        return ExecuteUtils.getR(mapper.updateById(detail),"个人实名认证完成");
    }

    @Override
    public UserDetail queryByUid(int uid) {
        return mapper.selectByUid(uid);
    }

    @Override
    public List<UserDetail> queryByFlag(int flag) {
        return mapper.selectByFlag(flag);
    }

    @Override
    public R realNameAuth(int uid) {
        UserDetail detail=mapper.selectByUid(uid);
        System.out.println("ud---->"+detail+"----->"+uid);
        System.out.println(detail.getRealname()+"--->"+detail.getUid());
        R r=null;
        switch (detail.getFlag()){
            case 0://初始化 需要认证
                r=new R(1001,"请完成实名认证",detail);
                break;
            case 1://认证审核中
                r=new R(1002,"实名认证审核中",detail);
                break;
            case 2://认证通过
                r=new R(1003,"实名认证审核通过",detail);
                break;
            case 3://认证拒绝
                r=new R(1004,"实名认证审核失败：",detail);
                break;
        }
        return r;
    }

    @Override
    public R save(OSSPo po) {
        return ExecuteUtils.getR(ossPoMapper.insert(po),"oss图片成功");
    }
}
