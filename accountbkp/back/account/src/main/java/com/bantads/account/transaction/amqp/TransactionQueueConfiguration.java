package com.bantads.account.transaction.amqp;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionQueueConfiguration {

    @Bean
    public Queue transactionQueue() {
        return new Queue("transaction");
    }

    @Bean
    public Queue customerQueue(){
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

    @Bean
    public MessageConverter tJsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
