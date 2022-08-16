package com.bantads.account.model;

import java.io.Serializable;

import org.modelmapper.ModelMapper;

public class TransactionDTO implements Serializable {
    private Long id;
    private String type;
    private Double amount;
    private Long timestamp;
    private String extraData;

    public TransactionDTO() {
    }

    public TransactionDTO(Long id, String type, Double amount, Long timestamp, String extraData) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
        this.extraData = extraData;
    }

    public Transaction toEntity() {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, Transaction.class);
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

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }

}
