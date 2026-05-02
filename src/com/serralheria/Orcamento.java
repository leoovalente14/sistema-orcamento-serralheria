package com.serralheria;

import java.util.List;

public class Orcamento {
    private Cliente cliente;
    private Servico servico;
    private Material material;
    private double medida1;
    private double medida2;
    private double precoBase;
    private double maoDeObra;
    private List<Extra> extras;
    private double desconto;
    private double total;

    public void CalcularTotal(){
        double valorBase;

        if (servico.getTipoCalculo() == TipoCalculo.AREA) {
            valorBase = medida1 * medida2 * precoBase;
        } else {
            valorBase = medida1* precoBase;
        }

        double somaExtras = 0;
        for (Extra e : extras) {
            somaExtras += e.getValor();
        }

        total = valorBase + maoDeObra + somaExtras - desconto;
    }

    public double getTotal() {
        return total;
    }
}
