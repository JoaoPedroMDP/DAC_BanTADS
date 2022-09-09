package com.bantads.cliente.cliente.amqp;

import java.io.Serializable;

public class GerenteTransfer implements Serializable {
  int gerente;
  Long cliente;
  String action;

  public GerenteTransfer() {
  }

  public GerenteTransfer(int gerente, String action) {
    this.gerente = gerente;
    this.action = action;
  }

  public int getGerente() {
    return gerente;
  }

  public void setGerente(int gerente) {
    this.gerente = gerente;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public Long getCliente() {
    return cliente;
  }

  public void setCliente(Long cliente) {
    this.cliente = cliente;
  }

}
