package br.ufrj.coppe.pesc.ratatouille.consulta;

import java.util.Collections;
import java.util.List;

import br.ufrj.coppe.pesc.ratatouille.model.Receita;

public class Ranqueador {
	
	
	public static void ranquear(List<Receita> receitas, String query){
		for (Receita r : receitas){
			r.calcularRank(query);
		}
		RankComparator comparator = new RankComparator();
		Collections.sort(receitas, comparator);
	} 
}
