package exercicio09;

import java.util.Scanner;

public class Principal {
    static void main(String[] args) {

        ConsultarCotacao consultarCotacao = new ConsultarCotacao();


        try (Scanner sc = new Scanner(System.in)) {

            while (true) {
                try {
                    System.out.print("Digite o código da moeda ou 'sair' para encerrar: ");
                    String codigo = sc.nextLine();
                    if (codigo.equalsIgnoreCase("sair")) break;
                    DadosCotacao dadosCotacao = consultarCotacao.buscar(codigo);
                    Cotacao cotacao = new Cotacao(dadosCotacao);
                    System.out.println(cotacao);
                } catch (IllegalArgumentException e) {
                    System.out.println("Entrada inválida: " + e.getMessage());
                } catch (MoedaNaoEncontradaException e) {
                    System.out.println("Moeda não encontrada: " + e.getMessage());
                } catch (ErroDeConsultaException e) {
                    System.out.println("Erro na consulta: " + e.getMessage());
                } catch (ErroDeConversaoException e) {
                    System.out.println("Erro na conversao: " + e.getMessage());
                }
            }
        }


    }
}
