package br.ufrj.coppe.pesc.ratatouille.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Representa uma receita culinária.
 * 
 */
@Entity
@Table(name = "REC_RECEITA")
public class Receita {

	private Integer id;
	private String nome;
	private String url;
	private String urlImagem;
	private String html;
	private String textoIngredientes;
	private String textoModoPreparo;
	private List<Ingrediente> ingredientes;
	private List<InstrucaoPreparo> modoPreparo;
	private Float rank;
	
	
	public Receita() {
	}
	
	
	@Id
	@Column(name = "rec_id_receita")
	@GeneratedValue
	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}


	
	public List<InstrucaoPreparo> getModoPreparo() {
		return modoPreparo;
	}



	public void setModoPreparo(List<InstrucaoPreparo> modoPreparo) {
		this.modoPreparo = modoPreparo;
	}



	public List<Ingrediente> getIngredientes() {
		return ingredientes;
	}



	public void setIngredientes(List<Ingrediente> ingredientes) {
		this.ingredientes = ingredientes;
	}

	@Column(name = "rec_url_imagem")
	public String getUrlImagem() {
		return urlImagem;
	}



	public void setUrlImagem(String urlImagem) {
		this.urlImagem = urlImagem;
	}
	



	@Column(name = "rec_nome")
	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	@Column(name = "rec_url")
	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}



	@Column(name = "rec_html")
	public String getHtml() {
		return html;
	}



	public void setHtml(String html) {
		this.html = html;
	}



	@Column(name = "rec_ingredientes")
	public String getTextoIngredientes() {
		return textoIngredientes;
	}



	public void setTextoIngredientes(String textoIngredientes) {
		this.textoIngredientes = textoIngredientes;
	}

	

	@Column(name = "rec_modo_preparo")
	public String getTextoModoPreparo() {
		return textoModoPreparo;
	}



	public void setTextoModoPreparo(String textoModoPreparo) {
		this.textoModoPreparo = textoModoPreparo;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((html == null) ? 0 : html.hashCode());
		result = prime * result + ((ingredientes == null) ? 0 : ingredientes.hashCode());
		result = prime * result + ((modoPreparo == null) ? 0 : modoPreparo.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((textoIngredientes == null) ? 0 : textoIngredientes.hashCode());
		result = prime * result + ((textoModoPreparo == null) ? 0 : textoModoPreparo.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + ((urlImagem == null) ? 0 : urlImagem.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Receita other = (Receita) obj;
		if (html == null) {
			if (other.html != null)
				return false;
		}
		else if (!html.equals(other.html))
			return false;
		if (ingredientes == null) {
			if (other.ingredientes != null)
				return false;
		}
		else if (!ingredientes.equals(other.ingredientes))
			return false;
		if (modoPreparo == null) {
			if (other.modoPreparo != null)
				return false;
		}
		else if (!modoPreparo.equals(other.modoPreparo))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		}
		else if (!nome.equals(other.nome))
			return false;
		if (textoIngredientes == null) {
			if (other.textoIngredientes != null)
				return false;
		}
		else if (!textoIngredientes.equals(other.textoIngredientes))
			return false;
		if (textoModoPreparo == null) {
			if (other.textoModoPreparo != null)
				return false;
		}
		else if (!textoModoPreparo.equals(other.textoModoPreparo))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		}
		else if (!url.equals(other.url))
			return false;
		if (urlImagem == null) {
			if (other.urlImagem != null)
				return false;
		}
		else if (!urlImagem.equals(other.urlImagem))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Receita [nome=" + nome + ", rank = " + rank + "]";
	}


	public void calcularRank(String query) {
		rank = 0f;
		String tokens[] = query.toLowerCase().split(" ");
		String nomeLowerCase = this.nome.toLowerCase();
		String textoIngredientesLowerCase = this.textoIngredientes.toLowerCase();
		for (String token : tokens){
			if (nomeLowerCase.contains(token)){
				rank += 1.2f;
			}
			if (textoIngredientesLowerCase.contains(token)){
				rank += 1.0f / (ingredientes==null? 1 : ingredientes.size());
			}
		}
	}


	public Float getRank() {
		return rank;
	}

}
