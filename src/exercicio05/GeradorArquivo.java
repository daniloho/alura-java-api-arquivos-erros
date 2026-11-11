package exercicio05;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class GeradorArquivo {
    public void salvar(List<DadosFilme> filmes, String nomeArquivo) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter fw = new FileWriter(nomeArquivo)) {
            gson.toJson(filmes, fw);
        }
    }
}
