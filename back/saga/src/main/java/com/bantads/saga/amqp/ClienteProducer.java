package com.bantads.saga.amqp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;

import com.bantads.saga.models.ClienteDTO;

import org.springframework.amqp.core.Queue;

public class ClienteProducer {
  @Autowired
  private RabbitTemplate template;

  @Autowired
  @Qualifier("cliente")
  private Queue queue;

  public void send(ClienteTransfer cliente) {
    System.out.println(cliente);
    this.template.convertAndSend(this.queue.getName(), cliente);
  }

  // public void send(ClienteDTO cliente) {
  // this.template.convertAndSend(this.queue.getName(), cliente);
  // }

  // public boolean sendAndReceive(ClienteDTO cliente, String action) {
  // System.out.println(action);
  // ClienteTransfer dt = new ClienteTransfer(cliente, action);
  // ClienteTransfer resposta =
  // this.template.convertSendAndReceiveAsType(this.queue.getName(), dt,
  // new ParameterizedTypeReference<ClienteTransfer>() {
  // });
  // if (resposta != null) {
  // System.out.println(resposta.getAction());
  // return true;
  // } else {
  // System.out.println("Falhou");
  // return false;
  // }
  // }

  public ClienteTransfer sendAndReceive(ClienteDTO cliente, String action) {
    ClienteTransfer dt = new ClienteTransfer(cliente, action);
    System.out.println("Chegou no sendandreceive");
    Object resposta = (ClienteTransfer) this.template.convertSendAndReceive(this.queue.getName(), dt);
    System.out.println("Resposta: " + resposta);
    return resposta;
  }
}