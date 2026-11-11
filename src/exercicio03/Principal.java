package exercicio03;

import java.util.Scanner;

public class Principal {
    static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            ConsultarFilme consultarFilme = new ConsultarFilme();

            System.out.print("Digite o nome de um filme: ");
            String filme = sc.nextLine();

            try {
                DadosFilme filmeEncontrado = consultarFilme.buscar(filme);

                if (filmeEncontrado == null) {
                    System.out.println("Não foi possível realizar a consulta.");
                    return;
                }
                System.out.printf("""
                        Título: %s
                        Ano: %s
                        Diretor: %s
                        Duração: %s
                        Nota IMDb: %s
                        """, filmeEncontrado.titulo(), filmeEncontrado.ano(), filmeEncontrado.diretor(), filmeEncontrado.duracao(), filmeEncontrado.nota());

            } catch (FilmeNaoEncontradoException e) {
                System.out.println("Filme não encontrado: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Entrada inválida: " + e.getMessage());
            }
        }


    }
}
