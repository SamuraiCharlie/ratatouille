package br.ufrj.coppe.pesc.ratatouille.lucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import br.ufrj.coppe.pesc.ratatouille.model.Receita;

public class SearchReceitas {

	private static SearchReceitas instance;
	private IndexSearcher searcher;
	private QueryParser parser;

	private SearchReceitas() throws IOException{

		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(IndexReceitas.PATH_INDEX)));
		Analyzer analyzer = new StandardAnalyzer();

		searcher = new IndexSearcher(reader);
		parser = new QueryParser("ingredientes", analyzer);
	}

	public static SearchReceitas getInstance() throws IOException{

		if(instance == null){
			return instance = new SearchReceitas();
		}

		return instance;
	}


	public List<Receita> pesquisaReceitas(String pesquisa) throws ParseException, IOException{
		List<Receita> receitas = new ArrayList<Receita>();

		BooleanQuery booleanQuery = new BooleanQuery();

		booleanQuery.add(parser.parse(pesquisa), Occur.SHOULD);
		booleanQuery.add(parser.parse(IndexReceitas.FIELD_NOME + ":" + pesquisa), Occur.SHOULD);

		TopDocs results = searcher.search(booleanQuery, Integer.MAX_VALUE);

		ScoreDoc[] hits = results.scoreDocs;

		for (ScoreDoc scoreDoc : hits) {
			//System.out.println(searcher.explain(booleanQuery, scoreDoc.doc));
			Document doc = searcher.doc(scoreDoc.doc);
			//System.out.println(doc.get("nome") + " " +  scoreDoc.score);

			Receita receita = new Receita();
			receita.setNome(doc.get(IndexReceitas.FIELD_NOME));
			receita.setLink(doc.get(IndexReceitas.FIELD_LINK));
			
			receitas.add(receita);
		}

		return receitas;
	}

}
