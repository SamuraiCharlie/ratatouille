package br.ufrj.coppe.pesc.ratatouille.dao;

import java.util.List;

import br.com.felix.orm.DataAccessObject;
import br.com.felix.orm.exception.ImpossivelObterDadosException;
import br.ufrj.coppe.pesc.ratatouille.model.Alimento;

public interface AlimentoDAO extends DataAccessObject<Alimento> {

	List<Alimento> obterTodosOrdenadoPorTamanho() throws ImpossivelObterDadosException;

	Alimento obterPorNome(String string) throws ImpossivelObterDadosException;

}
