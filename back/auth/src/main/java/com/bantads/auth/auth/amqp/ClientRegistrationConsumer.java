package com.bantads.auth.auth.amqp;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;

import com.bantads.auth.auth.models.Login;
import com.bantads.auth.auth.repository.LoginRepository;
import com.bantads.auth.auth.serializers.BasicClienteDTO;
import com.bantads.auth.auth.serializers.Role;
import com.bantads.auth.auth.utils.PasswordEnc;

// import com.bantads.auth.auth.serializers.BasicClienteDTO;

@RabbitListener(queues = "cliente-registration")
public class ClientRegistrationConsumer {

  @Autowired
  private LoginRepository repo;

  @Autowired
  private ModelMapper mapper;

  @RabbitHandler
  public void receive(@Payload BasicClienteDTO cliente) {
    System.out.println("Received registration message from cliente");

    if (cliente.getEmail() != null && cliente.getPassword() != null && cliente.getUser() != null) {

      Login loginEntity = repo.findByEmail(cliente.getEmail());

      if (loginEntity != null) {
        return;
      }

      if (cliente.getRole() == null) {
        cliente.setRole(Role.CLIENTE.name());
      }

      String password = cliente.getPassword();
      String saltValue = PasswordEnc.getSaltvalue(10);
      String passwordEnc = PasswordEnc.generateSecurePassword(password, saltValue);

      // System.out.println(passwordEnc);

      cliente.setPassword(passwordEnc);
      repo.save(mapper.map(cliente, Login.class));

      System.out.println("Cliente registration success");
      return;
    }

    System.out.println("Cliente registration failed");
  }
}