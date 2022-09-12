package com.bantads.account.account.amqp.account;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bantads.account.account.amqp.AccountTransfer;

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

    @Bean
    public DefaultClassMapper classMapper() {
        DefaultClassMapper classMapper = new DefaultClassMapper();
        Map<String, Class<?>> idClassMapping = new HashMap();
        classMapper.setTrustedPackages("*");
        idClassMapping.put("com.bantads.saga.amqp.AccountTransfer", AccountTransfer.class);
        classMapper.setIdClassMapping(idClassMapping);

        return classMapper;
    }

    @Bean
    public MessageConverter aJsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setClassMapper(classMapper());

        return converter;
    }
}
