package com.bantads.gerente.amqp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.amqp.core.Queue;

public class GerenteProducer {
  @Autowired
  private RabbitTemplate template;

  @Autowired
  private Queue queue;

  public void send(GerenteTransfer gerente) {
    this.template.convertAndSend(this.queue.getName(), gerente);
  }
}