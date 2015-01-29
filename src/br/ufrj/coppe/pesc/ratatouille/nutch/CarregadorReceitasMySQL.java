package br.ufrj.coppe.pesc.ratatouille.nutch;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelAtualizarFrequenciaAlimentoException;
import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelBuscarReceitaPorIngredienteException;
import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelCadastrarReceitaException;
import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelCarregarReceitasAPartirDasPaginasWebException;
import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelConsultarListaAlimentosException;
import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelContarFrequenciaDosAlimentosException;
import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelCorrelacionarAlimentosException;
import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelExcluirReceitasException;
import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelObterWebpagesComConteudoException;
import br.ufrj.coppe.pesc.ratatouille.exception.ImpossivelReunirInformacoesAlimentosException;
import br.ufrj.coppe.pesc.ratatouille.model.Alimento;
import br.ufrj.coppe.pesc.ratatouille.model.Receita;
import br.ufrj.coppe.pesc.ratatouille.model.Webpage;
import br.ufrj.coppe.pesc.ratatouille.service.AlimentoService;
import br.ufrj.coppe.pesc.ratatouille.service.ReceitaService;
import br.ufrj.coppe.pesc.ratatouille.service.ServiceLocator;
import br.ufrj.coppe.pesc.ratatouille.service.WebpageService;

public class CarregadorReceitasMySQL {
	
	private static final Logger logger = LoggerFactory.getLogger(CarregadorReceitasMySQL.class);

	/**
	 * Apaga a base de dados de receitas e as carrega a partir das páginas web.
	 * */
	public void recarregarReceitasAPartirDasPaginasWeb() throws ImpossivelCarregarReceitasAPartirDasPaginasWebException {
		ParserReceitas parser = new ParserReceitas();
		ServiceLocator serviceLocator = ServiceLocator.instance();
		WebpageService ws = serviceLocator.getWebpageService();
		ReceitaService rs = serviceLocator.getReceitaService();
		AlimentoService as = serviceLocator.getAlimentoService();
		try {
			List<Alimento> listaAlimentos = as.obterListaAlimentos();
			parser.setAlimentos(listaAlimentos);
			rs.excluirReceitas();
			List<Webpage> res = ws.obterWepbagesComConteudo();
			for (Webpage w : res) {
				Receita receita = parser.parseWebpage(w);
				if (receita!=null){
					rs.cadastrarReceita(receita);
				}
			}
		}
		catch (ImpossivelObterWebpagesComConteudoException | ImpossivelCadastrarReceitaException | ImpossivelExcluirReceitasException | ImpossivelConsultarListaAlimentosException e) {
			String msg = "Erro realizando carga de receitas a partir das páginas web.";
			logger.error(msg, e);
			throw new ImpossivelCarregarReceitasAPartirDasPaginasWebException(msg, e);
		}
	}
	
	
	
	/**
	 * Conta a frequência dos alimentos nas receitas.
	 * @throws ImpossivelContarFrequenciaDosAlimentosException caso não seja possível realizar a tarefa.
	 */
	public void contarFrequenciaDosAlimentos() throws ImpossivelContarFrequenciaDosAlimentosException{
		ServiceLocator serviceLocator = ServiceLocator.instance();
		ReceitaService rs = serviceLocator.getReceitaService();
		AlimentoService as = serviceLocator.getAlimentoService();
		try {
			List<Receita> receitas = rs.buscaTodos();
			List<Alimento> alimentos = as.obterListaAlimentos();
			int frequencia;
			for (Alimento alimento : alimentos){
				frequencia = 0;
				for (Receita receita: receitas){
					if (receita.getTextoIngredientes().contains(alimento.getNome())){
						frequencia++;
					}
				}
				alimento.setFrequencia(frequencia);
				as.atualizar(alimento);
			}
		}
		catch (ImpossivelBuscarReceitaPorIngredienteException | ImpossivelConsultarListaAlimentosException | ImpossivelAtualizarFrequenciaAlimentoException e) {
			String msg = "Erro contando frequência dos alimentos.";
			logger.error(msg, e);
			throw new ImpossivelContarFrequenciaDosAlimentosException();
		}
	}

	
	
