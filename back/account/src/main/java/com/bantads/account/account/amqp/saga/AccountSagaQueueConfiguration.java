package com.bantads.account.account.amqp.saga;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountSagaQueueConfiguration {

    @Bean
    public Queue sagaQueue() {
        return new Queue("account-saga");
    }

    @Bean
    public AccountSagaListener asReceiver() {
        return new AccountSagaListener();
    }

    // @Bean
    // public MessageConverter asJsonMessageConverter() {
    // return new Jackson2JsonMessageConverter(
    // }
}
