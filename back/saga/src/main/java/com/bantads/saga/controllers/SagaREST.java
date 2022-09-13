package com.bantads.saga.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bantads.saga.models.ClienteDTO;
import com.bantads.saga.models.EnderecoDTO;
import com.bantads.saga.models.GerenteDTO;
import com.bantads.saga.models.LoginDTO;
import com.bantads.saga.models.Role;
import com.bantads.saga.models.AccountDTO;
import com.bantads.saga.amqp.ClienteProducer;
import com.bantads.saga.amqp.ClienteTransfer;
import com.bantads.saga.amqp.GerenteProducer;
import com.bantads.saga.amqp.GerenteTransfer;
import com.bantads.saga.amqp.AccountProducer;
import com.bantads.saga.amqp.AccountTransfer;
import com.bantads.saga.amqp.AuthProducer;
import com.bantads.saga.amqp.AuthTransfer;
import com.bantads.saga.utils.JsonResponse;
import com.bantads.saga.utils.ValidarCpf;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin
@RestController
public class SagaREST {

  @Autowired
  private ClienteProducer clienteSender;

  @Autowired
  private GerenteProducer gerenteSender;

  @Autowired
  private AccountProducer accountSender;

  @Autowired
  private AuthProducer authSender;

  @GetMapping(value = "/", produces = "application/json")
  public void getMethodName() {
    System.out.println("funcionou");
  }

