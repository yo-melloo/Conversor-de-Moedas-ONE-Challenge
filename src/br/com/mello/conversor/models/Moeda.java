package br.com.mello.conversor.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Moeda {
    private String codigo;
    private String nome;
    private String pais;
    private String retorno;
    private double quantidade;
    private double valor;
    private Map<String, Double> valores;
    private Map<String, Double> possibilidades;


    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getPais() {
        return pais;
    }

    public String getRetorno() {
        return retorno;
    }

    public double getValor() {
        return valor;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public Map<String, Double> getPossibilidades() {
        return possibilidades;
    }

    public Moeda(MoedaApi moedaApi){
        this.quantidade = 1;
        this.retorno = moedaApi.result();
        this.codigo = moedaApi.base_code();

        this.possibilidades = HashMap.newHashMap(6);;
        this.valores = HashMap.newHashMap(161);
        valores.putAll(moedaApi.conversion_rates());

        possibilidades.put("ARS", valores.get("ARS"));
        possibilidades.put("BOB", valores.get("BOB"));
        possibilidades.put("BRL", valores.get("BRL"));
        possibilidades.put("CLP", valores.get("CLP"));
        possibilidades.put("COP", valores.get("COP"));
        possibilidades.put("USD", valores.get("USD"));

        this.valor = possibilidades.get(this.codigo);

    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return String.format("[Sys] %s (%s) - quantidade: %s", this.valor, this.codigo, this.quantidade);
    }
}
