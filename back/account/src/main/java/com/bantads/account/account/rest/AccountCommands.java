package com.bantads.account.account.rest;

import com.bantads.account.account.models.AccountDTO;
import com.bantads.account.account.services.AccountServices;
import com.bantads.account.exceptions.AccountNotFound;
import com.bantads.account.exceptions.BenignException;
import com.bantads.account.exceptions.DuplicateAccountException;
import com.bantads.account.lib.JsonResponse;
import com.bantads.account.lib.ValidationViolations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;

@CrossOrigin
@RestController
public class AccountCommands {
    @Autowired
    private AccountServices serv;

    @PutMapping(value = "/accounts/{id}", produces = "application/json")
    public ResponseEntity<JsonResponse> updateAccount(@PathVariable("id") Long id, @RequestBody AccountDTO account) {
        try {
            AccountDTO updated = serv.updateAccount(id, account);
            return JsonResponse.ok("Conta atualizada com sucesso!", updated);
        } catch (AccountNotFound e) {
            e.printStackTrace();
            return JsonResponse.notFound("Conta não encontrada!", null);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResponse.internalServerError("Erro ao atualizar conta!", null);
        }
    }

    @PostMapping(value = "/accounts", produces = "application/json")
    public ResponseEntity<JsonResponse> createAccount(@RequestBody AccountDTO account) {
        try {
            AccountDTO created = serv.createAccount(account);

            return JsonResponse.created("Conta criada com sucesso!", created);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return JsonResponse.badRequest("O id da conta é inválido!", null);
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
            ValidationViolations violations = new ValidationViolations(e.getConstraintViolations());
            return JsonResponse.badRequest("Erro de validação!", violations);
        } catch (BenignException e) {
            e.printStackTrace();
            return JsonResponse.internalServerError(e.getMessage(), null);
        }  catch (Exception e) {
            e.printStackTrace();
            return JsonResponse.internalServerError("Erro ao criar conta!", null);
        }
    }

    @DeleteMapping(value = "/accounts/{id}", produces = "application/json")
    public ResponseEntity<JsonResponse> deleteAccount(@PathVariable("id") Long id) {
        try {
            serv.deleteAccount(id);
            return JsonResponse.ok("Conta deletada com sucesso!", null);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return JsonResponse.badRequest("O id da conta é inválido!", null);
        } catch (AccountNotFound e) {
            e.printStackTrace();
            return JsonResponse.notFound("Conta não encontrada!", null);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResponse.internalServerError("Erro ao deletar conta!", null);
        }
    }

}
