package exercicio10;

public class Cotacao {
    private String nome;
    private double precoCompra;
    private double precoMaximo;
    private double precoMinimo;
    private double variacao;
    private String codigo;

    public Cotacao(DadosCotacao cotacao) {
        if (cotacao.precoCompra() == null || cotacao.precoMaximo() == null || cotacao.precoMinimo() == null || cotacao.variacao() == null) {
            throw new ErroDeConversaoException("Dados incompletos para cotação: " + cotacao.nome());
        }

        try {
            this.nome = cotacao.nome();
            this.precoCompra = Double.parseDouble(cotacao.precoCompra());
            this.precoMaximo = Double.parseDouble(cotacao.precoMaximo());
            this.precoMinimo = Double.parseDouble(cotacao.precoMinimo());
            this.variacao = Double.parseDouble(cotacao.variacao());
            this.codigo = cotacao.codigo();
        } catch (NumberFormatException e) {
            throw new ErroDeConversaoException("Não foi possível converter os dados da cotação: " + cotacao.nome() , e);
        }
    }

    public String getNome() {
        return nome;
    }

    public double getPrecoCompra() {
        return precoCompra;
    }

    public double getPrecoMaximo() {
        return precoMaximo;
    }

    public double getPrecoMinimo() {
        return precoMinimo;
    }

    public double getVariacao() {
        return variacao;
    }

    public String getCodigo() {
        return codigo;
    }

    @Override
    public String toString() {
        return String.format("""
                %s
                Compra: R$ %,.2f | Alta: R$ %,.2f | Baixa: R$ %,.2f | Variação: %.2f%%
                """, this.nome, this.precoCompra, this.precoMaximo, this.precoMinimo, this.variacao);
    }
}
