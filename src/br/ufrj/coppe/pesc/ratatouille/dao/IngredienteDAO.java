package br.ufrj.coppe.pesc.ratatouille.dao;

import java.util.List;

import br.com.felix.orm.DataAccessObject;
import br.com.felix.orm.exception.ImpossivelObterDadosException;
import br.ufrj.coppe.pesc.ratatouille.model.Ingrediente;
import br.ufrj.coppe.pesc.ratatouille.model.Receita;

public interface IngredienteDAO extends DataAccessObject<Ingrediente> {

	List<Ingrediente> obterPorReceita(Receita receita) throws ImpossivelObterDadosException;

}
