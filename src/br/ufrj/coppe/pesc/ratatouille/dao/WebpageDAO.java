package br.ufrj.coppe.pesc.ratatouille.dao;

import java.util.List;

import br.com.felix.orm.DataAccessObject;
import br.com.felix.orm.exception.ImpossivelObterDadosException;
import br.ufrj.coppe.pesc.ratatouille.model.Webpage;

/**
 * DAO para entidades Webpage.
 *
 */
public interface WebpageDAO extends DataAccessObject<Webpage> {

	public List<Webpage> obterComConteudo() throws ImpossivelObterDadosException;
}
