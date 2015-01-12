package br.ufrj.coppe.pesc.ratatouille.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.felix.orm.exception.ImpossivelObterDadosException;
import br.ufrj.coppe.pesc.ratatouille.dao.AlimentoDAO;
import br.ufrj.coppe.pesc.ratatouille.dao.RatatouilleDAOFactory;
import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelConsultarAlimentoPorNomeException;
import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelConsultarListaAlimentosException;
import br.ufrj.coppe.pesc.ratatouille.model.Alimento;


/**
 * Fornece serviços relacionados com a entidade Alimento.
 *
 */
public class AlimentoServiceImpl implements AlimentoService {

	private static final Logger logger = LoggerFactory.getLogger(AlimentoServiceImpl.class);
	
	public AlimentoServiceImpl() {
	}

	
	
	@Override
	public List<Alimento> obterListaAlimentos() throws ImpossivelConsultarListaAlimentosException {
		RatatouilleDAOFactory daof = RatatouilleDAOFactory.instance();
		AlimentoDAO aDAO = daof.getAlimentoDAO();
		List<Alimento> lst = null;
		
		try {
			daof.beginTransaction();
			lst = aDAO.obterTodosOrdenadoPorTamanho();
			daof.commit();
		}
		catch (ImpossivelObterDadosException e) {
			daof.rollback();
			String msg = "Erro consultando lista de alimentos.";
			logger.error(msg, e);
			throw new ImpossivelConsultarListaAlimentosException(msg, e);
		}
		return lst;
	}



	@Override
	public Alimento obterPorNome(String string) throws ImpossivelConsultarAlimentoPorNomeException {
		RatatouilleDAOFactory daof = RatatouilleDAOFactory.instance();
		AlimentoDAO aDAO = daof.getAlimentoDAO();
		Alimento result = null;
		
		
		try {
			daof.beginTransaction();
			result = aDAO.obterPorNome(string);
			daof.commit();
		}
		catch (ImpossivelObterDadosException e) {
			daof.rollback();
			String msg = "Erro consultando alimento por nome.";
			logger.error(msg, e);
			throw new ImpossivelConsultarAlimentoPorNomeException(msg, e);
		}
		
		
		return result;
	}

}
