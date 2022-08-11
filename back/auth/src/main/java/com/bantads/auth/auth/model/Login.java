package com.bantads.auth.auth.model;

import java.io.Serializable;

public class Login implements Serializable {
  private String email;
  private String password;
  private String user;
  private Role role;

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

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public Login() {

  }

  public Login(String email, String password, String user, Role role) {
    this.email = email;
    this.password = password;
    this.user = user;
    this.role = role;
  }

}
