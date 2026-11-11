package exercicio01;

import java.util.Scanner;

public class Principal {
    static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ConsultarFilme consultarFilme = new ConsultarFilme();

        System.out.print("Digite o nome de um filme: ");
        String filme = sc.nextLine();

        String filmeEncontrado = consultarFilme.buscar(filme);

        System.out.println("Filme encontrado: " + filmeEncontrado);

        sc.close();

    }
}
