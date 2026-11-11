package desafio;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegistroLog {
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private final String arquivo;

    public RegistroLog() {
        this.arquivo = "log.txt";
    }

    public void registrar(String mensagem) throws IOException {
        String dataHora = LocalDateTime.now().format(FORMATO);
        String mensagemFinal = "[" + dataHora + "] " + mensagem;
        try (FileWriter fw = new FileWriter(arquivo, true)) {
            fw.write(mensagemFinal + System.lineSeparator());
        }
    }
}
