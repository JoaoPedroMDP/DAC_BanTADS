package com.bantads.account.account.rest;

import com.bantads.account.account.models.AccountDTO;
import com.bantads.account.account.models.query.AccountQ;
import com.bantads.account.account.services.AccountServices;
import com.bantads.account.exceptions.AccountNotFound;
import com.bantads.account.lib.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;

@RestController
public class AccountQueries {
    @Autowired
    private AccountServices serv;

    @GetMapping(value = "/accounts", produces = "application/json")
    public JsonResponse getAllAccounts() {
        try {
            List<AccountDTO> dtos = serv.getAllAccounts();
            return new JsonResponse(true, "Listando " + dtos.size() + " contas.", dtos);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResponse(false, "Erro ao listar contas.", null);
        }
    }

    @GetMapping(value = "/accounts/{id}", produces = "application/json")
    public JsonResponse getAccount(@PathVariable("id") Long id) {
        try {
            AccountDTO acc = serv.getAccountDTO(id);
            return new JsonResponse(true, "Retornando conta " + acc.getId(), acc);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new JsonResponse(false, "Id passado é nulo!", null);
        } catch (AccountNotFound e) {
            e.printStackTrace();
            return new JsonResponse(false, "Conta não encontrada!", null);
        }
    }

    @GetMapping(value = "/accounts/{id}/balance", produces = "application/json")
    public JsonResponse getBalance(@PathVariable Long id) {
        try {
            AccountQ account = serv.getAccount(id);

            LinkedHashMap<String, Double> balance = new LinkedHashMap<>();
            balance.put("balance", account.getBalance());

            return new JsonResponse(true, "Saldo!", balance);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new JsonResponse(false, "Revise os dados passados!", null);
        } catch (AccountNotFound e) {
            e.printStackTrace();
            return new JsonResponse(false, "Conta não encontrada!", null);
        }
    }
}
