package br.ufrj.coppe.pesc.ratatouille.dao.mysql;

import java.util.List;

import br.com.felix.orm.BaseDAOFactory;
import br.com.felix.orm.exception.ImpossivelObterDadosException;
import br.ufrj.coppe.pesc.ratatouille.dao.AlimentoDAO;
import br.ufrj.coppe.pesc.ratatouille.model.Alimento;

public class MySQLAlimentoDAO extends MySQLBaseDAO<Alimento> implements AlimentoDAO {

	public MySQLAlimentoDAO(BaseDAOFactory factory) {
		super(Alimento.class, factory);
	}



	@Override
	public List<Alimento> obterTodosOrdenadoPorTamanho() throws ImpossivelObterDadosException {
		return getFRMObjectStatement().obterOnde("TRUE ORDER BY CHAR_LENGTH(aig_nome) DESC");
	}



	@Override
	public Alimento obterPorNome(String string) throws ImpossivelObterDadosException {
		List<Alimento> result = getFRMObjectStatement().obterOnde("aig_nome = '"+string+"'");
		if (result.isEmpty()) return null;
		return result.get(0);
	}

}
