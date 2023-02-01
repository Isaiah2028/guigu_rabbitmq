package com.itguigu.rabbitmq.three;

import com.itguigu.rabbitmq.utils.RabbitMqUtils;
import com.itguigu.rabbitmq.utils.SleepUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @Author: IsaiahLu
 * @date: 2023/2/1 14:14
 * 消费者，消息手动应答不丢失，放回队列重新消费
 */
public class Work03 {
    public static final String TASK_QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws Exception {
        //接收消息
        Channel channel = RabbitMqUtils.getChannel();
        System.out.println("C1等待时间短...");

        //接口回调
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            //沉睡1秒
            SleepUtils.sleep(1);
            System.out.println("监听到的消息：" + new String(message.getBody(), "UTF-8"));
            //手动应答
            /**
             * 消息的标记 Tag
             * 是否批量应答 false
             */
            channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
        };

        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println("消息消费被中断!");
        };

        //设置不公平分发
        channel.basicQos(1);

        //手动应答
        Boolean autoAck = false; //不自动应答
        channel.basicConsume(TASK_QUEUE_NAME, autoAck,deliverCallback,cancelCallback);

    }
}
