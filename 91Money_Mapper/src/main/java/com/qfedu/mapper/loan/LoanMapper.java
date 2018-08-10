package com.qfedu.mapper.loan;

import com.qfedu.core.vo.LoanVo;
import com.qfedu.domain.loan.Loan;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoanMapper {
    int insert(Loan record);
    Loan selectById(Integer id);
    int updateFlag(@Param("id")int id,@Param("flag")int flag);
    List<Loan> selectAll();
    List<Loan> selectByType(int type);

    LoanVo queryById(int id);

}