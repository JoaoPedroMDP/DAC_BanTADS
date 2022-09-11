package com.bantads.account.account.rest;

import com.bantads.account.account.models.AccountDTO;
import com.bantads.account.account.services.AccountServices;
import com.bantads.account.exceptions.AccountNotFound;
import com.bantads.account.lib.JsonResponse;
import com.bantads.account.lib.ValidationViolations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;

@RestController
public class AccountCommands {
    @Autowired
    private AccountServices serv;

    @CrossOrigin
    @PutMapping(value = "/accounts/{id}", produces = "application/json")
    public JsonResponse updateAccount(@PathVariable("id") Long id, @RequestBody AccountDTO account) {
        try {
            AccountDTO updated = serv.updateAccount(id, account);
            return new JsonResponse(true, "Conta atualizada!", updated);
        } catch (AccountNotFound e) {
            e.printStackTrace();
            return new JsonResponse(false, "Conta não encontrada!", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResponse(false, "Erro ao atualizar conta!", null);
        }
    }

    @CrossOrigin
    @PostMapping(value = "/accounts", produces = "application/json")
    public JsonResponse createAccount(@RequestBody AccountDTO account) {
        try {
            account.setId(null); // Para o caso de tentarem colocar um id
            AccountDTO created = serv.createAccount(account);
            return new JsonResponse(true, "Conta criada!", created);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new JsonResponse(false, "A conta enviada é nula!", null);
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
            ValidationViolations violations = new ValidationViolations(e.getConstraintViolations());
            return new JsonResponse(false, "Problema nos dados enviados!", violations);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResponse(false, "Erro ao criar conta!", null);
        }
    }

    @CrossOrigin
    @DeleteMapping(value = "/accounts/{id}", produces = "application/json")
    public JsonResponse deleteAccount(@PathVariable("id") Long id) {
        try {
            serv.deleteAccount(id);
            return new JsonResponse(true, "Conta removida!", null);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new JsonResponse(false, "O id enviado é nulo!", null);
        } catch (AccountNotFound e) {
            e.printStackTrace();
            return new JsonResponse(false, "Conta não encontrada!", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResponse(false, "Erro ao deletar conta!", null);
        }
    }

}
