package com.bantads.saga.amqp;

import java.io.Serializable;

import com.bantads.saga.models.LoginDTO;

public class AuthTransfer implements Serializable {
  LoginDTO login;
  String action;

  public AuthTransfer() {
  }

  public AuthTransfer(LoginDTO login, String action) {
    this.login = login;
    this.action = action;
  }

  public LoginDTO getLogin() {
    return login;
  }

  public void setLogin(LoginDTO login) {
    this.login = login;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

}
