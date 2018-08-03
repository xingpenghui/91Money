package com.qfedu.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 *@Author feri
 *@Date Created in 2018/8/3 09:19
 * 接收端  p2p
 */
public class ActiveMQSend_Main1 {
    public static void main(String[] args) throws Exception{
        String url="tcp://10.8.155.34:61616";
        //1、创建工厂
        ConnectionFactory factory=new ActiveMQConnectionFactory(url);
        ((ActiveMQConnectionFactory) factory).setTrustAllPackages(true);
        //2、创建连接
        Connection connection=factory.createConnection();
        //3、开启连接
        connection.start();
        //4、获取会话
        //参数说明：1、是否使用事务  2、应答模式
        Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //5、创建队列
        Queue queue=session.createQueue("hello");
        //6、创建消息提供者
        MessageProducer producer=session.createProducer(queue);
        //7、创建文本消息
        //TextMessage message=session.createTextMessage("早上好");
        ObjectMessage objectMessage=session.createObjectMessage(new Offer(1,"香蕉","拼多多",30000));
        //8、发送消息
        producer.send(objectMessage);
        //9、关闭销毁
        session.close();
        connection.close();
    }

}
