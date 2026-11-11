package desafio;

public class ErroDeConsultaException extends RuntimeException {
    public ErroDeConsultaException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErroDeConsultaException(String mensagem) {
        super(mensagem);
    }
}
