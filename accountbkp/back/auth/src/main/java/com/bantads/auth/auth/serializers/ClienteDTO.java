package com.bantads.auth.auth.serializers;

import java.io.Serializable;

public class ClienteDTO implements Serializable {
  private Long id;
  private String nome;
  private String cpf;
  private String aprovado;
  private Long gerente;
  private Long conta;
  private String email;
  private String password;
  private String role;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAprovado() {
    return aprovado;
  }

  public void setAprovado(String aprovado) {
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
