import com.fasterxml.jackson.annotation.JsonProperty;

public class RequisicaoImpressao {
	@JsonProperty("nome")
	public String nome;
	@JsonProperty("texto")
	public String texto;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

}
