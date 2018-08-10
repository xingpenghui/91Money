package com.qfedu.mapper.loan;

import com.qfedu.domain.loan.IncomeLog;

public interface IncomeLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IncomeLog record);

    int insertSelective(IncomeLog record);

    IncomeLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IncomeLog record);

    int updateByPrimaryKey(IncomeLog record);
}