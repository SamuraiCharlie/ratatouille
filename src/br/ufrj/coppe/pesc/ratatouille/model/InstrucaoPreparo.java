package br.ufrj.coppe.pesc.ratatouille.model;

/**
 * Representa uma instrução de preparo de receita.
 *
 */
public class InstrucaoPreparo {

	private int ordem;
	private String texto;



	public int getOrdem() {
		return ordem;
	}



	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}



	public String getTexto() {
		return texto;
	}



	public void setTexto(String texto) {
		this.texto = texto;
	}
}
