package br.ufrj.coppe.pesc.ratatouille.service;

import java.util.List;

import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelConsultarAlimentoPorNomeException;
import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelConsultarListaAlimentosException;
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

}