  @PostMapping(value = "/clientes", produces = "application/json")
  public ResponseEntity<Object> postCliente(@RequestBody ClienteDTO cliente) {
    if (cliente.getNome() == null || cliente.getCpf() == null || cliente.getEndereco() == null) {
      return new ResponseEntity<>("Dados do cliente inválidos", HttpStatus.BAD_REQUEST);
    }

    if (cliente.getPassword() == null || cliente.getEmail() == null) {
      return new ResponseEntity<>(
          new JsonResponse(false, "Por favor, insira email e senha para completar seu cadastro!", null),
          HttpStatus.BAD_REQUEST);
    }

    if (!ValidarCpf.isCpfValid(cliente.getCpf())) {
      return new ResponseEntity<>(
          new JsonResponse(false, "CPF Inválido", null), HttpStatus.BAD_REQUEST);
    }

    EnderecoDTO endereco = cliente.getEndereco();

    if (endereco.getRua() == null || endereco.getNumero() == null
        || endereco.getCidade() == null || endereco.getEstado() == null || endereco.getCep() == null) {
      return new ResponseEntity<>(
          new JsonResponse(false, "Dados do endereço inválidos", null), HttpStatus.BAD_REQUEST);
    }

    if (cliente.getEmail() == null && cliente.getPassword() == null) {
      return new ResponseEntity<>(
          new JsonResponse(false, "Email e senha são obrigatórios", null), HttpStatus.BAD_REQUEST);
    }

    try {
      ClienteTransfer resCliente = clienteSender.sendAndReceive(cliente, "cliente-register");

      if (resCliente.getAction().equals("cliente-ok")) {
        Long clienteID = resCliente.getCliente().getId();
        String senha = cliente.getPassword();
        String email = cliente.getEmail();

        LoginDTO loginData = new LoginDTO();
        loginData.setUser(clienteID);
        loginData.setEmail(email);
        loginData.setPassword(senha);
        // System.out.println("loginData " + clienteID + senha + email);
        AuthTransfer resAuth = authSender.sendAndReceive(loginData, "auth-register");
        System.out.println("resCliente" + resCliente);
        System.out.println("resAuth " + resAuth.getAction());
        if (resAuth.getAction().equals("auth-ok")) {
          return new ResponseEntity<>(
              new JsonResponse(true, "Cliente criado com sucesso", cliente),
              HttpStatus.OK);
        } else if (resAuth.getAction().equals("auth-failed/email-registered")) {
          return new ResponseEntity<>(
              new JsonResponse(false, "Email já cadastrado, informe outro email", null),
              HttpStatus.BAD_REQUEST);
        } else {
          clienteSender.sendAndReceive(cliente, "cliente-delete");
          System.out.println("Ação n reconhecida" + resAuth.getAction());
          return new ResponseEntity<>(
              new JsonResponse(false, "Erro ao criar cliente", null),
              HttpStatus.BAD_REQUEST);
        }
      }
      System.out.println("Ação n reconhecida " + resCliente.getAction());
    } catch (Exception e) {
      System.out.println(e.getLocalizedMessage());

      return new ResponseEntity<>(
          new JsonResponse(false, "Erro interno ao criar usuário", e),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<>(
        new JsonResponse(false, "Erro interno ao criar usuário", null),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @PostMapping(value = "/gerente")
  public ResponseEntity<Object> postGerente(@RequestBody GerenteDTO gerente) {
    try {
      if (gerente.getEmail() == null || gerente.getCpf() == null || gerente.getNome() == null) {
        return new ResponseEntity<>(
            new JsonResponse(false, "Dados do gerente inválidos", null),
            HttpStatus.BAD_REQUEST);
      }

      GerenteTransfer resGerente = gerenteSender.sendAndReceive(gerente, "create-gerente");
      System.out.println("resGerente " + resGerente.getAction());

      if (resGerente.getAction().equals("gerente-ok")) {
        Integer gerenteId = resGerente.getGerente().getId();
        System.out.println("GERENTE ID " + gerenteId);
        String senha = gerente.getCpf();
        String email = gerente.getEmail();

        LoginDTO loginData = new LoginDTO();
        loginData.setUser(gerenteId.longValue());
        loginData.setEmail(email);
        loginData.setRole("GERENTE");
        loginData.setPassword(senha);

        AuthTransfer resAuth = authSender.sendAndReceive(loginData, "auth-register");

        if (resAuth.getAction().equals("auth-ok")) {
          return new ResponseEntity<>(
              new JsonResponse(true, "Gerente criado com sucesso", gerente),
              HttpStatus.OK);
        } else if (resAuth.getAction().equals("auth-failed/email-registered")) {
          return new ResponseEntity<>(
              new JsonResponse(false, "Email já cadastrado, informe outro email", null),
              HttpStatus.BAD_REQUEST);
        } else {
          gerenteSender.sendAndReceive(gerente, "remove-gerente");
          return new ResponseEntity<>(
              new JsonResponse(false, "Erro ao criar gerente", null),
              HttpStatus.BAD_REQUEST);
        }
      }
    } catch (Exception e) {
      System.out.println(e.getLocalizedMessage());
      return new ResponseEntity<>(
          new JsonResponse(false, "Erro interno ao criar gerente", null),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(
        new JsonResponse(false, "Erro interno ao criar gerente", null),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @PostMapping(value = "/accounts")
  public ResponseEntity<Object> updateCliente(@RequestBody AccountDTO account) {
    try {
      ClienteTransfer resCliente = clienteSender.sendAndReceiveInt(account.getUserId(),
          "cliente-aprovado");
      System.out.println(resCliente);

      if (resCliente.getAction().equals("cliente-approved-ok")) {
        System.out.println("Salario " + resCliente.getCliente().getSalario());
        if (resCliente.getCliente().getSalario() > 2000) {
          Double limite = (Double) (resCliente.getCliente().getSalario() * 0.5);
          account.setLimit(limite);
          System.out.println("limite " + limite);
        }
        AccountTransfer resAcc = accountSender.sendAndReceive(account, "create-account");
        System.out.println("here" + resAcc.getAction());
        if (resAcc.getAction().equals("create-account-ok")) {
          return new ResponseEntity<>(
              new JsonResponse(true, "Conta criada com sucesso", account),
              HttpStatus.OK);
        } else {
          clienteSender.sendAndReceiveInt(account.getUserId(), "cliente-approved-rollback");
          return new ResponseEntity<>(
              new JsonResponse(false, "Erro ao criar conta", null),
              HttpStatus.INTERNAL_SERVER_ERROR);
        }
      }
    } catch (Exception e) {
      return new ResponseEntity<>(
          new JsonResponse(false, "Erro interno ao criar conta", e),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(
        new JsonResponse(false, "Erro interno ao criar conta", null),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
