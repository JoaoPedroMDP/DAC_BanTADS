package com.bantads.auth.auth.amqp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

  @Bean
  public Queue clientQueue() {
    System.out.println("Criando fila cliente");
    return new Queue("cliente");
  }

  @Bean
  public ClientRegistrationConsumer receiver() {
    return new ClientRegistrationConsumer();
  }

  // @Bean
  // public AccountSender sender() {
  // return new AccountSender();
  // }

  @Bean
  public DefaultClassMapper classMapper() {
    DefaultClassMapper classMapper = new DefaultClassMapper();
    Map<String, Class<?>> idClassMapping = new HashMap<>();
    idClassMapping.put("com.bantads.cliente.cliente.amqp.ClienteTransfer", ClienteTransfer.class);
    classMapper.setIdClassMapping(idClassMapping);

    return classMapper;

  }

  @Bean
  public MessageConverter jsonMessageConverter() {
    Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
    converter.setClassMapper(classMapper());

    return converter;
  }
}