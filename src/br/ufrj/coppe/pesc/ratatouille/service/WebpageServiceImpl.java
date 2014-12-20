package br.ufrj.coppe.pesc.ratatouille.service;

import java.util.List;

import br.com.felix.orm.exception.ImpossivelObterDadosException;
import br.ufrj.coppe.pesc.ratatouille.dao.RatatouilleDAOFactory;
import br.ufrj.coppe.pesc.ratatouille.dao.WebpageDAO;
import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelObterWebpagesComConteudoException;
import br.ufrj.coppe.pesc.ratatouille.model.Webpage;

public class WebpageServiceImpl implements WebpageService{

	@Override
	public List<Webpage> obterWepbagesComConteudo() throws ImpossivelObterWebpagesComConteudoException {
		RatatouilleDAOFactory daof = RatatouilleDAOFactory.instance();
		WebpageDAO wDAO = daof.getWebpageDAO();
		try{
			daof.beginTransaction();
			List<Webpage> result = wDAO.obterComConteudo();
			daof.commit();
			return result;
		}
		catch(ImpossivelObterDadosException e){
			daof.rollback();
			String msg = "Erro obtendo webpages.";
			throw new ImpossivelObterWebpagesComConteudoException(msg, e);
		}
	}

}
