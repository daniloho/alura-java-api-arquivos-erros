package desafio;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConsultarCotacao {
    private final HttpClient client = HttpClient.newHttpClient();

    public Cotacao buscar(String codigo) {
        validarCodigo(codigo);

        String json = respostaJson(codigo + "-BRL");
        String chave = codigo.toUpperCase() + "BRL";
        JsonObject objetoJson = JsonParser.parseString(json).getAsJsonObject();

        Gson gson = new Gson();

        DadosCotacao dadosCotacao = gson.fromJson(objetoJson.get(chave), DadosCotacao.class);

        return new Cotacao(dadosCotacao);

    }

    public Map<String, Cotacao> buscarVarias(List<String> codigos) {
        for (String codigo : codigos) {
            validarCodigo(codigo);
        }
        String codigosFormatados = codigos.stream()
                .map(c -> c + "-BRL")
                .collect(Collectors.joining(","));

        String json;
        try {
            json = respostaJson(codigosFormatados);
        } catch (MoedaNaoEncontradaException e) {
            throw new MoedaNaoEncontradaException("Uma ou mais moedas da lista informada não foram encontradas ou são inválidas: " + codigos);
        }
        JsonObject objetoJson = JsonParser.parseString(json).getAsJsonObject();
        Gson gson = new Gson();
        Map<String, Cotacao> cotacoes = new HashMap<>();

        for (Map.Entry<String, JsonElement> entrada : objetoJson.entrySet()) {
            String chaveJson = entrada.getKey();

            String moedaChave = chaveJson.replace("BRL", "");
            DadosCotacao dadosCotacao = gson.fromJson(entrada.getValue(), DadosCotacao.class);
            Cotacao cotacao = new Cotacao(dadosCotacao);

            cotacoes.put(moedaChave, cotacao);
        }

        return cotacoes;
    }

    private void validarCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("O código da moeda não pode ser nulo ou vazio.");
        }

        boolean apenasLetras = codigo.matches("[a-zA-Z]+");

        if (!apenasLetras) {
            throw new IllegalArgumentException("O código da moeda só pode conter letras.");
        }

        if (codigo.length() != 3) {
            throw new IllegalArgumentException("O código precisa ter exatamente 3 caracteres");
        }
    }

    private String respostaJson(String codigo) {
        try {
            URI uri = URI.create("https://economia.awesomeapi.com.br/json/last/" + codigo.toUpperCase());
            HttpRequest request = HttpRequest.newBuilder(uri).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 404) {
                throw new MoedaNaoEncontradaException("O código da moeda informada não foi encontrada ou é inválida");
            }
            if (response.statusCode() != 200) {
                throw new ErroDeConsultaException("A API de Cotações respondeu com status " + response.statusCode());
            }
            return response.body();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ErroDeConsultaException("A requisição foi interrompida", e);
        } catch (IOException e) {
            throw new ErroDeConsultaException("Não foi possível conectar à API de Cotações", e);
        }
    }
}

