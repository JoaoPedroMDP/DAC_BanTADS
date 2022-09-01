package com.bantads.account.account.rest;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bantads.account.account.amqp.AccountSender;
import com.bantads.account.account.models.AccountDTO;
import com.bantads.account.account.services.AccountServices;
import com.bantads.account.exceptions.AccountNotFound;
import com.bantads.account.lib.JsonResponse;
import com.bantads.account.lib.ValidationViolations;

@CrossOrigin
@RestController
public class AccountCommands {
    @Autowired
    private AccountServices serv;

    @Autowired
    private AccountSender sender;

    @PutMapping(value = "/accounts/{id}", produces = "application/json")
    public JsonResponse updateAccount(@PathVariable("id") Long id, @RequestBody AccountDTO account) {
        try {
            AccountDTO updated = serv.updateAccount(id, account);
            sender.send(updated, "update");
            return new JsonResponse(200, "Conta atualizada!", updated);
        } catch (AccountNotFound e) {
            System.out.println(e);
            return new JsonResponse(404, "Conta não encontrada!", null);
        }
    }

    @PostMapping(value = "/accounts", produces = "application/json")
    public JsonResponse createAccount(@RequestBody AccountDTO account) {
        try {
            account.setId(null); // Para o caso de tentarem colocar um id
            AccountDTO created = serv.createAccount(account);
            sender.send(created, "creation");
            return new JsonResponse(201, "Conta criada!", created);
        } catch (IllegalArgumentException e) {
            System.out.println(e);
            return new JsonResponse(400, "A conta enviada é nula!", null);
        } catch (ConstraintViolationException e) {
            System.out.println(e);
            ValidationViolations violations = new ValidationViolations(e.getConstraintViolations());
            return new JsonResponse(400, "Problema nos dados enviados!", violations);
        }
    }

    @DeleteMapping(value = "/accounts/{id}", produces = "application/json")
    public JsonResponse deleteAccount(@PathVariable("id") Long id) {
        try {
            AccountDTO deleted = serv.getAccountDTO(id);
            serv.deleteAccount(id);
            sender.send(deleted, "deletion");
            return new JsonResponse(200, "Conta removida!", null);
        } catch (IllegalArgumentException e) {
            System.out.println(e);
            return new JsonResponse(400, "O id enviado é nulo!", null);
        } catch (AccountNotFound e) {
            System.out.println(e);
            return new JsonResponse(404, "Conta não encontrada!", null);
        }
    }

}
