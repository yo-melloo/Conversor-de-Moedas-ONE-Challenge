package br.com.mello.conversor.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Moeda {
    String codigo;
    String nome;
    String pais;
    String retorno;
    double valor;
    Map<String, Double> valores;
    Map<String, Double> possibilidades;

    public Moeda(MoedaApi moedaApi){
        this.retorno = moedaApi.result();
        this.codigo = moedaApi.base_code();

        this.possibilidades = HashMap.newHashMap(161);;
        this.valores = HashMap.newHashMap(161);
        valores.putAll(moedaApi.conversion_rates());

        possibilidades.put("ARS", valores.get("ARS"));
        possibilidades.put("BOB", valores.get("BOB"));
        possibilidades.put("BRL", valores.get("BRL"));
        possibilidades.put("CLP", valores.get("CLP"));
        possibilidades.put("COP", valores.get("COP"));
        possibilidades.put("USD", valores.get("USD"));

        this.valor = valores.get(this.codigo);

    }

    @Override
    public String toString() {
        return String.format("""
                Resultado: %s (%s)
                ----------------
                Possibilidades: %s
                """, this.valor, this.codigo, this.possibilidades);
    }
}
