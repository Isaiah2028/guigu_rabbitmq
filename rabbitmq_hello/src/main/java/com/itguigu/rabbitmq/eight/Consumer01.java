package com.itguigu.rabbitmq.eight;

import com.itguigu.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import org.omg.CORBA.OBJ_ADAPTER;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: IsaiahLu
 * @date: 2023/2/2 14:57
 * 死信队列实战
 * 消费者1
 */
public class Consumer01 {
    // 路由
    private static final String ROUTING_KEY = "dlx";
    private static final String NORMAL_ROUTING_KEY = "normal";

    /**
     * 普通交换机
     */
    public static final String NORMAL_EXCHANGE = "normal_exchange";
    /**
     * 死信交换机
     */
    public static final String DEAD_EXCHANGE = "dead_exchange";
    /**
     * 普通队列
     */
    public static final String NORMAL_QUEUE = "normal_queue";
    /**
     * 死信队列
     */
    public static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws Exception {
        //声明信道
        Channel channel = RabbitMqUtils.getChannel();

        // 声明死信和普通交换机   类型为  direct
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);

        //声明死信队列
        channel.queueDeclare(DEAD_QUEUE, true, false, false, null);
        //死信队列绑定死信交换机
        channel.queueBind(DEAD_QUEUE, DEAD_EXCHANGE, ROUTING_KEY);

        //正常队列绑定死信队列信息
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-message-ttl", 10000);
        arguments.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        arguments.put("x-dead-letter-routing-key", ROUTING_KEY);

        //声明正常队列
        channel.queueDeclare(NORMAL_QUEUE, true, false, false, arguments);
        channel.queueBind(NORMAL_QUEUE, NORMAL_EXCHANGE, NORMAL_ROUTING_KEY);

        // channel.queueBind(DEAD_QUEUE, DEAD_EXCHANGE, "lisi");
        System.out.println("Consumer01等待接收消息.....");

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("Consumer01正常队列监听到消息：" + new String(message.getBody(), StandardCharsets.UTF_8));
            channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
        };
        channel.basicConsume(NORMAL_QUEUE, true, deliverCallback, (consumerTag) -> {
        });
    }

}
