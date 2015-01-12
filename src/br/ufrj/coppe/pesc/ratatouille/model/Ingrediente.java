package br.ufrj.coppe.pesc.ratatouille.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * Representa o ingrediente de uma receita. Sua descrição textual com quantidade
 * e alimento.
 * 
 * 
 */
@Entity
@Table(name = "ING_INGREDIENTE")
public class Ingrediente {

	private Integer id;
	private String texto;
	private String html;
	private Alimento alimento;
	private Receita receita;

	@Column(name = "ing_html")
	public String getHtml() {
		return html;
	}



	public void setHtml(String html) {
		this.html = html;
	}


	@Column(name = "ing_texto")
	public String getTexto() {
		return texto;
	}



	public void setTexto(String descricao) {
		this.texto = descricao;
	}



	public Alimento getAlimento() {
		return alimento;
	}



	public void setAlimento(Alimento alimento) {
		this.alimento = alimento;
	}


	@Id
	@Column(name = "ing_id_ingrediente")
	@GeneratedValue
	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alimento == null) ? 0 : alimento.hashCode());
		result = prime * result + ((html == null) ? 0 : html.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((texto == null) ? 0 : texto.hashCode());
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
		Ingrediente other = (Ingrediente) obj;
		if (alimento == null) {
			if (other.alimento != null)
				return false;
		}
		else if (!alimento.equals(other.alimento))
			return false;
		if (html == null) {
			if (other.html != null)
				return false;
		}
		else if (!html.equals(other.html))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		if (texto == null) {
			if (other.texto != null)
				return false;
		}
		else if (!texto.equals(other.texto))
			return false;
		return true;
	}



	@ManyToOne()
	@JoinColumn(name="ing_id_receita", referencedColumnName="rec_id_receita")
	public Receita getReceita() {
		return receita;
	}



	public void setReceita(Receita receita) {
		this.receita = receita;
	}
}
