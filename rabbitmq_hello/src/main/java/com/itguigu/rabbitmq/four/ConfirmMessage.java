package com.itguigu.rabbitmq.four;

import com.itguigu.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;

import java.util.UUID;

/**
 * @Author: IsaiahLu
 * @date: 2023/2/1 18:00
 * <p>
 * 发布确认模式
 * 1单个确认
 * 2批量确认
 * 3异步批量确认
 */

public class ConfirmMessage {

    public static void main(String[] args) throws Exception {
        //单个确认 发布1000条消息单独确认，共耗时：46516 毫秒
        //ConfirmMessage.publicMessageIndividual();
        //批量确认 发布1000条消息，批量确认，共耗时：579  毫秒
        //ConfirmMessage.publishMessageBatch();
        //异步批量确认
        ConfirmMessage.publishMessageAsync();
    }

    /**
     * 单个确认
     */
    public static void publicMessageIndividual() throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        //队列声明
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, true, false, false, null);
        //开启发布确认
        channel.confirmSelect();
        //开始时间：
        long starTime = System.currentTimeMillis();
        for (int i = 1; i <= 1000; i++) {
            String message = i + "";
            channel.basicPublish("", queueName, null, message.getBytes());
            boolean flag = channel.waitForConfirms();
            if (flag) {
                System.out.println("消息发送成功");
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("发布1000条消息单独确认，" + "共耗时：" + (endTime - starTime));
    }

    public static void publishMessageBatch() throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        //队列声明
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, true, false, false, null);
        //开启发布确认
        channel.confirmSelect();
        //开始时间：
        long starTime = System.currentTimeMillis();

        //批量确认消息大小
        int batchSize = 100;
        //批量发送消息，批量发布确认
        for (int i = 1; i <= 1000; i++) {
            String message = i + "";
            channel.basicPublish("", queueName, null, message.getBytes());
            if (i % batchSize == 0) {
                //发布确认
                channel.waitForConfirms();
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("发布1000条消息，批量确认，" + "共耗时：" + (endTime - starTime) + "  毫秒");
    }

    public static void publishMessageAsync() throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        //队列声明
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, true, false, false, null);
        //开启发布确认
        channel.confirmSelect();
        //开始时间：
        long starTime = System.currentTimeMillis();

        /**
         * 消息确认成功回调接口
         */
        ConfirmCallback ackCallback = (deliveryTag, multiple) -> {
            System.out.println("确认的消息： " +deliveryTag);
        };
        /**
         * 消息确认失败回调接口
         */
        ConfirmCallback nackCallback = (deliveryTag, multiple) -> {
            System.out.println("未确认的消息： " +deliveryTag);
        };
        //监听器，监听那些消息成功了，那些消息失败了，
        channel.addConfirmListener(ackCallback, nackCallback);

        for (int i = 1; i <= 1000; i++) {
            String message = "消息 " + i;
            channel.basicPublish("", queueName, null, message.getBytes());


        }

        long endTime = System.currentTimeMillis();
        System.out.println("发布1000条消息，异步确认，" + "共耗时：" + (endTime - starTime) + "  毫秒");

    }

}
