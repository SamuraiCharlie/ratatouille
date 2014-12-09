package br.ufrj.coppe.pesc.ratatouille.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RECEITAS")
public class Receita {

	private String nome;
	private String link;
	private String ingredientes;
	
	public Receita() {
	}


	@Column(name="nome")
	public String getNome() {
		return nome;
	}

	
	public void setNome(String nome) {
		this.nome = nome;
	}

	@Id
	@Column(name="link")
	public String getLink() {
		return link;
	}

	
	public void setLink(String url) {
		this.link = url;
	}

//	@Column(name="rec_html")
//	public String getHtml() {
//		return html;
//	}
//
//	
//	public void setHtml(String html) {
//		this.html = html;
//	}

	
	@Column(name="ingredientes")
	public String getIngredientes() {
		return ingredientes;
	}

	
	public void setIngredientes(String textoIngredientes) {
		this.ingredientes = textoIngredientes;
	}

}
