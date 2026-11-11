package desafio;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoricoConversoes {
    private final List<Conversao> conversoes;
    private final String nomeArquivo = "historico.json";

    public HistoricoConversoes() {
        this.conversoes = new ArrayList<>();
    }

    public void carregar() throws IOException {
        Path arquivo = Path.of(nomeArquivo);
        if (!Files.exists(arquivo)) {
            return;
        }
        try (FileReader fr = new FileReader(nomeArquivo)) {
            Gson gson = new Gson();
            List<Conversao> conversoesSalvas = gson.fromJson(fr, new TypeToken<List<Conversao>>() {
            }.getType());
            if (conversoesSalvas != null) {
                this.conversoes.addAll(conversoesSalvas);
            }
        } catch (JsonParseException e) {
            Path backup = Path.of(nomeArquivo + ".bak");
            Files.copy(arquivo, backup, StandardCopyOption.REPLACE_EXISTING);
            throw new IOException("O histórico estava corrompido. Uma cópia foi salva em " + backup, e);
        }
    }

    private void salvar() throws IOException {
        try (FileWriter fw = new FileWriter(nomeArquivo)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(conversoes, fw);
        }
    }

    public Map.Entry<String, Integer> maisConsultada() {
        if (conversoes.isEmpty()) {
            return null;
        }
        Map<String, Integer> codigos = new HashMap<>();
        for (Conversao conversao : conversoes) {
            String codigo;
            if ("BRL".equals(conversao.moedaOrigem())) {
                codigo = conversao.moedaDestino();
            } else {
                codigo = conversao.moedaOrigem();
            }
            codigos.put(codigo, codigos.getOrDefault(codigo, 0) + 1);
        }
        Map.Entry<String, Integer> maior = null;
        for (Map.Entry<String, Integer> item : codigos.entrySet()) {
            if (maior == null || item.getValue() > maior.getValue()) {
                maior = item;
            }
        }
        return maior;
    }

    public void adicionar(Conversao conversao) throws IOException {
        this.conversoes.add(conversao);
        salvar();
    }

    public List<Conversao> getConversoes() {
        return List.copyOf(this.conversoes);
    }
}
