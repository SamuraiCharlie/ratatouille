package br.ufrj.coppe.pesc.ratatouille.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelBuscarReceitaPorIngredienteException;
import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelCadastrarReceitaException;
import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelConsultarAlimentoPorNomeException;
import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelConsultarReceitaPorNomeException;
import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelExcluirReceitaException;
import br.ufrj.coppe.pesc.ratatouille.model.Alimento;
import br.ufrj.coppe.pesc.ratatouille.model.Ingrediente;
import br.ufrj.coppe.pesc.ratatouille.model.InstrucaoPreparo;
import br.ufrj.coppe.pesc.ratatouille.model.Receita;
import br.ufrj.coppe.pesc.ratatouille.service.ReceitaService;
import br.ufrj.coppe.pesc.ratatouille.service.ServiceLocator;

@RunWith(JUnit4.class)
public class TestReceitaService {

	private ReceitaService rs = ServiceLocator.instance().getReceitaService();
	private AlimentoService as = ServiceLocator.instance().getAlimentoService();
	
	@Test
	public void testBuscaPorIngredientes() throws ImpossivelBuscarReceitaPorIngredienteException {
		List<Receita> lst = rs.buscaPorIngredientes("maminha", ReceitaService.TipoBusca.MySQL);
		
		assertNotNull(lst);
		assertFalse(lst.isEmpty());
		
		Receita rMaminha = lst.get(0);
		assertEquals("Receita de Maminha assada - Comida e Receitas", rMaminha.getNome());
	}
	
	
	
	@Test
	public void testCadastroERecuperacaoDeReceita() throws ImpossivelConsultarAlimentoPorNomeException, ImpossivelCadastrarReceitaException, ImpossivelConsultarReceitaPorNomeException, ImpossivelExcluirReceitaException{
		Receita rTeste = rs.buscaPorNome("Receita teste");
		if (rTeste!=null) rs.excluirReceita(rTeste);
		
		Receita receita = new Receita();
		receita.setNome("Receita teste");
		receita.setHtml("<html><body><h1>Receita</h1><ul><li>1kg de tomate</li><li>2 folhas de alface</li></ul>");
		receita.setUrl("http://localhost:8080/teste");
		receita.setUrlImagem("http://upload.wikimedia.org/wikipedia/commons/4/4c/Tomato_scanned.jpg");
		receita.setTextoIngredientes("1kg de tomate 2 folhas de alface");
		receita.setTextoModoPreparo("Corte o tomate em cubinhos. Junte o alface com o tomate.");
		
		Alimento aTomate = as.obterPorNome("tomate");
		Ingrediente tomate = new Ingrediente();
		tomate.setTexto("1kg de tomate");
		tomate.setHtml("<li>1kg de <span class=\"alimento\">tomate</span>");
		tomate.setAlimento(aTomate);
		
		Alimento aAlface = as.obterPorNome("alface");
		Ingrediente alface = new Ingrediente();
		alface.setTexto("2 folhas de alface");
		alface.setHtml("<li>2 folhas de <span class=\"alimento\">alface</span>");
		alface.setAlimento(aAlface);
		
		
		List<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
		ingredientes.add(tomate);
		ingredientes.add(alface);
		
		List<InstrucaoPreparo> modoPreparo = new ArrayList<InstrucaoPreparo>();
		modoPreparo.add(new InstrucaoPreparo("Corte o tomate em cubinhos.", 1));
		modoPreparo.add(new InstrucaoPreparo("Junte o alface com o tomate.", 2));
		
		receita.setIngredientes(ingredientes);
		receita.setModoPreparo(modoPreparo);
		
		rs.cadastrarReceita(receita);
		
		Receita receitaResult = rs.buscaPorNome("Receita teste");
		assertEquals(receita, receitaResult);
	}

}
