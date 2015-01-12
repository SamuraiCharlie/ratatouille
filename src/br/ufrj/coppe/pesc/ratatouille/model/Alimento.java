package br.ufrj.coppe.pesc.ratatouille.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Classe representa um alimento. Ao contrário de um ingrediente, não está
 * associado a uma receita. Ex.: "cebola", "tomate".
 * */
@Entity
@Table(name = "AIG_ALIMENTO_IG")
public class Alimento {

	private String nome;



	public Alimento() {
	}



	@Id
	@Column(name = "aig_nome")
	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Alimento other = (Alimento) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		}
		else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	



	@Override
	public String toString() {
		return "Alimento [nome=" + nome + "]";
	}
}
