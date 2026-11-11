package exercicio02;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultarFilme {
    public DadosFilme buscar(String filme) {
        DadosFilme dadosFilme = null;
        try {
            String key = System.getenv("OMDB_API_KEY");

            if (key == null || key.isBlank()) {
                throw new IllegalStateException(
                        "A variável de ambiente OMDB_API_KEY não foi configurada."
                );
            }

            HttpClient client = HttpClient.newHttpClient();
            URI uri = URI.create("https://www.omdbapi.com/?t=" + filme.replace(" ", "+") + "&apikey=" + key);
            HttpRequest request = HttpRequest.newBuilder(uri).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status da resposta: " + response.statusCode());
            String json = response.body();

            System.out.println(json);

            if (json.contains("Error")){
                return null;
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            dadosFilme = gson.fromJson(json, DadosFilme.class);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("A requisição foi interrompida.");
        } catch (IOException e) {
            System.out.println("Erro de comunicação: " + e.getMessage());
        }
        return dadosFilme;
    }
}
