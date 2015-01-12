package br.ufrj.coppe.pesc.ratatouille.nutch;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelConsultarListaAlimentosException;
import br.ufrj.coppe.pesc.ratatouille.model.Alimento;
import br.ufrj.coppe.pesc.ratatouille.model.Ingrediente;
import br.ufrj.coppe.pesc.ratatouille.model.InstrucaoPreparo;
import br.ufrj.coppe.pesc.ratatouille.model.Receita;
import br.ufrj.coppe.pesc.ratatouille.model.Webpage;
import br.ufrj.coppe.pesc.ratatouille.service.AlimentoService;
import br.ufrj.coppe.pesc.ratatouille.service.ServiceLocator;

@RunWith(JUnit4.class)
public class TestParserReceitas {

	private static List<Alimento> alimentos;

	@BeforeClass
	public static void setUp() throws ImpossivelConsultarListaAlimentosException{
		AlimentoService as = ServiceLocator.instance().getAlimentoService();
		alimentos = as.obterListaAlimentos();
	}
	
	
	
	@Test
	/**
	 * Testa parse dos ingredientes da receita.
	 * */
	public void testParseIngredientes() throws IOException, ImpossivelConsultarListaAlimentosException{
		String content = lerArquivoTeste("receita_teste.html");
		Webpage w = new Webpage();
		w.setContent(content);
		
		
		ParserReceitas parser = new ParserReceitas();
		parser.setAlimentos(alimentos);
		Receita r = parser.parseWebpage(w);
		assertNotNull(r);
		
		List<Ingrediente> lst = r.getIngredientes();
		assertNotNull("Lista de ingredientes não pode estar vazia.", lst);
		assertFalse(lst.isEmpty());
		
		assertEquals("2 colheres (sopa) de azeite de oliva", lst.get(0).getTexto());
		assertEquals("1 cebola média cortada em cubinhos", lst.get(1).getTexto());
		assertEquals("2 dentes de alho picados", lst.get(2).getTexto());
		assertEquals("1 frango cortado em pedaços", lst.get(3).getTexto());
		assertEquals("2 envelopes de caldo de galinha em pó", lst.get(4).getTexto());
		assertEquals("2 xícaras (chá) de arroz", lst.get(5).getTexto());
		assertEquals("1 cenoura média cortada em cubinhos", lst.get(6).getTexto());
		assertEquals("1 tomate sem pele e sem sementes picado", lst.get(7).getTexto());
		assertEquals("1 pimentão vermelho cortado em rodelas", lst.get(8).getTexto());
		assertEquals("¼ de xícara (chá) de cheiro verde picado", lst.get(9).getTexto());
		assertEquals("4 xícaras (chá) de água fervente", lst.get(10).getTexto());
		
		
		assertEquals("2 colheres (sopa) de <span class=\"alimento\">azeite de oliva</span>", lst.get(0).getHtml());
		assertEquals("1 <span class=\"alimento\">cebola</span> média cortada em cubinhos", lst.get(1).getHtml());
		assertEquals("2 dentes de <span class=\"alimento\">alho</span> picados", lst.get(2).getHtml());
		assertEquals("1 <span class=\"alimento\">frango</span> cortado em pedaços", lst.get(3).getHtml());
		assertEquals("2 envelopes de <span class=\"alimento\">caldo de galinha</span> em pó", lst.get(4).getHtml());
		assertEquals("2 xícaras (chá) de <span class=\"alimento\">arroz</span>", lst.get(5).getHtml());
		assertEquals("1 <span class=\"alimento\">cenoura</span> média cortada em cubinhos", lst.get(6).getHtml());
		assertEquals("1 <span class=\"alimento\">tomate</span> sem pele e sem sementes picado", lst.get(7).getHtml());
		assertEquals("1 <span class=\"alimento\">pimentão vermelho</span> cortado em rodelas", lst.get(8).getHtml());
		assertEquals("¼ de xícara (chá) de <span class=\"alimento\">cheiro verde</span> picado", lst.get(9).getHtml());
		assertEquals("4 xícaras (chá) de <span class=\"alimento\">água</span> fervente", lst.get(10).getHtml());
	}

	
	
	@Test
	/**
	 * Testa parse do modo de preparo da receita.
	 * */
	public void testParseModoDePreparo() throws IOException{
		String content = lerArquivoTeste("receita_teste.html");
		Webpage w = new Webpage();
		w.setContent(content);
		
		ParserReceitas parser = new ParserReceitas();
		parser.setAlimentos(alimentos);
		Receita r = parser.parseWebpage(w);
		assertNotNull(r);
		
		List<InstrucaoPreparo> lst = r.getModoPreparo();
		assertNotNull("Modo de preparo não pode estar vazio.", lst);
		assertFalse(lst.isEmpty());
		
		assertEquals("Leve ao fogo uma panela de pressão com o azeite, a cebola e o alho.", lst.get(0).getTexto());
		assertEquals(new Integer(0), lst.get(0).getOrdem());
		assertEquals("Frite acrescentando os cubos de frango e o caldo de galinha em pó.", lst.get(1).getTexto());
		assertEquals(new Integer(1), lst.get(1).getOrdem());
		assertEquals("Junte o arroz, a cenoura, o tomate, o pimentão e o cheiro verde e refogue.", lst.get(2).getTexto());
		assertEquals(new Integer(2), lst.get(2).getOrdem());
		assertEquals("Adicione a água, feche a panela e, depois de iniciar fervura,mantenha no fogo por 3 minutos.", lst.get(3).getTexto());
		assertEquals(new Integer(3), lst.get(3).getOrdem());
		assertEquals("Retire do fogo e deixe a panela tampada por 20 minutos.", lst.get(4).getTexto());
		assertEquals(new Integer(4), lst.get(4).getOrdem());
		assertEquals("Sirva a seguir.", lst.get(5).getTexto());
		assertEquals(new Integer(5), lst.get(5).getOrdem());
	}
	//<img class="photo" src="http://www.comidaereceitas.com.br/images/stories/2013/04/frango_panela_pressao.jpg" width="420" height="280" alt="Foto: Shutterstock" title="Foto: Shutterstock" />
	
	

	@Test
	/**
	 * Testa parse da URL da imagem.
	 * */
	public void testParseUrlImagem() throws IOException{
		String content = lerArquivoTeste("receita_teste.html");
		Webpage w = new Webpage();
		w.setContent(content);
		
		ParserReceitas parser = new ParserReceitas();
		parser.setAlimentos(alimentos);
		Receita r = parser.parseWebpage(w);
		assertNotNull(r);
		
		assertEquals("http://www.comidaereceitas.com.br/images/stories/2013/04/frango_panela_pressao.jpg", r.getUrlImagem());
		
	}

	
	
	/**
	 * Efetua leitura do arquivo passado como parâmetro. 
	 * @param nomeArquivo arquivo a ser lido.
	 * @return conteúdo do arquivo.
	 * @throws IOException caso haja erro lendo o arquivo.
	 */
	private String lerArquivoTeste(String nomeArquivo) throws IOException {
		InputStream is = getClass().getClassLoader().getResourceAsStream(nomeArquivo);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder strb = new StringBuilder();
		String linha;
		while((linha=br.readLine())!=null){
			strb.append(linha).append("\n");
		}
		return strb.toString();
	}

}
