package com.yqw.rabbitmq.work;

import com.yqw.rabbitmq.simple.ConnectionUtil;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * * work模式
 * * 一个生产者、2个消费者。 一个消息只能被一个消费者获取。
 * <p>
 * <p>
 * 生产者
 * 向队列中发送100条消息。
 * <p>
 * <p>
 * 测试结果：
 * 1、消费者1和消费者2获取到的消息内容是不同的，同一个消息只能被一个消费者获取。
 * 2、消费者1和消费者2获取到的消息的数量是相同的，一个是消费奇数号消息，一个是偶数。
 * <p>
 * 其实，这样是不合理的，因为消费者1线程停顿的时间短。应该是消费者1要比消费者2获取到的消息多才对。
 * RabbitMQ 默认将消息顺序发送给下一个消费者，这样，每个消费者会得到相同数量的消息。即轮询（round-robin）分发消息。
 * <p>
 * 怎样才能做到按照每个消费者的能力分配消息呢？联合使用 Qos 和 Acknowledge 就可以做到。
 * basicQos 方法设置了当前信道最大预获取（prefetch）消息数量为1。消息从队列异步推送给消费者，消费者的 ack 也是异步发送给队列，从队列的视角去看，总是会有一批消息已推送但尚未获得 ack 确认，Qos 的 prefetchCount 参数就是用来限制这批未确认消息数量的。设为1时，队列只有在收到消费者发回的上一条消息 ack 确认后，才会向该消费者发送下一条消息。prefetchCount 的默认值为0，即没有限制，队列会将所有消息尽快发给消费者。
 * <p>
 * 2个概念
 * <p>
 * 轮询分发 ：使用任务队列的优点之一就是可以轻易的并行工作。如果我们积压了好多工作，我们可以通过增加工作者（消费者）来解决这一问题，使得系统的伸缩性更加容易。在默认情况下，RabbitMQ将逐个发送消息到在序列中的下一个消费者(而不考虑每个任务的时长等等，且是提前一次性分配，并非一个一个分配)。平均每个消费者获得相同数量的消息。这种方式分发消息机制称为Round-Robin（轮询）。
 * <p>
 * 公平分发 ：虽然上面的分配法方式也还行，但是有个问题就是：比如：现在有2个消费者，所有的奇数的消息都是繁忙的，而偶数则是轻松的。按照轮询的方式，奇数的任务交给了第一个消费者，所以一直在忙个不停。偶数的任务交给另一个消费者，则立即完成任务，然后闲得不行。而RabbitMQ则是不了解这些的。这是因为当消息进入队列，RabbitMQ就会分派消息。它不看消费者为应答的数目，只是盲目的将消息发给轮询指定的消费者。
 * <p>
 * 为了解决这个问题，我们使用basicQos( prefetchCount = 1)方法，来限制RabbitMQ只发不超过1条的消息给同一个消费者。当消息处理完毕后，有了反馈，才会进行第二次发送。
 * 还有一点需要注意，使用公平分发，必须关闭自动应答，改为手动应答。
 * <p>
 * 5.4.Work模式的“能者多劳”
 * 打开上述代码的注释：
 * <p>
 * // 同一时刻服务器只会发一条消息给消费者
 * channel.basicQos(1);
 * 1
 * 2
 * //开启这行 表示使用手动确认模式
 * channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
 * 1
 * 2
 * 同时改为手动确认：
 * <p>
 * // 监听队列，false表示手动返回完成状态，true表示自动
 * channel.basicConsume(QUEUE_NAME, false, consumer);
 * 1
 * 2
 * 测试：
 * 消费者1比消费者2获取的消息更多。
 * <p>
 * 5.5.消息的确认模式
 * 消费者从队列中获取消息，服务端如何知道消息已经被消费呢？
 * <p>
 * 模式1：自动确认
 * 只要消息从队列中获取，无论消费者获取到消息后是否成功消息，都认为是消息已经成功消费。
 * 模式2：手动确认
 * 消费者从队列中获取消息后，服务器会将该消息标记为不可用状态，等待消费者的反馈，如果消费者一直没有反馈，那么该消息将一直处于不可用状态。
 * <p>
 * 手动模式：
 * <p>
 * <p>
 * 自动模式：
 * <p>
 * ---------------------
 * 原文：https://blog.csdn.net/hellozpc/article/details/81436980
 */
public class Send {

    private final static String QUEUE_NAME = "test_queue_work";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        for (int i = 0; i < 100; i++) {
            // 消息内容
            String message = "" + i;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

            Thread.sleep(i * 10);
        }

        channel.close();
        connection.close();
    }
}
