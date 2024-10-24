package br.com.mello.conversor.models;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class ConversorDeMoedas {
    private Scanner entrada = new Scanner(System.in);
    private Moeda primeiraMoeda;
    private String segundaMoedaCod;
    private ArrayList<String> historicoDeConversoes = new ArrayList<>();

    public ArrayList<String> getHistoricoDeConversoes() {
        return historicoDeConversoes;
    }

    public void setPrimeiraMoeda(Moeda primeiraMoeda) {
        this.primeiraMoeda = primeiraMoeda;
    }

    //Determina o valor a ser convertido
    public void setValorDeConversao(double valorDeConversao) {
        this.primeiraMoeda.setQuantidade(valorDeConversao);
        System.out.println("Definido: " + this.primeiraMoeda.getQuantidade() + " " + this.primeiraMoeda.getCodigo());
    }

    public double converter(Double valorDeConversao, Moeda primeiraMoeda, String segundaMoedaCodigo){
        this.primeiraMoeda = primeiraMoeda;
        //this.primeiraMoeda.setQuantidade(valorDeConversao);
        this.segundaMoedaCod = segundaMoedaCodigo;

        return this.primeiraMoeda.getPossibilidades().get(this.segundaMoedaCod);
    }

    @Override
    public String toString() {
        return String.format("""
                Atualmente no conversor:
                Primeira moeda: %s
                Segunda moeda: %s
                """, this.primeiraMoeda.getCodigo(), this.segundaMoedaCod);
    }
}
