package com.yqw.rabbitmq.pub_sub;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.yqw.rabbitmq.simple.ConnectionUtil;

/**
 * 订阅模式
 * 1、1个生产者，多个消费者
 * 2、每一个消费者都有自己的一个队列
 * 3、生产者没有将消息直接发送到队列，而是发送到了交换机
 * 4、每个队列都要绑定到交换机
 * 5、生产者发送的消息，经过交换机，到达队列，实现，一个消息被多个消费者获取的目的
 * 注意：一个消费者队列可以有多个消费者实例，只有其中一个消费者实例会消费
 * <p>
 * <p>
 * 消息的生产者（看作是后台系统）
 * 向交换机中发送消息。
 * 注意：消息发送到没有队列绑定的交换机时，消息将丢失，因为，交换机没有存储消息的能力，消息只能存在在队列中。
 * <p>
 * <p>
 * 测试结果：
 * 同一个消息被多个消费者获取。一个消费者队列可以有多个消费者实例，只有其中一个消费者实例会消费到消息。
 */
public class Send {

    private final static String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        // 消息内容
        String message = "Hello World!";
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
}