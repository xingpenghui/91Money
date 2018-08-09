package com.qfedu.mapper.account;

import com.qfedu.domain.loan.Account;
import org.apache.ibatis.annotations.Param;

public interface AccountMapper {
    int insert(int uid);
    Account selectById(Integer id);
    int updateYe(@Param("uid") int uid, @Param("money") int money);
    int updateDj(@Param("uid") int uid, @Param("money") int money);
    int updateXy(@Param("uid") int uid, @Param("money") int money);
}