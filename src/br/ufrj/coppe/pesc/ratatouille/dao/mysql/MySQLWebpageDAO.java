package br.ufrj.coppe.pesc.ratatouille.dao.mysql;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

import br.com.felix.orm.BaseDAOFactory;
import br.com.felix.orm.FRMStatement;
import br.com.felix.orm.exception.ImpossivelObterDadosException;
import br.com.felix.orm.exception.InicializacaoFRMStatementException;
import br.ufrj.coppe.pesc.ratatouille.dao.WebpageDAO;
import br.ufrj.coppe.pesc.ratatouille.model.Webpage;

public class MySQLWebpageDAO extends MySQLBaseDAO<Webpage> implements WebpageDAO{

	private static final Logger logger = LoggerFactory.getLogger(MySQLWebpageDAO.class);
	
	public MySQLWebpageDAO(BaseDAOFactory factory) {
		super(Webpage.class, factory);
	}

	@Override
	public List<Webpage> obterComConteudo() throws ImpossivelObterDadosException {
		List<Webpage> lst = new ArrayList<Webpage>();
		try (FRMStatement stmt = getFRMStatement("SELECT id, content FROM webpage WHERE content IS NOT NULL")) {
			ResultSet rs = stmt.executeQuery();
			Webpage w;
			Blob blob;
			InputStream is;
			StringBuilder strb = new StringBuilder();;
			int lidos;
			byte[] b;
			while(rs.next()){
				w = new Webpage();
				w.setUrl(rs.getString("id"));
				
				blob = rs.getBlob("content");
				is = blob.getBinaryStream();
				strb.delete(0, strb.length());
				lidos = 0;
				b = new byte[2048];
				String temp = null;
				while ((lidos = is.read(b)) != -1) {
					temp = new String(b, 0, lidos);
					strb.append(temp);
				}
				w.setContent(strb.toString());
				lst.add(w);
			}
		}
		catch (InicializacaoFRMStatementException e) {
			String msg = "Erro consultando webpages.";
			logger.error(msg, e);
			throw new ImpossivelObterDadosException(msg, e);
		}
		catch (SQLException e) {
			String msg = "Erro de SQL consultando webpages.";
			logger.error(msg, e);
			throw new ImpossivelObterDadosException(msg, e);
		}
		catch (IOException e) {
			String msg = "Erro de IO consultando webpages.";
			logger.error(msg, e);
			throw new ImpossivelObterDadosException(msg, e);
		}
		return lst;
	}

}
