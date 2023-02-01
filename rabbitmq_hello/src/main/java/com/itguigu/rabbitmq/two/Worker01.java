package com.itguigu.rabbitmq.two;

import com.itguigu.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

/**
 * @Author: IsaiahLu
 * @date: 2023/2/1 13:28
 *
 * 工作线程：消费者
 */
public class Worker01 {
    //队列名称
    private static final String QUEUE_NAME= "hello";

    //接收消息
    public static void main(String[] args) throws Exception {

        //消息的接收
        DeliverCallback deliverCallback = (consumerTag,  message) -> {
            System.out.println("监听到消息："+new String(message.getBody()));
        };
        //监听消息中断返回
        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println(consumerTag +"消息监听中断逻辑回调!");
        };

        //获取信道
        Channel channel = RabbitMqUtils.getChannel();

        System.out.println("C2等待接收消息...");
        //消息的接收
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);

    }
}
