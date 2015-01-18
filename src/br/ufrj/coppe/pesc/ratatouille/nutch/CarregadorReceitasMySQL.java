package br.ufrj.coppe.pesc.ratatouille.nutch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelCadastrarReceitaException;
import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelCarregarReceitasAPartirDasPaginasWebException;
import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelConsultarListaAlimentosException;
import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelExcluirReceitasException;
import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelObterWebpagesComConteudoException;
import br.ufrj.coppe.pesc.ratatouille.model.Receita;
import br.ufrj.coppe.pesc.ratatouille.model.Webpage;
import br.ufrj.coppe.pesc.ratatouille.service.AlimentoService;
import br.ufrj.coppe.pesc.ratatouille.service.ReceitaService;
import br.ufrj.coppe.pesc.ratatouille.service.ServiceLocator;
import br.ufrj.coppe.pesc.ratatouille.service.WebpageService;

public class CarregadorReceitasMySQL {
	
	private static final Logger logger = LoggerFactory.getLogger(CarregadorReceitasMySQL.class);

	/**
	 * Apaga a base de dados de receitas e as carrega a partir das páginas web.
	 * */
	public void recarregarReceitasAPartirDasPaginasWeb() throws ImpossivelCarregarReceitasAPartirDasPaginasWebException {
		ParserReceitas parser = new ParserReceitas();
		ServiceLocator serviceLocator = ServiceLocator.instance();
		WebpageService ws = serviceLocator.getWebpageService();
		ReceitaService rs = serviceLocator.getReceitaService();
		AlimentoService as = serviceLocator.getAlimentoService();
		try {
			parser.setAlimentos(as.obterListaAlimentos());
			rs.excluirReceitas();
			List<Webpage> res = ws.obterWepbagesComConteudo();
			for (Webpage w : res) {
				Receita receita = parser.parseWebpage(w);
				if (receita!=null){
					rs.cadastrarReceita(receita);
				}
			}
		}
		catch (ImpossivelObterWebpagesComConteudoException | ImpossivelCadastrarReceitaException | ImpossivelExcluirReceitasException | ImpossivelConsultarListaAlimentosException e) {
			String msg = "Erro realizando carga de receitas a partir das páginas web.";
			logger.error(msg, e);
			throw new ImpossivelCarregarReceitasAPartirDasPaginasWebException(msg, e);
		}
	}

	
	
	public static void main(String args[]){
		CarregadorReceitasMySQL carregador = new CarregadorReceitasMySQL();
		try {
			logger.info("Início da carga.");
			carregador.recarregarReceitasAPartirDasPaginasWeb();
			logger.info("Fim da carga");
		}
		catch (ImpossivelCarregarReceitasAPartirDasPaginasWebException e) {
			logger.error("Erro carregando receitas.");
			e.printStackTrace();
		}
	}
}
