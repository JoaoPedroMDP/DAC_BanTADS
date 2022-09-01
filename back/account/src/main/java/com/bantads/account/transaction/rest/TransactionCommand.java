package com.bantads.account.transaction.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bantads.account.account.models.Account;
import com.bantads.account.account.services.AccountServices;
import com.bantads.account.exceptions.AccountNotFound;
import com.bantads.account.exceptions.InsufficientFunds;
import com.bantads.account.lib.JsonResponse;
import com.bantads.account.transaction.models.TransactionDTO;
import com.bantads.account.transaction.services.TransactionServices;

@RestController
@CrossOrigin
public class TransactionCommand {
    @Autowired
    private AccountServices accServ;

    @Autowired
    private TransactionServices serv;

    @PostMapping(value = "/accounts/{id}/deposit", produces = "application/json")
    public JsonResponse deposit(@PathVariable Long id, @RequestBody Map<String, String> json) {
        try {
            Double amount = Double.parseDouble(json.get("amount").toString());
            Account account = accServ.getAccount(id);
            accServ.deposit(account, amount);

            TransactionDTO dto = serv.deposit(account, amount).toDto();
            return new JsonResponse(201, "Deposito confirmado!", dto);
        } catch (IllegalArgumentException e) {
            return new JsonResponse(400, "Revise os dados passados!", null);
        } catch (AccountNotFound e) {
            return new JsonResponse(404, "Conta não encontrada!", null);
        }
    }

    @PostMapping(value = "/accounts/{id}/withdraw", produces = "application/json")
    public JsonResponse withdraw(@PathVariable Long id, @RequestBody Map<String, String> json) {
        try {
            Double amount = Double.parseDouble(json.get("amount").toString());
            Account account = accServ.getAccount(id);

            accServ.withdraw(account, amount);
            TransactionDTO dto = serv.withdraw(account, amount).toDto();

            return new JsonResponse(201, "Saque confirmado!", dto);
        } catch (IllegalArgumentException e) {
            return new JsonResponse(400, "Revise os dados passados!", null);
        } catch (AccountNotFound e) {
            return new JsonResponse(404, "Conta não encontrada!", null);
        } catch (InsufficientFunds e) {
            return new JsonResponse(400, "Saldo insuficiente!!", null);
        }
    }

    @PostMapping(value = "/accounts/{id}/transfer", produces = "application/json")
    public JsonResponse transfer(@PathVariable Long id, @RequestBody Map<String, String> json) {
        try {
            Double amount = Double.parseDouble(json.get("amount").toString());
            Long to = Long.parseLong(json.get("to"));

            Account origin = accServ.getAccount(id);
            Account destination = accServ.getAccount(to);
            accServ.transferFunds(origin, destination, amount);

            TransactionDTO dto = serv.transfer(origin, destination, amount).toDto();

            return new JsonResponse(201, "Transferência confirmada!", dto);
        } catch (IllegalArgumentException e) {
            return new JsonResponse(400, "Revise os dados passados!", null);
        } catch (AccountNotFound e) {
            return new JsonResponse(404, "Conta não encontrada!", null);
        } catch (InsufficientFunds e) {
            return new JsonResponse(400, "Saldo insuficiente!!", null);
        }
    }

}
