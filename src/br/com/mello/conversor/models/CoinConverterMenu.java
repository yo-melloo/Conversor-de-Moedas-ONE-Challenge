package br.com.mello.conversor.models;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CoinConverterMenu {
    private final ConversorDeMoedas conversor = new ConversorDeMoedas();
    private final Scanner entradaMenu = new Scanner(System.in);
    private String codigoMoedaPrimaria = "";
    private String codigoMoedaSecundaria = "";

    //Ao inicializar o menu
    //Obtem o nome do PC para o definir como nome de usuário
    public CoinConverterMenu(){
    }

    public boolean iniciarMenu() {
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
                        7) Outras moedas.
                        8) Acessar histórico.
                        9) Sair.
                        
                        """
        );

        //Numero definido para encerrar o loop
        int numeroFinal = 9;

        //Entrada do usuário
        int escolha = 0;
        System.out.print("Escolha uma opção: @ ");

        try {
            escolha = entradaMenu.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida");
            System.out.println("Tente novamente.");
        }

        entradaMenu.nextLine();
        System.out.println("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n");

        if (escolha == numeroFinal) {
            return false;
        } else if (escolha == 0) {
            return true;
        } else if (escolha <= 7) {
            boolean conversaoPersonalizada = false;
            //Determina moedas a serem convertidas
            //-- se a escolha estiver entre as opçoes de conversão
            //-- configura as moedas para prosseguir para o conversor
            if (escolha == 1) {
                codigoMoedaPrimaria = "USD";
                codigoMoedaSecundaria = "ARS";
                System.out.println("Conversão selecionada: ");
                System.out.println(escolha + ". Dólar Americano em Peso Argentino\n");

            } else if (escolha == 2) {
                codigoMoedaPrimaria = "ARS";
                codigoMoedaSecundaria = "USD";
                System.out.println("Conversão selecionada: ");
                System.out.println(escolha + ". Peso Argentino em Dólar Americano\n");

            } else if (escolha == 3) {
                codigoMoedaPrimaria = "USD";
                codigoMoedaSecundaria = "BRL";
                System.out.println("Conversão selecionada: ");
                System.out.println(escolha + ". Dólar Americano em Real Brasileiro\n");

            } else if (escolha == 4) {
                codigoMoedaPrimaria = "BRL";
                codigoMoedaSecundaria = "USD";
                System.out.println("Conversão selecionada: ");
                System.out.println(escolha + ". Real Brasileiro em Dólar Americano\n");

            } else if (escolha == 5) {
                codigoMoedaPrimaria = "USD";
                codigoMoedaSecundaria = "COP";
                System.out.println("Conversão selecionada: ");
                System.out.println(escolha + ". Dólar Americano em Peso Colombiano\n");

            } else if (escolha == 6) {
                codigoMoedaPrimaria = "COP";
                codigoMoedaSecundaria = "USD";
                System.out.println("Conversão selecionada: ");
                System.out.println(escolha + ". Peso Colombiano em Dólar Americano\n");

            } else if (escolha == 7) {
                System.out.println("Conversão selecionada: ");
                System.out.println("7. Outras moedas (Conversão personalizada).");
                System.out.print("Qual o código da primeira moeda?\n3 letras @ ");
                codigoMoedaPrimaria = entradaMenu.nextLine();
                conversaoPersonalizada = true;
            }
            System.out.println("--------------------------------------\n");
            //Prossegue para a API
            conversor.realizarConsultaNaAPI(codigoMoedaPrimaria, codigoMoedaSecundaria, conversaoPersonalizada);
            conversor.converter();

        } else if (escolha == 8) {

            System.out.println("------------------------------------\n");
            System.out.println("[Sys] Aguarde... Acessando histórico.\n");
            System.out.println("------------------------------------\n");

            //Acessa historico diretamente do conversor

            if (!conversor.getHistoricoDeConversoes().isEmpty()) {
                System.out.println("\nHistórico de conversões de " + conversor.getUserName() + ":\n");
                int is = 1;
                for (String i : conversor.getHistoricoDeConversoes()) {
                    System.out.print(is + ". " + i);
                    is++;
                }
                System.out.println("\n");

            } else {
                System.out.println("[Sys error] Desculpe, parece que seu histórico está vazio...\n");
                System.out.println("Voltando ao menu principal...\n");
            }
        }
        return true;
    }

}