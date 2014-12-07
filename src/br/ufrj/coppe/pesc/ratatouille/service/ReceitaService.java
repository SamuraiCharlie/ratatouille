package br.ufrj.coppe.pesc.ratatouille.service;

import java.util.List;

import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelBuscarReceitaPorIngredienteException;
import br.ufrj.coppe.pesc.ratatouille.model.Receita;


public interface ReceitaService {

	List<Receita> buscaPorIngredientes(String query) throws ImpossivelBuscarReceitaPorIngredienteException;

}
