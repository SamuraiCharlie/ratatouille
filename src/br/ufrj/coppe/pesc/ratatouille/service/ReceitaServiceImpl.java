package br.ufrj.coppe.pesc.ratatouille.service;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.felix.orm.exception.ImpossivelExcluirException;
import br.com.felix.orm.exception.ImpossivelInserirException;
import br.com.felix.orm.exception.ImpossivelObterDadosException;
import br.ufrj.coppe.pesc.ratatouille.dao.RatatouilleDAOFactory;
import br.ufrj.coppe.pesc.ratatouille.dao.ReceitaDAO;
import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelBuscarReceitaPorIngredienteException;
import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelCadastrarReceitaException;
import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelExcluirReceitasException;
import br.ufrj.coppe.pesc.ratatouille.lucene.SearchReceitas;
import br.ufrj.coppe.pesc.ratatouille.model.Receita;


public class ReceitaServiceImpl implements ReceitaService {

	private static final Logger logger = LoggerFactory.getLogger(ReceitaServiceImpl.class);
	
	
	public ReceitaServiceImpl() {
	}

	
	
	/* (non-Javadoc)
	 * @see br.ufrj.coppe.pesc.ratatouille.service.ReceitaService#buscaPorIngredientes(java.lang.String)
	 */
	@Override
	public List<Receita> buscaPorIngredientes(String query) throws ImpossivelBuscarReceitaPorIngredienteException {
		//RatatouilleDAOFactory daoFactory = RatatouilleDAOFactory.instance();
		
		//ReceitaDAO rDAO = daoFactory.getReceitaDAO();
		String [] ingredientes = query.split(" ");
		try {
			//daoFactory.beginTransaction();
			//List<Receita> listaReceitas = rDAO.obterPorIngredientes(ingredientes);
			SearchReceitas search = SearchReceitas.getInstance();
			List<Receita> listaReceitas = search.pesquisaReceitas(query);
			//daoFactory.commit();
			return listaReceitas;
		}
		catch (ParseException | IOException e) {
			e.printStackTrace();
			String msg = "Erro obtendo receitas.";
			logger.error(msg, e);
			//daoFactory.rollback();
			throw new ImpossivelBuscarReceitaPorIngredienteException(msg, e);
		}
	}
	
	
	
	
	@Override
	public List<Receita> buscaTodos() throws ImpossivelBuscarReceitaPorIngredienteException {
		RatatouilleDAOFactory daoFactory = RatatouilleDAOFactory.instance();
		
		ReceitaDAO rDAO = daoFactory.getReceitaDAO();
		try {
			daoFactory.beginTransaction();
			List<Receita> listaReceitas = rDAO.obterTodos();
			daoFactory.commit();
			return listaReceitas;
		}
		catch (ImpossivelObterDadosException e) {
			String msg = "Erro obtendo receitas.";
			logger.error(msg, e);
			daoFactory.rollback();
			throw new ImpossivelBuscarReceitaPorIngredienteException(msg, e);
		}
	}



	/* (non-Javadoc)
	 * @see br.ufrj.coppe.pesc.ratatouille.service.ReceitaService#cadastrarReceita(br.ufrj.coppe.pesc.ratatouille.model.Receita)
	 */
	@Override
	public void cadastrarReceita(Receita receita) throws ImpossivelCadastrarReceitaException {
		RatatouilleDAOFactory daof = RatatouilleDAOFactory.instance();
		ReceitaDAO rDAO = daof.getReceitaDAO();
		try {
			logger.info(String.format("Cadastrando receita '%s'", receita.getNome()));
			daof.beginTransaction();
			rDAO.inserir(receita);
			daof.commit();
		}
		catch (ImpossivelInserirException e) {
			String msg = "Erro cadastrando receita.";
			logger.error(msg, e);
			daof.rollback();
			throw new ImpossivelCadastrarReceitaException(msg, e);
		}
		
	}



	@Override
	public void excluirReceitas() throws ImpossivelExcluirReceitasException {
		logger.info("Excluindo todas as receitas do banco de dados");
		RatatouilleDAOFactory daof = RatatouilleDAOFactory.instance();
		ReceitaDAO rDAO = daof.getReceitaDAO();
		try {
			daof.beginTransaction();
			rDAO.excluirTodas();
			daof.commit();
		}
		catch (ImpossivelExcluirException e) {
			String msg = "Erro excluindo receitas.";
			logger.error(msg, e);
			daof.rollback();
			throw new ImpossivelExcluirReceitasException(msg, e);
		}
		
	}

}
