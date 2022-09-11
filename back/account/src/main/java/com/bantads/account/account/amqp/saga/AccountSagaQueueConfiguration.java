package com.bantads.account.account.amqp.saga;

import org.springframework.amqp.core.Queue;
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
}
