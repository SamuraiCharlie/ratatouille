package br.ufrj.coppe.pesc.ratatouille.consulta;

import java.util.Comparator;

import br.ufrj.coppe.pesc.ratatouille.model.Receita;

public class RankComparator implements Comparator<Receita> {

	@Override
	public int compare(Receita r1, Receita r2) {
		return -1 * r1.getRank().compareTo(r2.getRank());
	}
}
