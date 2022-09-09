package com.bantads.account.transaction.models;

import com.bantads.account.lib.TransferDetails;
import com.bantads.account.transaction.models.command.TransactionC;
import com.bantads.account.transaction.models.query.TransactionQ;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.google.gson.Gson;

import org.modelmapper.ModelMapper;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TransactionDTO implements Serializable {
    private Long id;
    private String type;
    private Double amount;
    private Long timestamp;
    @JsonRawValue
    private String extraData;
    private Double balanceBefore;

    private Long accountId;

    public TransactionDTO() {
    }

    public TransactionDTO(Long id, String type, Double amount, Long timestamp, String extraData, Double balanceBefore,
            Long accountId) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
        this.extraData = extraData;
        this.balanceBefore = balanceBefore;
        this.accountId = accountId;
    }

    public TransactionC toCommand() {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, TransactionC.class);
    }

    public TransactionQ toQuery() {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, TransactionQ.class);
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

    public Double getBalanceBefore() {
        return balanceBefore;
    }

    public void setBalanceBefore(Double balanceBefore) {
        this.balanceBefore = balanceBefore;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @JsonIgnore
    public Boolean isTransfer() {
        if (this.getExtraData() != null) {
            return true;
        }
        return false;
    }

    @JsonIgnore
    public String getTransferParticipant() {
        TransferDetails details = new Gson().fromJson(this.getExtraData(), TransferDetails.class);
        return details.getTransferParticipant();
    }

    @JsonIgnore
    public void setTransferParticipant(String participant) {
        TransferDetails details = new Gson().fromJson(this.getExtraData(), TransferDetails.class);
        details.setTransferParticipant(participant);
        this.setExtraData(new Gson().toJson(details));
    }
}
