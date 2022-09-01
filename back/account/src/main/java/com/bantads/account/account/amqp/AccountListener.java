package com.bantads.account.account.amqp;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;

@RabbitListener(queues = "account")
public class AccountListener {

    @RabbitHandler
    public void receive(@Payload DataTransfer dt) {
        System.out.println("Ação executada: " + dt.getAction());
        System.out.println(dt);
    }
}