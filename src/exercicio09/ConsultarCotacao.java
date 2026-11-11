package exercicio09;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultarCotacao {

    public DadosCotacao buscar(String codigo) {

        if(codigo == null || codigo.isBlank()){
            throw new IllegalArgumentException("O código da moeda não pode ser nulo ou vazio.");
        }

        boolean apenasLetras = codigo.matches("[a-zA-Z]+");

        if(!apenasLetras){
            throw new IllegalArgumentException("O código da moeda só pode conter letras.");
        }

        if (codigo.length() != 3) {
            throw new IllegalArgumentException("O código precisa ter exatamente 3 caracteres");
        }
        try {
            HttpClient client = HttpClient.newHttpClient();
            URI uri = URI.create("https://economia.awesomeapi.com.br/json/last/" + codigo.toUpperCase() + "-BRL");
            HttpRequest request = HttpRequest.newBuilder(uri).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 404) {
                throw new MoedaNaoEncontradaException("O código da moeda informada não foi encontrada ou é inválida");
            }
            if (response.statusCode() != 200) {
                throw new ErroDeConsultaException("A API de Cotações respondeu com status " + response.statusCode());
            }
            String json = response.body();
            String chave = codigo.toUpperCase() + "BRL";
            JsonObject objetoJson = JsonParser.parseString(json).getAsJsonObject();


            Gson gson = new Gson();


            return gson.fromJson(objetoJson.get(chave), DadosCotacao.class);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ErroDeConsultaException("A requisição foi interrompida", e);
        } catch (IOException e) {
            throw new ErroDeConsultaException("Não foi possível conectar à API de Cotações", e);
        }


    }
}