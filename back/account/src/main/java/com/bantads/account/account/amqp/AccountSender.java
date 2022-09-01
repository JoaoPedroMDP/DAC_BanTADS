package com.bantads.account.account.amqp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.bantads.account.account.models.AccountDTO;

import org.springframework.amqp.core.Queue;

public class AccountSender {
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue queue;

    public void send(AccountDTO account, String action) {
        DataTransfer dt = new DataTransfer(account, action);
        this.template.convertAndSend(this.queue.getName(), dt);
    }
}
