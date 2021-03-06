package br.ufrj.coppe.pesc.ratatouille.dao;

import java.util.List;

import br.com.felix.orm.DataAccessObject;
import br.com.felix.orm.exception.ImpossivelExcluirException;
import br.com.felix.orm.exception.ImpossivelObterDadosException;
import br.ufrj.coppe.pesc.ratatouille.model.Receita;


/**
 * DAO para entidades Receita.
 *
 */
public interface ReceitaDAO extends DataAccessObject<Receita>{

	/**
	 * 
	 * Busca receitas pelos seus ingredientes.
	 * @param ingredientes lista de ingredientes.
	 * @return lista de receitas que contém os ingredientes informados.
	 * @throws ImpossivelObterDadosException quando não é possível recuperar as receitas.
	 */
	List<Receita> obterPorIngredientes(String... ingredientes) throws ImpossivelObterDadosException;

	
	
	/**
	 * Exclui todas as receitas. 
	 * @throws ImpossivelExcluirException quando não é possível excluir as receitas.
	 */
	void excluirTodas() throws ImpossivelExcluirException;



	/**
	 * Obtém uma receita pelo seu nome.
	 * @param nome nome da receita que se busca.
	 * @return receita cujo nome foi informado como parâmetro.
	 */
	Receita obterPorNome(String nome) throws ImpossivelObterDadosException;

}
