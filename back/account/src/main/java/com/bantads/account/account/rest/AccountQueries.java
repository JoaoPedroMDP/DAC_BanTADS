package com.bantads.account.account.rest;

import com.bantads.account.account.models.AccountDTO;
import com.bantads.account.account.models.query.AccountQ;
import com.bantads.account.account.services.AccountServices;
import com.bantads.account.exceptions.AccountNotFound;
import com.bantads.account.lib.CustomerDTO;
import com.bantads.account.lib.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @CrossOrigin
    @GetMapping(value = "/accounts", produces = "application/json")
    public ResponseEntity<JsonResponse> getAllAccounts() {
        try {
            List<AccountDTO> dtos = serv.getAllAccounts();
            return JsonResponse.ok("Listando " + dtos.size() + " contas.", dtos);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResponse.internalServerError("Erro ao listar contas!", null);
        }
    }

    @CrossOrigin
    @GetMapping(value = "/accounts/{id}", produces = "application/json")
    public ResponseEntity<JsonResponse> getAccount(@PathVariable("id") Long id) {
        try {
            AccountDTO acc = serv.getAccountDTO(id);
            return JsonResponse.ok("Conta encontrada!", acc);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return JsonResponse.badRequest("O id da conta é inválido!", null);
        } catch (AccountNotFound e) {
            e.printStackTrace();
            return JsonResponse.notFound("Conta não encontrada!", null);
        }
    }

    @CrossOrigin
    @GetMapping(value = "/accounts/user/{id}", produces = "application/json")
    public ResponseEntity<JsonResponse> getUserAccount(@PathVariable("id") Long id) {
        try {
            AccountDTO acc = serv.getAccountDTO(id);
            return JsonResponse.ok("Conta encontrada!", acc);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return JsonResponse.badRequest("O id da conta é inválido!", null);
        } catch (AccountNotFound e) {
            e.printStackTrace();
            return JsonResponse.notFound("Conta não encontrada!", null);
        }
    }

    @CrossOrigin
    @GetMapping(value = "/accounts/users/{users}", produces = "application/json")
    // Passa uma lista de ClienteDTO para mim e eu devolvo uma lista de AccountDTO
    public ResponseEntity<JsonResponse> getAccountsByUsers(@PathVariable("id") String users) {
        try {
            List<String> ids = new ArrayList(users.split(",")).;
            List<AccountDTO> accounts = serv.getAccountsByUsers(users);
            return JsonResponse.ok("Contas encontradas!", accounts);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return JsonResponse.badRequest("O id da conta é inválido!", null);
        } catch (AccountNotFound e) {
            e.printStackTrace();
            return JsonResponse.notFound("Conta não encontrada!", null);
        }
    }

    @CrossOrigin
    @GetMapping(value = "/accounts/{id}/balance", produces = "application/json")
    public ResponseEntity<JsonResponse> getBalance(@PathVariable Long id) {
        try {
            AccountQ account = serv.getAccount(id);

            LinkedHashMap<String, Double> balance = new LinkedHashMap<>();
            balance.put("balance", account.getBalance());

            return JsonResponse.ok("Retornando saldo", balance);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return JsonResponse.badRequest("O id da conta é inválido!", null);
        } catch (AccountNotFound e) {
            e.printStackTrace();
            return JsonResponse.notFound("Conta não encontrada!", null);
        }
    }
}
