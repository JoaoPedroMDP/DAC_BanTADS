package com.bantads.auth.auth.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.bantads.auth.auth.model.Login;
import com.bantads.auth.auth.model.Role;

public class LoginREST {

  Login baseLogin = new Login("admin@admin.com", "admin", "12345678", Role.ADMIN);

  @PostMapping("/login")
  ResponseEntity<Object> login(@RequestBody Login login) {
    if (login.getEmail().equals(baseLogin.getEmail()) && login.getPassword().equals(baseLogin.getPassword())) {
      return new ResponseEntity<>(baseLogin, HttpStatus.OK);
    }

    return new ResponseEntity<>("Wrong credentials", HttpStatus.UNAUTHORIZED);
  }

}
