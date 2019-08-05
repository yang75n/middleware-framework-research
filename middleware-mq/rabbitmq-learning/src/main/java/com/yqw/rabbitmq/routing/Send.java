package com.yqw.rabbitmq.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.yqw.rabbitmq.simple.ConnectionUtil;

/**
 * 路由模式
 * 生产者
 */
public class Send {

    private final static String EXCHANGE_NAME = "test_exchange_direct";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明队列  direct是交互机类型
        channel.exchangeDeclare(EXCHANGE_NAME, "dierct");


        // 消息内容
        String message = "删除商品";
        //delete表示消息类型
        channel.basicPublish(EXCHANGE_NAME, "delete", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
}
