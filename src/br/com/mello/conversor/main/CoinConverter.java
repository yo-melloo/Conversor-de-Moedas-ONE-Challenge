package br.com.mello.conversor.main;

import br.com.mello.conversor.models.Moeda;
import br.com.mello.conversor.models.MoedaApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLOutput;

public class CoinConverter {
    public static void main(String[] args) throws IOException, InterruptedException {
        String chaveDaApi = System.getenv("API_KEY");
        String moedaPesquisada = "USD";
        String uriDaApi = String.format("https://v6.exchangerate-api.com/v6/%s/latest/%s", chaveDaApi, moedaPesquisada);
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        try{
            System.out.println("[Sys] Moeda pesquisada: " + moedaPesquisada);
//            System.out.println("Valor a ser convertido " + valorParaConverter);
            System.out.println("[Sys] Iniciando requisição");
            System.out.println("[Sys] URI: " + uriDaApi);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uriDaApi))
                    .build();

            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            String jsonResponse = response.body();

            MoedaApi moedaApi = gson.fromJson(jsonResponse, MoedaApi.class);
            Moeda moeda = new Moeda(moedaApi);

            System.out.println(moeda);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("[Sys] Finalizando programa...");
        }

    }

}

