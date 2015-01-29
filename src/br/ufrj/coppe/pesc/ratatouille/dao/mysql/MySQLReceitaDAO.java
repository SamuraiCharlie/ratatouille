package br.ufrj.coppe.pesc.ratatouille.dao.mysql;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.felix.orm.BaseDAOFactory;
import br.com.felix.orm.exception.ImpossivelExcluirException;
import br.com.felix.orm.exception.ImpossivelObterDadosException;
import br.com.felix.orm.exception.InicializacaoFRMStatementException;
import br.ufrj.coppe.pesc.ratatouille.dao.ReceitaDAO;
import br.ufrj.coppe.pesc.ratatouille.model.Receita;


public class MySQLReceitaDAO extends MySQLBaseDAO<Receita> implements ReceitaDAO{
	private static final Logger logger = LoggerFactory.getLogger(MySQLReceitaDAO.class);

	public MySQLReceitaDAO(BaseDAOFactory factory) {
		super(Receita.class, factory);
	}

	
	
	@Override
	public List<Receita> obterPorIngredientes(String... ingredientes) throws ImpossivelObterDadosException {
		StringBuilder strb = new StringBuilder("(rec_ingredientes LIKE '%").append(ingredientes[0]).append("%' OR rec_nome LIKE '%").append(ingredientes[0]).append("%')");
		for (int i=1;i<ingredientes.length;i++){
			strb.append(" OR (rec_ingredientes LIKE '%").append(ingredientes[i]).append("%' OR rec_nome LIKE '%").append(ingredientes[i]).append("%')");
		}
		
		return getFRMObjectStatement().obterOnde(strb.toString());
	}



	@Override
	public void excluirTodas() throws ImpossivelExcluirException {
		try {
			getFRMStatement("DELETE FROM REC_RECEITA").executeUpdate();
		}
		catch (InicializacaoFRMStatementException | SQLException e) {
			String msg = "Erro apagando receitas.";
			logger.error(msg, e);
			throw new ImpossivelExcluirException(msg, e);
		}
	}



	@Override
	public Receita obterPorNome(String nome) throws ImpossivelObterDadosException {
		List<Receita> result = getFRMObjectStatement().obterOnde("rec_nome = '" + nome + "'");
		if (result.isEmpty()) return null;
		return result.get(0);
	}
}
