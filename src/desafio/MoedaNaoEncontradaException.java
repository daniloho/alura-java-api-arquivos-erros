package desafio;

public class MoedaNaoEncontradaException extends RuntimeException {

    public MoedaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
