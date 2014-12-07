package br.ufrj.coppe.pesc.ratatouille.dao.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;

import br.com.felix.orm.BaseDAOFactory;
import br.com.felix.orm.exception.TransactionException;


public class MySQLBaseDAO<T> extends br.com.felix.orm.mysql.MySQLBaseDAO<T> {

	private static final Logger logger = LoggerFactory.getLogger(MySQLBaseDAO.class);
	
	public MySQLBaseDAO(Class<T> clazz, BaseDAOFactory factory) {
		super(clazz, factory);
	}

	
	@Override
	public void rollback() {
		try {
			super.rollback();
		}
		catch (TransactionException e) {
			logger.error(MarkerFactory.getMarker("FATAL"), "Erro executando rollback.", e);
		}
	}
}
