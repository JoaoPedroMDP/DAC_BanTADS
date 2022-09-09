package com.bantads.account.transaction.services;

import com.bantads.account.account.models.command.AccountC;
import com.bantads.account.account.models.query.AccountQ;
import com.bantads.account.lib.RestService;
import com.bantads.account.lib.Statement;
import com.bantads.account.transaction.amqp.TransactionSender;
import com.bantads.account.transaction.models.TransactionDTO;
import com.bantads.account.transaction.models.command.TransactionC;
import com.bantads.account.transaction.models.query.TransactionQ;
import com.bantads.account.transaction.repository.command.TransactionRepositoryC;
import com.bantads.account.transaction.repository.query.TransactionRepositoryQ;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TransactionServices {
    @Autowired
    private TransactionRepositoryQ queries;

    @Autowired
    private TransactionRepositoryC commands;

    @Autowired
    private TransactionSender sender;

    @Autowired
    private RestService rest;

    public ArrayList<TransactionDTO> getAccountTransactions(AccountQ acc, Long from, Long to) {
        List<TransactionQ> transactions = queries.findByAccountIdAndTimestampBetween(acc, from, to);

        return transactions.stream()
                .map(TransactionQ::toDto).collect(Collectors.toCollection(ArrayList::new));
    }

    public List<TransactionDTO> getAccountTransactions(Long accountId) {
        List<TransactionQ> transactions = queries.findByAccountId(accountId);

        return transactions.stream()
                .map(TransactionQ::toDto)
                .collect(Collectors.toList());
    }

    private TransactionC createTransaction(AccountC account, String type, Double amount, Long timestamp,
            String extra_data) {
        TransactionC newTransaction = new TransactionC();
        newTransaction.setAccount(account);
        newTransaction.setType(type);
        newTransaction.setAmount(amount);
        newTransaction.setTimestamp(timestamp);
        newTransaction.setExtraData(Objects.equals(extra_data, "") ? null : extra_data);
        newTransaction.setBalanceBefore(account.getBalance() - amount);

        TransactionC created = commands.save(newTransaction);
        sender.send(created.toDto(), "create");
        return created;
    }

    private TransactionC createTransaction(AccountC account, String type, Double amount, Long timestamp) {
        return createTransaction(account, type, amount, timestamp, "");
    }

    public TransactionC deposit(AccountC account, Double amount) {
        return createTransaction(account, "deposit", amount, System.currentTimeMillis());
    }

    public TransactionC withdraw(AccountC account, Double amount) {
        return createTransaction(account, "withdraw", -1 * amount, System.currentTimeMillis());
    }

    public TransactionC transfer(AccountC from, AccountC to, Double amount) {
        LinkedHashMap<String, String> origin = new LinkedHashMap<String, String>();
        origin.put("origin", from.getUserId().toString());
        LinkedHashMap<String, String> destiny = new LinkedHashMap<String, String>();
        destiny.put("destiny", to.getUserId().toString());

        Gson gson = new Gson();

        createTransaction(
                from, "transfer", -1 * amount,
                System.currentTimeMillis(), gson.toJson(destiny));

        return createTransaction(
                to, "transfer", amount,
                System.currentTimeMillis(), gson.toJson(origin));
    }

    public Statement buildStatement(ArrayList<TransactionDTO> dtos, Long fromTS, Long toTS) {
        return new Statement(dtos, fromTS, toTS, this.rest);
    }
}
