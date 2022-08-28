package com.bantads.account.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.bantads.account.exceptions.AccountNotFound;
import com.bantads.account.exceptions.InsufficientFunds;
import com.bantads.account.model.Account;
import com.bantads.account.model.AccountDTO;
import com.bantads.account.repository.AccountRepository;

@Service
public class AccountServices {
    @Autowired
    private AccountRepository repo;
    @Autowired
    private ModelMapper mapper;

    public List<AccountDTO> getAllAccounts() {
        List<Account> accounts = repo.findAll();
        List<AccountDTO> dtos = accounts.stream()
                .map(e -> e.toDto())
                .collect(Collectors.toList());
        return dtos;
    }

    public Account getAccount(Long accountId) throws AccountNotFound {
        Account acc = null;
        try {
            acc = repo.findById(accountId).get();
            return acc;
        } catch (NoSuchElementException e) {
            throw new AccountNotFound();
        }
    }

    public AccountDTO getAccountDTO(Long accountId) throws AccountNotFound {
        Account acc = null;
        try {
            acc = repo.findById(accountId).get();
        } catch (NoSuchElementException e) {
            throw new AccountNotFound();
        }

        AccountDTO dto = acc.toDto();
        return dto;
    }

    public AccountDTO updateAccount(Long accountId, AccountDTO newData) throws AccountNotFound {
        Account toUpdate = null;

        toUpdate = repo.findById(accountId).get();
        if (toUpdate == null) {
            throw new AccountNotFound();
        }

        toUpdate.setBalance(newData.getBalance());
        toUpdate.setLimit(newData.getLimit());
        toUpdate.setUserId(newData.getUserId());
        toUpdate = repo.save(toUpdate);

        return toUpdate.toDto();
    }

    public AccountDTO createAccount(AccountDTO newAccount) {
        Account toAccount = mapper.map(newAccount, Account.class);
        Account created = repo.save(toAccount);
        AccountDTO toDTO = mapper.map(created, AccountDTO.class);

        return toDTO;
    }

    public void deleteAccount(Long id) throws AccountNotFound {
        try {
            repo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new AccountNotFound();
        }
    }

    public void transferFunds(Account origin, Account destination, Double amount)
            throws InsufficientFunds, AccountNotFound {
        if (!origin.allowTransaction(-amount)) {
            throw new InsufficientFunds();
        }

        origin.updateBalance(-amount);
        destination.updateBalance(amount);
        updateAccount(origin.getId(), origin.toDto());
        updateAccount(destination.getId(), destination.toDto());
    }

    public void withdraw(Account account, Double amount) throws InsufficientFunds, AccountNotFound {
        if (account.allowTransaction(amount)) {
            account.updateBalance(-amount);
            updateAccount(account.getId(), account.toDto());
        } else {
            throw new InsufficientFunds();
        }
    }

    public void deposit(Account account, Double amount) throws AccountNotFound {
        account.updateBalance(amount);
        updateAccount(account.getId(), account.toDto());
    }
}
