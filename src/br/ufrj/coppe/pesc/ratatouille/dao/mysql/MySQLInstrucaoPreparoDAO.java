package br.ufrj.coppe.pesc.ratatouille.dao.mysql;

import java.util.List;

import br.com.felix.orm.BaseDAOFactory;
import br.com.felix.orm.exception.ImpossivelObterDadosException;
import br.ufrj.coppe.pesc.ratatouille.dao.InstrucaoPreparoDAO;
import br.ufrj.coppe.pesc.ratatouille.model.InstrucaoPreparo;
import br.ufrj.coppe.pesc.ratatouille.model.Receita;

public class MySQLInstrucaoPreparoDAO extends MySQLBaseDAO<InstrucaoPreparo> implements InstrucaoPreparoDAO {

	public MySQLInstrucaoPreparoDAO(BaseDAOFactory factory) {
		super(InstrucaoPreparo.class, factory);
	}

	@Override
	public List<InstrucaoPreparo> obterPorReceita(Receita receita) throws ImpossivelObterDadosException {
		return getFRMObjectStatement().obterOnde("ipr_id_receita = " + receita.getId());
	}



}
