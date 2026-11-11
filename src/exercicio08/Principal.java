package exercicio08;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Principal {
    static void main(String[] args) {
        ConsultarFilme consultarFilme;
        try {
            consultarFilme = new ConsultarFilme();
        } catch (IllegalStateException e) {
            System.out.println("Erro de configuração: " + e.getMessage());
            return;
        }
        try (Scanner sc = new Scanner(System.in)) {
            CatalogoFilmes catalogo = new CatalogoFilmes();
            GeradorArquivo geradorArquivo = new GeradorArquivo();
            try {
                List<DadosFilme> filmesArquivo = geradorArquivo.carregar("filmes.json");
                for (DadosFilme filme : filmesArquivo) {
                    catalogo.adicionar(filme);
                }
                System.out.printf("%d filme(s) carregado(s) do arquivo.%n", catalogo.getQuantidade());
            } catch (FileNotFoundException e) {
                System.out.println("Nenhum catálogo salvo. Começando do zero.");
            } catch (IOException e) {
                System.out.println("Erro ao carregar arquivo: " + e.getMessage());
            }

            while (true) {
                System.out.print("Digite o nome de um filme ou 'sair' para encerrar: ");
                String filme = sc.nextLine();
                if (filme.equalsIgnoreCase("sair")) {
                    break;
                }
                try {
                    DadosFilme filmeEncontrado = consultarFilme.buscar(filme);

                    if (catalogo.adicionar(filmeEncontrado)) {
                        System.out.println("Filme adicionado: " + filmeEncontrado.titulo());
                    } else {
                        System.out.println("Esse filme já está no catálogo.");
                    }

                } catch (FilmeNaoEncontradoException e) {
                    System.out.println("Filme não encontrado: " + e.getMessage());
                } catch (IllegalArgumentException e) {
                    System.out.println("Entrada inválida: " + e.getMessage());
                } catch (ErroDeConsultaException e) {
                    System.out.println("Erro na consulta: " + e.getMessage());
                }
            }
            System.out.println("\nLista não ordenada");
            catalogo.exibirFilmes();

            catalogo.ordenarPorTitulo();

            System.out.println("\nLista ordenada");
            catalogo.exibirFilmes();

            System.out.println("Total de filmes no catálogo: " + catalogo.getQuantidade());

            if (catalogo.getQuantidade() > 0) {
                try {
                    geradorArquivo.salvar(catalogo.getFilmes(), "filmes.json");
                    System.out.println("Arquivo salvo com sucesso!");
                } catch (IOException e) {
                    System.out.println("Erro ao salvar arquivo: " + e.getMessage());
                }
                List<Filme> filmes = new ArrayList<>();
                for (DadosFilme filme : catalogo.getFilmes()) {
                    try {
                        Filme novoFilme = new Filme(filme);
                        filmes.add(novoFilme);
                    } catch (ErroDeConversaoException e) {
                        System.out.println("Erro ao converter filme: " + e.getMessage());
                    }
                }
                Collections.sort(filmes);
                System.out.println("=== Ranking por nota ===");
                for (int i = 0; i < filmes.size(); i++) {
                    System.out.printf("%dº %s%n", i + 1, filmes.get(i));
                }
            }

            System.out.println("Programa finalizado!");
        }
    }
}

