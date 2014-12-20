package br.ufrj.coppe.pesc.ratatouille.service;

import java.util.List;

import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelBuscarReceitaPorIngredienteException;
import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelCadastrarReceitaException;
import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelExcluirReceitasException;
import br.ufrj.coppe.pesc.ratatouille.model.Receita;


/**
 * Serviços relacionados com a entidade Receita.
 *
 */
public interface ReceitaService {

	
	/**
	 * Busca receitas por ingredientes.
	 * @param query termos de consulta em texto corrido.
	 * @return lista de receita que satisfazem a busca.
	 * @throws ImpossivelBuscarReceitaPorIngredienteException caso não seja possível executar a consulta.
	 */
	List<Receita> buscaPorIngredientes(String query) throws ImpossivelBuscarReceitaPorIngredienteException;
	
	
	
	/**
	 * Busca todas as receitas.
	 * @return lista de todas as receitas cadastradas.
	 * @throws ImpossivelBuscarReceitaPorIngredienteException
	 */
	List<Receita> buscaTodos() throws ImpossivelBuscarReceitaPorIngredienteException;


	
	/**
	 * Cadastra receita no banco de dados.
	 * @param receita a ser cadastrada.
	 * @throws ImpossivelCadastrarReceitaException quando não é possível cadastrar a receita.
	 */
	void cadastrarReceita(Receita receita) throws ImpossivelCadastrarReceitaException;



	/**
	 * Exclui todas as receitas do banco de dados.
	 * @throws ImpossivelExcluirReceitasException quando não é possível excluir as receitas do banco de dados.
	 */
	void excluirReceitas() throws ImpossivelExcluirReceitasException;

}
