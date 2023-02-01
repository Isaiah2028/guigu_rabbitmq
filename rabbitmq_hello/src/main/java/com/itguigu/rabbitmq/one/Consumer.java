package com.itguigu.rabbitmq.one;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: IsaiahLu
 * @date: 2023/2/1 13:12
 */
public class Consumer {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("121.40.160.237");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("123");

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        System.out.println("等待接收消息！");

        //接口回调
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("监听到的消息：" + message.getBody());

        };

        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println("消息消费被中断!");
        };

        /**
         * 消费者消费消费
         */
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
    }
}
