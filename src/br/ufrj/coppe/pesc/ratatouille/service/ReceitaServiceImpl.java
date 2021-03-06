package br.ufrj.coppe.pesc.ratatouille.service;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.felix.orm.exception.ImpossivelExcluirException;
import br.com.felix.orm.exception.ImpossivelInserirException;
import br.com.felix.orm.exception.ImpossivelObterDadosException;
import br.ufrj.coppe.pesc.ratatouille.dao.IngredienteDAO;
import br.ufrj.coppe.pesc.ratatouille.dao.InstrucaoPreparoDAO;
import br.ufrj.coppe.pesc.ratatouille.dao.RatatouilleDAOFactory;
import br.ufrj.coppe.pesc.ratatouille.dao.ReceitaDAO;
import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelBuscarReceitaPorIngredienteException;
import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelCadastrarReceitaException;
import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelConsultarReceitaPorNomeException;
import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelExcluirReceitaException;
import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelExcluirReceitasException;
import br.ufrj.coppe.pesc.ratatouille.lucene.SearchReceitas;
import br.ufrj.coppe.pesc.ratatouille.model.Ingrediente;
import br.ufrj.coppe.pesc.ratatouille.model.InstrucaoPreparo;
import br.ufrj.coppe.pesc.ratatouille.model.Receita;


public class ReceitaServiceImpl implements ReceitaService {

	private static final Logger logger = LoggerFactory.getLogger(ReceitaServiceImpl.class);
	
	
	public ReceitaServiceImpl() {
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
	 * @see br.ufrj.coppe.pesc.ratatouille.service.ReceitaService#buscaPorIngredientes(java.lang.String)
	 */
	@Override
	public List<Receita> buscaPorIngredientes(String query, TipoBusca tipoBusca) throws ImpossivelBuscarReceitaPorIngredienteException {
		switch (tipoBusca){
			case MySQL: 
				RatatouilleDAOFactory daoFactory = RatatouilleDAOFactory.instance();
				
				ReceitaDAO rDAO = daoFactory.getReceitaDAO();
				String [] ingredientes = query.split(" ");
				try {
					daoFactory.beginTransaction();
					List<Receita> listaReceitas = rDAO.obterPorIngredientes(ingredientes);
					daoFactory.commit();
					return listaReceitas;
				}
				catch (ImpossivelObterDadosException e) {
					String msg = "Erro obtendo receitas do MySQL.";
					logger.error(msg, e);
					daoFactory.rollback();
					throw new ImpossivelBuscarReceitaPorIngredienteException(msg, e);
				}
			case Lucene:
				try{
					SearchReceitas sr = SearchReceitas.getInstance();
					return sr.pesquisaReceitas(query);
				}
				catch (IOException | ParseException e){
					String msg = "Erro obtendo receitas do Lucene.";
					logger.error(msg, e);
					throw new ImpossivelBuscarReceitaPorIngredienteException(msg, e);
				}
				
		}
		return null;
	}



	/* (non-Javadoc)
	 * @see br.ufrj.coppe.pesc.ratatouille.service.ReceitaService#cadastrarReceita(br.ufrj.coppe.pesc.ratatouille.model.Receita)
	 */
	@Override
	public void cadastrarReceita(Receita receita) throws ImpossivelCadastrarReceitaException {
		RatatouilleDAOFactory daof = RatatouilleDAOFactory.instance();
		ReceitaDAO rDAO = daof.getReceitaDAO();
		IngredienteDAO iDAO = daof.getIngredienteDAO();
		InstrucaoPreparoDAO iprDAO = daof.getInstrucaoPreparoDAO();
		try {
			logger.info(String.format("Cadastrando receita '%s'", receita.getNome()));
			daof.beginTransaction();
			rDAO.inserir(receita);
			for (Ingrediente ingrediente: receita.getIngredientes()){
				ingrediente.setReceita(receita);
				iDAO.inserir(ingrediente);
			}
			if (receita.getModoPreparo() != null){
				for (InstrucaoPreparo instrucaoPreparo: receita.getModoPreparo()){
					instrucaoPreparo.setReceita(receita);
					iprDAO.inserir(instrucaoPreparo);
				}
			}
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



	@Override
	public Receita buscaPorNome(String nome) throws ImpossivelConsultarReceitaPorNomeException {
		RatatouilleDAOFactory daof = RatatouilleDAOFactory.instance();
		ReceitaDAO rDAO = daof.getReceitaDAO();
		IngredienteDAO iDAO = daof.getIngredienteDAO();
		InstrucaoPreparoDAO iprDAO = daof.getInstrucaoPreparoDAO();
		try {
			daof.beginTransaction();
			Receita result = rDAO.obterPorNome(nome);
			result.setIngredientes(iDAO.obterPorReceita(result));
			result.setModoPreparo(iprDAO.obterPorReceita(result));
			daof.commit();
			return result;
		}
		catch (ImpossivelObterDadosException e) {
			String msg = "Erro consultando receita por nome.";
			logger.error(msg, e);
			daof.rollback();
			throw new ImpossivelConsultarReceitaPorNomeException(msg, e);
		}
	}



	@Override
	public void excluirReceita(Receita receita) throws ImpossivelExcluirReceitaException {
		RatatouilleDAOFactory daof = RatatouilleDAOFactory.instance();
		ReceitaDAO rDAO = daof.getReceitaDAO();
		try {
			daof.beginTransaction();
			rDAO.excluir(receita);
			daof.commit();
		}
		catch (ImpossivelExcluirException e) {
			String msg = "Erro excluindo receita.";
			logger.error(msg, e);
			daof.rollback();
			throw new ImpossivelExcluirReceitaException(msg, e);
		}
	}

}
