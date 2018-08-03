package com.qfedu.listener;

import javax.jms.*;

/**
 *@Author feri
 *@Date Created in 2018/8/3 10:40
 */
public class MyMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        //JMSContext  c;
        if(message instanceof TextMessage){
            TextMessage tm=(TextMessage)message;
            try {
                System.err.println("接收消息："+tm.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }else if(message instanceof ObjectMessage){
            System.err.println("接收消息：对象消息");
        }
    }
}
