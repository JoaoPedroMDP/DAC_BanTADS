package com.bantads.account.transaction.amqp;

import com.bantads.account.transaction.models.TransactionDTO;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class TransactionSender {
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue transactionQueue;

    public void send(TransactionDTO transaction, String action) {
        TransactionTransfer dt = new TransactionTransfer(transaction, action);
        System.out.println("Queue name: " + this.transactionQueue.getName());
        this.template.convertAndSend(this.transactionQueue.getName(), dt);
    }

    public void getCustomer(Long id) {
        this.template.convertAndSend(this.transactionQueue.getName(), id);
    }
}
