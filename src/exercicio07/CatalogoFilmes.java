package exercicio07;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CatalogoFilmes {
    private List<DadosFilme> filmes = new ArrayList<>();

    public boolean adicionar(DadosFilme filme) {
        if (filmes.stream().anyMatch(f -> f.titulo().equalsIgnoreCase(filme.titulo()))) {
            return false;
        } else {
            this.filmes.add(filme);
            return true;
        }
    }

    public void ordenarPorTitulo() {
        if (this.filmes.isEmpty()) {
            System.out.println("A lista está vazia!");
        } else {
            filmes.sort(Comparator.comparing(DadosFilme::titulo));
            System.out.println("Lista de filmes ordenada com sucesso!");
        }
    }

    public void exibirFilmes() {
        if (this.filmes.isEmpty()) {
            System.out.println("A lista está vazia!");
        } else {
            for (DadosFilme filme : filmes) {
                System.out.println(filme.titulo());
            }
        }
    }

    public int getQuantidade() {
        return this.filmes.size();
    }

    public List<DadosFilme> getFilmes() {
        return new ArrayList<>(filmes);
    }
}
