package com.bantads.account.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bantads.account.exceptions.AccountNotFound;
import com.bantads.account.lib.JsonResponse;
import com.bantads.account.model.Account;
import com.bantads.account.model.AccountDTO;
import com.bantads.account.repository.AccountRepository;

@CrossOrigin
@RestController
public class AccountREST {
    @Autowired
    private AccountRepository repo;
    @Autowired
    private ModelMapper mapper;

    @GetMapping(value = "/accounts", produces = "application/json")
    public JsonResponse getAllAccounts() {
        try {
            List<Account> accounts = repo.findAll();
            List<AccountDTO> dtos = accounts.stream()
                    .map(
                            e -> mapper.map(e, AccountDTO.class))
                    .collect(
                            Collectors.toList());

            return new JsonResponse(200, "Listando " + dtos.size() + " contas.", dtos);
        } catch (Exception e) {
            return new JsonResponse(500, "Erro ao listar contas.", new ArrayList<>());
        }
    }

    @GetMapping(value = "/accounts/{id}", produces = "application/json")
    public JsonResponse getAccount(@PathVariable("id") Long id) {
        try {
            AccountDTO acc = mapper.map(repo.findById(id).get(), AccountDTO.class);
            if (acc == null) {
                throw new AccountNotFound();
            }

            return new JsonResponse(200, "Retornando conta " + acc.getId(), acc);
        } catch (IllegalArgumentException e) {
            return new JsonResponse(404, "Id passado é nulo!", null);
        } catch (AccountNotFound e) {
            return new JsonResponse(404, "Conta não encontrada!", null);
        }
    }

    @PutMapping(value = "/accounts/{id}", produces = "application/json")
    public JsonResponse put(@PathVariable("id") Long id, @RequestBody AccountDTO account) {
        Account toUpdate = null;

        try {
            toUpdate = repo.findById(id).get();
            if (toUpdate == null) {
                throw new AccountNotFound();
            }
        } catch (IllegalArgumentException e) {
            return new JsonResponse(404, "Id passado é nulo!", null);
        } catch (AccountNotFound e) {
            return new JsonResponse(404, "Conta não encontrada!", null);
        }

        toUpdate.setBalance(account.getBalance());
        toUpdate.setUserId(account.getUserId());

        try {
            AccountDTO updated = mapper.map(repo.save(toUpdate), AccountDTO.class);
            return new JsonResponse(200, "Conta atualizada!", updated);
        } catch (Exception e) {
            return new JsonResponse(500, "Erro ao atualizar conta!", null);
        }
    }

    @PostMapping(value = "/accounts", produces = "application/json")
    public JsonResponse post(@RequestBody AccountDTO account) {
        try {
            Account toAccount = mapper.map(account, Account.class);
            Account created = repo.save(toAccount);
            AccountDTO toDTO = mapper.map(created, AccountDTO.class);

            return new JsonResponse(201, "Conta criada!", toDTO);
        } catch (IllegalArgumentException e) {
            return new JsonResponse(400, "A conta enviada é nula!", null);
        }
    }

    @DeleteMapping(value = "/accounts/{id}", produces = "application/json")
    public JsonResponse delete(@PathVariable("id") Long id) {
        try {
            repo.deleteById(id);
            return new JsonResponse(200, "Conta removida!", null);
        } catch (IllegalArgumentException e) {
            return new JsonResponse(400, "O id enviado é nulo!", null);
        }
    }
}
