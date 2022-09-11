package com.bantads.saga.amqp;

import com.bantads.saga.models.AccountDTO;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;

public class AccountProducer {
    @Autowired
    private RabbitTemplate template;

    @Autowired
    @Qualifier("account")
    private Queue queue;

    public void send(AccountDTO account, String action) {
        this.template.convertAndSend(this.queue.getName(), account);
    }

    public AccountTransfer sendAndReceive(AccountDTO account, String action) {
        AccountTransfer dt = new AccountTransfer(account, action);
        System.out.println("Chegou no sendandreceive");
        AccountTransfer resposta = (AccountTransfer) this.template.convertSendAndReceive(this.queue.getName(), dt);
        System.out.println("Resposta: " + resposta);
        return resposta;
    }
}
