package br.ufrj.coppe.pesc.ratatouille.exception;

public class ImpossivelConsultarReceitaPorNomeException extends Exception {

	private static final long serialVersionUID = 2737104071823854547L;



	public ImpossivelConsultarReceitaPorNomeException() {
	}



	public ImpossivelConsultarReceitaPorNomeException(String message) {
		super(message);
	}



	public ImpossivelConsultarReceitaPorNomeException(Throwable cause) {
		super(cause);
	}



	public ImpossivelConsultarReceitaPorNomeException(String message, Throwable cause) {
		super(message, cause);
	}



	public ImpossivelConsultarReceitaPorNomeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
