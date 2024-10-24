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

    public Moeda consultarAPI(String codigoMoeda){

        //Preparando API
        System.out.println("Realizando pesquisa para: " + codigoMoeda);

        //Montando URI
        String uriDaApi = String.format("https://v6.exchangerate-api.com/v6/%s/latest/%s", chaveDaApi, codigoMoeda);

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
            return new Moeda(moedaRespostaDaAPI);
        }
        //em caso de exceção
        catch (Exception e) {
            //exibir mensagem de exceção
            System.out.println("Erro ao acessar a API: " + e.getMessage());
            System.out.println(e.getLocalizedMessage());
            //-- A primeira vista não ocorre nenhuma exceção na aplicação até o momento
            //-- é estranho? pra caramba.
            return null;
        }
    }
}