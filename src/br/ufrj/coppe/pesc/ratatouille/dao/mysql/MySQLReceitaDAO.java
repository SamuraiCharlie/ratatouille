package br.ufrj.coppe.pesc.ratatouille.dao.mysql;

import java.util.List;

import br.com.felix.orm.BaseDAOFactory;
import br.com.felix.orm.exception.ImpossivelObterDadosException;
import br.ufrj.coppe.pesc.ratatouille.dao.ReceitaDAO;
import br.ufrj.coppe.pesc.ratatouille.model.Receita;


public class MySQLReceitaDAO extends MySQLBaseDAO<Receita> implements ReceitaDAO{

	public MySQLReceitaDAO(BaseDAOFactory factory) {
		super(Receita.class, factory);
	}

	
	
	@Override
	public List<Receita> obterPorIngredientes(String... ingredientes) throws ImpossivelObterDadosException {
		StringBuilder strb = new StringBuilder("rec_ingredientes LIKE '%").append(ingredientes[0]).append("%'");
		for (int i=1;i<ingredientes.length;i++){
			strb.append(" AND rec_ingredientes LIKE '%").append(ingredientes[i]).append("%'");
		}
		
		return getFRMObjectStatement().obterOnde(strb.toString());
	}
}
