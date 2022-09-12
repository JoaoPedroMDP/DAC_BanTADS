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

  public GerenteTransfer sendAndReceive(GerenteDTO gerente, String action) {
    GerenteTransfer dt = new GerenteTransfer(gerente, action);
    System.out.println("Chegou no send and receive");
    GerenteTransfer resposta = (GerenteTransfer) this.template.convertSendAndReceive(this.queue.getName(), dt);
    return resposta;
  }
}