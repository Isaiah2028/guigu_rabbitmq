package com.itguigu.rabbitmq.springbootrabbitmq.consumer;

import com.itguigu.rabbitmq.springbootrabbitmq.config.rabbitmq.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: IsaiahLu
 * @date: 2023/2/3 22:08
 * 备份交换机，报警队列的消息监听器
 */
@Component
@Slf4j
public class WarningConsumer {

    @RabbitListener(queues = ConfirmConfig.WARNING_QUEUE_NAME)
    public void receiveWarningMsg(Message message) {
        String msg = new String(message.getBody());
        log.info("报警发现发送不可路由消息: {}",msg);
    }
}
