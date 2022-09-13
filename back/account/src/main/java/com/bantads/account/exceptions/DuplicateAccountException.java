package com.bantads.account.exceptions;

public class DuplicateAccountException extends BenignException {

    public DuplicateAccountException() {
        super("Este usuário já possui uma conta");
    }

}
