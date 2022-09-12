package com.bantads.account.transaction.amqp;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionQueueConfiguration {

    @Bean
    public Queue transactionQueue() {
        return new Queue("transaction");
    }

    @Bean
    public Queue customerQueue() {
        return new Queue("cliente");
    }

    @Bean
    public TransactionListener tReceiver() {
        return new TransactionListener();
    }

    @Bean
    public TransactionSender tSender() {
        return new TransactionSender();
    }
}
