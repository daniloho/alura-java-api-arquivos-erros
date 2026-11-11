package exercicio02;

import com.google.gson.annotations.SerializedName;

public record DadosFilme(@SerializedName("Title") String titulo,
                         @SerializedName("Year") String ano,
                         @SerializedName("Director") String diretor,
                         @SerializedName("Runtime") String duracao,
                         @SerializedName("imdbRating")String nota) {
}
