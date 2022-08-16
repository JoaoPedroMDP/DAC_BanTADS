package com.bantads.auth.auth.rest;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.bantads.auth.auth.model.Login;
import com.bantads.auth.auth.model.Role;
import com.bantads.auth.auth.utils.PasswordEnc;

@CrossOrigin
@RestController
public class LoginREST {

  // Login baseLogin = new Login("admin@admin.com", "admin", "12345678",
  // Role.ADMIN);

  ArrayList<Login> logins = new ArrayList<Login>();

  @PostMapping("/auth/login")
  ResponseEntity<Object> login(@RequestBody Login login) {
    String email = login.getEmail();
    String password = login.getPassword();

    if (email == null || password == null) {
      return new ResponseEntity<>("Missing email or password", HttpStatus.BAD_REQUEST);
    }

    // TODO: REMOVE
    if (logins.size() == 0) {
      return new ResponseEntity<>("No logins", HttpStatus.BAD_REQUEST);
    }

    Login baseLogin = logins.get(0);

    String salt = PasswordEnc.getSaltvalue(10);

    if (login.getEmail().equals(baseLogin.getEmail())
        && PasswordEnc.verifyUserPassword(password, baseLogin.getPassword(), salt)) {
      try {
        Algorithm alg = Algorithm.HMAC256("secret");

        String token = JWT.create().withIssuer("auth0").sign(alg);

        baseLogin.setToken(token);
        System.out.println(token);
        baseLogin.setPassword("");

        return new ResponseEntity<>(baseLogin, HttpStatus.OK);
      } catch (JWTCreationException e) {
        return new ResponseEntity<>("Internal server error while creating JWT", HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    return new ResponseEntity<>("Wrong credentials", HttpStatus.UNAUTHORIZED);
  }

  @PostMapping("/auth/register")
  ResponseEntity<Object> register(@RequestBody Login login) {
    if (login.getEmail() != null && login.getPassword() != null && login.getUser() != null) {
      if (login.getRole() == null) {
        login.setRole(Role.CLIENTE);
      }

      String password = login.getPassword();

      String saltValue = PasswordEnc.getSaltvalue(10);

      String passwordEnc = PasswordEnc.generateSecurePassword(password, saltValue);

      System.out.println(passwordEnc);

      login.setPassword(passwordEnc);

      logins.add(login);

      return new ResponseEntity<>("User registered", HttpStatus.OK);

    }

    return new ResponseEntity<>("We had a problem creating your authentication.", HttpStatus.BAD_REQUEST);
  }

}
