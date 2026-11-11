package exercicio10;

import com.google.gson.annotations.SerializedName;

public record DadosCotacao(@SerializedName("name") String nome,
                           @SerializedName("bid") String precoCompra,
                           @SerializedName("high") String precoMaximo,
                           @SerializedName("low") String precoMinimo,
                           @SerializedName("pctChange") String variacao,
                           @SerializedName("code")  String codigo) {
}
