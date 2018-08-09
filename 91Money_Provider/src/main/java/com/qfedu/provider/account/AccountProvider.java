package com.qfedu.provider.account;

import com.qfedu.core.util.ExecuteUtils;
import com.qfedu.core.vo.R;
import com.qfedu.domain.loan.Account;
import com.qfedu.mapper.account.AccountMapper;
import com.qfedu.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *@Author feri
 *@Date Created in 2018/8/9 15:55
 */
@Service
public class AccountProvider implements AccountService {
   @Autowired
   private AccountMapper mapper;
    @Override
    public R save(int uid) {
        return ExecuteUtils.getR(mapper.insert(uid),"新增账户");
    }

    @Override
    public R updateYe(int uid, int money) {
        return ExecuteUtils.getR(mapper.updateYe(uid,money),"修改余额");
    }

    @Override
    public R updateDj(int uid, int money) {
        return ExecuteUtils.getR(mapper.updateDj(uid,money),"冻结余额");
    }
}
