package br.com.mello.conversor.models;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class ConversorDeMoedas {
    Scanner entrada = new Scanner(System.in);
    private Moeda primeiraMoeda;
    private String segundaMoedaCod;
    private ArrayList<String> historicoDeConversoes = new ArrayList<>();

    public ArrayList<String> getHistoricoDeConversoes() {
        return historicoDeConversoes;
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

    void exemplo(){
        //Importando e formatando data e hora para geração de logs ao final
        LocalDateTime dataEhora = LocalDateTime.now(); // pega "agora"
        DateTimeFormatter formatacaoPadrao = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        DateTimeFormatter formatacaoSimples = DateTimeFormatter.ofPattern("dd-MM HH:mm:ss");

        String dataFormatada = String.format("[%s]", dataEhora.format(formatacaoPadrao));
        String dataEhoraFormatadas = String.format("[%s]", dataEhora.format(formatacaoSimples));

        //Exibindo resultado para o usuário - log
        String conversaoAtualString = String.format("%s %.2f %s ---> %.2f %s%n", dataFormatada, this.primeiraMoeda.getQuantidade(), this.primeiraMoeda.getCodigo(), conversao, segundaMoedaCodigo);
        System.out.println("Resultado:");
        System.out.printf(
                "%s %s %s convertidos em %s equivalem a apróx.: %.2f %s%n%n",
                dataEhoraFormatadas,
                this.primeiraMoeda.getQuantidade(),
                this.primeiraMoeda.getCodigo(),
                this.segundaMoedaCod,
                conversao,
                this.segundaMoedaCod
        );

        //Registando no Histórico
        System.out.println("Conversão adicionada no histórico...\n");
        historicoDeConversoes.add(conversaoAtualString);

        try {
            FileWriter escritaTXT = new FileWriter("historicoDaUltimaSessão.txt");

            //Percorre a lista escrevendo cada item como uma linha
            for (String i : historicoDeConversoes) {
                escritaTXT.write(i);
            }
            escritaTXT.close();
        } catch (IOException e){
            System.out.println("Não foi possível acessar o arquivo especificado: " + e.getMessage());
        }
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
