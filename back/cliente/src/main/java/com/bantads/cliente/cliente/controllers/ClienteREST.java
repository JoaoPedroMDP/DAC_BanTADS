package com.bantads.cliente.cliente.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.bantads.cliente.cliente.amqp.ClienteProducer;
import com.bantads.cliente.cliente.amqp.GerenteProducer;
import com.bantads.cliente.cliente.amqp.GerenteTransfer;
import com.bantads.cliente.cliente.model.Cliente;
import com.bantads.cliente.cliente.model.Endereco;
import com.bantads.cliente.cliente.repositories.ClienteRepository;
import com.bantads.cliente.cliente.repositories.EnderecoRepository;
import com.bantads.cliente.cliente.serializers.AccountDTO;
import com.bantads.cliente.cliente.serializers.AccountStatus;
import com.bantads.cliente.cliente.serializers.ClientAccountsDTO;
import com.bantads.cliente.cliente.serializers.ClienteDTO;
import com.bantads.cliente.cliente.serializers.EnderecoDTO;
import com.bantads.cliente.cliente.utils.JsonResponse;
import com.bantads.cliente.cliente.utils.RestService;
import com.bantads.cliente.cliente.utils.ValidarCpf;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin
@RestController
public class ClienteREST {

  @Autowired
  private ClienteRepository repo;

  @Autowired
  private EnderecoRepository repoEnd;

  @Autowired
  private ModelMapper mapper;

  @Autowired
  private ClienteProducer sender;

  @Autowired
  private GerenteProducer gerenteSender;

  @Autowired
  private RestService rest;

  @PostMapping(value = "/clientes")
  public ResponseEntity<Object> postMethodName(@RequestBody ClienteDTO cliente) {
    // TODO: validação do usuário

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
      Endereco end = repoEnd.save(mapper.map(cliente.getEndereco(), Endereco.class));

      cliente.setEndereco(mapper.map(end, EnderecoDTO.class));
      cliente.setAprovado(AccountStatus.ANALISE);

      // TODO: assinalar ao gerente com menos clientes
      // cliente.setGerente(1L);

      Cliente clienteObj = repo.save(mapper.map(cliente, Cliente.class));

      cliente.setId(clienteObj.getId());

      // Rabbit para criar a autenticação do usuário
      // sender.send(cliente);

      GerenteTransfer gt = new GerenteTransfer();

      gt.setAction("create-cliente");
      gt.setCliente(cliente.getId());

      gerenteSender.send(gt);
      cliente.setPassword("");

    } catch (Exception e) {
      return new ResponseEntity<>(
          new JsonResponse(false, "Erro interno ao criar usuário", null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<>(
        new JsonResponse(true, "Usuário criado com sucesso", cliente), HttpStatus.OK);
  }

  @GetMapping(value = "/clientes/{id}")
  public ResponseEntity<Object> getMethodName(@PathVariable String id) {
    if (id == null) {
      return new ResponseEntity<>(new JsonResponse(false, "Id não informado", null), HttpStatus.BAD_REQUEST);
    }

    Optional<Cliente> cliente = repo.findById(Long.parseLong(id));

    if (!cliente.isPresent()) {
      return new ResponseEntity<>(new JsonResponse(false, "Cliente não encontrado", null), HttpStatus.NOT_FOUND);
    }

    ClienteDTO clienteObject = mapper.map(cliente.get(), ClienteDTO.class);

    return new ResponseEntity<>(new JsonResponse(true, "", clienteObject), HttpStatus.OK);
  }

  @DeleteMapping("/clientes/{id}")
  ResponseEntity<Object> delete(@PathVariable String id) {
    if (id != null) {
      try {
        repo.deleteById(Long.parseLong(id));
      } catch (Exception e) {
        return new ResponseEntity<>(new JsonResponse(false, "Erro ao deletar usuário!", null),
            HttpStatus.BAD_REQUEST);
      }
      return new ResponseEntity<>(new JsonResponse(true, "Usuário deletado com sucesso!", null),
          HttpStatus.OK);
    }

    return new ResponseEntity<>(new JsonResponse(false, "Id não fornecido para deletar usuário", null),
        HttpStatus.BAD_REQUEST);
  }

  @PutMapping("/clientes/{id}")
  ResponseEntity<Object> update(@PathVariable String id, @RequestBody ClienteDTO cliente) {
    if (id != null) {
      try {
        Cliente clienteObj = repo.findById(Long.parseLong(id)).get();

        if (clienteObj == null) {
          return new ResponseEntity<>(new JsonResponse(false, "Cliente não encontrado", null), HttpStatus.NOT_FOUND);
        }
        repo.save(mapper.map(cliente, Cliente.class));
      } catch (Exception e) {
        return new ResponseEntity<>(new JsonResponse(false, "Erro ao atualizar o usuário!", null),
            HttpStatus.BAD_REQUEST);
      }
      return new ResponseEntity<>(new JsonResponse(true, "Usuário atualizado com sucesso!", null),
          HttpStatus.OK);
    }

    return new ResponseEntity<>(new JsonResponse(false, "Id não fornecido para atualizar usuário", null),
        HttpStatus.BAD_REQUEST);
  }

  @GetMapping(value = "/clientes")
  ResponseEntity<Object> show(@RequestParam(required = false) int gerente,
      @RequestParam(required = false) Boolean conta) {
    System.out.println(gerente);
    if (gerente == 0) {
      List<Cliente> clientes = repo.findAll();
      List<ClienteDTO> clientesDTO = clientes.stream().map(c -> mapper.map(c, ClienteDTO.class))
          .collect(Collectors.toList());
      return new ResponseEntity<>(new JsonResponse(true, "", clientesDTO), HttpStatus.BAD_REQUEST);
    }

    // Long gerenteId = Long.parseLong(gerente.ge);
    System.out.println(gerente);

    List<Cliente> clientes = repo.findByGerente(gerente);

    List<ClienteDTO> clientesDTO = clientes.stream().map(c -> mapper.map(c, ClienteDTO.class))
        .collect(Collectors.toList());

    if (conta == true) {
      String ids = clientesDTO.stream().map(c -> c.getId().toString()).collect(Collectors.joining(","));

      Object accounts = rest.getAccountDTOs(ids);

      ClientAccountsDTO cad = new ClientAccountsDTO();

      cad.setClientes(clientesDTO);
      cad.setAccounts(accounts);

      System.out.println("Lista de clientes recebida");

      return new ResponseEntity<>(new JsonResponse(true, "", cad), HttpStatus.OK);

    }

    return new ResponseEntity<>(new JsonResponse(true, "", clientesDTO), HttpStatus.OK);

  }

}
