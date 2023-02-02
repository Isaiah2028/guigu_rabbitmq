package com.itguigu.rabbitmq.six;

import com.itguigu.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.*;

/**
 * @Author: IsaiahLu
 * @date: 2023/2/2 12:58
 */
public class ReceiveLogsDirect01 {
    public static final String EXCHANGE_NAME = "direct_logs";
    public static final String QUEUE_NAME = "console";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();

        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        //声明队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        //绑定
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "info");
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "warning");
        //确认消息回调
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("ReceiveLogsDirect01 监听到消息：" + new String(message.getBody(), "UTF-8"));
        };
        //消息中断回调
        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println("ReceiveLogsDirect01 中断了");
        };
        //监听消息
        channel.basicConsume(QUEUE_NAME, deliverCallback, cancelCallback);
    }
}
