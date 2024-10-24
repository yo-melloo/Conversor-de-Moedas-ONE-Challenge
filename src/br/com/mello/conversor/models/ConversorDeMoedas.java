package br.com.mello.conversor.models;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class ConversorDeMoedas {
    private final Scanner entrada = new Scanner(System.in);
    private final ExchangeRateAPI consulta = new ExchangeRateAPI();
    private final ArrayList<String> historicoDeConversoes = new ArrayList<>();
    private Moeda primeiraMoeda;
    private String segundaMoedaCod;
    private String conversaoAtualString;

    public ArrayList<String> getHistoricoDeConversoes() {
        return historicoDeConversoes;
    }

    public void setPrimeiraMoeda(Moeda primeiraMoeda) {
        this.primeiraMoeda = primeiraMoeda;
    }

    public double converter(Moeda primeiraMoeda, String segundaMoedaCodigo) {
        this.primeiraMoeda = primeiraMoeda;
        this.segundaMoedaCod = segundaMoedaCodigo;

        double equivalencia = this.primeiraMoeda.getPossibilidades().get(this.segundaMoedaCod);
        return equivalencia * this.primeiraMoeda.getQuantidade();
    }

    public void realizarConsultaNaAPI(String codigoMoedaPrimaria, String codigoMoedaSecundaria) {
        //Define moeda
        this.setPrimeiraMoeda(consulta.consultarAPI(codigoMoedaPrimaria));

        //Define valor
        System.out.print("Quanto " + codigoMoedaPrimaria + " deseja converter para " + codigoMoedaSecundaria + "?\n@ ");
        double valorDeConversao = this.entrada.nextDouble();
        this.entrada.nextLine();
        this.primeiraMoeda.setQuantidade(valorDeConversao);
        System.out.println("* Definido: " + this.primeiraMoeda + "\n");
        //conversor.setValorDeConversao(valorDeConversao);

        double resultado = this.converter(this.primeiraMoeda, codigoMoedaSecundaria);

        //Importando e formatando data e hora para geração de logs ao final
        LocalDateTime dataEhora = LocalDateTime.now(); // pega "agora"
        DateTimeFormatter formatacaoPadrao = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        DateTimeFormatter formatacaoSimples = DateTimeFormatter.ofPattern("dd-MM HH:mm:ss");

        String dataFormatada = String.format("[%s]", dataEhora.format(formatacaoPadrao));
        String dataEhoraFormatadas = String.format("[%s]", dataEhora.format(formatacaoSimples));

        //Exibindo resultado para o usuário - log
        System.out.println("[Sys] Resultado:");
        System.out.printf(
                "%s %s %s convertidos em %s equivalem a apróx.: %.2f %s%n%n",
                dataEhoraFormatadas,
                this.primeiraMoeda.getQuantidade(),
                this.primeiraMoeda.getCodigo(),
                codigoMoedaSecundaria,
                resultado,
                codigoMoedaSecundaria
        );

        //retorno para ser adicionado ao histórico
        this.conversaoAtualString = String.format("%s %.2f %s ---> %.2f %s%n", dataFormatada, this.primeiraMoeda.getQuantidade(), this.primeiraMoeda.getCodigo(), resultado, codigoMoedaSecundaria);
        this.armazenarConversaoNoHistorico();
    }

    public void armazenarConversaoNoHistorico() {
        this.historicoDeConversoes.add(conversaoAtualString);
        System.out.println("** Conversão adicionada no histórico...\n");

        //Testa registrar no historicoDaUltimaSessao.txt
        try {
            FileWriter escritaTXT = new FileWriter("historicoDaUltimaSessão.txt");

            //Percorre a lista escrevendo cada item como uma linha
            for (String i : this.historicoDeConversoes) {
                escritaTXT.write(i);
            }
            escritaTXT.close();
        } catch (IOException e) {
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
