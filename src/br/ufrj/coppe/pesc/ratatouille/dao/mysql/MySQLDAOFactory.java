package br.ufrj.coppe.pesc.ratatouille.dao.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;

import br.com.felix.orm.DataAccessObject;
import br.com.felix.orm.exception.ImpossivelObterConexaoException;
import br.ufrj.coppe.pesc.ratatouille.dao.RatatouilleDAOFactory;
import br.ufrj.coppe.pesc.ratatouille.dao.ReceitaDAO;
import br.ufrj.coppe.pesc.ratatouille.model.Receita;

public class MySQLDAOFactory extends RatatouilleDAOFactory {

	private static Logger logger = LoggerFactory.getLogger(MySQLDAOFactory.class);
	private ReceitaDAO receitaDAO;

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (ClassNotFoundException e) {
			logger.error(MarkerFactory.getMarker("FATAL"), "Driver do MySQL não encontrado.");
		}
	}
	
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/nutch?charset=utf8";

	private static final String password = "bri2014";

	private static final String username = "nutch";
	
	private static DataSource dataSource;
	
	private static boolean useDataSource = true;


	public MySQLDAOFactory() {
		receitaDAO = new MySQLReceitaDAO(this);
	}



	
	@Override
	public ReceitaDAO getReceitaDAO() {
		return receitaDAO;
	}



	@Override
	public Connection createConnection() throws ImpossivelObterConexaoException {
		try {
			if (useDataSource){
				try{
					if (dataSource == null){
						Context ctx = new InitialContext();
						Context envCtx = (Context)ctx.lookup("java:comp/env");
						if (envCtx!=null){
							Object obj = envCtx.lookup("jdbc/RatatouilleDB");
							if (obj!=null) dataSource = (DataSource)obj;
						}
					}
					return dataSource.getConnection();
				}
				catch (NamingException e) {
					useDataSource = false;
					String msg = "Erro JNDI criando conexão MySQL. Conexões serão abertas SEM POOL.";
					logger.error(msg, e);
				}
			}
			
			return DriverManager.getConnection(JDBC_URL, username, password);
			
		}
		catch (SQLException e) {
			String msg = "Erro criando conexão MySQL.";
			logger.error(MarkerFactory.getMarker("FATAL"), msg, e);
			throw new ImpossivelObterConexaoException(msg, e);
		}
		
	}



	@Override
	public String getOwner() {
		return "";
	}


	@Override
	public Map<Class<?>, DataAccessObject<?>> getMapaDAO() {
		if (mapaDAO == null){
			mapaDAO = new HashMap<Class<?>, DataAccessObject<?>>();
			Class<?>[] classes = new Class<?>[]{
					Receita.class
			};
			DataAccessObject<?>[] daos = new DataAccessObject<?>[]{
					receitaDAO
			};
			for (int i=0; i<classes.length;i++){
				mapaDAO.put(classes[i], daos[i]);
			}
		}
		return mapaDAO;
	}





}
