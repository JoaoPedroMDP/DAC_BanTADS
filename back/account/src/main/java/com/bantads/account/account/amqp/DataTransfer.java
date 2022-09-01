package com.bantads.account.account.amqp;

import java.io.Serializable;

import com.bantads.account.account.models.AccountDTO;
import com.google.gson.Gson;

public class DataTransfer implements Serializable {
    private AccountDTO account;
    private String action;

    public DataTransfer() {
        System.out.println("Inicializando um DT vazio");
    }

    public DataTransfer(AccountDTO account, String action) {
        this.account = account;
        this.action = action;
        System.out.println("Inicializando um DT com dados");
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static DataTransfer fromJson(String json) {
        return new Gson().fromJson(json, DataTransfer.class);
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
