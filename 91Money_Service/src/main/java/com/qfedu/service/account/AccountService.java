package com.qfedu.service.account;

import com.qfedu.core.vo.R;
import com.qfedu.domain.loan.Account;

/**
 *@Author feri
 *@Date Created in 2018/8/9 15:50
 */
public interface AccountService {
    R save(int uid);
    R updateYe(int uid,int money);
    R updateDj(int uid,int money);
}
