package com.bantads.saga.amqp;

import com.bantads.saga.models.AccountDTO;
// import com.google.gson.Gson;

import java.io.Serializable;

public class AccountTransfer implements Serializable {
    private AccountDTO account;
    private String action;

    public AccountTransfer() {
        System.out.println("Inicializando um DT vazio");
    }

    public AccountTransfer(AccountDTO account, String action) {
        this.account = account;
        this.action = action;
        System.out.println("Inicializando um DT com dados");
    }

    // public String toJson() {
    // return new Gson().toJson(this);
    // }

    // public static AccountTransfer fromJson(String json) {
    // return new Gson().fromJson(json, AccountTransfer.class);
    // }

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
