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

    public boolean sendAndReceive(AccountDTO account, String action) {
        AccountTransfer dt = new AccountTransfer(account, action);
        AccountTransfer resposta = this.template.convertSendAndReceiveAsType(this.queue.getName(), dt,
                new ParameterizedTypeReference<AccountTransfer>() {
                });
        if (resposta != null) {
            System.out.println(resposta.getAction());
            return true;
        } else {
            System.out.println("Falhou");
            return false;
        }
    }
}
