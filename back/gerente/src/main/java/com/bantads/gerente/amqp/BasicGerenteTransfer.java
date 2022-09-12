package com.bantads.gerente.amqp;

import java.io.Serializable;

public class BasicGerenteTransfer implements Serializable {
  int gerente;
  Long cliente;
  String action;
  String error;

  public BasicGerenteTransfer() {
  }

  public BasicGerenteTransfer(int gerente, String action) {
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

  public void setError(String error) {
    this.error = error;
  }
}
