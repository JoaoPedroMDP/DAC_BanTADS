package com.bantads.account.account.amqp;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    @Bean
    public Queue accountQueue() {
        return new Queue("account");
    }

    @Bean
    public AccountListener receiver() {
        return new AccountListener();
    }

    @Bean
    public AccountSender sender() {
        return new AccountSender();
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
