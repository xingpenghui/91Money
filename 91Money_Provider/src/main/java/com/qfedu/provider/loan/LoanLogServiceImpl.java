package com.qfedu.provider.loan;

import com.qfedu.core.util.ExecuteUtils;
import com.qfedu.core.vo.R;
import com.qfedu.domain.loan.LoanLog;
import com.qfedu.mapper.loan.LoanLogMapper;
import com.qfedu.service.loan.LoanLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *@Author feri
 *@Date Created in 2018/8/3 14:33
 */
@Service
public class LoanLogServiceImpl implements LoanLogService {
    @Autowired
    private LoanLogMapper mapper;
    @Override
    public R save(LoanLog loanLog) {
        return ExecuteUtils.getR(mapper.insert(loanLog),"新增借款日志");
    }
}
