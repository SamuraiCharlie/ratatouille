package br.ufrj.coppe.pesc.ratatouille.exception;

public class ImpossivelCarregarReceitasAPartirDasPaginasWebException extends Exception {

	private static final long serialVersionUID = 268792892132039670L;



	public ImpossivelCarregarReceitasAPartirDasPaginasWebException() {
	}



	public ImpossivelCarregarReceitasAPartirDasPaginasWebException(String message) {
		super(message);
	}



	public ImpossivelCarregarReceitasAPartirDasPaginasWebException(Throwable cause) {
		super(cause);
	}



	public ImpossivelCarregarReceitasAPartirDasPaginasWebException(String message, Throwable cause) {
		super(message, cause);
	}



	public ImpossivelCarregarReceitasAPartirDasPaginasWebException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
