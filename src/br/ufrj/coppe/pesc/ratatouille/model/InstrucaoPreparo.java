package br.ufrj.coppe.pesc.ratatouille.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Representa uma instrução de preparo de receita.
 * 
 */
@Entity
@Table(name = "IPR_INSTRUCAO_PREPARO")
public class InstrucaoPreparo {

	private Integer id;
	private Integer ordem;
	private String texto;
	private Receita receita;



	public InstrucaoPreparo() {

	}



	public InstrucaoPreparo(String texto, int ordem) {
		this.texto = texto;
		this.ordem = ordem;
	}



	@Column(name = "ipr_ordem")
	public Integer getOrdem() {
		return ordem;
	}



	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}



	@Column(name = "ipr_texto")
	public String getTexto() {
		return texto;
	}



	public void setTexto(String texto) {
		this.texto = texto;
	}



	@Id
	@Column(name = "ipr_id_instrucao_preparo")
	@GeneratedValue
	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	@ManyToOne
	@JoinColumn(name = "ipr_id_receita", referencedColumnName="rec_id_receita")
	public Receita getReceita() {
		return receita;
	}



	public void setReceita(Receita receita) {
		this.receita = receita;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ordem;
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
		InstrucaoPreparo other = (InstrucaoPreparo) obj;
		if (id != other.id)
			return false;
		if (ordem != other.ordem)
			return false;
		if (texto == null) {
			if (other.texto != null)
				return false;
		}
		else if (!texto.equals(other.texto))
			return false;
		return true;
	}

}
