package exercicio07;

public class Filme implements Comparable<Filme> {
    private String titulo;
    private String diretor;
    private int ano;
    private int duracaoEmMinutos;
    private double nota;

    public Filme(DadosFilme dados) {
        if (dados.ano() == null || dados.duracao() == null || dados.nota() == null) {
            throw new ErroDeConversaoException("Dados incompletos para o filme " + dados.titulo());
        }
        try {


            this.titulo = dados.titulo();
            this.diretor = dados.diretor();
            this.ano = Integer.parseInt(dados.ano().substring(0, 4));
            int index = dados.duracao().indexOf(" ");
            this.duracaoEmMinutos = Integer.parseInt(dados.duracao().substring(0, index));
            this.nota = Double.parseDouble(dados.nota());
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            throw new ErroDeConversaoException("Não foi possível converter os dados do filme " + dados.titulo(), e);
        }
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDiretor() {
        return diretor;
    }

    public int getAno() {
        return ano;
    }

    public int getDuracaoEmMinutos() {
        return duracaoEmMinutos;
    }

    public double getNota() {
        return nota;
    }

    @Override
    public int compareTo(Filme o) {
        return Double.compare(o.nota, this.nota);
    }

    @Override
    public String toString() {
        return String.format("%s (%d) - %d min - nota %.1f", this.titulo, this.ano, this.duracaoEmMinutos, this.nota);
    }
}
