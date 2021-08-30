package com.garven.rabbitmq;

import com.garven.rabbitmq.config.RabbitMqConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: Administrator
 * @Date: 2021/8/29 23:21
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class Producer {

    @Autowired
    private RabbitTemplate template;

    @Test
    public void testSend() {
        template.convertAndSend(RabbitMqConfig.EXCHANGE_NAME + "111", "boot.start", "Hello Rabbit!");
    }

    /**
     * 确认模式：
     * 步骤：
     * 1. 确认模式开启：ConnectionFactory 中开启publisher-confirms="true"
     * 2. 再rabbitTemplate 定义confirmCallback回调函数
     */
    @Test
    public void testConfirm() {

        template.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {

            /**
             * @param correlationData 相关配置信息
             * @param ack   exchange 交换机是否成功接受到消息：true - 成功， false - 失败
             * @param cause 失败原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("confirm 方法被执行了...");
                if (ack) {
                    System.out.println("消息接受成功！" + cause);
                } else {
                    System.out.println("消息接受失败！" + cause);
                }
            }
        });

        template.convertAndSend(RabbitMqConfig.EXCHANGE_NAME + "111", "boot.start", "Hello Rabbit!");
    }


    /**
     * 回退模式：当消息发送给Exchange 之后，Exchange 路由到Queue 失败时，才会执行ReturnCallback
     * 步骤：
     * 1. ConnectionFactory 开启回退模式
     * 2. rabbitTemplate 设置ReturnCallback
     * 3. 设置Exchange 处理消息的模式：
     * <1>. 如果消息没有路由到Queue，则丢弃消息(默认)
     * <2>. 如果消息没有路由到Queue，Mandatory返回给消息发送方ReturnCallback。
     */
    @Test
    public void testReturn() {

        template.setMandatory(true);

        template.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            /**
             * @param message 消息
             * @param replyCode 返回code 码
             * @param replyText 返回消息
             * @param exchange 交换机名称
             * @param routingKey routingKey 名称
             */
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println(message);
                System.out.println(replyCode);
                System.out.println(replyText);
                System.out.println(exchange);
                System.out.println(routingKey);
            }
        });

        template.convertAndSend(RabbitMqConfig.EXCHANGE_NAME, "boots.start", "Hello Rabbit!");
    }

}
