package com.bantads.cliente.cliente.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  @Column(name = "nome")
  private String nome;
  @Column(name = "cpf")
  private String cpf;
  @Column(name = "aprovado")
  private String aprovado;
  @Column(name = "gerente")
  private int gerente;
  @Column(name = "conta")
  private Long conta;
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "endereco", referencedColumnName = "id")
  private Endereco endereco;
  @Column(name = "salario")
  private int salario = 0;

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

  public Endereco getEndereco() {
    return endereco;
  }

  public void setEndereco(Endereco endereco) {
    this.endereco = endereco;
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

  public int getGerente() {
    return gerente;
  }

  public void setGerente(int gerente) {
    this.gerente = gerente;
  }

  public Long getConta() {
    return conta;
  }

  public void setConta(Long conta) {
    this.conta = conta;
  }

  public int getSalario() {
    return salario;
  }

  public void setSalario(int salario) {
    this.salario = salario;
  }

}
