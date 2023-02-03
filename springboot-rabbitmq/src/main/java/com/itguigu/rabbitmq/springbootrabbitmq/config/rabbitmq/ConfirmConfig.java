package com.itguigu.rabbitmq.springbootrabbitmq.config.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: IsaiahLu
 * @date: 2023/2/3 16:29
 * 配置类 发布确认，高级
 */
@Configuration
public class ConfirmConfig {

    /**
     * 交换机
     */
    public static final String CONFIRM_EXCHANGE_NAME = "confirm.exchange";
    /**
     * 队列
     */
    public static final String CONFIRM_QUEUE_NAME = "confirm.queue";
    /**
     * routingKey
     */
    public static final String CONFIRM_ROUTING_KEY = "key1";

    @Bean("confirmExchange")
    public DirectExchange confirmExchange(){
        return new DirectExchange(CONFIRM_EXCHANGE_NAME);
    }

    @Bean("confirmQueue")
    public Queue confirmQueue(){
        return QueueBuilder.durable(CONFIRM_QUEUE_NAME).build();
    }

    @Bean
    public Binding queueBinding(@Qualifier("confirmQueue") Queue queue,
                                @Qualifier("confirmExchange") DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(CONFIRM_ROUTING_KEY);

    }



}
