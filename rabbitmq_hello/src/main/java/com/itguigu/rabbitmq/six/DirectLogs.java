package com.itguigu.rabbitmq.six;

import com.itguigu.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

/**
 * @Author: IsaiahLu
 * @date: 2023/2/2 13:12
 * @Describe: 生产者
 */
public class DirectLogs {
    public static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtils.getChannel();
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String message = scanner.next();
            // channel.basicPublish(EXCHANGE_NAME,"info",null,message.getBytes());
            channel.basicPublish(EXCHANGE_NAME, "error", null, message.getBytes());
        }
    }

}
