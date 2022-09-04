package com.bantads.cliente.cliente.amqp;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

  @Bean
  public Queue clientQueue() {
    System.out.println("Criando fila cliente-registration");
    return new Queue("cliente-registration");
  }

  @Bean
  public ClientRegistrationProducer sender() {
    return new ClientRegistrationProducer();
  }

  @Bean
  public MessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }
}