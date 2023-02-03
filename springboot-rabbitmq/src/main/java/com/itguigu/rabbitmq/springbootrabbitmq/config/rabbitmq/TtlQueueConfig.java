package com.itguigu.rabbitmq.springbootrabbitmq.config.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: IsaiahLu
 * @date: 2023/2/2 21:17
 */
@Configuration
public class TtlQueueConfig {

    /**
     * 普通交换机名称
     */
    public static final String X_EXCHANGE = "X";
    /**
     * 死信交换机名称
     */
    public static final String Y_DEAD_LETTER_EXCHANGE = "Y";
    /**
     * 普通队列名称
     */
    public static final String QUEUE_A = "QA";
    public static final String QUEUE_B = "QB";
    /**
     * 死信队列名称
     */
    public static final String DEAD_LETTER_QUEUE = "QD";
    /**
     * 普通队列名称 不添加 ttl过期时间的队列
     */
    public static final String QUEUE_C = "QC";

    @Bean("queueC")
    public Queue queueC() {
        Map<String, Object> arguments = new HashMap<>(3);
        //设置死信交换机
        arguments.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        //设置死信routingKey
        arguments.put("x-dead-letter-routing-key", "YD");
        //不设置ttl过期时间属性
        return QueueBuilder.durable(QUEUE_C).withArguments(arguments).build();
    }

    /**
     * 绑定qc到 xExchange交换机
     * @param queueC qc队列
     * @param xExchange xexchange 交换机
     * @return 返回绑定对象
     */
    @Bean
    public Binding  queueCBindingX(@Qualifier("queueC") Queue queueC,
                                         @Qualifier("xExchange") DirectExchange xExchange) {
        return BindingBuilder.bind(queueC).to(xExchange).with("XC");
    }


    /**
     * 声明x_exchange交换机
     *
     * @return 交换机
     */
    @Bean("xExchange")
    public DirectExchange xExchange() {
        return new DirectExchange(X_EXCHANGE);
    }

    /**
     * 声明y_exchange交换机
     *
     * @return 交换机
     */
    @Bean("yExchange")
    public DirectExchange yExchange() {

        return new DirectExchange(Y_DEAD_LETTER_EXCHANGE);
    }

    //声明队列

    /**
     * 普通队列，需要过期时间
     *
     * @return queueA
     */
    @Bean("queueA")
    public Queue queueA() {
        Map<String, Object> arguments = new HashMap<>(3);
        //设置死信交换机
        arguments.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        //设置死信routingKey
        arguments.put("x-dead-letter-routing-key", "YD");
        //过期时间 单位是ms
        arguments.put("x-message-ttl", 10000);
        return QueueBuilder.durable(QUEUE_A).withArguments(arguments).build();
    }

    /**
     * 普通队列，需要过期时间
     *
     * @return queueB
     */
    @Bean("queueB")
    public Queue queueB() {
        Map<String, Object> arguments = new HashMap<>(3);
        //设置死信交换机
        arguments.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        //设置死信routingKey
        arguments.put("x-dead-letter-routing-key", "YD");
        //过期时间 单位是ms
        arguments.put("x-message-ttl", 40000);
        return QueueBuilder.durable(QUEUE_B).withArguments(arguments).build();
    }

    /**
     * @return queue
     */
    @Bean("queueD")
    public Queue queueD() {
        return QueueBuilder.durable(DEAD_LETTER_QUEUE).build();
    }

    //绑定
    @Bean
    public Binding queueABindingX(@Qualifier("queueA") Queue queueA, @Qualifier("xExchange") DirectExchange xExchange) {
        return BindingBuilder.bind(queueA).to(xExchange).with("XA");
    }

    @Bean
    public Binding queueBBindingX(@Qualifier("queueB") Queue queueB, @Qualifier("xExchange") DirectExchange xExchange) {
        return BindingBuilder.bind(queueB).to(xExchange).with("XB");
    }

    @Bean
    public Binding queueDBindingY(@Qualifier("queueD") Queue queueD, @Qualifier("yExchange") DirectExchange yExchange) {
        return BindingBuilder.bind(queueD).to(yExchange).with("YD");
    }

}
