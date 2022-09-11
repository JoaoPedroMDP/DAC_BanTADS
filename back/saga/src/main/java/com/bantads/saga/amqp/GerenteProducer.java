package com.bantads.saga.amqp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;

import com.bantads.saga.models.GerenteDTO;

import org.springframework.amqp.core.Queue;

public class GerenteProducer {
  @Autowired
  private RabbitTemplate template;

  @Autowired
  @Qualifier("gerente")
  private Queue queue;

  public void send(GerenteTransfer gerente) {
    this.template.convertAndSend(this.queue.getName(), gerente);
  }

  public boolean sendAndReceive(GerenteDTO gerente, String action) {
    GerenteTransfer dt = new GerenteTransfer(gerente, action);
    GerenteTransfer resposta = this.template.convertSendAndReceiveAsType(this.queue.getName(), dt,
        new ParameterizedTypeReference<GerenteTransfer>() {
        });
    if (resposta != null) {
      System.out.println(resposta.getAction());
      return true;
    } else {
      System.out.println("Falhou");
      return false;
    }
  }
}