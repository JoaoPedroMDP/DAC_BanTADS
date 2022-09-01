package com.bantads.account.transaction.rest;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bantads.account.account.models.Account;
import com.bantads.account.account.services.AccountServices;
import com.bantads.account.exceptions.AccountNotFound;
import com.bantads.account.lib.JsonResponse;
import com.bantads.account.lib.Statement;
import com.bantads.account.transaction.models.TransactionDTO;
import com.bantads.account.transaction.services.TransactionServices;

@CrossOrigin
@RestController
public class TransactionQueries {
    @Autowired
    private AccountServices accServ;

    @Autowired
    private TransactionServices serv;

    @GetMapping(value = "/accounts/{id}/statement", produces = "application/json")
    public JsonResponse getStatement(@PathVariable Long id, @RequestParam String from, @RequestParam String to) {
        try {
            Long fromTS = Long.parseLong(from);
            Long toTS = Long.parseLong(to);
            Account acc = accServ.getAccount(id);
            ArrayList<TransactionDTO> dtos = serv.getAccountTransactions(acc, fromTS, toTS);
            Statement statement = new Statement(dtos, fromTS, toTS);

            return new JsonResponse(200, "Extrato", statement);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return new JsonResponse(400, "Revise os dados passados!", null);
        } catch (AccountNotFound e) {
            return new JsonResponse(404, "Conta não encontrada!", null);
        }

    }

}
