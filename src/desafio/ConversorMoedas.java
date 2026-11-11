package desafio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConversorMoedas {
    private final ConsultarCotacao consultarCotacao;

    public ConversorMoedas() {
        consultarCotacao = new ConsultarCotacao();
    }

    public Conversao converterDeReais(String codigoMoeda, String valorEmReais) {
        double valorReais = converterDouble(valorEmReais);
        validacaoValor(valorReais);
        Cotacao cotacao = consultarCotacao.buscar(codigoMoeda);
        double valorFinal = valorReais / cotacao.getPrecoCompra();
        String dateTime = formatarData(LocalDateTime.now());
        return new Conversao("BRL", valorReais, valorFinal, cotacao.getPrecoCompra(), cotacao.getCodigo(), dateTime);
    }

    public Conversao converterParaReais(String codigoMoeda, String valor) {
        double valorDouble = converterDouble(valor);
        validacaoValor(valorDouble);
        Cotacao cotacao = consultarCotacao.buscar(codigoMoeda);
        double valorEmReais = valorDouble * cotacao.getPrecoCompra();
        String dateTime = formatarData(LocalDateTime.now());
        return new Conversao(cotacao.getCodigo(), valorDouble, valorEmReais, cotacao.getPrecoCompra(), "BRL", dateTime);
    }

    private void validacaoValor(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor deve ser maior que zero");
        }
        if (Double.isNaN(valor) || Double.isInfinite(valor)) {
            throw new IllegalArgumentException("Digite um número válido");
        }

    }

    private double converterDouble(String valor) {
        try {
            if (valor.isBlank()) {
                throw new IllegalArgumentException("O valor da moeda não pode ser vazio.");
            }
            valor = valor.replace(",", ".");
            return Double.parseDouble(valor);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Digite apenas números.");
        }
    }

    private String formatarData(LocalDateTime dateTime) {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dateTime.format(formatador);
    }


}
