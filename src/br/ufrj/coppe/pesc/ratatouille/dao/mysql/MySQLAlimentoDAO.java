package br.ufrj.coppe.pesc.ratatouille.dao.mysql;

import java.sql.SQLException;
import java.util.List;

import br.com.felix.orm.BaseDAOFactory;
import br.com.felix.orm.FRMObjectStatement;
import br.com.felix.orm.FRMStatement;
import br.com.felix.orm.ObjectBuilder;
import br.com.felix.orm.exception.BuildException;
import br.com.felix.orm.exception.ImpossivelAlterarException;
import br.com.felix.orm.exception.ImpossivelObterDadosException;
import br.com.felix.orm.exception.InicializacaoFRMStatementException;
import br.ufrj.coppe.pesc.ratatouille.dao.AlimentoDAO;
import br.ufrj.coppe.pesc.ratatouille.model.Alimento;

public class MySQLAlimentoDAO extends MySQLBaseDAO<Alimento> implements AlimentoDAO {

	public MySQLAlimentoDAO(BaseDAOFactory factory) {
		super(Alimento.class, factory);
	}



	@Override
	public List<Alimento> obterTodosOrdenadoPorTamanho() throws ImpossivelObterDadosException {
		return getFRMObjectStatement().obterOnde("TRUE ORDER BY CHAR_LENGTH(aig_nome) DESC");
	}



	@Override
	public Alimento obterPorNome(String string) throws ImpossivelObterDadosException {
		List<Alimento> result = getFRMObjectStatement().obterOnde("aig_nome = '"+string+"'");
		if (result.isEmpty()) return null;
		return result.get(0);
	}



	@Override
	public void correlacionarAlimentos() throws ImpossivelAlterarException {
		String sql = "DELETE FROM CAL_CORRELACAO_ALIMENTO";
		String sqlInserir = "INSERT INTO CAL_CORRELACAO_ALIMENTO (cal_id_alimento_a, cal_id_alimento_b, cal_frequencia) SELECT a.aig_id_alimento, b.aig_id_alimento, (SELECT COUNT(*) FROM ING_INGREDIENTE x JOIN ING_INGREDIENTE y WHERE x.ing_id_alimento = a.aig_id_alimento AND y.ing_id_alimento = b.aig_id_alimento AND x.ing_id_receita = y.ing_id_receita) FROM AIG_ALIMENTO_IG a JOIN AIG_ALIMENTO_IG b ON a.aig_nome <> b.aig_nome";
		try (FRMStatement stmt = getFRMStatement(sql); FRMStatement stmt2 = getFRMStatement(sqlInserir)) {
			stmt.executeUpdate();
			stmt2.executeUpdate();
		}
		catch (InicializacaoFRMStatementException | SQLException e) {
			String msg = "Erro correlacionando alimentos.";
			throw new ImpossivelAlterarException(msg, e);
		}
	}



	@Override
	public List<Alimento> obterAlimentosCorrelacionados(Alimento alimento, int n) throws ImpossivelObterDadosException {
		String sql = String.format("SELECT AIG_ALIMENTO_IG.*, cal_frequencia FROM AIG_ALIMENTO_IG JOIN CAL_CORRELACAO_ALIMENTO ON aig_id_alimento = cal_id_alimento_b WHERE cal_frequencia > 0 AND cal_id_alimento_a = %s ORDER BY cal_frequencia DESC LIMIT %s", alimento.getId(), n);
		ObjectBuilder<Alimento> alimentoBuilder = getFRMObjectStatement().getObjectBuilder();
		FRMStatement fstmt;
		try {
			fstmt = getFRMStatement(sql);
			return alimentoBuilder.buildObjects(fstmt.executeQuery());
		}
		catch (InicializacaoFRMStatementException | BuildException | SQLException e) {
			String msg = "Impossível obter alimentos correlacionados.";
			throw new ImpossivelObterDadosException(msg, e);
		}
	}




}
