package com.qfedu.provider.invest;

import com.qfedu.core.util.ExecuteUtils;
import com.qfedu.core.vo.R;
import com.qfedu.domain.invest.Invest;
import com.qfedu.mapper.invest.InvestMapper;
import com.qfedu.service.invest.InvestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *@Author feri
 *@Date Created in 2018/8/9 15:53
 */
@Service
public class InvestProvider implements InvestService {
    @Autowired
    private InvestMapper mapper;
    @Override
    public R save(Invest invest) {

        return ExecuteUtils.getR(mapper.insert(invest),"新增投资");
    }
}
