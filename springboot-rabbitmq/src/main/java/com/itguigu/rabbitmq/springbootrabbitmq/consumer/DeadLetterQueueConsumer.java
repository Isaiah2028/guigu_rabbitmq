package com.itguigu.rabbitmq.springbootrabbitmq.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @Author: IsaiahLu
 * @date: 2023/2/3 11:07
 * 监听器
 */
@Slf4j
@Component
public class DeadLetterQueueConsumer {

    @RabbitListener(queues = "QD")
    public void receiveD(Message message, Channel channel) {
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("当前时间：{}，收到死信队列的消息：{}", new Date(), msg);
    }

    @RabbitListener(queues = "QC")
    public void receiveC(Message message, Channel channel) {
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("当前时间：{}，收到死信队列c的消息：{}", new Date(), msg);

    }

}
