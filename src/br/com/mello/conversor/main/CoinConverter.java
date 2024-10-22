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

public class CoinConverter {
    public static void main(String[] args) throws IOException, InterruptedException {
        //Importando Chave da Api para a IDE
        String chaveDaApi = System.getenv("API_KEY");

        //Código da moeda a ser pesquisada
        String moedaPesquisada = "USD";
        //Código da moeda a ser pesquisada (2)
        String moedaBRL = "BRL";

        //Montando URI
        String uriDaApi = String.format("https://v6.exchangerate-api.com/v6/%s/latest/%s", chaveDaApi, moedaPesquisada);
        String uriDaApi2 = String.format("https://v6.exchangerate-api.com/v6/%s/latest/%s", chaveDaApi, moedaBRL);

        //Montando Gson
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        //Montando conversor como Objeto
        ConversorDeMoedas conversor = new ConversorDeMoedas();

        try{
            System.out.println("[Sys] Moeda pesquisada: " + moedaPesquisada);
//            System.out.println("Valor a ser convertido " + valorParaConverter);
            System.out.println("[Sys] Iniciando requisição");
            System.out.println("[Sys] URI: " + uriDaApi);

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
            System.out.println(moeda);

            System.out.println("[Sys] Moeda para multiplicar: " + moedaBRL);
//            System.out.println("Valor a ser convertido " + valorParaConverter);
            System.out.println("[Sys] Iniciando nova requisição");
            System.out.println("[Sys] URI: " + uriDaApi2);


//////////////////////// Inicializando NOVA requisição///////////////////////////////////////////////////
////////////////////// CODIGO INTENCIONALMENTE ERRÔNEO//////////////////////////////////////////////////
////////////////////// DESVENDANDO A LÓGICA NECESSÀRIA PARA CHEGAR NO RESUTLADO ESPERADO///////////////
////////////////////// E DEU CERTO////////////////////////////////////////////////////////////////////
/////////////////////// DELETANDO NO PRÓXIMO COMMIT /////////////////////////////////////////////////
            HttpRequest novarequest = HttpRequest.newBuilder()
                    .uri(URI.create(uriDaApi2))
                    .build();

            //Moldando o recebimento da NOVA resposta do cliente consultado pela API
            HttpResponse<String> novaresponse = client
                    .send(novarequest, HttpResponse.BodyHandlers.ofString());
            String novajsonResponse = novaresponse.body();
            MoedaApi moedaApiNova = gson.fromJson(novajsonResponse, MoedaApi.class);
            Moeda novaMoeda = new Moeda(moedaApiNova);
            System.out.println(novaMoeda);

/////////////////////////////////////////////////////////////////////////////////////////////////

            //Utiliznado o conversor para estabelecer e converter as moedas
            moeda.setQuantidade(10); // --> Primeiro se define a quantidade a ser convertida em x:1
            conversor.adicionarMoedas(moeda, novaMoeda); // --> Valores atualizados 'entrando no conversor'
            double valorConvertido = conversor.converter(); // --> recebe o retorno da conversão

            //Exibindo resultado
            System.out.println(conversor);
            System.out.println(moeda.getQuantidade() + " " + moeda.getCodigo() + " convertidos em " + novaMoeda.getCodigo() + " = " + valorConvertido);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("[Sys] Finalizando programa...");
        }

    }

}

