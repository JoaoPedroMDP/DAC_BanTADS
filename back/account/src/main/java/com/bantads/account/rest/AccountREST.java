package com.bantads.account.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bantads.account.lib.JsonResponse;
import com.bantads.account.model.Account;

@CrossOrigin
@RestController
public class AccountREST {
    @GetMapping(value = "/accounts", produces = "application/json")
    public static JsonResponse getAllAccounts() {
        ArrayList<Account> accounts = new ArrayList<Account>() {
            {
                add(new Account(1, 1, 100.0));
                add(new Account(2, 2, 200.0));
                add(new Account(3, 3, 300.0));
            }
        };

        return new JsonResponse(200, "Listando " + accounts.size() + " contas.", accounts);
    }

    @GetMapping(value = "/accounts/{id}", produces = "application/json")
    public static JsonResponse getAccount(@PathVariable("id") Integer id) {
        Account acc = new Account(id, 1, 100.0);
        return new JsonResponse(200, "Retornando conta " + acc.getId(), acc);
    }

    @PutMapping(value = "/accounts/{id}", produces = "application/json")
    public static JsonResponse put(
            @PathVariable("id") Integer id,
            @RequestBody Account account) {
        // TODO: Procura conta no banco de dados
        // TODO: Atualiza conta no banco de dados
        return new JsonResponse(200, "Conta atualizada.", account);
    }

    @PostMapping(value = "/accounts", produces = "application/json")
    public static JsonResponse post(@RequestBody Account account) {
        // TODO: Insere conta no banco de dados
        return new JsonResponse(201, "Conta criada!", account);
    }

    @DeleteMapping(value = "/accounts/{id}", produces = "application/json")
    public static JsonResponse delete(@PathVariable("id") Integer id) {
        // TODO: Deleta conta no banco de dados
        return new JsonResponse(200, "Conta deletada.", null);
    }
}
