package br.ufrj.coppe.pesc.ratatouille.exception;

public class ImpossivelConsultarAlimentoPorNomeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5670969876696836349L;



	public ImpossivelConsultarAlimentoPorNomeException() {
		super();
	}



	public ImpossivelConsultarAlimentoPorNomeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}



	public ImpossivelConsultarAlimentoPorNomeException(String message, Throwable cause) {
		super(message, cause);
	}



	public ImpossivelConsultarAlimentoPorNomeException(String message) {
		super(message);
	}



	public ImpossivelConsultarAlimentoPorNomeException(Throwable cause) {
		super(cause);
	}

}
