package com.bantads.account.account.amqp.saga;

import com.bantads.account.account.amqp.AccountTransfer;
import com.bantads.account.account.amqp.account.AccountSender;
import com.bantads.account.account.models.AccountDTO;
import com.bantads.account.account.services.AccountServices;
import com.bantads.account.exceptions.AccountNotFound;
import com.bantads.account.exceptions.DuplicateAccountException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class AccountSagaListener {
    @Autowired
    private AccountServices service;

    @Autowired
    private AccountSender sender;

    @RabbitListener(queues = "account-saga")
    public AccountTransfer receive(@Payload AccountTransfer dt) {
        System.out.println("[ACCOUNTS-SAGA] Ação executada: " + dt.getAction());
        switch (dt.getAction()) {
            case "teste":
                dt.setAction("teste-deu-certo");
                System.out.println("Retornando resposta");
                // this.sender.send(dt.getAccount(), dt.getAction());
            break;
            case "create-account":
                // Vindo da SAGA
                try{
                    AccountDTO created = this.service.createAccount(dt.getAccount());
                    dt.setAccount(created);
                    dt.setAction("create-account-ok");
                }catch(DuplicateAccountException e){
                    dt.setAction("create-account-error");
                    dt.setAccount(null);
                }
            break;
            case "delete-account":
                // Vindo da SAGA
                Boolean success = this.deleteAccount(dt);
                if (success) {
                    // Como tanto faz se não achou a conta ou se a conta foi encontrada, mas, então,
                    // deletada, retorno a mesma mensagem sempre
                    dt.setAccount(null);
                    dt.setAction("delete-account-ok");
                }
            break;
        }

        return dt;
    }

    private Boolean deleteAccount(AccountTransfer dt) {
        try {
            this.service.deleteAccount(dt.getAccount().getId());
        } catch (AccountNotFound e) {
            System.out.println("Tentativa de exclusão de uma conta não existente (" + dt.getAccount().getId() + ")");
        }

        return true;
    }
}
