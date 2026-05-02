package com.serralheria;

public class Servico {
    private String nome;
    private TipoCalculo tipoCalculo;

    public Servico(String nome, TipoCalculo tipoCalculo) {
        this.nome = nome;
        this.tipoCalculo = tipoCalculo;
    }

    public String getNome() {
        return nome;
    }

    public TipoCalculo getTipoCalculo() {
        return tipoCalculo;
    }
}
