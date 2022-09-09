package com.bantads.account.account.amqp;

import com.bantads.account.account.models.AccountDTO;
import com.google.gson.Gson;

import java.io.Serializable;

public class ADataTransfer implements Serializable {
    private AccountDTO account;
    private String action;

    public ADataTransfer() {
        System.out.println("Inicializando um DT vazio");
    }

    public ADataTransfer(AccountDTO account, String action) {
        this.account = account;
        this.action = action;
        System.out.println("Inicializando um DT com dados");
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static ADataTransfer fromJson(String json) {
        return new Gson().fromJson(json, ADataTransfer.class);
    }

    public AccountDTO getAccount() {
        return account;
    }

    public void setAccount(AccountDTO account) {
        this.account = account;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

}
