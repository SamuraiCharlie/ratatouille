package br.ufrj.coppe.pesc.ratatouille.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Classe representa um alimento. Ao contrário de um ingrediente, não está
 * associado a uma receita. Ex.: "cebola", "tomate".
 * */
@Entity
@Table(name = "AIG_ALIMENTO_IG")
public class Alimento {

	private Integer id;
	private String nome;
	private Integer frequencia;
	private String informacoes;


	public Alimento() {
	}



	@Id
	@Column(name = "aig_id_alimento")
	@GeneratedValue
	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	@Column(name = "aig_nome")
	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	@Column(name = "aig_frequencia")
	public Integer getFrequencia() {
		return frequencia;
	}



	public void setFrequencia(Integer frequencia) {
		this.frequencia = frequencia;
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


	@Column(name = "aig_informacoes")
	public String getInformacoes() {
		return informacoes;
	}



	public void setInformacoes(String informacoes) {
		this.informacoes = informacoes;
	}

}
