package com.itguigu.rabbitmq.springbootrabbitmq.config.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

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
    /**
     * 备份交换机
     */
    public static final String BACKUP_EXCHANGE_NAME = "backup.exchange";
    /**
     * 备份队列
     */
    public static final String BACKUP_QUEUE_NAME = "backup.exchange";
    /**
     * 报警队列
     */
    public static final String WARNING_QUEUE_NAME = "warning.exchange";


    /**
     * 无投递消息将消息备份发送给备份交换机
     *
     * @return 返回交换机，
     */
    @Bean("confirmExchange")
    public DirectExchange confirmExchange() {
        // return new DirectExchange(CONFIRM_EXCHANGE_NAME);
        return ExchangeBuilder.directExchange(CONFIRM_EXCHANGE_NAME).durable(true).withArgument("alternate-exchange", BACKUP_EXCHANGE_NAME).build();
    }

    @Bean("confirmQueue")
    public Queue confirmQueue() {
        return QueueBuilder.durable(CONFIRM_QUEUE_NAME).build();
    }

    @Bean
    public Binding queueBinding(@Qualifier("confirmQueue") Queue queue,
                                @Qualifier("confirmExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(CONFIRM_ROUTING_KEY);
    }

    /**
     * 备份交换机
     *
     * @return 绑定对象
     */
    @Bean("backupExchange")
    public FanoutExchange backupExchange() {
        return new FanoutExchange(BACKUP_EXCHANGE_NAME);
    }

    /**
     * 备份队列
     *
     * @return 绑定对象
     */
    @Bean("backupQueue")
    public Queue backupQueue() {
        return QueueBuilder.durable(BACKUP_QUEUE_NAME).build();
    }

    /**
     * 报警队列
     *
     * @return 返回队列
     */
    @Bean("warningQueue")
    public Queue warningQueue() {
        return QueueBuilder.durable(WARNING_QUEUE_NAME).build();
    }

    /**
     * 备份队列绑定备份交换机
     *
     * @param backupQueue 备份的队列
     * @param exchange    备份交换机
     * @return 返回绑定对象
     */
    @Bean
    public Binding backupQueueBinding(@Qualifier("backupQueue") Queue backupQueue,
                                      @Qualifier("backupExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(backupQueue).to(exchange);
    }

    /**
     * 报警队列绑定备份交换机
     *
     * @param warningQueue 报警队列的队列
     * @param exchange     备份交换机
     * @return 返回绑定对象
     */
    @Bean
    public Binding warningQueueBinding(@Qualifier("warningQueue") Queue warningQueue,
                                       @Qualifier("backupExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(warningQueue).to(exchange);
    }


}
