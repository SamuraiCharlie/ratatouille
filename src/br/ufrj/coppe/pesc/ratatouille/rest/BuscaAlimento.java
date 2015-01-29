package br.ufrj.coppe.pesc.ratatouille.rest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelConsultarAlimentoPorNomeException;
import br.ufrj.coppe.pesc.ratatouille.model.Alimento;
import br.ufrj.coppe.pesc.ratatouille.service.AlimentoService;
import br.ufrj.coppe.pesc.ratatouille.service.ServiceLocator;

/**
 * Realiza a busca de receitas por ingredientes.
 *
 */
@Path("/buscaalimento/{alimento}")
public class BuscaAlimento {

	
	private static final Logger logger = LoggerFactory.getLogger(BuscaAlimento.class);

	
	
	/**
	 * Efetua a busca de receitas por ingredientes.
	 * @param query termos da busca do usuário.
	 * @return Lista de receitas no formato JSON.
	 */
	@GET
	@Produces("text/html")
	public String buscaAlimento(@PathParam("alimento") String nomeAlimento) {
		AlimentoService as = ServiceLocator.instance().getAlimentoService();
		Alimento alimento;
		try {
			alimento = as.obterPorNome(nomeAlimento);
			return alimento.getInformacoes();
		}
		catch (ImpossivelConsultarAlimentoPorNomeException e) {
			return "erro";
		}
	}
}
