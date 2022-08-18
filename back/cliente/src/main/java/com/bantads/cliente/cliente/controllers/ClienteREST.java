package com.bantads.cliente.cliente.controllers;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.bantads.cliente.cliente.model.Cliente;
import com.bantads.cliente.cliente.model.Endereco;
import com.bantads.cliente.cliente.repositories.ClienteRepository;
import com.bantads.cliente.cliente.repositories.EnderecoRepository;
import com.bantads.cliente.cliente.serializers.ClienteDTO;
import com.bantads.cliente.cliente.serializers.EnderecoDTO;

import org.springframework.web.bind.annotation.PostMapping;
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

  @PostMapping(value = "/clientes")
  public ResponseEntity<Object> postMethodName(@RequestBody ClienteDTO cliente) {
    // TODO: validação do usuário

    if (cliente.getNome() == null || cliente.getCpf() == null || cliente.getEndereco() == null) {
      return new ResponseEntity<>("Dados do cliente inválidos", HttpStatus.BAD_REQUEST);
    }

    EnderecoDTO endereco = cliente.getEndereco();

    if (endereco.getRua() == null || endereco.getNumero() == null
        || endereco.getCidade() == null || endereco.getEstado() == null || endereco.getCep() == null) {
      return new ResponseEntity<>("Dados do endereço inválidos", HttpStatus.BAD_REQUEST);
    }

    try {
      // TODO: Salvar endereço / decidir se salva no controller do endereco em si
      Endereco end = repoEnd.save(mapper.map(cliente.getEndereco(), Endereco.class));

      cliente.setEndereco(mapper.map(end, EnderecoDTO.class));

      repo.save(mapper.map(cliente, Cliente.class));
    } catch (Exception e) {
      return new ResponseEntity<>("Erro interno ao criar seu usuário", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<>("Usuário criado com sucesso!", HttpStatus.OK);
  }

  @GetMapping(value = "/clientes/{id}")
  public ResponseEntity<Object> getMethodName(@PathVariable String id) {
    if (id == null) {
      return new ResponseEntity<>("Id não informado", HttpStatus.BAD_REQUEST);
    }

    Optional<Cliente> cliente = repo.findById(Long.parseLong(id));

    if (!cliente.isPresent()) {
      return new ResponseEntity<>("Cliente não encontrado", HttpStatus.NOT_FOUND);
    }

    ClienteDTO clienteObject = mapper.map(cliente.get(), ClienteDTO.class);

    return new ResponseEntity<>(clienteObject, HttpStatus.OK);
  }

}
