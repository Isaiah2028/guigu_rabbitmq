package com.itguigu.rabbitmq.seven;

import com.itguigu.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @Author: IsaiahLu
 * @date: 2023/2/2 13:57
 * 消费者 topic模式
 */
public class ReceiveLogsTopic01 {
    public static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtils.getChannel();

        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        //声明队列
        String queueName = "Q1";
        channel.queueDeclare(queueName, true, false, false, null);

        //绑定
        channel.queueBind(queueName, EXCHANGE_NAME, "*.orange.*");
        System.out.println("ReceiveLogsTopic01等待监听消息....");

        //确认消息回调
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("ReceiveLogsTopic01 监听到队列：" + queueName + "绑定键：" + message.getEnvelope().getRoutingKey() + ",     消息：" + new String(message.getBody(), "UTF-8"));
        };
        //消息中断回调
        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println("ReceiveLogsTopic01 中断了");
        };

        channel.basicConsume(queueName, true, deliverCallback, cancelCallback);

    }

}
