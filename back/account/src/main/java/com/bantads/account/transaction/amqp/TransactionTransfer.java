package com.bantads.account.transaction.amqp;

import com.bantads.account.transaction.models.TransactionDTO;
import com.google.gson.Gson;

import java.io.Serializable;

public class TransactionTransfer implements Serializable {
    private TransactionDTO transaction;
    private String action;

    public TransactionTransfer() {
    }

    public TransactionTransfer(TransactionDTO transaction, String action) {
        this.transaction = transaction;
        this.action = action;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static TransactionTransfer fromJson(String json) {
        return new Gson().fromJson(json, TransactionTransfer.class);
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
