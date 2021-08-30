package com.garven.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitmq 配置类
 *
 * @Author: Administrator
 * @Date: 2021/8/28 18:41
 */
@Configuration
public class RabbitMqConfig {

    public static final String EXCHANGE_NAME = "boot_topic";

    public static final String QUEUE_NAME = "boot_queue";

    // 配置Exchange
    @Bean("bootExchange")
    public Exchange bootExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME).build();
    }

    // 配置Queue
    @Bean("bootQueue")
    public Queue bootQueue() {
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    // 绑定Exchange 和 Queue
    @Bean
    public Binding bindQueueExchange(@Qualifier("bootExchange") Exchange exchange,
                                     @Qualifier("bootQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with("boot.#").noargs();
    }

}
