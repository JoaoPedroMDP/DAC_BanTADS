package com.bantads.account.model;

import java.io.Serializable;
import java.util.List;
import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity
@Table(name = "accounts")
public class Account implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "acc_limit")
    private Double limit;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    public Account() {
    }

    public Account(Long id, Long userId, Double balance, List<Transaction> transactions, Double limit) {
        this.id = id;
        this.userId = userId;
        this.balance = balance;
        this.transactions = transactions;
        this.limit = limit;
    }

    public AccountDTO toDto() {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, AccountDTO.class);
    }

    public boolean allowTransaction(Double amount) {
        if (amount > limit) {
            return false;
        }

        return true;
    }

    public void updateBalance(Double amount) {
        this.balance += amount;
        if ((amount < 0 && this.balance < 0)) {
            this.limit += this.balance;
            this.balance = 0D;
        }
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

    public static Long getSerialversionuid() {
        return serialVersionUID;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Double getLimit() {
        return limit;
    }

    public void setLimit(Double limit) {
        this.limit = limit;
    }

}
