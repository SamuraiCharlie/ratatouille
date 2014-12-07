package br.ufrj.coppe.pesc.ratatouille.dao;

import java.util.List;

import br.com.felix.orm.DataAccessObject;
import br.com.felix.orm.exception.ImpossivelObterDadosException;
import br.ufrj.coppe.pesc.ratatouille.model.Receita;


public interface ReceitaDAO extends DataAccessObject<Receita>{

	List<Receita> obterPorIngredientes(String... ingredientes) throws ImpossivelObterDadosException;

}
