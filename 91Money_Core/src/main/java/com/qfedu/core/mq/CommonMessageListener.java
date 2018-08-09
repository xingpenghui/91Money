package com.qfedu.core.mq;

import com.alibaba.fastjson.JSON;
import com.qfedu.core.vo.R;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *@Author feri
 *@Date Created in 2018/8/3 14:41
 */
public class CommonMessageListener implements MessageListener {


    @Override
    public void onMessage(Message message) {
        try {
            TextMessage msg=(TextMessage)message;
            R r= JSON.parseObject(msg.getText(),R.class);
            switch (r.getCode()){
                case 1001://借款日志

                    break;
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
