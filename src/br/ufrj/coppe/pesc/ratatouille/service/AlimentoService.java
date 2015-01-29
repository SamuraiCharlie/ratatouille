package br.ufrj.coppe.pesc.ratatouille.service;

import java.util.List;

import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelAtualizarFrequenciaAlimentoException;
import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelConsultarAlimentoPorNomeException;
import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelConsultarListaAlimentosException;
import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelCorrelacionarAlimentosException;
import br.ufrj.coppe.pesc.ratatouille.model.Alimento;

public interface AlimentoService {

	
	/**
	 * Obtém lista de todos os alimentos cadastrados.
	 * @return lista de alimentos ordenada por tamanho do nome.
	 * @throws ImpossivelConsultarListaAlimentosException 
	 */
	List<Alimento> obterListaAlimentos() throws ImpossivelConsultarListaAlimentosException;

	
	
	 /**
	 * Obtém a referência de um alimento pelo seu nome.
	 * @param string
	 * @return
	 * @throws ImpossivelConsultarAlimentoPorNomeException 
	 */
	Alimento obterPorNome(String string) throws ImpossivelConsultarAlimentoPorNomeException;



	/**
	 * Atualiza os dados frequência do alimento.
	 * @param alimento
	 * @throws ImpossivelAtualizarFrequenciaAlimentoException 
	 */
	void atualizar(Alimento alimento) throws ImpossivelAtualizarFrequenciaAlimentoException;


	/**
	 * Faz a conta de quantas vezes um ingrediente aparece junto de outro.
	 * @throws ImpossivelCorrelacionarAlimentosException 
	 * */
	void correlacionarAlimentos() throws ImpossivelCorrelacionarAlimentosException;



	/**
	 * Consulta os n alimentos que ocorrem nas mesmas receitas passadas como parâmetro.
	 * @param alimento
	 * @param n
	 * @return
	 * @throws ImpossivelConsultarListaAlimentosException 
	 */
	List<Alimento> obterAlimentosCorrelacionados(Alimento alimento, int n) throws ImpossivelConsultarListaAlimentosException;

}
