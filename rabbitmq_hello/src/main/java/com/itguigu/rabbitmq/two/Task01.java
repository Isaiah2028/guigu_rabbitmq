package com.itguigu.rabbitmq.two;

import com.itguigu.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

/**
 * @Author: IsaiahLu
 * @date: 2023/2/1 13:45
 * @TODO 生产者
 */
public class Task01 {
    public static final String QUEUE_NAME = "hello";

    /**
     * 发送大量消息
     * @param args
     */
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        //申明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String message = scanner.next();
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            System.out.println("发送消息完成："+message);
        }

    }
}
