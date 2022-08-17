package com.bantads.cliente.cliente.serializers;

import java.io.Serializable;

public class ClienteDTO implements Serializable {
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

  public EnderecoDTO getEndereco() {
    return endereco;
  }

  public void setEndereco(EnderecoDTO endereco) {
    this.endereco = endereco;
  }

}
