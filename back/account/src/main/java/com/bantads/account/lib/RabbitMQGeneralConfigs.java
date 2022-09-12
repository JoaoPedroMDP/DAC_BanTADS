package com.bantads.account.lib;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bantads.account.account.amqp.AccountTransfer;

import ch.qos.logback.classic.pattern.MessageConverter;

@Configuration
public class RabbitMQGeneralConfigs {
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
    public MessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setClassMapper(classMapper());

        return converter;
    }
}
