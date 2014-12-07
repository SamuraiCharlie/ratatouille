package br.ufrj.coppe.pesc.ratatouille.service;


public class ServiceLocator {

	private static ServiceLocator instance;
	private ReceitaService receitaService;

	private ServiceLocator() {
		receitaService = new ReceitaServiceImpl();
	}



	public static ServiceLocator instance() {
		if (instance == null) {
			instance = new ServiceLocator();
		}
		return instance;
	}

	
	
	public ReceitaService getReceitaService(){
		return receitaService;
	}

	
}
