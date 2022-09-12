package com.bantads.account.transaction.rest;

import com.bantads.account.account.models.command.AccountC;
import com.bantads.account.account.services.AccountServices;
import com.bantads.account.exceptions.AccountNotFound;
import com.bantads.account.exceptions.InsufficientFunds;
import com.bantads.account.lib.JsonResponse;
import com.bantads.account.transaction.models.TransactionDTO;
import com.bantads.account.transaction.services.TransactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<JsonResponse> deposit(@PathVariable Long id, @RequestBody Map<String, String> json) {
        try {
            Double amount = Double.parseDouble(json.get("amount"));
            AccountC account = accServ.getAccountC(id);
            accServ.deposit(account, amount);

            TransactionDTO dto = serv.deposit(account, amount).toDto();
            return JsonResponse.ok("Deposito confirmado!", dto);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return JsonResponse.badRequest("Valor inválido!", null);
        } catch (AccountNotFound e) {
            e.printStackTrace();
            return JsonResponse.notFound("Conta não encontrada!", null);
        }
    }

    @CrossOrigin
    @PostMapping(value = "/accounts/{id}/withdraw", produces = "application/json")
    public ResponseEntity<JsonResponse> withdraw(@PathVariable Long id, @RequestBody Map<String, String> json) {
        try {
            Double amount = Double.parseDouble(json.get("amount").toString());
            AccountC account = accServ.getAccountC(id);

            accServ.withdraw(account, amount);
            TransactionDTO dto = serv.withdraw(account, amount).toDto();

            return JsonResponse.ok("Saque confirmado!", dto);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return JsonResponse.badRequest("Valor inválido!", null);
        } catch (AccountNotFound e) {
            e.printStackTrace();
            return JsonResponse.notFound("Conta não encontrada!", null);
        } catch (InsufficientFunds e) {
            e.printStackTrace();
            return JsonResponse.badRequest("Saldo insuficiente!", null);
        }
    }

    @CrossOrigin
    @PostMapping(value = "/accounts/{id}/transfer", produces = "application/json")
    public ResponseEntity<JsonResponse> transfer(@PathVariable Long id, @RequestBody Map<String, String> json) {
        try {
            Double amount = Double.parseDouble(json.get("amount").toString());
            Long to = Long.parseLong(json.get("to"));

            AccountC origin = accServ.getAccountC(id);
            AccountC destination = accServ.getAccountC(to);
            accServ.transferFunds(origin, destination, amount);

            TransactionDTO dto = serv.transfer(origin, destination, amount).toDto();

            return JsonResponse.ok("Transferência confirmada!", dto);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return JsonResponse.badRequest("Valor inválido!", null);
        } catch (AccountNotFound e) {
            e.printStackTrace();
            return JsonResponse.notFound("Conta não encontrada!", null);
        } catch (InsufficientFunds e) {
            e.printStackTrace();
            return JsonResponse.badRequest("Saldo insuficiente!", null);
        }
    }

}
