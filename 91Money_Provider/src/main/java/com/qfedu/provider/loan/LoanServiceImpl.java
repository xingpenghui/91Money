package com.qfedu.provider.loan;

import com.qfedu.core.util.ExecuteUtils;
import com.qfedu.core.vo.R;
import com.qfedu.domain.loan.Loan;
import com.qfedu.mapper.loan.LoanMapper;
import com.qfedu.service.loan.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *@Author feri
 *@Date Created in 2018/8/3 14:30
 */
@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanMapper mapper;
    @Override
    public R save(Loan loan) {
        //数据库存储的金钱为分  页面显示的为元
        loan.setMoney(loan.getMoney()*100);
        loan.setMinmoney(loan.getMinmoney()*100);
        loan.setRate(loan.getRate()/100);
//        3/2
        return ExecuteUtils.getR(mapper.insert(loan),"新增借款");
    }
}
