package com.bantads.account.transaction.models.query;

import com.bantads.account.account.models.query.AccountQ;
import com.bantads.account.transaction.models.TransactionDTO;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "transactions")
public class TransactionQ implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private AccountQ account;

    @Column(name = "type")
    private String type;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "timestamp")
    private Long timestamp;

    @Column(name = "extra_data")
    private String extraData;

    @Column(name = "balance_before")
    private Double balanceBefore;

    public TransactionQ() {
    }

    public TransactionQ(Long id, AccountQ account, String type, Double amount,
                        Long timestamp, String extraData, Double balanceBefore) {
        this.id = id;
        this.account = account;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
        this.extraData = extraData;
        this.balanceBefore = balanceBefore;
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

    public static Long getSerialversionuid() {
        return serialVersionUID;
    }

    public AccountQ getAccount() {
        return account;
    }

    public void setAccount(AccountQ account) {
        this.account = account;
    }

    public Double getBalanceBefore() {
        return balanceBefore;
    }

    public void setBalanceBefore(Double balanceBefore) {
        this.balanceBefore = balanceBefore;
    }
}
