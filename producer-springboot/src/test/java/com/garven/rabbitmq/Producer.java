package com.garven.rabbitmq;

import com.garven.rabbitmq.config.RabbitMqConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
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
        template.convertAndSend(RabbitMqConfig.EXCHANGE_NAME, "boot.start", "Hello Rabbit!");
    }

}
