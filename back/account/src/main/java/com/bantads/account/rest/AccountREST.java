package com.bantads.account.rest;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bantads.account.exceptions.AccountNotFound;
import com.bantads.account.exceptions.InsufficientFunds;
import com.bantads.account.lib.JsonResponse;
import com.bantads.account.lib.Statement;
import com.bantads.account.lib.ValidationViolations;
import com.bantads.account.model.Account;
import com.bantads.account.model.AccountDTO;
import com.bantads.account.model.TransactionDTO;
import com.bantads.account.services.AccountServices;
import com.bantads.account.services.TransactionServices;

@CrossOrigin
@RestController
public class AccountREST {

    @Autowired
    private AccountServices serv;

    @Autowired
    private TransactionServices trServ;

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

    @PutMapping(value = "/accounts/{id}", produces = "application/json")
    public JsonResponse updateAccount(@PathVariable("id") Long id, @RequestBody AccountDTO account) {

        try {
            AccountDTO updated = serv.updateAccount(id, account);
            return new JsonResponse(200, "Conta atualizada!", updated);
        } catch (AccountNotFound e) {
            return new JsonResponse(404, "Conta não encontrada!", null);
        }
    }

    @PostMapping(value = "/accounts", produces = "application/json")
    public JsonResponse createAccount(@RequestBody AccountDTO account) {
        try {
            AccountDTO created = serv.createAccount(account);
            return new JsonResponse(201, "Conta criada!", created);
        } catch (IllegalArgumentException e) {
            return new JsonResponse(400, "A conta enviada é nula!", null);
        } catch (ConstraintViolationException e) {
            ValidationViolations violations = new ValidationViolations(e.getConstraintViolations());
            return new JsonResponse(400, "Problema nos dados enviados!", violations);
        }
    }

    @DeleteMapping(value = "/accounts/{id}", produces = "application/json")
    public JsonResponse deleteAccount(@PathVariable("id") Long id) {
        try {
            serv.deleteAccount(id);
            return new JsonResponse(200, "Conta removida!", null);
        } catch (IllegalArgumentException e) {
            return new JsonResponse(400, "O id enviado é nulo!", null);
        } catch (AccountNotFound e) {
            return new JsonResponse(404, "Conta não encontrada!", null);
        }
    }

    @PostMapping(value = "/accounts/{id}/deposit", produces = "application/json")
    public JsonResponse deposit(@PathVariable Long id, @RequestBody Map<String, String> json) {
        try {
            Double amount = Double.parseDouble(json.get("amount").toString());
            Account account = serv.getAccount(id);
            serv.deposit(account, amount);

            TransactionDTO dto = trServ.deposit(account, amount).toDto();
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
            Account account = serv.getAccount(id);

            serv.withdraw(account, amount);
            TransactionDTO dto = trServ.withdraw(account, amount).toDto();

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

            Account origin = serv.getAccount(id);
            Account destination = serv.getAccount(to);
            serv.transferFunds(origin, destination, amount);

            TransactionDTO dto = trServ.transfer(origin, destination, amount).toDto();

            return new JsonResponse(201, "Transferência confirmada!", dto);
        } catch (IllegalArgumentException e) {
            return new JsonResponse(400, "Revise os dados passados!", null);
        } catch (AccountNotFound e) {
            return new JsonResponse(404, "Conta não encontrada!", null);
        } catch (InsufficientFunds e) {
            return new JsonResponse(400, "Saldo insuficiente!!", null);
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

    @GetMapping(value = "/accounts/{id}/statement", produces = "application/json")
    public JsonResponse getStatement(@PathVariable Long id, @RequestParam String from, @RequestParam String to) {
        try {
            Long fromTS = Long.parseLong(from);
            Long toTS = Long.parseLong(to);
            Account acc = serv.getAccount(id);
            ArrayList<TransactionDTO> dtos = trServ.getAccountTransactions(acc, fromTS, toTS);
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
