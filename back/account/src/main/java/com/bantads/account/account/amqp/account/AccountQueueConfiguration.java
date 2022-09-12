package com.bantads.account.account.amqp.account;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountQueueConfiguration {

    @Bean
    public Queue accountQueue() {
        return new Queue("account");
    }

    @Bean
    public AccountListener aReceiver() {
        return new AccountListener();
    }

    @Bean
    public AccountSender aSender() {
        return new AccountSender();
    }
}
