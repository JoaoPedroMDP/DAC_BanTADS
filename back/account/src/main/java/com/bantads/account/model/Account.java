package com.bantads.account.model;

import java.io.Serializable;

public class Account implements Serializable {
    private Integer id;
    private Integer userId;
    private Double balance;

    public Account() {
        super();
    }

    public Account(Integer id, Integer userId, Double balance) {
        super();
        this.id = id;
        this.userId = userId;
        this.balance = balance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

}
