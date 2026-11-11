package exercicio07;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GeradorArquivo {
    public void salvar(List<DadosFilme> filmes, String nomeArquivo) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter fw = new FileWriter(nomeArquivo)) {
            gson.toJson(filmes, fw);
        }
    }

    public List<DadosFilme> carregar(String nomeArquivo) throws IOException {
        try (FileReader fr = new FileReader(nomeArquivo)) {
            Gson gson = new Gson();
            List<DadosFilme> filmesArquivo = gson.fromJson(fr, new TypeToken<List<DadosFilme>>() {
            }.getType());
            List<DadosFilme> filmes =  new ArrayList<>();
            if(filmesArquivo != null){
                filmes.addAll(filmesArquivo);
            }
            return filmes;
        }
    }
}
