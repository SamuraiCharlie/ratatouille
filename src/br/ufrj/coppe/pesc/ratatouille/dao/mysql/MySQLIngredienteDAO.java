package br.ufrj.coppe.pesc.ratatouille.dao.mysql;

import java.util.List;

import br.com.felix.orm.BaseDAOFactory;
import br.com.felix.orm.exception.ImpossivelObterDadosException;
import br.ufrj.coppe.pesc.ratatouille.dao.IngredienteDAO;
import br.ufrj.coppe.pesc.ratatouille.model.Ingrediente;
import br.ufrj.coppe.pesc.ratatouille.model.Receita;

public class MySQLIngredienteDAO extends MySQLBaseDAO<Ingrediente> implements IngredienteDAO{

	public MySQLIngredienteDAO(BaseDAOFactory factory) {
		super(Ingrediente.class, factory);
	}

	@Override
	public List<Ingrediente> obterPorReceita(Receita receita) throws ImpossivelObterDadosException {
		return getFRMObjectStatement().obterOnde("ing_id_receita = " + receita.getId());
	}

}
