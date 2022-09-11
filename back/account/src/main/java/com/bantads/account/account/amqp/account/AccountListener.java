package com.bantads.account.account.amqp.account;

import com.bantads.account.account.amqp.AccountTransfer;
import com.bantads.account.account.models.query.AccountQ;
import com.bantads.account.account.repository.query.AccountRepositoryQ;
import com.bantads.account.account.services.AccountServices;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class AccountListener {
    @Autowired
    private AccountRepositoryQ viewDatabase;

    @Autowired
    private AccountServices service;

    @RabbitListener(queues = "account")
    public void receive(@Payload AccountTransfer dt) {
        System.out.println("[ACCOUNTS] Ação executada: " + dt.getAction());
        switch (dt.getAction()) {
            case "create":
            case "update":
                AccountQ saved = viewDatabase.save(dt.getAccount().toQuery());
                System.out.println("Operação create/update concluida: " + saved.getId());
                break;
            case "delete":
                viewDatabase.delete(dt.getAccount().toQuery());
                System.out.println("Operação delete concluida: " + dt.getAccount().getId());
                break;
        }
    }
}
