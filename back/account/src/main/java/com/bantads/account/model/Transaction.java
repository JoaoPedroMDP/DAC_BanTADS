package com.bantads.account.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

import org.modelmapper.ModelMapper;

@Entity
@Table(name = "transactions")
public class Transaction implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    // @Column(name = "account_id")
    // private Long accountId;
    private Account account;

    @Column(name = "type")
    private String type;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    @Column(name = "extra_data")
    private String extraData;

    public Transaction() {
    }

    public Transaction(Long id, Account account, String type, Double amount,
            // public Transaction(Long id, Long accountId, String type, Double amount,
            Timestamp timestamp, String extraData) {
        this.id = id;
        this.account = account;
        // this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
        this.extraData = extraData;
    }

    public TransactionDTO toDto() {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, TransactionDTO.class);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }

    public static Long getSerialversionuid() {
        return serialVersionUID;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    // public Long getAccountId() {
    // return accountId;
    // }

    // public void setAccountId(Long accountId) {
    // this.accountId = accountId;
    // }

}
