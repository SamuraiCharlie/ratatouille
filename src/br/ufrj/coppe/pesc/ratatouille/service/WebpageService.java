package br.ufrj.coppe.pesc.ratatouille.service;

import java.util.List;

import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelObterWebpagesComConteudoException;
import br.ufrj.coppe.pesc.ratatouille.model.Webpage;

/**
 * Serviços relacionados com a entidade Webpage.
 *
 */
public interface WebpageService {

	/**
	 * Obtém lista de todas webpages que o possuam conteúdo. 
	 * @return lista de webpages com conteúdo.
	 * @throws ImpossivelObterWebpagesComConteudoException caso não seja possível consultar a base de dados.
	 */
	public List<Webpage> obterWepbagesComConteudo() throws ImpossivelObterWebpagesComConteudoException;
}
