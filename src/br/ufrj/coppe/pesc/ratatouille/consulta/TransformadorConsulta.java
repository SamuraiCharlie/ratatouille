package br.ufrj.coppe.pesc.ratatouille.consulta;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import br.ufrj.coppe.pesc.ratatouille.util.DamerauLevenshtein;

/**
 * Classe que auxilia a transformar os termos de consulta para termos do vocabulário de indexação (alimentos).
 *
 */
public class TransformadorConsulta {

	private Set<String> termosIndexacao;
	private Map<String, String> mapTransformacao;
	
	public TransformadorConsulta() {
		mapTransformacao = new HashMap<String, String>();
	}

	
	
	/**
	 * Define quais são os termos de indexação.
	 * @param lst
	 */
	public void setTermosIndexacao(Set<String> set) {
		this.termosIndexacao = set;
	}
	
	
	
	
	/**
	 * Transforma a consulta passada como parâmetro numa consulta com termos de indexação.
	 * @param consulta consulta do usuário
	 * @return lista de termos de indexação
	 */
	public String transformarConsulta(String consulta){
		String tokens [] = consulta.split(" ");
		StringBuilder strb = new StringBuilder();
		for (String token : tokens){
			strb.append(corrigir(token)).append(" ");
		}
		
		return strb.toString();
	}
	
	
	
	/**
	 * Mantém um mapa entre termos de consulta do usuário e termos de indexação.
	 * @param token palavra da consulta do usuário.
	 * @return termo de indexação correspondente.
	 */
	private String corrigir(String token){
		String termoIndexacao = mapTransformacao.get(token);
		if (termoIndexacao == null){
			termoIndexacao = obterTermoMenorDistancia(token);
			mapTransformacao.put(token, termoIndexacao);
		}
		return termoIndexacao;
	}
	
	
	
	/**
	 * Encontra o termo de indexação correspondente ao termo da consulta do usuário.
	 * @param termo termo da consulta do usuário.
	 * @return termo de indexação correspondente.
	 */
	private String obterTermoMenorDistancia(String termo){
		String termoMenorDistancia = termo;
		int menorDistancia = termo.length();
		int distancia;
		for (String termoIndexacao : termosIndexacao){
			distancia = DamerauLevenshtein.calculateOptimalStringAlignmentDistance(termo.toCharArray(), termoIndexacao.toCharArray());
			if (distancia < menorDistancia){
				menorDistancia = distancia;
				termoMenorDistancia = termoIndexacao;
			}
		}
		return termoMenorDistancia;
	}
}
