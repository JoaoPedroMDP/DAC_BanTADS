package com.bantads.saga.amqp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;

import com.bantads.saga.models.LoginDTO;

import org.springframework.amqp.core.Queue;

public class AuthProducer {
    @Autowired
    private RabbitTemplate template;

    @Autowired
    @Qualifier("auth")
    private Queue queue;

    public void send(AuthTransfer auth) {
        this.template.convertAndSend(this.queue.getName(), auth);
    }

    public boolean sendAndReceive(LoginDTO auth, String action) {
        AuthTransfer dt = new AuthTransfer(auth, action);
        AuthTransfer resposta = this.template.convertSendAndReceiveAsType(this.queue.getName(), dt,
                new ParameterizedTypeReference<AuthTransfer>() {
                });
        if (resposta != null) {
            System.out.println(resposta.getAction());
            return true;
        } else {
            System.out.println("Falhou");
            return false;
        }
    }

}