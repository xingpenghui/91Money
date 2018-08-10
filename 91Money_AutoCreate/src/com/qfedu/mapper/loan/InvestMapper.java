package com.qfedu.mapper.loan;

import com.qfedu.domain.loan.Invest;

public interface InvestMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Invest record);

    int insertSelective(Invest record);

    Invest selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Invest record);

    int updateByPrimaryKey(Invest record);
}