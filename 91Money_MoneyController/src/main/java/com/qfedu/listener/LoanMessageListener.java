package com.qfedu.listener;

import com.alibaba.fastjson.JSON;
import com.qfedu.core.vo.R;
import com.qfedu.domain.loan.LoanLog;
import com.qfedu.service.loan.LoanLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *@Author feri
 *@Date Created in 2018/8/3 14:41
 */
@Service
public class LoanMessageListener implements MessageListener {

    @Autowired
    private LoanLogService service;

    @Override
    public void onMessage(Message message) {
        try {
            TextMessage msg=(TextMessage)message;
            R r= JSON.parseObject(msg.getText(),R.class);
            switch (r.getCode()){
                case 1001://借款日志
                    LoanLog loanLog=new LoanLog();
                    loanLog.setType(1);
                    loanLog.setSuid(0);
                    loanLog.setMsg("借款日志："+r);
                    service.save(loanLog);
                    break;
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
