package br.ufrj.coppe.pesc.ratatouille.nutch;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import br.ufrj.coppe.pesc.ratatouille.model.Ingrediente;
import br.ufrj.coppe.pesc.ratatouille.model.InstrucaoPreparo;
import br.ufrj.coppe.pesc.ratatouille.model.Receita;
import br.ufrj.coppe.pesc.ratatouille.model.Webpage;

@RunWith(JUnit4.class)
public class TestParserReceitas {

	
	@Test
	/**
	 * Testa parse dos ingredientes da receita.
	 * */
	public void testParseIngredientes() throws IOException{
		String content = lerArquivoTeste("receita_teste.html");
		Webpage w = new Webpage();
		w.setContent(content);
		
		ParserReceitas parser = new ParserReceitas();
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
		Receita r = parser.parseWebpage(w);
		assertNotNull(r);
		
		List<InstrucaoPreparo> lst = r.getModoPreparo();
		assertNotNull("Modo de preparo não pode estar vazio.", lst);
		assertFalse(lst.isEmpty());
		
		assertEquals("Leve ao fogo uma panela de pressão com o azeite, a cebola e o alho.", lst.get(0).getTexto());
		assertEquals(0, lst.get(0).getOrdem());
		assertEquals("Frite acrescentando os cubos de frango e o caldo de galinha em pó.", lst.get(1).getTexto());
		assertEquals(1, lst.get(1).getOrdem());
		assertEquals("Junte o arroz, a cenoura, o tomate, o pimentão e o cheiro verde e refogue.", lst.get(2).getTexto());
		assertEquals(2, lst.get(2).getOrdem());
		assertEquals("Adicione a água, feche a panela e, depois de iniciar fervura,mantenha no fogo por 3 minutos.", lst.get(3).getTexto());
		assertEquals(3, lst.get(3).getOrdem());
		assertEquals("Retire do fogo e deixe a panela tampada por 20 minutos.", lst.get(4).getTexto());
		assertEquals(4, lst.get(4).getOrdem());
		assertEquals("Sirva a seguir.", lst.get(5).getTexto());
		assertEquals(5, lst.get(5).getOrdem());
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
