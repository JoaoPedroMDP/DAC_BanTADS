package com.bantads.cliente.cliente.serializers;

import java.io.Serializable;

public class BasicClienteDTO implements Serializable {
  private Long user;
  private String email;
  private String password;
  private String role;

  public String getRole() {
    return role;
  }

  public Long getUser() {
    return user;
  }

  public void setUser(Long user) {
    this.user = user;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public BasicClienteDTO(ClienteDTO cliente) {
    this.user = cliente.getId();
    this.email = cliente.getEmail();
    this.password = cliente.getPassword();
    this.role = cliente.getRole();
  }

}
