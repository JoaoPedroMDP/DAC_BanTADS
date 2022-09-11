package com.bantads.account.transaction.rest;

import com.bantads.account.account.models.command.AccountC;
import com.bantads.account.account.services.AccountServices;
import com.bantads.account.exceptions.AccountNotFound;
import com.bantads.account.exceptions.InsufficientFunds;
import com.bantads.account.lib.JsonResponse;
import com.bantads.account.transaction.models.TransactionDTO;
import com.bantads.account.transaction.services.TransactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class TransactionCommand {
    @Autowired
    private AccountServices accServ;

    @Autowired
    private TransactionServices serv;

    @CrossOrigin
    @PostMapping(value = "/accounts/{id}/deposit", produces = "application/json")
    public JsonResponse deposit(@PathVariable Long id, @RequestBody Map<String, String> json) {
        try {
            Double amount = Double.parseDouble(json.get("amount"));
            AccountC account = accServ.getAccountC(id);
            accServ.deposit(account, amount);

            TransactionDTO dto = serv.deposit(account, amount).toDto();
            return new JsonResponse(true, "Deposito confirmado!", dto);
        } catch (IllegalArgumentException e) {
            return new JsonResponse(false, "Revise os dados passados!", null);
        } catch (AccountNotFound e) {
            return new JsonResponse(false, "Conta não encontrada!", null);
        }
    }

    @CrossOrigin
    @PostMapping(value = "/accounts/{id}/withdraw", produces = "application/json")
    public JsonResponse withdraw(@PathVariable Long id, @RequestBody Map<String, String> json) {
        try {
            Double amount = Double.parseDouble(json.get("amount").toString());
            AccountC account = accServ.getAccountC(id);

            accServ.withdraw(account, amount);
            TransactionDTO dto = serv.withdraw(account, amount).toDto();

            return new JsonResponse(true, "Saque confirmado!", dto);
        } catch (IllegalArgumentException e) {
            return new JsonResponse(false, "Revise os dados passados!", null);
        } catch (AccountNotFound e) {
            return new JsonResponse(false, "Conta não encontrada!", null);
        } catch (InsufficientFunds e) {
            return new JsonResponse(false, "Saldo insuficiente!!", null);
        }
    }

    @CrossOrigin
    @PostMapping(value = "/accounts/{id}/transfer", produces = "application/json")
    public JsonResponse transfer(@PathVariable Long id, @RequestBody Map<String, String> json) {
        try {
            Double amount = Double.parseDouble(json.get("amount").toString());
            Long to = Long.parseLong(json.get("to"));

            AccountC origin = accServ.getAccountC(id);
            AccountC destination = accServ.getAccountC(to);
            accServ.transferFunds(origin, destination, amount);

            TransactionDTO dto = serv.transfer(origin, destination, amount).toDto();

            return new JsonResponse(true, "Transferência confirmada!", dto);
        } catch (IllegalArgumentException e) {
            return new JsonResponse(false, "Revise os dados passados!", null);
        } catch (AccountNotFound e) {
            return new JsonResponse(false, "Conta não encontrada!", null);
        } catch (InsufficientFunds e) {
            return new JsonResponse(false, "Saldo insuficiente!!", null);
        }
    }

}
