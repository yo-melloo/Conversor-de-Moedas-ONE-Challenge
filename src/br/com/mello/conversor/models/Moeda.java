package br.com.mello.conversor.models;

import java.util.HashMap;
import java.util.Map;

public class Moeda {
    private final String codigo;
    private final Map<String, Double> possibilidades;
    private double quantidade;


    public String getCodigo() {
        return codigo;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public Map<String, Double> getPossibilidades() {
        return possibilidades;
    }

    public Moeda(MoedaApi moedaApi){
        this.quantidade = 1;
        this.codigo = moedaApi.base_code();
        this.possibilidades = new HashMap<>();

        Map<String, Double> valores = new HashMap<>(moedaApi.conversion_rates());

        for (String cotacao: valores.keySet()){
            possibilidades.put(cotacao, valores.get(cotacao));
        }

    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", this.quantidade, this.codigo);
    }
}
