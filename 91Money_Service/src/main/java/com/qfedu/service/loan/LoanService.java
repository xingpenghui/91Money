package com.qfedu.service.loan;

import com.qfedu.core.vo.LoanVo;
import com.qfedu.core.vo.R;
import com.qfedu.domain.loan.Loan;

import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/8/3 14:29
 */
public interface LoanService {
    R save(Loan loan);
    List<Loan> queryAll();
    List<Loan> queryAll(int type);

    LoanVo queryById(int id);
}
