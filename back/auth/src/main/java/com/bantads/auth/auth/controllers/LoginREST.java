package com.bantads.auth.auth.controllers;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.bantads.auth.auth.models.Login;
import com.bantads.auth.auth.repository.LoginRepository;
import com.bantads.auth.auth.serializers.LoginDTO;
import com.bantads.auth.auth.serializers.Role;
import com.bantads.auth.auth.utils.JsonResponse;
import com.bantads.auth.auth.utils.PasswordEnc;

@CrossOrigin
@RestController
public class LoginREST {

  @Autowired
  private LoginRepository repo;

  @Autowired
  private ModelMapper mapper;

  @PostMapping("/auth/login")
  ResponseEntity<Object> login(@RequestBody LoginDTO login) {

    String email = login.getEmail();
    String password = login.getPassword();

    if (email == null || password == null) {
      return new ResponseEntity<>("Missing email or password", HttpStatus.BAD_REQUEST);
    }

    Login loginEntity = repo.findByEmail(email);
    String salt = PasswordEnc.getSaltvalue(10);

    if (loginEntity == null) {
      return new ResponseEntity<>(new JsonResponse(false, "Email não registrado!", null), HttpStatus.NOT_FOUND);
    }

    Boolean isPasswordCorrect = PasswordEnc.verifyUserPassword(password, loginEntity.getPassword(), salt);

    if (login.getEmail().equals(loginEntity.getEmail())
        && isPasswordCorrect) {
      try {
        Algorithm alg = Algorithm.HMAC256("secret");
        String token = JWT.create().withIssuer("auth0").withClaim("role", loginEntity.getRole().toString()).sign(alg);

        loginEntity.setToken(token);
        loginEntity.setPassword("");

        return new ResponseEntity<>(new JsonResponse(true, "", mapper.map(loginEntity, LoginDTO.class)), HttpStatus.OK);
      } catch (JWTCreationException e) {
        return new ResponseEntity<>(new JsonResponse(false, "Internal server error while creating JWT", null),
            HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    return new ResponseEntity<>(new JsonResponse(false, "Usuário e/ou senha incorretos", null),
        HttpStatus.UNAUTHORIZED);
  }

  @PostMapping("/auth")
  ResponseEntity<Object> register(@RequestBody LoginDTO login) {
    if (login.getEmail() != null && login.getPassword() != null && login.getUser() != null) {

      Login loginEntity = repo.findByEmail(login.getEmail());

      if (loginEntity != null) {
        return new ResponseEntity<>(new JsonResponse(false, "Email já cadastrado!", null), HttpStatus.BAD_REQUEST);
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

      return new ResponseEntity<>(new JsonResponse(true, "Autenticação do usuário criada com sucesso!", null),
          HttpStatus.OK);

    }

    return new ResponseEntity<>("We had a problem creating your authentication.", HttpStatus.BAD_REQUEST);
  }

  @DeleteMapping("/auth/{id}")
  ResponseEntity<Object> delete(@PathVariable String id) {
    if (id != null) {
      try {
        repo.deleteById(Long.parseLong(id));
      } catch (Exception e) {
        return new ResponseEntity<>(new JsonResponse(false, "Erro ao deletar autenticação do usuário!", null),
            HttpStatus.BAD_REQUEST);
      }
      return new ResponseEntity<>(new JsonResponse(true, "Autenticação do usuário deletada com sucesso!", null),
          HttpStatus.OK);
    }

    return new ResponseEntity<>(new JsonResponse(false, "Id não fornecido para deletar autenticação", null),
        HttpStatus.BAD_REQUEST);
  }

}
