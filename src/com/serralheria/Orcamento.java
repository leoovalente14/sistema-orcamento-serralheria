package com.serralheria;

public class Orcamento {
    private Cliente cliente;
    private Servico servico;
    private Material material;
    private double valorMaterial;
    private double valorMaoDeObra;
    private double valorExtras;
    private double valorDesconto;
    private double valorTotal;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public double getValorMaterial() {
        return valorMaterial;
    }

    public void setValorMaterial(double valorMaterial) {
        this.valorMaterial = valorMaterial;
    }

    public double getValorMaoDeObra() {
        return valorMaoDeObra;
    }

    public void setValorMaoDeObra(double valorMaoDeObra) {
        this.valorMaoDeObra = valorMaoDeObra;
    }

    public double getValorExtras() {
        return valorExtras;
    }

    public void setValorExtras(double valorExtras) {
        this.valorExtras = valorExtras;
    }

    public double getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(double valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Orcamento (Cliente cliente, Servico servico, Material material,
                      double valorMaterial, double valorMaoDeObra,
                      double valorExtras, double valorDesconto){
        this.cliente = cliente;
        this.servico = servico;
        this.material = material;
        this.valorMaterial = valorMaterial;
        this.valorMaoDeObra = valorMaoDeObra;
        this.valorExtras = valorExtras;
        this.valorDesconto = valorDesconto;
        this.valorTotal = valorMaterial + valorMaoDeObra + valorExtras - valorDesconto;
    }
}
