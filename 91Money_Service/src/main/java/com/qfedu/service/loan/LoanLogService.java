package com.qfedu.service.loan;

import com.qfedu.core.vo.R;
import com.qfedu.domain.loan.LoanLog;

/**
 *@Author feri
 *@Date Created in 2018/8/3 14:29
 */
public interface LoanLogService {
    R save(LoanLog loanLog);
}
