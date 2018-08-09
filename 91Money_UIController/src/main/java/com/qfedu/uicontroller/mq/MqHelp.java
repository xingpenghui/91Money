package com.qfedu.uicontroller.mq;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 *@Author feri
 *@Date Created in 2018/8/3 14:52
 */
@Service
public class MqHelp {
    @Autowired
    private JmsTemplate template;
    @Autowired
    private ActiveMQQueue queue;

    //发送消息
    public void sendMsg(String msg){
        template.send(queue,(session) -> {
            return session.createTextMessage(msg);
        });
    }
}
