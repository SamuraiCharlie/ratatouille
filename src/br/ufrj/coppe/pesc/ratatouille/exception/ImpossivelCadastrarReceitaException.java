package br.ufrj.coppe.pesc.ratatouille.exception;

public class ImpossivelCadastrarReceitaException extends Exception {


	private static final long serialVersionUID = -7385231189893068228L;



	public ImpossivelCadastrarReceitaException() {
	}



	public ImpossivelCadastrarReceitaException(String message) {
		super(message);
	}



	public ImpossivelCadastrarReceitaException(Throwable cause) {
		super(cause);
	}



	public ImpossivelCadastrarReceitaException(String message, Throwable cause) {
		super(message, cause);
	}



	public ImpossivelCadastrarReceitaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
