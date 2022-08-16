package com.bantads.account.model;

import java.io.Serializable;
import java.util.List;

import org.modelmapper.ModelMapper;

public class AccountDTO implements Serializable {
    private Long id;
    private Long userId;
    private Double balance;
    private Double limit;
    private List<TransactionDTO> transactions;

    public AccountDTO() {
        super();
    }

    public AccountDTO(Long id, Long userId, Double balance, List<TransactionDTO> transactions, Double limit) {
        super();
        this.id = id;
        this.userId = userId;
        this.balance = balance;
        this.transactions = transactions;
        this.limit = limit;
    }

    public Account toEntity() {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, Account.class);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public List<TransactionDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionDTO> transactions) {
        this.transactions = transactions;
    }

    public Double getLimit() {
        return limit;
    }

    public void setLimit(Double limit) {
        this.limit = limit;
    }

}
