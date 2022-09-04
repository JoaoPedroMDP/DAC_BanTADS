package com.bantads.account.account.amqp;

import com.bantads.account.account.models.query.AccountQ;
import com.bantads.account.account.repository.query.AccountRepositoryQ;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;

@RabbitListener(queues = "account")
public class AccountListener {
    @Autowired
    private AccountRepositoryQ viewDatabase;

    @RabbitHandler
    public void receive(@Payload ADataTransfer dt) {
        System.out.println("[ACCOUNTS] Ação executada: " + dt.getAction());
        switch(dt.getAction()){
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