package exercicio09;

public class ErroDeConversaoException extends RuntimeException {
    public ErroDeConversaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErroDeConversaoException(String message) {
        super(message);
    }
}
