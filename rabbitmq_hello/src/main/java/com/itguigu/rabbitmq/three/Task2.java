package com.itguigu.rabbitmq.three;

import com.itguigu.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.util.Scanner;

/**
 * @Author: IsaiahLu
 * @date: 2023/2/1 14:08
 * 消息应答时不丢失，重新入队
 */
public class Task2 {
    public static final String ACK_QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        //申明对队列
        boolean durable = true;
        channel.queueDeclare(ACK_QUEUE_NAME, durable, false, false, null);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.next();
            //  channel.basicPublish("",ACK_QUEUE_NAME,null,message.getBytes("UTF-8"));
            channel.basicPublish("", ACK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
            System.out.println("生产者发送消息:" + message);
        }

    }

}
