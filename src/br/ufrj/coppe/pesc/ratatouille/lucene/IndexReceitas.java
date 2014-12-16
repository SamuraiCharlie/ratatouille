package br.ufrj.coppe.pesc.ratatouille.lucene;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelBuscarReceitaPorIngredienteException;
import br.ufrj.coppe.pesc.ratatouille.model.Receita;
import br.ufrj.coppe.pesc.ratatouille.service.ReceitaService;
import br.ufrj.coppe.pesc.ratatouille.service.ServiceLocator;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;


public class IndexReceitas {
	
	public static String FIELD_NOME = "nome";
	
	public static String FIELD_INGREDIENTES = "ingredientes";
	
	public static String FIELD_LINK = "link";
	
	public static String PATH_INDEX = "/Users/vinicius/indexReceitas/";
	

	private IndexReceitas() {}

	
	public static void main(String[] args) throws IOException, ImpossivelBuscarReceitaPorIngredienteException{

		ReceitaService rs = ServiceLocator.instance().getReceitaService();
		List<Receita> receitas = rs.buscaTodos();
		
		Date start = new Date();
		System.out.println("Indexando em diretório '" + PATH_INDEX + "'...");

		Directory dir = FSDirectory.open(new File(PATH_INDEX));
		Analyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_4_10_2, analyzer);
		iwc.setOpenMode(OpenMode.CREATE);

		IndexWriter writer = new IndexWriter(dir, iwc);
		
		for (Receita receita : receitas) {
			indexaReceita(writer, receita);
		}
		
		writer.forceMerge(1);
		writer.close();

		Date end = new Date();
		System.out.println("Tempo total - " + (end.getTime() - start.getTime()));
	}


	static void indexaReceita(IndexWriter writer, Receita receita)
			throws IOException {

		Document doc = new Document();


		Field nomeField = new TextField(FIELD_NOME, receita.getNome(), Field.Store.YES);
		nomeField.setBoost(1.5f);
		doc.add(nomeField);
		
		Field fieldIngredientes = new TextField(FIELD_INGREDIENTES, receita.getIngredientes(), Field.Store.NO);
		fieldIngredientes.setBoost(0.5f);
		doc.add(fieldIngredientes);
		
		Field linkField = new TextField(FIELD_LINK, receita.getLink(), Field.Store.YES);
		doc.add(linkField);


//		Field fieldIngredientesPrincipais = new TextField("ingredientesPrincipais", getIngredientesPrincipais(receita), Field.Store.NO);
//		fieldIngredientesPrincipais.setBoost(1.5f);
//		doc.add(fieldIngredientesPrincipais);

		writer.addDocument(doc);


	}

//	private static String getIngredientesPrincipais(Receita receita) {
//
//		StringBuilder ingredientesPrincipais = new StringBuilder();
//		String[] ingredientes = receita.getIngredientes().split(" ");
//
//		for (String ingrediente : ingredientes) {
//			if(receita.getNome().toLowerCase().contains(ingrediente.toLowerCase())){
//				ingredientesPrincipais.append(ingrediente + " ");
//			}
//		}
//
//		return ingredientesPrincipais.toString();
//	}
}
