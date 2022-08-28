package com.bantads.cliente.cliente.serializers;

import java.io.Serializable;

public class ClienteDTO implements Serializable {
  private Long id;
  private String nome;
  private String cpf;
  private AccountStatus aprovado;
  private Long gerente;
  private Long conta;
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

  public AccountStatus getAprovado() {
    return aprovado;
  }

  public void setAprovado(AccountStatus aprovado) {
    this.aprovado = aprovado;
  }

  public Long getGerente() {
    return gerente;
  }

  public void setGerente(Long gerente) {
    this.gerente = gerente;
  }

  public Long getConta() {
    return conta;
  }

  public void setConta(Long conta) {
    this.conta = conta;
  }

}
