package com.itguigu.rabbitmq.springbootrabbitmq.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: IsaiahLu
 * @date: 2023/2/3 14:19
 */
@Configuration
public class DelayedQueueConfig {

    /**
     * 交换机
     */
    public static final String DELAYED_EXCHANGE_NAME = "delayed.exchange";
    /**
     * 队列
     */
    public static final String DELAYED_QUEUE_NAME = "delayed.queue";
    /**
     * routingKey
     */
    public static final String DELAYED_ROUTING_KEY = "delayed.routingkey";

    /**
     * 声明队列
     *
     * @return Queue(DELAYED_QUEUE_NAME
     */
    @Bean
    public Queue delayedQueue() {
        return new Queue(DELAYED_QUEUE_NAME);
    }

    /**
     * 自定义延迟交换机
     */
    @Bean
    public CustomExchange delayedExchange() {
        Map<String, Object> args = new HashMap<>(3);
        args.put("x-delayed-type", "direct");
        return new CustomExchange(DELAYED_EXCHANGE_NAME, "x-delayed-message", true, false, args);
    }

    /**
     * 绑定队列到延迟交换机
     */
    @Bean
    public Binding bindingDelayedQueue(@Qualifier("delayedQueue") Queue queue,
                                       @Qualifier("delayedExchange") CustomExchange delayExchange) {
        return BindingBuilder.bind(queue).to(delayExchange).with(DELAYED_ROUTING_KEY).noargs();
    }

}
