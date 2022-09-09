package com.bantads.account.transaction.amqp;

import com.bantads.account.transaction.models.TransactionDTO;
import com.google.gson.Gson;

import java.io.Serializable;

public class TDataTransfer implements Serializable {
    private TransactionDTO transaction;
    private String action;

    public TDataTransfer() {
    }

    public TDataTransfer(TransactionDTO transaction, String action) {
        this.transaction = transaction;
        this.action = action;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static TDataTransfer fromJson(String json) {
        return new Gson().fromJson(json, TDataTransfer.class);
    }

    public TransactionDTO getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionDTO transaction) {
        this.transaction = transaction;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

}
