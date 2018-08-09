package com.qfedu.mapper.account;

import com.qfedu.domain.loan.Risk;

import java.util.List;

public interface RiskMapper {

    int insert(Risk record);

    Risk selectById(Integer id);

    List<Risk> selectByUid(int aid);
}