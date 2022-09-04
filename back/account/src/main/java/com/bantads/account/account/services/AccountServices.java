package com.bantads.account.account.services;

import com.bantads.account.account.models.AccountDTO;
import com.bantads.account.account.models.command.AccountC;
import com.bantads.account.account.models.query.AccountQ;
import com.bantads.account.account.repository.command.AccountRepositoryC;
import com.bantads.account.account.repository.query.AccountRepositoryQ;
import com.bantads.account.exceptions.AccountNotFound;
import com.bantads.account.exceptions.InsufficientFunds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class AccountServices {
    @Autowired
    private AccountRepositoryC commands;

    @Autowired
    private AccountRepositoryQ queries;

    public List<AccountDTO> getAllAccounts() {
        List<AccountQ> accounts = queries.findAll();
        return accounts.stream()
                .map(AccountQ::toDto)
                .collect(Collectors.toList());
    }

    public AccountQ getAccount(Long accountId) throws AccountNotFound {
        AccountQ acc = null;
        try {
            acc = queries.findById(accountId).get();
            return acc;
        } catch (NoSuchElementException e) {
            throw new AccountNotFound();
        }
    }

    public AccountC getAccountC(Long accountId) throws AccountNotFound {
        AccountC acc = null;
        try {
            acc = queries.findById(accountId).get().toCommand();
            return acc;
        } catch (NoSuchElementException e) {
            throw new AccountNotFound();
        }
    }

    public AccountDTO getAccountDTO(Long accountId) throws AccountNotFound {
        AccountQ acc = null;
        try {
            acc = queries.findById(accountId).get();
        } catch (NoSuchElementException e) {
            throw new AccountNotFound();
        }

        AccountDTO dto = acc.toDto();
        return dto;
    }

    public AccountDTO updateAccount(Long accountId, AccountDTO newData) throws AccountNotFound {
        AccountC toUpdate = null;

        try{
            toUpdate = queries.findById(accountId).get().toCommand();
        }catch(NoSuchElementException e){
            throw new AccountNotFound();
        }

        toUpdate.setBalance(newData.getBalance());
        toUpdate.setLimit(newData.getLimit());
        toUpdate.setUserId(newData.getUserId());
        toUpdate = commands.save(toUpdate);

        return toUpdate.toDto();
    }

    public AccountDTO createAccount(AccountDTO newAccount) {
        AccountC toAccount = newAccount.toCommand();
        AccountC created = commands.save(toAccount);
        AccountDTO toDTO = created.toDto();

        return toDTO;
    }

    public void deleteAccount(Long id) throws AccountNotFound {
        try {
            commands.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new AccountNotFound();
        }
    }

    public void transferFunds(AccountC origin, AccountC destination, Double amount)
            throws InsufficientFunds, AccountNotFound {
        if (!origin.allowTransaction(-amount)) {
            throw new InsufficientFunds();
        }

        origin.updateBalance(-amount);
        destination.updateBalance(amount);
        updateAccount(origin.getId(), origin.toDto());
        updateAccount(destination.getId(), destination.toDto());
    }

    public void withdraw(AccountC account, Double amount) throws InsufficientFunds, AccountNotFound {
        if (account.allowTransaction(amount)) {
            account.updateBalance(-amount);
            updateAccount(account.getId(), account.toDto());
        } else {
            throw new InsufficientFunds();
        }
    }

    public void deposit(AccountC account, Double amount) throws AccountNotFound {
        account.updateBalance(amount);
        updateAccount(account.getId(), account.toDto());
    }
}
