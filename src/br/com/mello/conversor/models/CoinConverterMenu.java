package br.com.mello.conversor.models;

import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.spi.AbstractResourceBundleProvider;

public class CoinConverterMenu {
    private ConversorDeMoedas conversor = new ConversorDeMoedas();
    private Scanner entradaMenu = new Scanner(System.in);
    private ExchangeRateAPI consulta = new ExchangeRateAPI();
    private ArrayList<String> historicoDeConversoesLocal = new ArrayList<>();
    private String codigoMoedaPrimaria = "";
    private String codigoMoedaSecundaria = "";
    private int numeroFinal = 8;
    private String userName;
    private Moeda moedaPrimaria;

    public CoinConverterMenu(){
        try {
            InetAddress informacoesPC = InetAddress.getLocalHost();
            this.userName = informacoesPC.getHostName();
        } catch (
                UnknownHostException e) {
            this.userName = "";

            System.out.println("não é possível acessar o nome do computador.");
        }
    }

    public boolean exibirMenu() {
        //Exibe menu
        while (true) {
            System.out.println(
                    """
                            @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                            
                                # CONVERSOR DE MOEDAS
                                @ by Mello
                            
                                    -------
                            
                            1) Dólar Americano em Peso Argentino.
                            2) Peso Argentino em Dólar Americano.
                            3) Dólar Americano em Real Brasileiro.
                            4) Real Brasileiro em Dólar Americano.
                            5) Dólar Americano em Peso Colombiano.
                            6) Peso Colombiano em Dólar Americano.
                            7) Acessar histórico.
                            8) Sair.
                            
                            """
            );
            System.out.print("Escolha uma opção: @ ");
            int escolha = entradaMenu.nextInt();
            System.out.println("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n");

            //Espera entrada do usuario
            //Determina moedas a serem convertidas
            if (escolha <= 6) {
                if (escolha == 1) {
                    codigoMoedaPrimaria = "USD";
                    codigoMoedaSecundaria = "ARS";
                    System.out.println("Conversão selecionada: ");
                    System.out.println("1. Dólar Americano em Peso Argentino\n");

                } else if (escolha == 2) {
                    codigoMoedaPrimaria = "ARS";
                    codigoMoedaSecundaria = "USD";
                    System.out.println("Conversão selecionada: ");
                    System.out.println("2. Peso Argentino em Dólar Americano\n");

                } else if (escolha == 3) {
                    codigoMoedaPrimaria = "USD";
                    codigoMoedaSecundaria = "BRL";
                    System.out.println("Conversão selecionada: ");
                    System.out.println("3. Dólar Americano em Real Brasileiro\n");

                } else if (escolha == 4) {
                    codigoMoedaPrimaria = "BRL";
                    codigoMoedaSecundaria = "USD";
                    System.out.println("Conversão selecionada: ");
                    System.out.println("4. Real Brasileiro em Dólar Americano\n");

                } else if (escolha == 5) {
                    codigoMoedaPrimaria = "USD";
                    codigoMoedaSecundaria = "COP";
                    System.out.println("Conversão selecionada: ");
                    System.out.println("5. Dólar Americano em Peso Colombiano\n");

                } else if (escolha == 6) {
                    String codigoMoedaPrimaria = "COP";
                    String codigoMoedaSecundaria = "USD";
                    System.out.println("Conversão selecionada: ");
                    System.out.println("6. Peso Colombiano em Dólar Americano\n");
                }

            } else if (escolha == 7) {

                System.out.println("------------------------------------\n");
                System.out.println("[Sys] Aguarde... Acessando histórico.\n");
                System.out.println("------------------------------------\n");

                //Acessa historico diretamente do conversor
                historicoDeConversoesLocal = conversor.getHistoricoDeConversoes();

                if (!historicoDeConversoesLocal.isEmpty()) {
                    System.out.println("\nHistórico de conversões de " + userName + ":\n");
                    for (String i : historicoDeConversoesLocal) {
                        System.out.println(i);
                    }

                } else {
                    System.out.println("[Sys error] Desculpe, parece que seu histórico está vazio...\n");
                    System.out.println("Voltando ao menu principal...\n");

                    //Feedback evitando que a quebra de linhas tire o foco do resultado
                    //System.out.print("[Sys] Digite qualquer número para continuar ou " + numeroFinal + " para sair do programa @ ");
                    //int novaEscolha = entradaMenu.nextInt();
                    //if (novaEscolha == numeroFinal) {
                    //}
                    //System.out.println("---------------------------------------------------\n");

                }

            } else if (escolha == numeroFinal) {
                return false;
            }
        }

        //todo: reestruturar codigo geral
        String conversaoAtual = this.realizarConsultaNaAPI();
        this.registrarNoHistorico(conversaoAtual);



        return true;
    }

    public String realizarConsultaNaAPI() {
        Moeda moedaPrimaria = consulta.consultarAPI(codigoMoedaPrimaria);
        conversor.setPrimeiraMoeda(moedaPrimaria);

        System.out.print("Quanto " + codigoMoedaPrimaria + " deseja converter para " + codigoMoedaSecundaria + "?\n@ ");
        double valorDeConversao = entradaMenu.nextDouble();
        entradaMenu.nextLine();
        conversor.setValorDeConversao(valorDeConversao);

        double resultado = conversor.converter(valorDeConversao, moedaPrimaria, codigoMoedaSecundaria);

        //Importando e formatando data e hora para geração de logs ao final
        LocalDateTime dataEhora = LocalDateTime.now(); // pega "agora"
        DateTimeFormatter formatacaoPadrao = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        DateTimeFormatter formatacaoSimples = DateTimeFormatter.ofPattern("dd-MM HH:mm:ss");

        String dataFormatada = String.format("[%s]", dataEhora.format(formatacaoPadrao));
        String dataEhoraFormatadas = String.format("[%s]", dataEhora.format(formatacaoSimples));

        //Exibindo resultado para o usuário - log
        System.out.println("Resultado:");
        System.out.printf(
                "%s %s %s convertidos em %s equivalem a apróx.: %.2f %s%n%n",
                dataEhoraFormatadas,
                moedaPrimaria.getQuantidade(),
                moedaPrimaria.getCodigo(),
                codigoMoedaSecundaria,
                resultado,
                codigoMoedaSecundaria
        );

        //retorno para ser adicionado ao histórico
        return String.format("%s %.2f %s ---> %.2f %s%n", dataFormatada, moedaPrimaria.getQuantidade(), moedaPrimaria.getCodigo(), resultado, codigoMoedaSecundaria);

    }

    //Registando no Histórico
    public void registrarNoHistorico(String conversaoAtualString) {
        historicoDeConversoesLocal.add(conversaoAtualString);
        System.out.println("Conversão adicionada no histórico...\n");

        //Testa registrar no historico.txt
        try {
            FileWriter escritaTXT = new FileWriter("historicoDaUltimaSessão.txt");

            //Percorre a lista escrevendo cada item como uma linha
            for (String i : historicoDeConversoesLocal) {
                escritaTXT.write(i);
            }
            escritaTXT.close();
        } catch (IOException e) {
            System.out.println("Não foi possível acessar o arquivo especificado: " + e.getMessage());
        }
    }
}