	/**
	 * Conta as vezes que os alimentos aparecem juntos em receitas.
	 * @throws ImpossivelCorrelacionarAlimentosException
	 */
	public void correlacionarAlimentos() throws ImpossivelCorrelacionarAlimentosException {
		ServiceLocator serviceLocator = ServiceLocator.instance();
		AlimentoService as = serviceLocator.getAlimentoService();
		try {
			as.correlacionarAlimentos();
		}
		catch (ImpossivelCorrelacionarAlimentosException e) {
			String msg = "Erro correlacionando alimentos.";
			logger.error(msg, e);
			throw e;
		}
	}
	
	
	
	/**
	 * Reúne informações dos alimentos e atualiza tabela de alimentos.
	 * @throws ImpossivelReunirInformacoesAlimentosException
	 */
	public void reunirInformacoesAlimentos() throws ImpossivelReunirInformacoesAlimentosException{
		ServiceLocator serviceLocator = ServiceLocator.instance();
		AlimentoService as = serviceLocator.getAlimentoService();
		try {
			List<Alimento> alimentos = as.obterListaAlimentos();
			Collections.sort(alimentos, new Comparator<Alimento>() {
				@Override
				public int compare(Alimento a1, Alimento a2) {
					return -1 * a1.getFrequencia().compareTo(a2.getFrequencia());
				}
			});
			List<Alimento> correlacionados;
			StringBuilder strb = new StringBuilder();
			Alimento alimento;
			String nome;
			int maxAlimentosCorrelacionados = 5;
			for (int i=0; i< alimentos.size(); i++){
				strb.delete(0, strb.length());
				alimento = alimentos.get(i);
				correlacionados = as.obterAlimentosCorrelacionados(alimento, maxAlimentosCorrelacionados);
				nome = alimento.getNome();
				nome = nome.substring(0, 1).toUpperCase() + nome.substring(1);
				strb.append(nome).append(String.format(" é o %d&ordm; alimento mais utilizado.", i));
				if (!correlacionados.isEmpty()){
					strb.append(" Costuma ser utilizado com ");
					for (int j=0;j<correlacionados.size();j++){
						strb.append(correlacionados.get(j).getNome()).append(", ");
					}
					if (correlacionados.size()==maxAlimentosCorrelacionados){
						strb.append("etc, ");
					}
					strb.delete(strb.length()-2, strb.length());
					strb.append(".");
				}
				alimento.setInformacoes(strb.toString());
				as.atualizar(alimento);
			}
		}
		catch (ImpossivelConsultarListaAlimentosException | ImpossivelAtualizarFrequenciaAlimentoException e) {
			String msg = "Erro reunindo informações dos alimentos.";
			logger.error(msg, e);
			throw new ImpossivelReunirInformacoesAlimentosException(msg, e);
		}
	}
	
	
	public static void main(String args[]){
		CarregadorReceitasMySQL carregador = new CarregadorReceitasMySQL();
		try {
			logger.info("Início da carga.");
			carregador.recarregarReceitasAPartirDasPaginasWeb();
			carregador.contarFrequenciaDosAlimentos();
			carregador.correlacionarAlimentos();
			carregador.reunirInformacoesAlimentos();
			logger.info("Fim da carga");
		}
		catch (ImpossivelCarregarReceitasAPartirDasPaginasWebException e) {
			logger.error("Erro carregando receitas.");
			e.printStackTrace();
		}
		catch (ImpossivelContarFrequenciaDosAlimentosException e){
			logger.error("Erro contando frequência dos alimentos.");
			e.printStackTrace();
		}
		catch (ImpossivelCorrelacionarAlimentosException e) {
			logger.error("Erro atualizando frequência dos alimentos.");
			e.printStackTrace();
		}
		catch (ImpossivelReunirInformacoesAlimentosException e) {
			logger.error("Erro reunindo informações dos alimentos.");
			e.printStackTrace();
		}
	}
}
