package com.bantads.account.model;

import java.io.Serializable;

public class AccountDTO implements Serializable {
    private Long id;
    private Long userId;
    private Double balance;

    public AccountDTO() {
        super();
    }

    public AccountDTO(Long id, Long userId, Double balance) {
        super();
        this.id = id;
        this.userId = userId;
        this.balance = balance;
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

}
