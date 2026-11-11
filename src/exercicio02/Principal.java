package exercicio02;


import java.util.Scanner;

public class Principal {
    static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ConsultarFilme consultarFilme = new ConsultarFilme();

        System.out.print("Digite o nome de um filme: ");
        String filme = sc.nextLine();

        DadosFilme filmeEncontrado = consultarFilme.buscar(filme);

       if(filmeEncontrado == null){
           System.out.println("Filme não encontrado");
           sc.close();
           return;
       }

        System.out.printf("""
                Título: %s
                Ano: %s
                Diretor: %s
                Duração: %s
                Nota IMDb: %s
                """, filmeEncontrado.titulo(), filmeEncontrado.ano(), filmeEncontrado.diretor(), filmeEncontrado.duracao(),filmeEncontrado.nota());

        sc.close();

    }
}
