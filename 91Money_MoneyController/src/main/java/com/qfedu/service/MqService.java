package com.qfedu.service;

import com.qfedu.core.vo.R;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTempTopic;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 *@Author feri
 *@Date Created in 2018/8/3 10:32
 */
@Service
public class MqService {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private ActiveMQQueue queue;

    @Autowired
    private ActiveMQTopic topic;

    public R sendMsg(int type,String msg){
        switch (type){
            case 1://p2p
                jmsTemplate.send(queue, (session)-> {
                    {
                        return session.createTextMessage(msg);
                    }
                });
                break;
            case 2://pub/sub
                jmsTemplate.send(topic,(session) -> {

                    return session.createTextMessage(msg);
                });
                break;
        }
        return R.setOK("发送成功："+jmsTemplate.getConnectionFactory().toString());
    }

}
