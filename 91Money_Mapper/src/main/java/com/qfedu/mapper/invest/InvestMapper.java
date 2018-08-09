package com.qfedu.mapper.invest;


import com.qfedu.domain.invest.Invest;

public interface InvestMapper {
    int insert(Invest record);
    Invest selectById(Integer id);
    int updateFlag(Invest record);
}