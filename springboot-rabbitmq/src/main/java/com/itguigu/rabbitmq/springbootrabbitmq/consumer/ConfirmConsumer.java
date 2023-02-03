package com.itguigu.rabbitmq.springbootrabbitmq.consumer;

import com.itguigu.rabbitmq.springbootrabbitmq.config.rabbitmq.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;


/**
 * @Author: IsaiahLu
 * @date: 2023/2/3 16:52
 * 消费者
 */
@Component
@Slf4j
public class ConfirmConsumer {


    @RabbitListener(queues = ConfirmConfig.CONFIRM_QUEUE_NAME )
    public void receiveMsg(Message message){
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("接收到的队列confirm.queue消息: " + msg);
    }


}
