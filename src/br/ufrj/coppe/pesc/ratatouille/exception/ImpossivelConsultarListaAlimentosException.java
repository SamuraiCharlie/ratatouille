package br.ufrj.coppe.pesc.ratatouille.exception;

public class ImpossivelConsultarListaAlimentosException extends Exception {

	private static final long serialVersionUID = -6544488956116874995L;



	public ImpossivelConsultarListaAlimentosException() {
	}



	public ImpossivelConsultarListaAlimentosException(String message) {
		super(message);
	}



	public ImpossivelConsultarListaAlimentosException(Throwable cause) {
		super(cause);
	}



	public ImpossivelConsultarListaAlimentosException(String message, Throwable cause) {
		super(message, cause);
	}



	public ImpossivelConsultarListaAlimentosException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
