package com.serralheria;

public class PrecoServico {
    private Servico servico;
    private Material material;
    private double preco;

    public PrecoServico(Servico servico, Material material, double preco){
        this.servico = servico;
        this.material = material;
        this.preco = preco;

    }

    public Servico getServico() {
        return servico;
    }

    public Material getMaterial(){
        return material;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco (double preco) {
        this.preco = preco;
    }

}
