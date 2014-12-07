package br.ufrj.coppe.pesc.ratatouille.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "REC_RECEITA")
public class Receita {

	private String nome;
	private String url;
	private String html;
	private String textoIngredientes;
	
	public Receita() {
	}


	@Column(name="rec_nome")
	public String getNome() {
		return nome;
	}

	
	public void setNome(String nome) {
		this.nome = nome;
	}

	@Id
	@Column(name="rec_url")
	public String getUrl() {
		return url;
	}

	
	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name="rec_html")
	public String getHtml() {
		return html;
	}

	
	public void setHtml(String html) {
		this.html = html;
	}

	
	@Column(name="rec_ingredientes")
	public String getTextoIngredientes() {
		return textoIngredientes;
	}

	
	public void setTextoIngredientes(String textoIngredientes) {
		this.textoIngredientes = textoIngredientes;
	}

}
