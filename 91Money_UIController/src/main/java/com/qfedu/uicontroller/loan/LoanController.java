package com.qfedu.uicontroller.loan;

import com.alibaba.fastjson.JSON;
import com.qfedu.core.vo.LoanVo;
import com.qfedu.core.vo.R;
import com.qfedu.domain.loan.Loan;
import com.qfedu.service.loan.LoanService;
import com.qfedu.uicontroller.mq.MqHelp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/8/3 14:36
 */
@RestController
public class LoanController {
    @Autowired
    private LoanService service;
    @Autowired
    private MqHelp help;

    //新增
    @PostMapping("/loansave.do")
    public R save(Loan loan){
        R r= service.save(loan);

        if(r.getCode()==0){
            R r1=new R(1001,"借款成功",loan);
            //发送MQ消息
            help.sendMsg(JSON.toJSONString(r1));
        }
        return r;
    }
    //借款列表
    @RequestMapping("loanlist.do")
    public List<Loan> list(){
        return service.queryAll();
    }
    //借款详情
    @RequestMapping("loandetail.do")
    public LoanVo detail(int id){
        return service.queryById(id);
    }


}
