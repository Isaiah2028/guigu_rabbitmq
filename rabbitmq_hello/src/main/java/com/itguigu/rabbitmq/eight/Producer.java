package com.itguigu.rabbitmq.eight;

import com.itguigu.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

/**
 * @Author: IsaiahLu
 * @date: 2023/2/2 15:36
 * 死信队列 生产者
 */
public class Producer {
    /**
     * 普通交换机
     */
    public static final String NORMAL_EXCHANGE = "normal_exchange";
    private static final String NORMAL_ROUTING_KEY = "normal";


    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtils.getChannel();
        //死信消息 设置ttl时间
        /*AMQP.BasicProperties properties = null;
        properties = new AMQP.BasicProperties().builder().expiration("10000").build();*/

        for (int i = 0; i < 11; i++) {
            String message = "info" + i;
            //channel.basicPublish(NORMAL_EXCHANGE, NORMAL_ROUTING_KEY, properties, message.getBytes());
            channel.basicPublish(NORMAL_EXCHANGE, NORMAL_ROUTING_KEY, null, message.getBytes());
            System.out.println("生产者发送消息:" + message);
        }
    }


}
