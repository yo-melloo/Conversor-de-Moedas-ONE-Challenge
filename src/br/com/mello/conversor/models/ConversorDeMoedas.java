package br.com.mello.conversor.models;

import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class ConversorDeMoedas {
    private final Scanner entrada = new Scanner(System.in);
    private final ExchangeRateAPI consulta = new ExchangeRateAPI();
    private final ArrayList<String> historicoDeConversoes = new ArrayList<>();
    private Moeda primeiraMoeda;
    private String segundaMoedaCod;
    private String conversaoAtualString;
    Relogio relogio = new Relogio();
    String userName = "Usuário";

    public ConversorDeMoedas() {
        try {
            InetAddress informacoesPC = InetAddress.getLocalHost();
            this.userName = informacoesPC.getHostName();

        } catch (
                UnknownHostException e) {
            System.out.println("não é possível acessar o nome do computador.");
        }
    }

    public String getUserName() {
        return userName;
    }

    public ArrayList<String> getHistoricoDeConversoes() {
        return historicoDeConversoes;
    }

    public void setPrimeiraMoeda(Moeda primeiraMoeda) {
        this.primeiraMoeda = primeiraMoeda;
    }

    public void converter() {
        //realiza calculo de conversão acessando os valores na primeira moeda
        double resultado = this.primeiraMoeda.getPossibilidades().get(this.segundaMoedaCod) * this.primeiraMoeda.getQuantidade();

        //Exibindo resultado para o usuário - log

        System.out.println("\n######################################\n");

        System.out.println("[Sys] Resultado:\n");
        Double cotacao = this.primeiraMoeda.getPossibilidades().get(this.segundaMoedaCod);
        System.out.printf("%s Cotação atual: 1 %s = %s %s%n", relogio.getDataEHoraFormatacaoSimples(), this.primeiraMoeda.getCodigo(), cotacao, segundaMoedaCod);

        System.out.printf(
                "%s %s %s convertidos em %s = %.2f %s (aprox.)%n%n",
                relogio.getDataEHoraFormatacaoSimples(),
                this.primeiraMoeda.getQuantidade(),
                this.primeiraMoeda.getCodigo(),
                segundaMoedaCod,
                resultado,
                segundaMoedaCod
        );

        //retorno para ser adicionado ao histórico
        this.conversaoAtualString = String.format("%s %.2f %s = %.2f %s%n", relogio.getDataEHoraFormatacaoPadrao(), this.primeiraMoeda.getQuantidade(), this.primeiraMoeda.getCodigo(), resultado, segundaMoedaCod);
        this.armazenarConversaoNoHistorico();
    }

    public void realizarConsultaNaAPI(String codigoMoedaPrimaria, String codigoMoedaSecundaria, boolean conversaoPersonalizada) {
        //Define moeda
        this.setPrimeiraMoeda(consulta.consultarAPI(codigoMoedaPrimaria));
        this.segundaMoedaCod = codigoMoedaSecundaria;

        if (this.primeiraMoeda != null) {
            if (conversaoPersonalizada){
                System.out.print("Qual o código da moeda para qual você irá converter os " + codigoMoedaPrimaria + "?\n3 letras @ ");
                this.segundaMoedaCod = entrada.nextLine();
                System.out.println("Segunda moeda: " + this.segundaMoedaCod);

                if (this.primeiraMoeda.getPossibilidades().get(this.segundaMoedaCod) == null){
                    System.out.printf("[Sys error] Erro, "+ this.segundaMoedaCod + " não é uma moeda válida. Tente:\n");
                    for (String letras: this.primeiraMoeda.getPossibilidades().keySet()) {
                        System.out.println("* " + letras);
                    }
                }
            }

            //Define valor da conversão/primeira moeda
            System.out.print("Quanto " + codigoMoedaPrimaria + " deseja converter para " + this.segundaMoedaCod + "?\n@ ");
            double valorDeConversao = this.entrada.nextDouble();
            this.entrada.nextLine();
            this.primeiraMoeda.setQuantidade(valorDeConversao);
            System.out.println("* Definido: " + this.primeiraMoeda + "\n");
        } else {
            System.out.println("Voltando para o menu principal...\n");
        }
    }

        public void armazenarConversaoNoHistorico() {
            this.historicoDeConversoes.add(conversaoAtualString);
            System.out.println("** Conversão adicionada no histórico...\n");

            String nomeDoArquivo = String.format("%s %s conversões", relogio.getDataApenas(), this.userName);

            //Testa registrar no historicoDaUltimaSessao.txt
            try {
                FileWriter escritaTXT = new FileWriter(nomeDoArquivo + ".txt");

                //Percorre a lista escrevendo cada item como uma linha
                int is = 1;
                for (String i : this.historicoDeConversoes) {
                    escritaTXT.write(is + ". " + i);
                    is++;
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
