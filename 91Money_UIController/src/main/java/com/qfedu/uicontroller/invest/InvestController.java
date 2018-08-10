package com.qfedu.uicontroller.invest;

import com.qfedu.core.vo.R;
import com.qfedu.domain.invest.Invest;
import com.qfedu.service.invest.InvestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *@Author feri
 *@Date Created in 2018/8/10 10:36
 */
@RestController
public class InvestController {

    @Autowired
    private InvestService service;
    @RequestMapping("investsave.do")
    public R save(Invest invest){
        return service.save(invest);
    }
}
