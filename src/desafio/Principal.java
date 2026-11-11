package desafio;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BiFunction;

public class Principal {
    static void main(String[] args) {

        ConversorMoedas conversorMoedas = new ConversorMoedas();
        HistoricoConversoes historicoConversoes = new HistoricoConversoes();
        RegistroLog registroLog = new RegistroLog();

        try {
            historicoConversoes.carregar();
        } catch (IOException e) {
            System.out.println("Aviso: " + e.getMessage());
        }

        boolean executando = true;
        try (Scanner sc = new Scanner(System.in)) {
            while (executando) {
                try {
                    System.out.print("""
                            ===== CONVERSOR DE MOEDAS =====
                            1 - Converter de Reais para outra moeda
                            2 - Converter de outra moeda para Reais
                            3 - Ver histórico de conversões
                            4 - Ver moeda mais consultada
                            5 - Sair
                            """);
                    System.out.print("Escolha uma opção: ");

                    String opcao = sc.nextLine();
                    int opcaoInt;

                    try {
                        opcaoInt = Integer.parseInt(opcao.trim());
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("A opção deve ser um número e não pode ser vazia!");
                    }

                    switch (opcaoInt) {
                        case 1:
                            realizarConversao(sc, conversorMoedas::converterDeReais, historicoConversoes, registroLog);
                            break;
                        case 2:
                            realizarConversao(sc, conversorMoedas::converterParaReais, historicoConversoes, registroLog);
                            break;
                        case 3:
                            List<Conversao> listaConversoes = historicoConversoes.getConversoes();
                            if (listaConversoes.isEmpty()) {
                                System.out.println("Nenhuma conversão realizada ainda.");
                            } else {
                                for (int i = 0; i < listaConversoes.size(); i++) {
                                    Conversao conversao = listaConversoes.get(i);
                                    System.out.printf("%d - %s %s%n", i + 1, conversao.dataConversao(), conversao);
                                }
                            }
                            break;
                        case 4:
                            Map.Entry<String, Integer> moedaMaisConsultada = historicoConversoes.maisConsultada();
                            if (moedaMaisConsultada == null) {
                                System.out.println("Nenhuma conversão realizada ainda.");
                            } else {
                                System.out.printf("Moeda mais consultada: %s (%d conversões)%n",
                                        moedaMaisConsultada.getKey(), moedaMaisConsultada.getValue());
                            }
                            break;
                        case 5:
                            System.out.println("Programa finalizado!");
                            executando = false;
                            break;
                        default:
                            System.out.println("Opção inválida! Escolha de 1 a 5.");
                            break;
                    }

                } catch (IllegalArgumentException e) {
                    System.out.println("Entrada inválida: " + e.getMessage());
                } catch (MoedaNaoEncontradaException e) {
                    System.out.println("Moeda não encontrada: " + e.getMessage());
                } catch (ErroDeConsultaException e) {
                    System.out.println("Erro na consulta: " + e.getMessage());
                } catch (ErroDeConversaoException e) {
                    System.out.println("Erro na conversão: " + e.getMessage());
                }
            }
        }
    }

    private static void realizarConversao(Scanner sc,
                                          BiFunction<String, String, Conversao> conversor,
                                          HistoricoConversoes historico,
                                          RegistroLog log) {
        System.out.print("Digite o código da moeda: ");
        String codigoMoeda = sc.nextLine().trim();
        System.out.print("Digite o valor a ser convertido: ");
        String entradaValor = sc.nextLine().trim();

        Conversao conversao;
        try {
            conversao = conversor.apply(codigoMoeda, entradaValor);
        } catch (IllegalArgumentException | MoedaNaoEncontradaException
                 | ErroDeConsultaException | ErroDeConversaoException e) {
            registrarLog(log, "ERRO: [" + codigoMoeda + ", " + entradaValor + "] " + e.getMessage());
            throw e;
        }

        System.out.println(conversao);
        registrarLog(log, "SUCESSO: " + conversao);

        try {
            historico.adicionar(conversao);
        } catch (IOException e) {
            System.out.println("Aviso: a conversão não foi salva no histórico: " + e.getMessage());
        }
    }

    private static void registrarLog(RegistroLog log, String mensagem) {
        try {
            log.registrar(mensagem);
        } catch (IOException e) {
            System.out.println("Aviso: não foi possível gravar o log: " + e.getMessage());
        }
    }
}
