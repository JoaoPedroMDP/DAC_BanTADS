package com.bantads.account.account.amqp.account;

import com.bantads.account.account.amqp.AccountTransfer;
import com.bantads.account.account.models.AccountDTO;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;

public class AccountSender {
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue accountQueue;

    @Autowired
    private Queue sagaQueue;

    public void send(AccountDTO account, String action) {
        AccountTransfer dt = new AccountTransfer(account, action);
        System.out.println("Queue name: " + this.accountQueue.getName());
        this.template.convertAndSend(this.accountQueue.getName(), dt);
    }

    // public void sendSaga(AccountDTO account, String message) {
    // AccountTransfer dt = new AccountTransfer(account, message);
    // System.out.println("Queue name: " + this.sagaQueue.getName());
    // this.template.convertAndSend(this.sagaQueue.getName(), dt);
    // }

    public void sendAndReceive(AccountDTO account, String message) {
        AccountTransfer dt = new AccountTransfer(account, "teste");
        System.out.println("Chegou no sendandreceive");
        Object resposta = (AccountTransfer) this.template.convertSendAndReceive(this.sagaQueue.getName(), dt);
        System.out.println("Resposta: " + resposta);
    }
}
