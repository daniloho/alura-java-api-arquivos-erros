package exercicio01;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultarFilme {
    public String buscar(String filme) {
        String json = "";
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
            System.out.println("HTTP da resposta: " + response.statusCode());
            json = response.body();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("A requisição foi interrompida.");
        } catch (IOException e) {
            System.out.println("Erro de comunicação: " + e.getMessage());
        }
        return json;
    }
}
