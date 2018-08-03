package com.qfedu.activemq;

import com.sun.prism.TextureMap;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 *@Author feri
 *@Date Created in 2018/8/3 09:19
 * 发送端  p2p
 */
public class ActiveMQRev_Main1 {
    public static void main(String[] args) throws Exception {
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
        //6、消息消费者
        MessageConsumer consumer=session.createConsumer(queue);
        //7、接收消息  可以使用receive 也可以使用MessageListener
//        consumer.setMessageListener(new MessageListener() {
//            @Override
//            public void onMessage(Message message) {
//                try {
//                    if (message instanceof TextMessage) {
//                        System.err.println("文本消息：" + ((TextMessage) message).getText());
//                    }else if(message instanceof ObjectMessage){
//                        System.err.println("对象消息："+((ObjectMessage)message).getObject());
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        });
        consumer.setMessageListener((message)-> {
                try {
                    if (message instanceof TextMessage) {
                        System.err.println("文本消息：" + ((TextMessage) message).getText());
                    }else if(message instanceof ObjectMessage){
                        System.err.println("对象消息："+((ObjectMessage)message).getObject());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
        });
        //
       // TextMessage message= (TextMessage) consumer.receive();
        //System.err.println("接收："+message.getText());
        //8、关闭
//        session.close();
//        connection.close();

    }

}
