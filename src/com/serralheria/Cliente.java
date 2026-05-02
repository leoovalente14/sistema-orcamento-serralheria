package com.serralheria;

public class Cliente {
    private String nome;
    private String endereco;
    private String celular;

    public Cliente(String nome, String endereco, String celular) {
        this.nome = nome;
        this.endereco = endereco;
        this.celular = celular;
    }

    public String getNome(){
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getCelular() {
        return celular;
    }
}
