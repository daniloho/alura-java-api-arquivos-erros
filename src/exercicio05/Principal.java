package exercicio05;

import java.io.IOException;
import java.util.Scanner;

public class Principal {
    static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            CatalogoFilmes catalogo = new CatalogoFilmes();
            ConsultarFilme consultarFilme = new ConsultarFilme();


            while (true) {
                System.out.print("Digite o nome de um filme ou 'sair' para encerrar: ");
                String filme = sc.nextLine();
                if (filme.equalsIgnoreCase("sair")) {
                    break;
                }
                try {
                    DadosFilme filmeEncontrado = consultarFilme.buscar(filme);

                    if (filmeEncontrado == null) {
                        System.out.println("Não foi possível realizar a consulta.");
                        continue;
                    }

                    catalogo.adicionar(filmeEncontrado);
                    System.out.println("Filme adicionado: " + filmeEncontrado.titulo());

                } catch (FilmeNaoEncontradoException e) {
                    System.out.println("Filme não encontrado: " + e.getMessage());

                } catch (IllegalArgumentException e) {
                    System.out.println("Entrada inválida: " + e.getMessage());
                } catch (IllegalStateException e) {
                    System.out.println("Erro de configuração: " + e.getMessage());
                    break;
                }
            }
            System.out.println("\nLista não ordenada");
            catalogo.exibirFilmes();

            catalogo.ordenarPorTitulo();

            System.out.println("\nLista ordenada");
            catalogo.exibirFilmes();

            System.out.println("Total de filmes no catálogo: " + catalogo.getQuantidade());

            if (catalogo.getQuantidade() > 0) {
                GeradorArquivo geradorArquivo = new GeradorArquivo();
                try {

                    geradorArquivo.salvar(catalogo.getFilmes(), "filmes.json");
                    System.out.println("Arquivo salvo com sucesso!");
                } catch (IOException e) {
                    System.out.println("Erro ao salvar arquivo: " + e.getMessage());
                }
            }

            System.out.println("Programa finalizado!");
        }

    }
}

