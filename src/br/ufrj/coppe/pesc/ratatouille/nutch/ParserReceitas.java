package br.ufrj.coppe.pesc.ratatouille.nutch;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelConsultarListaAlimentosException;
import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelObterWebpagesComConteudoException;
import br.ufrj.coppe.pesc.ratatouille.model.Alimento;
import br.ufrj.coppe.pesc.ratatouille.model.Ingrediente;
import br.ufrj.coppe.pesc.ratatouille.model.InstrucaoPreparo;
import br.ufrj.coppe.pesc.ratatouille.model.Receita;
import br.ufrj.coppe.pesc.ratatouille.model.Webpage;
import br.ufrj.coppe.pesc.ratatouille.service.AlimentoService;
import br.ufrj.coppe.pesc.ratatouille.service.ServiceLocator;
import br.ufrj.coppe.pesc.ratatouille.service.WebpageService;

/**
 * Realiza o parse de páginas web de receitas.
 *
 */
public class ParserReceitas {

	private static final Logger logger = LoggerFactory.getLogger(ParserReceitas.class);
	
	private List<Alimento> alimentos;
	
	public List<Alimento> getAlimentos() {
		return alimentos;
	}


	public void setAlimentos(List<Alimento> alimentos) {
		this.alimentos = alimentos;
	}


	public static void main(String[] args) throws SQLException, IOException {

		PrintWriter writer = new PrintWriter("d:/temp/ingredientes_vinicius.txt", "UTF-8");

		try {
			ParserReceitas parser = new ParserReceitas();
			WebpageService ws = ServiceLocator.instance().getWebpageService();
			List<Webpage> res = ws.obterWepbagesComConteudo();
			int count = 0;
			for (Webpage w : res) {
				Receita receita = parser.parseWebpage(w);
				if (receita != null) {
					writer.write(receita.toString());
					count++;
				}
				if (count == 10) break;
			}
			writer.println("Total de receitas " + count);
			writer.close();
			System.out.println("FINISHED");
		}
		catch(ImpossivelObterWebpagesComConteudoException e){
			String msg = "Erro consultando banco de dados.";
			logger.error(msg, e);
		}
		
	}

	
	public Receita parseWebpage(Webpage webpage){
		Document doc = Jsoup.parse(webpage.getContent());
		if (verificaSeEhPaginaDeReceita(doc)) {
			Elements enumeracoes = doc.select(".contentpaneopen tbody tr:eq(1) td ul");
			Element elementIngredientes = enumeracoes.get(0);

			Receita receita = new Receita();
			if (elementIngredientes != null) {
				String titulo = pegaTituloReceita(doc);
				
				receita.setHtml(webpage.getContent());
				receita.setUrl(inverterUrl(webpage.getUrl()));
				receita.setNome(titulo);
				
				StringBuilder strb = new StringBuilder();
				Elements ingredientes = elementIngredientes.children();
				List<Ingrediente> lstIngredientes = new ArrayList<Ingrediente>();
				Ingrediente ingrediente;
				for (Element e : ingredientes){
					ingrediente = new Ingrediente();
					ingrediente.setTexto(e.text());
					definirHtml(ingrediente);
					lstIngredientes.add(ingrediente);
					strb.append(ingrediente.getHtml()).append("\n");
				}
				receita.setIngredientes(lstIngredientes);
				receita.setTextoIngredientes(strb.toString());

			}
			
			//System.out.println("###########################################################\n\n\n");
			//System.out.println(webpage.getContent());
			if (enumeracoes.size()>1){
				Element elementModoPreparo = enumeracoes.get(1);
				if (elementModoPreparo != null) {
					StringBuilder strb = new StringBuilder();
					Elements modoPreparo = elementModoPreparo.children();
					Element e;
					List<InstrucaoPreparo> lstInstrucaoPreparo = new ArrayList<InstrucaoPreparo>();
					InstrucaoPreparo instrucao;
					for (int i = 0; i < modoPreparo.size(); i++){
						e = modoPreparo.get(i);
						instrucao = new InstrucaoPreparo();
						instrucao.setTexto(e.text());
						instrucao.setOrdem(i);
						lstInstrucaoPreparo.add(instrucao);
						strb.append(e).append("\n");
					}
					receita.setModoPreparo(lstInstrucaoPreparo);
					receita.setTextoModoPreparo(strb.toString());
				}
			}
			
			Elements parentElements = elementIngredientes.parents();
			String text = parentElements.html();
			Pattern p = Pattern.compile("src=\"([^\"]*)\"");
			Matcher m = p.matcher(text);
			if (m.find()){
				receita.setUrlImagem(m.group(1));
			}
			
			if (receita.getTextoIngredientes()!=null) return receita;
		}
		return null;
	}
	
	
	
	private void definirHtml(Ingrediente ingrediente) {
		String texto = ingrediente.getTexto();
		for (Alimento alimento: alimentos){
			if (texto.indexOf(alimento.getNome())>=0){
				ingrediente.setHtml("<li>" + texto.replace(alimento.getNome(), "<span class=\"alimento\">"+alimento.getNome()+"</span>") + "</li>");
				return;
			}
		}
		logger.debug("Não identificou alimento para ==> " + texto);
	}
	
	
	
	/**
	 * Obtém nome da receita.
	 * @param doc
	 * @return
	 */
	private String pegaTituloReceita(Document doc) {
		Element e1 = doc.select(".item, .contentheading").first();
		if (e1 != null) {
			String titulo = e1.text();
			if (titulo.startsWith("Receita de")){
				titulo = titulo.substring("Receita de".length());
			}
			return titulo;
		}

		return null;
	}

	
	
	/**
	 * Verifica se a webpage passada como parâmetro contém uma receita de culinária.
	 * @param doc
	 * @return
	 */
	private boolean verificaSeEhPaginaDeReceita(Document doc) {
		Element e1 = doc.select(".contentpaneopen tbody tr:eq(1) td p:eq(0)").first();
		if (e1 != null) {
			if (e1.text().contains("Ingredientes")) {
				return true;
			}
		}

		Element e2 = doc.select(".contentpaneopen tbody tr:eq(1) td p:eq(1)").first();
		if (e2 != null) {
			if (e2.text().contains("Ingredientes")) {
				return true;
			}
		}

		return false;
	}
	
	
	
	/**
	 * Pega uma URL invertida e a coloca na forma normal, da esquerda para a direita.
	 * @param url URL a ser invertida.
	 * @return URL da forma como é utilizada pelos browsers.
	 */
	private String inverterUrl(String url){
		if (url == null) return null;
		String dominioInvertido = url.substring(0, url.indexOf(":"));
		String[] partesDominio = dominioInvertido.split("\\.");
		StringBuilder strb = new StringBuilder();
		strb.append(url.substring(url.indexOf(":")+1,url.indexOf("/"))).append("://");
		for (int i=0;i<partesDominio.length-1;i++){
			strb.append(partesDominio[partesDominio.length-1-i]).append(".");
		}
		strb.append(partesDominio[0]);
		strb.append(url.substring(url.indexOf("/")));
		return strb.toString();
	}
}
