package br.ufrj.coppe.pesc.ratatouille.dao;

import java.util.List;

import br.com.felix.orm.DataAccessObject;
import br.com.felix.orm.exception.ImpossivelObterDadosException;
import br.ufrj.coppe.pesc.ratatouille.model.InstrucaoPreparo;
import br.ufrj.coppe.pesc.ratatouille.model.Receita;

public interface InstrucaoPreparoDAO extends DataAccessObject<InstrucaoPreparo>{

	List<InstrucaoPreparo> obterPorReceita(Receita result) throws ImpossivelObterDadosException;

}
