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
import com.bantads.saga.models.AccountDTO;
import com.bantads.saga.amqp.ClienteProducer;
import com.bantads.saga.amqp.ClienteTransfer;
import com.bantads.saga.amqp.GerenteProducer;
import com.bantads.saga.amqp.AccountProducer;
import com.bantads.saga.amqp.AuthProducer;
import com.bantads.saga.utils.JsonResponse;
import com.bantads.saga.utils.ValidarCpf;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
  public ResponseEntity<Object> postCliente(@RequestBody ClienteDTO cliente, LoginDTO auth) {
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

    try {
      System.out.println(cliente);
      System.out.println(auth);

      ClienteTransfer gt = new ClienteTransfer();

      gt.setAction("create-cliente");
      gt.setCliente(cliente);

      System.out.println(gt.getCliente());
      boolean resCliente = clienteSender.sendAndReceive(gt, "create-cliente");

      // if (resCliente == true) {
      // boolean resAuth = authSender.sendAndReceive(auth, "create-auth");
      // System.out.println(resAuth);

      // if (resAuth == true) {
      // return new ResponseEntity<>(
      // new JsonResponse(true, "Cliente criado com sucesso", cliente),
      // HttpStatus.OK);
      // } else {
      // clienteSender.sendAndReceive(cliente, "delete-cliente");
      // return new ResponseEntity<>(
      // new JsonResponse(false, "Erro ao criar cliente", null),
      // HttpStatus.INTERNAL_SERVER_ERROR);
      // }
      // }
    } catch (Exception e) {
      return new ResponseEntity<>(
          new JsonResponse(false, "Erro interno ao criar usuário", null),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(
        new JsonResponse(true, "Usuário criado com sucesso", cliente),
        HttpStatus.OK);
  }

  @PostMapping(value = "/gerente")
  public ResponseEntity<Object> postGerente(@RequestBody GerenteDTO gerente, LoginDTO auth) {
    try {
      boolean resGerente = gerenteSender.sendAndReceive(gerente, "create-gerente");
      System.out.println(resGerente);

      if (resGerente == true) {
        boolean resAuth = authSender.sendAndReceive(auth, "create-auth");
        System.out.println(resAuth);

        if (resAuth == true) {
          return new ResponseEntity<>(
              new JsonResponse(true, "Gerente criado com sucesso", gerente),
              HttpStatus.OK);
        } else {
          gerenteSender.sendAndReceive(gerente, "delete-gerente");
          return new ResponseEntity<>(
              new JsonResponse(false, "Erro ao criar usuário", null),
              HttpStatus.INTERNAL_SERVER_ERROR);
        }
      }
    } catch (Exception e) {
      return new ResponseEntity<>(
          new JsonResponse(false, "Erro interno ao criar usuário", null),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(
        new JsonResponse(true, "gerente criado com sucesso", gerente),
        HttpStatus.OK);
  }

  @PutMapping("/clientes/{id}")
  public ResponseEntity<Object> updateCliente(@RequestBody ClienteDTO cliente, AccountDTO account) {
    try {
      boolean resCliente = clienteSender.sendAndReceive(cliente, "update-cliente");
      System.out.println(resCliente);

      if (resCliente == true) {
        boolean resAcc = accountSender.sendAndReceive(account, "create-account");
        System.out.println(resAcc);

        if (resAcc == true) {
          return new ResponseEntity<>(
              new JsonResponse(true, "Conta criada com sucesso", cliente),
              HttpStatus.OK);
        } else {
          clienteSender.sendAndReceive(cliente, "update-cliente-rollback");
          return new ResponseEntity<>(
              new JsonResponse(false, "Erro ao atualizar usuario", null),
              HttpStatus.INTERNAL_SERVER_ERROR);
        }
      }
    } catch (Exception e) {
      return new ResponseEntity<>(
          new JsonResponse(false, "Erro interno ao criar usuário", null),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(
        new JsonResponse(true, "Usuário criado com sucesso", cliente),
        HttpStatus.OK);
  }
}
