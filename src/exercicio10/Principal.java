package exercicio10;

import java.util.*;

public class Principal {
    static void main(String[] args) {

        ConsultarCotacao consultarCotacao = new ConsultarCotacao();
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {

                try {
                    System.out.print("Digite os códigos separados por vírgula ou 'sair' para encerrar: ");
                    String entrada = sc.nextLine();
                    if (entrada.equalsIgnoreCase("sair")) break;

                    String[] listaEntradas = entrada.split(",");
                    List<String> codigos = new ArrayList<>();
                    for (String codigo : listaEntradas) {
                        String codigoSemEspaco = codigo.trim();
                        if (codigoSemEspaco.isBlank()) continue;
                        codigos.add(codigoSemEspaco);
                    }
                    if (codigos.isEmpty()) {
                        throw new IllegalArgumentException("Nenhum código informado.");
                    }
                    Map<String, Cotacao> cotacoes = consultarCotacao.buscarVarias(codigos);
                    List<Cotacao> cotacoesList = new ArrayList<>(cotacoes.values());
                    cotacoesList.sort(Comparator.comparingDouble(Cotacao::getVariacao).reversed());
                    System.out.println("=== Cotações por variação ===");
                    for (Cotacao cotacao : cotacoesList) {
                        System.out.printf("%s - %s | Compra: R$ %,.2f | Variação: %.2f%%%n",cotacao.getCodigo(), cotacao.getNome(), cotacao.getPrecoCompra(), cotacao.getVariacao());
                    }
                    Cotacao cotacaoMaior = cotacoesList.getFirst();
                    Cotacao cotacaoMenor = cotacoesList.getLast();

                    System.out.printf("Maior alta: %s (%.2f%%)%n", cotacaoMaior.getCodigo(), cotacaoMaior.getVariacao());
                    System.out.printf("Maior queda: %s (%.2f%%)%n", cotacaoMenor.getCodigo(), cotacaoMenor.getVariacao());

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
            System.out.println("Programa finalizado!");
        }



    }
}
