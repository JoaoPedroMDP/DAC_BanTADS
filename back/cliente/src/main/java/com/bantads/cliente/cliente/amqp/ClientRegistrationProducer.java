package com.bantads.cliente.cliente.amqp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bantads.cliente.cliente.serializers.BasicClienteDTO;
import com.bantads.cliente.cliente.serializers.ClienteDTO;

import org.springframework.amqp.core.Queue;

public class ClientRegistrationProducer {
  @Autowired
  private RabbitTemplate template;

  @Autowired
  @Qualifier("cliente-registration")
  private Queue queue;

  public void send(ClienteDTO cliente) {
    BasicClienteDTO basicCliente = new BasicClienteDTO(cliente);
    this.template.convertAndSend(this.queue.getName(), basicCliente);
  }
}