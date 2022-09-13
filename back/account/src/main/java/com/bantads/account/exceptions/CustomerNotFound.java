package com.bantads.account.exceptions;

public class CustomerNotFound extends BenignException {

    public CustomerNotFound() {
        super("Cliente não encontrado");
    }

}
