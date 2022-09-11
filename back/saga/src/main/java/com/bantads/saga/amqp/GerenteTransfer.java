package com.bantads.saga.amqp;

import java.io.Serializable;

import com.bantads.saga.models.GerenteDTO;

public class GerenteTransfer implements Serializable {
  GerenteDTO gerente;
  Long cliente;
  String action;

  public GerenteTransfer() {
  }

  public GerenteTransfer(GerenteDTO gerente, String action) {
    this.gerente = gerente;
    this.action = action;
  }

  public GerenteDTO getGerente() {
    return gerente;
  }

  public void setGerente(GerenteDTO gerente) {
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
