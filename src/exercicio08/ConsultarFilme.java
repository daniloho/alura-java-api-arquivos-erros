package exercicio08;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultarFilme {
    private final String key;

    public ConsultarFilme() {
        this.key = System.getenv("OMDB_API_KEY");

        if (key == null || key.isBlank()) {
            throw new IllegalStateException(
                    "A variável de ambiente OMDB_API_KEY não foi configurada."
            );
        }
    }

    public DadosFilme buscar(String filme) {
        if (filme == null || filme.isBlank()) {
            throw new IllegalArgumentException("O título do filme não pode ser vazio.");
        }

        try {
            HttpClient client = HttpClient.newHttpClient();
            URI uri = URI.create("https://www.omdbapi.com/?t=" + filme.replace(" ", "+") + "&apikey=" + key);
            HttpRequest request = HttpRequest.newBuilder(uri).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new ErroDeConsultaException("A OMDb respondeu com status " + response.statusCode());
            }

            String json = response.body();

            JsonObject objetoJson = JsonParser.parseString(json).getAsJsonObject();

            if (objetoJson.get("Response").getAsString().equals("False")) {
                String mensagem = objetoJson.get("Error").getAsString();
                throw new FilmeNaoEncontradoException(mensagem);
            }

            Gson gson = new Gson();

            return gson.fromJson(json, DadosFilme.class);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ErroDeConsultaException("A requisição foi interrompida", e);
        } catch (IOException e) {
            throw new ErroDeConsultaException("Não foi possível conectar à OMDb", e);
        }
    }
}
