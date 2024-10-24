package br.com.mello.conversor.models;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class CoinConverterMenu {
    private final ConversorDeMoedas conversor = new ConversorDeMoedas();
    private final Scanner entradaMenu = new Scanner(System.in);
    private String userName;
    private String codigoMoedaPrimaria = "";
    private String codigoMoedaSecundaria = "";

    //Ao inicializar o menu
    //Obtem o nome do PC para o definir como nome de usuário
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

    public boolean iniciarMenu() {
        //Numero definido para encerrar o loop
        int numeroFinal = 8;

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

        //Entrada do usuário
        System.out.print("Escolha uma opção: @ ");
        int escolha = entradaMenu.nextInt();
        entradaMenu.nextLine();
        System.out.println("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n");

        if (escolha == numeroFinal) {
            return false;

            //Determina moedas a serem convertidas
            //-- se a escolha estiver entre as opçoes de conversão
            //-- configura as moedas para prosseguir para o conversor
        } else if (escolha <= 6) {
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
                codigoMoedaPrimaria = "COP";
                codigoMoedaSecundaria = "USD";
                System.out.println("Conversão selecionada: ");
                System.out.println("6. Peso Colombiano em Dólar Americano\n");
            }

            //Prossegue para a API
            conversor.realizarConsultaNaAPI(codigoMoedaPrimaria,codigoMoedaSecundaria);
            //return true;
        } else if (escolha == 7) {

            System.out.println("------------------------------------\n");
            System.out.println("[Sys] Aguarde... Acessando histórico.\n");
            System.out.println("------------------------------------\n");

            //Acessa historico diretamente do conversor

            if (!conversor.getHistoricoDeConversoes().isEmpty()) {
                System.out.println("\nHistórico de conversões de " + userName + ":\n");
                for (String i : conversor.getHistoricoDeConversoes()) {
                    System.out.println(i);
                }

            } else {
                System.out.println("[Sys error] Desculpe, parece que seu histórico está vazio...\n");
                System.out.println("Voltando ao menu principal...\n");
            }
        } else{
            System.out.println("Erro: Opção inválida.\n");
        }
        return true;
    }

}