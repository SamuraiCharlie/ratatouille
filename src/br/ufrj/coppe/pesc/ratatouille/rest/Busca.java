package br.ufrj.coppe.pesc.ratatouille.rest;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.google.gson.Gson;

import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelBuscarReceitaPorIngredienteException;
import br.ufrj.coppe.pesc.ratatouille.model.Receita;
import br.ufrj.coppe.pesc.ratatouille.service.ReceitaService;
import br.ufrj.coppe.pesc.ratatouille.service.ServiceLocator;

@Path("/busca/{query}")
public class Busca {


	@GET
	@Produces("text/json")
	public String buscaReceitas(@PathParam("query") String query) {
		try {
			ReceitaService rs = ServiceLocator.instance().getReceitaService();
			List<Receita> receitas;
			receitas = rs.buscaPorIngredientes(query);
			Gson gson = new Gson();
			return gson.toJson(receitas);
		}
		catch (ImpossivelBuscarReceitaPorIngredienteException e) {
			return "erro";
		}
	}

}
