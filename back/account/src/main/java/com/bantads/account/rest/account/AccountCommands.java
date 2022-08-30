package com.bantads.account.rest.account;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bantads.account.exceptions.AccountNotFound;
import com.bantads.account.lib.JsonResponse;
import com.bantads.account.lib.ValidationViolations;
import com.bantads.account.model.AccountDTO;
import com.bantads.account.services.AccountServices;

@CrossOrigin
@RestController
public class AccountCommands {
    @Autowired
    private AccountServices serv;

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

}
