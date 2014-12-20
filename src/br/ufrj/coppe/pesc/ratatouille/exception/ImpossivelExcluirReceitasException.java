package br.ufrj.coppe.pesc.ratatouille.exception;

public class ImpossivelExcluirReceitasException extends Exception {

	private static final long serialVersionUID = 713944599698122912L;



	public ImpossivelExcluirReceitasException() {
	}



	public ImpossivelExcluirReceitasException(String message) {
		super(message);
	}



	public ImpossivelExcluirReceitasException(Throwable cause) {
		super(cause);
	}



	public ImpossivelExcluirReceitasException(String message, Throwable cause) {
		super(message, cause);
	}



	public ImpossivelExcluirReceitasException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
