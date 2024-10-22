package br.com.mello.conversor.main;

import br.com.mello.conversor.models.ConversorDeMoedas;
import br.com.mello.conversor.models.Moeda;
import br.com.mello.conversor.models.MoedaApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class CoinConverter {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner entrada = new Scanner(System.in);
        String codigoMoedaPrimaria = "";
        String codigoMoedaSecundaria = "";

        //Importando Chave da Api para a IDE
        String chaveDaApi = System.getenv("API_KEY");

        while (true) {

            System.out.print("""
                    @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                        CONVERSOR DE MOEDAS
                        by @Mello
                    
                    1) Dólar Americano em Peso Argentino
                    2) Peso Argentino em Dólar Americano
                    3) Dólar Americano em Real Brasileiro
                    4) Real Brasileiro em Dólar Americano
                    5) Dólar Americano em Peso Colombiano
                    6) Peso Colombiano em Dólar Americano
                    7) Sair.
                    """);
            System.out.print("Escolha uma opção: @ ");
            int escolha = entrada.nextInt();
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n");

            if (escolha == 1) {
                //Código das moedas a serem pesquisadas
                codigoMoedaPrimaria = "USD";
                codigoMoedaSecundaria = "ARS";
                System.out.println("Conversão selecionada: ");
                System.out.println("1. Dólar Americano em Peso Argentino\n");
            } else if (escolha == 2) {
                //Código das moedas a serem pesquisadas
                codigoMoedaPrimaria = "ARS";
                codigoMoedaSecundaria = "USD";
                System.out.println("Conversão selecionada: ");
                System.out.println("2. Peso Argentino em Dólar Americano\n");
            } else if (escolha == 3) {
                //Código das moedas a serem pesquisadas
                codigoMoedaPrimaria = "USD";
                codigoMoedaSecundaria = "BRL";
                System.out.println("Conversão selecionada: ");
                System.out.println("3. Dólar Americano em Real Brasileiro\n");
            } else if (escolha == 4) {
                //Código das moedas a serem pesquisadas
                codigoMoedaPrimaria = "BRL";
                codigoMoedaSecundaria = "USD";
                System.out.println("Conversão selecionada: ");
                System.out.println("4. Real Brasileiro em Dólar Americano\n");
            } else if (escolha == 5) {
                //Código das moedas a serem pesquisadas
                codigoMoedaPrimaria = "USD";
                codigoMoedaSecundaria = "COP";
                System.out.println("Conversão selecionada: ");
                System.out.println("5. Dólar Americano em Peso Colombiano\n");
            } else if (escolha == 6) {
                //Código das moedas a serem pesquisadas
                codigoMoedaPrimaria = "COP";
                codigoMoedaSecundaria = "USD";
                System.out.println("Conversão selecionada: ");
                System.out.println("6. Peso Colombiano em Dólar Americano\n");
            } else if (escolha == 7) {
                break;
            }
            entrada.nextLine();

            System.out.print("Quanto " + codigoMoedaPrimaria + " deseja converter para " + codigoMoedaSecundaria + "?\n@ ");
            int quantidade = entrada.nextInt();

            System.out.println("---------------------------------------------------\n");

            //Montando URI
            String uriDaApi = String.format("https://v6.exchangerate-api.com/v6/%s/latest/%s", chaveDaApi, codigoMoedaPrimaria);

            //Montando Gson
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

            //Montando conversor como Objeto
            ConversorDeMoedas conversor = new ConversorDeMoedas();

            try {
                //Inicializando requisição
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(uriDaApi))
                        .build();

                //Moldando o recebimento da resposta do cliente consultado pela API
                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());
                String jsonResponse = response.body();
                MoedaApi moedaApi = gson.fromJson(jsonResponse, MoedaApi.class);
                Moeda moeda = new Moeda(moedaApi);

                //Utiliznado o conversor para estabelecer e converter as moedas
                moeda.setQuantidade(quantidade); // --> Primeiro se define a quantidade a ser convertida em x:1
                conversor.adicionarMoedas(moeda, codigoMoedaSecundaria); // --> Valores atualizados 'entrando no conversor'
                double valorConvertido = conversor.converter(); // --> recebe o retorno da conversão

                //Exibindo resultado
                System.out.println(conversor);
                System.out.println(moeda.getQuantidade() + " " + moeda.getCodigo() + " equivalem a: " + valorConvertido + " " + codigoMoedaSecundaria + ".\n");

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        
        System.out.println("[Sys] Finalizando programa...");
    }
}



