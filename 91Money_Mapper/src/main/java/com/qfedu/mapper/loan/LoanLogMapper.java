package com.qfedu.mapper.loan;

import com.qfedu.domain.loan.LoanLog;

public interface LoanLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LoanLog record);

    int insertSelective(LoanLog record);

    LoanLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LoanLog record);

    int updateByPrimaryKey(LoanLog record);
}