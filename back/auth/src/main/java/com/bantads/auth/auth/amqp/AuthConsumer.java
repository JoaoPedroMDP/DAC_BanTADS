package com.bantads.auth.auth.amqp;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;

import com.bantads.auth.auth.models.Login;
import com.bantads.auth.auth.repository.LoginRepository;
import com.bantads.auth.auth.serializers.LoginDTO;
// import com.bantads.auth.auth.serializers.ClienteDTO;/
import com.bantads.auth.auth.serializers.Role;
import com.bantads.auth.auth.utils.PasswordEnc;

@RabbitListener(queues = "auth")
public class AuthConsumer {

  @Autowired
  private RabbitTemplate template;

  @Autowired
  private LoginRepository repo;

  @Autowired
  private ModelMapper mapper;

  @RabbitHandler
  public void receive(@Payload AuthTransfer authTransfer) {
    System.out.println("Received registration message for auth");

    if (authTransfer.getAction().equals("auth-register")) {
      LoginDTO login = authTransfer.getLogin();

      if (login.getEmail() != null && login.getPassword() != null && login.getId() != null) {

        Login loginEntity = repo.findByEmail(login.getEmail());

        if (loginEntity != null) {
          return;
        }

        if (login.getRole() == null) {
          login.setRole(Role.CLIENTE);
        }

        String password = login.getPassword();
        String saltValue = PasswordEnc.getSaltvalue(10);
        String passwordEnc = PasswordEnc.generateSecurePassword(password, saltValue);

        // System.out.println(passwordEnc);

        login.setPassword(passwordEnc);
        repo.save(mapper.map(login, Login.class));

        // System.out.println("Cliente registration success");

        authTransfer.setAction("auth-ok");

        this.template.convertAndSend("saga", authTransfer);

        return;
      }

      authTransfer.setAction("auth-failed");

      this.template.convertAndSend("saga", authTransfer);

      System.out.println("Auth registration failed");
    } else if (authTransfer.getAction().equals("auth-delete")) {
      LoginDTO login = authTransfer.getLogin();

      Login loginObj = repo.findById(login.getId()).get();

      repo.delete(loginObj);

      authTransfer.setAction("auth-deleted");

      this.template.convertAndSend("saga", authTransfer);
    }
  }
}