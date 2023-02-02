package com.itguigu.rabbitmq.five;

import com.itguigu.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

/**
 * @Author: IsaiahLu
 * @date: 2023/2/1 21:58
 * 消费者1
 */
public class ReceiveLogs01 {
    //交换机名
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        //声明临时队列
        /**
         * 临时队列，连接是存在，
         * 消费者断开连接，队列自动删除
         */
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");
        System.out.println("等待接收消息...");

        //接受到消息回调消息
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("ReceiveLogs01监听到消息： " + new String(message.getBody(), "UTF-8"));
        };

        //接收消息中断回调接口
        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println("消息中断");
        };

        //接收消息
        channel.basicConsume(queueName, true, deliverCallback, cancelCallback);
    }

}
