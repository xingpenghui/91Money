package com.qfedu.sso.provider;

import com.qfedu.core.util.ExecuteUtils;
import com.qfedu.core.vo.R;
import com.qfedu.domain.user.User;
import com.qfedu.mapper.user.UserMapper;
import com.qfedu.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *@Author feri
 *@Date Created in 2018/7/24 14:45
 */
@Service
public class UserProvider implements UserService {
    @Autowired
    private UserMapper mapper;
    @Override
    public R save(User user) {
        return ExecuteUtils.getR(mapper.insert(user),"新增用户");
    }
}
