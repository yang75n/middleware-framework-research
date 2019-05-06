package com.yqw.test.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Comsumer {

    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;

    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;

    private static final String BROKEN_URL = ActiveMQConnection.DEFAULT_BROKER_URL;

    ConnectionFactory connectionFactory;

    Connection connection;

    Session session;

    ThreadLocal<MessageConsumer> threadLocal = new ThreadLocal<>();
    AtomicInteger count = new AtomicInteger();

    public void init() {
        try {
            System.out.printf("Consumer  init username=%s,passwd=%s,url=%s\n", USERNAME, PASSWORD, BROKEN_URL);

            connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEN_URL);
            connection = connectionFactory.createConnection();
            System.out.printf("创建连接成功,connect=%s\n", connection);
            System.out.println("开始session start....");
            connection.start();
            System.out.println("start成功");
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            System.out.printf("createSession session=%s", session);

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


    public void getMessage(String disname) {
        try {
            MessageConsumer consumer = null;

            if (threadLocal.get() != null) {
                consumer = threadLocal.get();
            } else {
                Queue queue = session.createQueue(disname);
                consumer = session.createConsumer(queue);
                threadLocal.set(consumer);
            }
            while (true) {
                Thread.sleep(1000);
                TextMessage msg = (TextMessage) consumer.receive();
                if (msg != null) {
                    msg.acknowledge();
                    System.out.println(Thread.currentThread().getName() + ": Consumer:我是消费者，我正在消费Msg" + msg.getText() + "--->" + count.getAndIncrement());
                } else {
                    break;
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}