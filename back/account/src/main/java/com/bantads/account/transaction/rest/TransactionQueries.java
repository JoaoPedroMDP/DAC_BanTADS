package com.bantads.account.transaction.rest;

import com.bantads.account.account.models.query.AccountQ;
import com.bantads.account.account.services.AccountServices;
import com.bantads.account.exceptions.AccountNotFound;
import com.bantads.account.lib.JsonResponse;
import com.bantads.account.lib.Statement;
import com.bantads.account.transaction.models.TransactionDTO;
import com.bantads.account.transaction.services.TransactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin
@RestController
public class TransactionQueries {
    @Autowired
    private AccountServices accServ;

    @Autowired
    private TransactionServices serv;

    @GetMapping(value = "/accounts/{id}/statement", produces = "application/json")
    public ResponseEntity<JsonResponse> getStatement(@PathVariable Long id, @RequestParam String from,
            @RequestParam String to) {
        try {
            Long fromTS = Long.parseLong(from);
            Long toTS = Long.parseLong(to);
            AccountQ acc = accServ.getAccount(id);
            ArrayList<TransactionDTO> dtos = serv.getAccountTransactions(acc, fromTS, toTS);
            Statement statement = serv.buildStatement(dtos, fromTS, toTS);

            return JsonResponse.ok("Extrato gerado!", statement);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return JsonResponse.badRequest("Revise os dados passados!", null);
        } catch (AccountNotFound e) {
            e.printStackTrace();
            return JsonResponse.notFound("Conta não encontrada!", null);
        }

    }

}
