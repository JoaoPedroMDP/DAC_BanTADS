package com.bantads.saga.models;

import java.io.Serializable;

public class LoginDTO implements Serializable {
  private Long id;
  private String email;
  private String password;
  private Long user_id;
  private String token;
  private String role;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUser() {
    return user_id;
  }

  public void setUser(Long user_id) {
    this.user_id = user_id;
  }

  public String getPassword() {
    return password;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public LoginDTO() {

  }

  public LoginDTO(String email, String password, Long user, String role) {
    this.email = email;
    this.password = password;
    this.user_id = user;
    this.role = role;
  }

}
