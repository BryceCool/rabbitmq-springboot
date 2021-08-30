package com.garven.rabbitmq.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: Administrator
 * @Date: 2021/8/30 9:30
 */
@Component
public class ListenerMsg {

    @RabbitListener(queues = "boot_queue")
    public void receiveMsg(Message msg) {

        System.out.println(msg);
        System.out.println(new String(msg.getBody()));
    }

}
