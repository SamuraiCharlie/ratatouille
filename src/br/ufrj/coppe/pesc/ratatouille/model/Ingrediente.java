package br.ufrj.coppe.pesc.ratatouille.model;

/**
 * 
 * Representa o ingrediente de uma receita. Sua descrição textual com quantidade e alimento.
 * 
 *
 */
public class Ingrediente {

	private String texto;

	public String getTexto() {
		return texto;
	}

	public void setTexto(String descricao) {
		this.texto = descricao;
	}
}
