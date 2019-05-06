package com.yqw.test.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 5、ActiveMQ的特性
 * <p>
 * --------------------------------------------------------------------------------
 * <p>
 * 　5.1 ActiveMq 的特性
 * 1.多种语言和协议编写客户端。语言: Java, C, C++, C#, Ruby, Perl, Python, PHP。应用协议: OpenWire,Stomp REST,WS Notification,XMPP,AMQP
 * 2.完全支持JMS1.1和J2EE 1.4规范 (持久化,XA消息,事务)
 * 3.对Spring的支持,ActiveMQ可以很容易内嵌到使用Spring的系统里面去,而且也支持Spring2.0的特性
 * 4.通过了常见J2EE服务器(如 Geronimo,JBoss 4, GlassFish,WebLogic)的测试,其中通过JCA 1.5 resource adaptors的配置,可以让ActiveMQ可以自动的部署到任何兼容J2EE 1.4 商业服务器上
 * 5.支持多种传送协议:in-VM,TCP,SSL,NIO,UDP,JGroups,JXTA
 * 6.支持通过JDBC和journal提供高速的消息持久化
 * 7.从设计上保证了高性能的集群,客户端-服务器,点对点
 * 8.支持Ajax
 * 9.支持与Axis的整合
 * 10.可以很容易得调用内嵌JMS provider,进行测试
 * <p>
 * <p>
 * <p>
 * 5.2 什么情况下使用ActiveMQ?
 * 1.多个项目之间集成
 * (1) 跨平台
 * (2) 多语言
 * (3) 多项目
 * 2.降低系统间模块的耦合度，解耦
 * (1) 软件扩展性
 * 3.系统前后端隔离
 * (1) 前后端隔离，屏蔽高安全区
 */
public class Producer {

    //ActiveMq 的默认用户名
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    //ActiveMq 的默认登录密码
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    //ActiveMQ 的链接地址
    private static final String BROKEN_URL = ActiveMQConnection.DEFAULT_BROKER_URL;

    AtomicInteger count = new AtomicInteger(0);
    //链接工厂
    ConnectionFactory connectionFactory;
    //链接对象
    Connection connection;
    //事务管理
    Session session;
    ThreadLocal<MessageProducer> threadLocal = new ThreadLocal<>();

    public void init() {
        try {
            System.out.printf("Provider init username=%s,passwd=%s,url=%s\n", USERNAME, PASSWORD, BROKEN_URL);
            //创建一个链接工厂
            connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEN_URL);
            //从工厂中创建一个链接
            connection = connectionFactory.createConnection();
            System.out.printf("创建连接成功,connect=%s\n", connection);
            //开启链接
            System.out.println("开始session start....");
            connection.start();
            System.out.println("start成功");
            //创建一个事务（这里通过参数可以设置事务的级别）
            session = connection.createSession(true, Session.SESSION_TRANSACTED);
            System.out.printf("createSession session=%s", session);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String disname) {
        try {
            //创建一个消息队列
            Queue queue = session.createQueue(disname);
            //消息生产者
            MessageProducer messageProducer = null;
            if (threadLocal.get() != null) {
                messageProducer = threadLocal.get();
            } else {
                messageProducer = session.createProducer(queue);
                threadLocal.set(messageProducer);
            }
            while (true) {
                Thread.sleep(1000);
                int num = count.getAndIncrement();
                //创建一条消息
                TextMessage msg = session.createTextMessage(Thread.currentThread().getName() +
                        "productor:<<<我是生产者，我现在正在生产东西！,count:" + num+">>>");
                System.out.println(Thread.currentThread().getName() +
                        "productor:我是生产者，我现在正在生产东西！,count:" + num);
                //发送消息
                messageProducer.send(msg);
                //提交事务
                session.commit();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}