package com.qfedu.sso.service;

import com.qfedu.core.vo.R;
import com.qfedu.domain.user.User;

import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/7/24 14:37
 */
public interface UserService {
    //新增
    R save(User user);

    List<User> query();
}
