package br.ufrj.coppe.pesc.ratatouille.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelBuscarReceitaPorIngredienteException;
import br.ufrj.coppe.pesc.ratatouille.model.Receita;
import br.ufrj.coppe.pesc.ratatouille.service.ReceitaService;
import br.ufrj.coppe.pesc.ratatouille.service.ServiceLocator;

@RunWith(JUnit4.class)
public class TestReceitaService {

	@Test
	public void testBuscaPorIngredientes() throws ImpossivelBuscarReceitaPorIngredienteException {
		ReceitaService rs = ServiceLocator.instance().getReceitaService();
		List<Receita> lst = rs.buscaPorIngredientes("maminha");
		
		assertNotNull(lst);
		assertFalse(lst.isEmpty());
		
		Receita rMaminha = lst.get(0);
		assertEquals("Receita de Maminha assada - Comida e Receitas", rMaminha.getNome());
	}

}
