package com.itguigu.rabbitmq.eight;

import com.itguigu.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: IsaiahLu
 * @date: 2023/2/2 14:57
 * 死信队列实战
 * 消费者2,消费死信队列中的消息
 */
public class Consumer02 {
    // 路由
    private static final String ROUTING_KEY = "dlx";
    private static final String NORMAL_ROUTING_KEY = "normal";

    /**
     * 死信队列
     */
    public static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws Exception {
        //声明信道
        Channel channel = RabbitMqUtils.getChannel();

        System.out.println("Consumer02等待接收消息.....");

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("Consumer02监听消费死信队列中的消息： " + new String(message.getBody(), StandardCharsets.UTF_8));
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        };
        channel.basicConsume(DEAD_QUEUE, true, deliverCallback, (consumerTag) -> {
        });
    }

}
