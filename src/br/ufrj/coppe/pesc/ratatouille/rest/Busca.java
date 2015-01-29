package br.ufrj.coppe.pesc.ratatouille.rest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.ufrj.coppe.pesc.ratatouille.consulta.Ranqueador;
import br.ufrj.coppe.pesc.ratatouille.consulta.TransformadorConsulta;
import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelBuscarReceitaPorIngredienteException;
import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelConsultarListaAlimentosException;
import br.ufrj.coppe.pesc.ratatouille.model.Alimento;
import br.ufrj.coppe.pesc.ratatouille.model.Receita;
import br.ufrj.coppe.pesc.ratatouille.service.AlimentoService;
import br.ufrj.coppe.pesc.ratatouille.service.ReceitaService;
import br.ufrj.coppe.pesc.ratatouille.service.ReceitaService.TipoBusca;
import br.ufrj.coppe.pesc.ratatouille.service.ServiceLocator;

import com.google.gson.Gson;

/**
 * Realiza a busca de receitas por ingredientes.
 *
 */
@Path("/busca/{query}")
public class Busca {

	private static TransformadorConsulta transformadorConsulta;
	
	private static final Logger logger = LoggerFactory.getLogger(Busca.class);
	
	static{
		inicializarTransformadorConsulta();
	}

	
	
	/**
	 * Efetua a busca de receitas por ingredientes.
	 * @param query termos da busca do usuário.
	 * @return Lista de receitas no formato JSON.
	 */
	@GET
	@Produces("text/json")
	public String buscaReceitas(@PathParam("query") String consultaUsuario) {
		try {
			String query = consultaUsuario;
			TipoBusca tipoBusca = ReceitaService.TipoBusca.MySQL;
			ReceitaService rs = ServiceLocator.instance().getReceitaService();
			
			List<Receita> receitas = rs.buscaPorIngredientes(query, tipoBusca);
			if (receitas.size()==0){
				query = transformadorConsulta.transformarConsulta(consultaUsuario);
				logger.debug(String.format("Consulta usuário: '%s' ==> '%s'", consultaUsuario, query));
				receitas = rs.buscaPorIngredientes(query, tipoBusca);
			}
			Ranqueador.ranquear(receitas, query);
			Gson gson = new Gson();
			return gson.toJson(receitas);
		}
		catch (ImpossivelBuscarReceitaPorIngredienteException e) {
			return "erro";
		}
	}
	
	
	
	/**
	 * Obtém lista de alimentos para montar lista de termos de indexação. 
	 */
	private static void inicializarTransformadorConsulta() {
		transformadorConsulta = new TransformadorConsulta();
		AlimentoService as = ServiceLocator.instance().getAlimentoService();
		List<Alimento> lstAlimento;
		try {
			lstAlimento = as.obterListaAlimentos();
			Set<String> termosIndexacao = new HashSet<String>();
			String arr[];
			for (Alimento alimento: lstAlimento){
				arr = alimento.getNome().split(" ");
				for (String token : arr){
					termosIndexacao.add(token);
				}
			}
			transformadorConsulta.setTermosIndexacao(termosIndexacao);
		}
		catch (ImpossivelConsultarListaAlimentosException e) {
			String msg = "Erro consultando lista de alimentos.";
			logger.error(msg, e);
			throw new IllegalStateException(msg,e);
		}
	}

}
