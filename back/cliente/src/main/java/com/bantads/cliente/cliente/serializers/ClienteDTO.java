package com.bantads.cliente.cliente.serializers;

import java.io.Serializable;

public class ClienteDTO implements Serializable {
  private Long id;
  private String nome;
  private String cpf;
  private EnderecoDTO endereco;

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public EnderecoDTO getEndereco() {
    return endereco;
  }

  public void setEndereco(EnderecoDTO endereco) {
    this.endereco = endereco;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

}
