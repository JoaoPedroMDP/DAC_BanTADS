package com.bantads.account.lib;

import java.util.ArrayList;

import com.bantads.account.transaction.models.TransactionDTO;

public class StatementDay {
    public Double consolidatedValue;
    public Long date;
    public ArrayList<TransactionDTO> transactions;

    public StatementDay(ArrayList<TransactionDTO> dtos) {
        this.transactions = dtos;
        TransactionDTO last = dtos.get(dtos.size() - 1);
        this.consolidatedValue = last.getBalanceBefore() + last.getAmount();
        this.date = last.getTimestamp();
    }
}
