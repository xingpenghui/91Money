package com.qfedu.mapper.invest;


import com.qfedu.domain.invest.Income;

public interface IncomeMapper {

    int insert(Income record);

    Income selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Income record);
}