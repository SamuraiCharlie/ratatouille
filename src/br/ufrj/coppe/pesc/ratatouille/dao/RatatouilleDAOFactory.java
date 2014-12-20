package br.ufrj.coppe.pesc.ratatouille.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;

import br.com.felix.orm.BaseDAOFactory;
import br.com.felix.orm.exception.TransactionException;
import br.ufrj.coppe.pesc.ratatouille.dao.mysql.MySQLDAOFactory;

/**
 * Responsável por obter instâncias dos DAOs e iniciar e fechar transações.
 * 
 * */
public abstract class RatatouilleDAOFactory extends BaseDAOFactory {

	private static ThreadLocal<RatatouilleDAOFactory> tlFactories;
	
	private static final Logger logger = LoggerFactory.getLogger(RatatouilleDAOFactory.class);
	
	static {
		tlFactories = new ThreadLocal<RatatouilleDAOFactory>();
	}



	/**
	 * 
	 * Obtém uma instância do DAOFactory.
	 * @return
	 */
	public static RatatouilleDAOFactory instance() {
		RatatouilleDAOFactory instance = tlFactories.get();
		if (instance == null){
			instance = new MySQLDAOFactory();
			tlFactories.set(instance);
		}
		return instance;
	}



	/* (non-Javadoc)
	 * @see br.com.felix.orm.BaseDAOFactory#rollback()
	 */
	@Override
	public synchronized void rollback() {
		try {
			super.rollback();
		}
		catch (TransactionException e) {
			logger.error(MarkerFactory.getMarker("FATAL"), "Erro dando rollback na transação.", e);
		}
	}

	

	public abstract ReceitaDAO getReceitaDAO();

	
	
	public abstract WebpageDAO getWebpageDAO();
}
