package com.itguigu.rabbitmq.springbootrabbitmq.config.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author: IsaiahLu
 * @date: 2023/2/3 17:40
 * 回调接口
 */
@Component
@Slf4j
public class MyCallBack implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {
    /**
     * 注入
     */
    @Autowired(required = false)
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }

    /**
     * 交换机确认回调方法，
     * 1：发消息，收到消息，回调
     *
     * @param correlationData 保存回调消息的id 以及相关信息
     * @param ack             是否收到消息 true or false
     * @param cause           收到消息成功值为null 接收失败，为失败原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {

        String id = correlationData != null ? correlationData.getId() : "";

        if (ack) {
            log.info("交换机已经收到 id 为:{}的消息", id);
        } else {
            log.info("交换机还未收到 id 为:{}消息,由于原因:{}", id, cause);
        }
    }

    /**
     * * 消息路由失败，回退接口
     * * 可以在消息传递过程中不可到底目的地时，将消息返回给生产者
     *
     * @param returned
     */
    @Override
    public void returnedMessage(ReturnedMessage returned) {
        int replyCode = returned.getReplyCode();
        String replyText = returned.getReplyText();
        String exchange = returned.getExchange();
        String routingKey = returned.getRoutingKey();
        String message = String.valueOf(returned.getMessage());
        log.info("消息:{}被服务器退回，退回原因:{}, 交换机是:{}, 路由 key:{}", message, replyText, exchange, routingKey);

    }
}
