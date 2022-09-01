package com.bantads.account.account.rest;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.bantads.account.account.models.Account;
import com.bantads.account.account.models.AccountDTO;
import com.bantads.account.account.services.AccountServices;
import com.bantads.account.exceptions.AccountNotFound;
import com.bantads.account.lib.JsonResponse;

@CrossOrigin
@RestController
public class AccountQueries {
    @Autowired
    private AccountServices serv;

    @GetMapping(value = "/accounts", produces = "application/json")
    public JsonResponse getAllAccounts() {
        try {
            List<AccountDTO> dtos = serv.getAllAccounts();
            return new JsonResponse(200, "Listando " + dtos.size() + " contas.", dtos);
        } catch (Exception e) {
            return new JsonResponse(500, "Erro ao listar contas.", null);
        }
    }

    @GetMapping(value = "/accounts/{id}", produces = "application/json")
    public JsonResponse getAccount(@PathVariable("id") Long id) {
        try {
            AccountDTO acc = serv.getAccountDTO(id);
            return new JsonResponse(200, "Retornando conta " + acc.getId(), acc);
        } catch (IllegalArgumentException e) {
            return new JsonResponse(400, "Id passado é nulo!", null);
        } catch (AccountNotFound e) {
            return new JsonResponse(404, "Conta não encontrada!", null);
        }
    }

    @GetMapping(value = "/accounts/{id}/balance", produces = "application/json")
    public JsonResponse getBalance(@PathVariable Long id) {
        try {
            Account account = serv.getAccount(id);

            LinkedHashMap<String, Double> balance = new LinkedHashMap<>();
            balance.put("balance", account.getBalance());

            return new JsonResponse(200, "Saldo!", balance);
        } catch (IllegalArgumentException e) {
            return new JsonResponse(400, "Revise os dados passados!", null);
        } catch (AccountNotFound e) {
            return new JsonResponse(404, "Conta não encontrada!", null);
        }
    }
}
