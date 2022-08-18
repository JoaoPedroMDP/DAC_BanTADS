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
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "endereco", referencedColumnName = "id")
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

  public Endereco getEndereco() {
    return endereco;
  }

  public void setEndereco(Endereco endereco) {
    this.endereco = endereco;
  }

}
