package br.ufrj.coppe.pesc.ratatouille.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Representa uma receita culinária.
 * 
 */
@Entity
@Table(name = "REC_RECEITA")
public class Receita {

	private String nome;
	private String url;
	private String html;
	private String textoIngredientes;
	private String textoModoPreparo;
	private List<Ingrediente> ingredientes;
	private List<InstrucaoPreparo> modoPreparo;


	
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



	public Receita() {
	}



	@Column(name = "rec_nome")
	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	@Id
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



	@Override
	public String toString() {
		return getNome() + "\n" + getTextoIngredientes();
	}


	@Column(name = "rec_modo_preparo")
	public String getTextoModoPreparo() {
		return textoModoPreparo;
	}



	public void setTextoModoPreparo(String textoModoPreparo) {
		this.textoModoPreparo = textoModoPreparo;
	}
}
