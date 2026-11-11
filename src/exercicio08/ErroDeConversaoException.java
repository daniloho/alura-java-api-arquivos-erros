package exercicio08;

public class ErroDeConversaoException extends RuntimeException {
    public ErroDeConversaoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

    public ErroDeConversaoException(String mensagem) {
        super(mensagem);
    }
}
