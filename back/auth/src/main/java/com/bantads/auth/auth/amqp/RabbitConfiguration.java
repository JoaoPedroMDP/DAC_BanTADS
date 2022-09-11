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
  public Queue authQueue() {
    System.out.println("Criando fila auth");
    return new Queue("auth");
  }

  // @Bean
  // public Queue sagaQueue() {
  // System.out.println("Criando fila saga");
  // return new Queue("saga");
  // }

  @Bean
  public AuthConsumer receiver() {
    return new AuthConsumer();
  }

  @Bean
  public DefaultClassMapper classMapper() {
    DefaultClassMapper classMapper = new DefaultClassMapper();
    Map<String, Class<?>> idClassMapping = new HashMap();
    classMapper.setTrustedPackages("*");
    idClassMapping.put("com.bantads.saga.amqp.AuthTransfer", AuthTransfer.class);
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