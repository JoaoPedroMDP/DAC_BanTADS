package com.bantads.cliente.cliente.model;

import java.io.Serializable;

public class Cliente implements Serializable {
  private String nome;
  private String cpf;
  private Number limite;
  private Boolean limiteAtivo;
  private Endereco endereco;

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

  public Number getLimite() {
    return limite;
  }

  public void setLimite(Number limite) {
    this.limite = limite;
  }

  public Boolean getLimiteAtivo() {
    return limiteAtivo;
  }

  public void setLimiteAtivo(Boolean limiteAtivo) {
    this.limiteAtivo = limiteAtivo;
  }

  public Endereco getEndereco() {
    return endereco;
  }

  public void setEndereco(Endereco endereco) {
    this.endereco = endereco;
  }

}
