package com.itguigu.rabbitmq.springbootrabbitmq.controller;

import com.itguigu.rabbitmq.springbootrabbitmq.config.rabbitmq.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: IsaiahLu
 * @date: 2023/2/3 16:43
 */
@RestController
@RequestMapping("/confirm")
@Slf4j
public class ProducerController {

    @Autowired(required = false)
    RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMessage/{message}")
    public void sendMessage(@PathVariable String message) {

        CorrelationData correlationData = new CorrelationData("1");


        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME,
                ConfirmConfig.CONFIRM_ROUTING_KEY,
                message, correlationData);
        log.info("发送消息内容:{}", message);


    }

}
