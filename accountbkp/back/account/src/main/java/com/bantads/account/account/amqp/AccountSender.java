package com.bantads.account.account.amqp;

import com.bantads.account.account.models.AccountDTO;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountSender {
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue accountQueue;

    public void send(AccountDTO account, String action) {
        ADataTransfer dt = new ADataTransfer(account, action);
        System.out.println("Queue name: " + this.accountQueue.getName());
        this.template.convertAndSend(this.accountQueue.getName(), dt);
    }
}
