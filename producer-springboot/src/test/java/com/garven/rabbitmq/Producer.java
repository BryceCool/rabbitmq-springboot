package com.garven.rabbitmq;

import com.garven.rabbitmq.config.RabbitMqConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
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

}
