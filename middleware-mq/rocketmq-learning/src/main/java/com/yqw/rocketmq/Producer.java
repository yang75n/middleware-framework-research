package com.yqw.rocketmq;

import java.io.Serializable;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import com.alibaba.fastjson.JSON;

/**
 * rocketmq是阿里巴巴开源的一款分布式的消息中间件，他源于jms规范但是不遵守jms规范。
 * 对于分布式只一点，如果你了用过其他mq并且了解过rocketmq，就知道rocketmq天生就是分布式的，
 * 可以说是broker（经纪人）、provider、consumer等各种分布式。
 *
 * @Function 消息生产者
 */
public class Producer {
    public static void main(String[] args) throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer("test-group");
        producer.setNamesrvAddr("localhost:9876");
        producer.setInstanceName("rmq-instance");
        producer.start();
        try {
            for (int i = 0; i < 100; i++) {
                User user = new User();
                user.setLoginName("abc" + i);
                user.setPwd(String.valueOf(i));
                Message message = new Message("log-topic", "user-tag", JSON.toJSONString(user).getBytes());
                System.out.println("生产者发送消息:" + JSON.toJSONString(user));
                producer.send(message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        producer.shutdown();
    }

    /**
     * 发送用户消息
     */
    static class User implements Serializable {
        private String loginName;
        private String pwd;

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }
    }
}