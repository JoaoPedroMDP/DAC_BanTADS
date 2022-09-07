package com.bantads.account.lib;

public class CustomerDTO {
    private String nome;

    public CustomerDTO() {
    }

    public CustomerDTO(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
