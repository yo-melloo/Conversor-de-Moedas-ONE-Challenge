package br.com.mello.conversor.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ExchangeRateAPI {
    //Importando Chave da Api para a IDE
    private final String chaveDaApi = System.getenv("API_KEY");

    public Moeda consultarAPI(String codigoMoedaPrincipal){

        //Preparando API
        System.out.println("[Sys] (Realizando pesquisa para: " + codigoMoedaPrincipal + ")\n");

        //Montando URI
        String uriDaApi = String.format("https://v6.exchangerate-api.com/v6/%s/latest/%s", chaveDaApi, codigoMoedaPrincipal);

        //Montando Gson
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        //Requisição
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
            MoedaApi moedaRespostaDaAPI = gson.fromJson(jsonResponse, MoedaApi.class);
            System.out.println("--------------------------------------\n");
            return new Moeda(moedaRespostaDaAPI);
        }
        //em caso de exceção
        catch (NullPointerException e) {
            System.out.println("[Sys error] Ocorreu um problema ao tentar pesquisar pelo código de moeda: " + codigoMoedaPrincipal);
            System.out.println("O código de moeda é uma abreviação de 3 letras (ex.: Real Brasileiro = BRL)");
            System.out.println("Voltando para o menu principal...\n");
            return null;
        } catch (IllegalStateException e) {
            System.out.println("Houve um retorno inesperado por parte da aplicação. Tente novamente.");
            System.out.println("Voltando para o menu principal...\n");
            return null;
        }catch (Exception e) {
            //System.out.println(e);
            System.out.println("Voltando para o menu principal...\n");
            return null;
        }
    }
}