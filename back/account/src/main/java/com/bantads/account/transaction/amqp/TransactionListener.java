package com.bantads.account.transaction.amqp;

import com.bantads.account.transaction.models.query.TransactionQ;
import com.bantads.account.transaction.repository.query.TransactionRepositoryQ;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;

@RabbitListener(queues = "transaction")
public class TransactionListener {
    @Autowired
    private TransactionRepositoryQ viewDatabase;

    @RabbitHandler
    public void receive(@Payload TransactionTransfer dt) {
        System.out.println("[TRANSACTIONS] Ação executada: " + dt.getAction());
        switch(dt.getAction()){
            case "create":
            case "update":
                TransactionQ saved = viewDatabase.save(dt.getTransaction().toQuery());
                System.out.println("Operação create/update concluida: " + saved.getId());
                break;
            case "delete":
                viewDatabase.delete(dt.getTransaction().toQuery());
                System.out.println("Operação delete concluida: " + dt.getTransaction().getId());
                break;
        }
    }
}