package br.ufrj.coppe.pesc.ratatouille.service;


public class ServiceLocator {

	private static ServiceLocator instance;
	private ReceitaService receitaService;
	private WebpageService webpageService;

	private ServiceLocator() {
		receitaService = new ReceitaServiceImpl();
		webpageService = new WebpageServiceImpl();
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



	public WebpageService getWebpageService() {
		return webpageService;
	}

	
}
