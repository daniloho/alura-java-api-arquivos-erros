package desafio;

import java.text.DecimalFormat;

public record Conversao(String moedaOrigem,
                        double valorOriginal,
                        double valorConvertido,
                        double taxa,
                        String moedaDestino,
                        String dataConversao) {

    @Override
    public String toString() {
        if ("BRL".equals(moedaDestino)) {
            return String.format("%s %s = R$ %s (taxa: %s)",
                    formatar(valorOriginal), moedaOrigem, formatar(valorConvertido), formatar(taxa));
        }
        return String.format("R$ %s = %s %s (taxa: %s)",
                formatar(valorOriginal), formatar(valorConvertido), moedaDestino, formatar(taxa));
    }

    private static String formatar(double valor) {
        if (valor != 0 && Math.abs(valor) < 1) {
            return new DecimalFormat("#,##0.00####").format(valor);
        }
        return String.format("%,.2f", valor);
    }
}
