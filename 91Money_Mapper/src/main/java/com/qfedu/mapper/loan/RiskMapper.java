package com.qfedu.mapper.loan;

import com.qfedu.domain.loan.Risk;

public interface RiskMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Risk record);

    int insertSelective(Risk record);

    Risk selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Risk record);

    int updateByPrimaryKeyWithBLOBs(Risk record);

    int updateByPrimaryKey(Risk record);
}