package br.ufrj.coppe.pesc.ratatouille.exception;

public class ImpossivelExcluirReceitaException extends Exception {

	private static final long serialVersionUID = 3190756174274457042L;



	public ImpossivelExcluirReceitaException() {
	}



	public ImpossivelExcluirReceitaException(String message) {
		super(message);
	}



	public ImpossivelExcluirReceitaException(Throwable cause) {
		super(cause);
	}



	public ImpossivelExcluirReceitaException(String message, Throwable cause) {
		super(message, cause);
	}



	public ImpossivelExcluirReceitaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
