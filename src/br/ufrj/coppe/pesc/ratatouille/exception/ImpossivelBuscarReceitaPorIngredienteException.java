package br.ufrj.coppe.pesc.ratatouille.exception;


public class ImpossivelBuscarReceitaPorIngredienteException extends Exception {

	private static final long serialVersionUID = 1851228890523729491L;



	public ImpossivelBuscarReceitaPorIngredienteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}



	public ImpossivelBuscarReceitaPorIngredienteException(String message, Throwable cause) {
		super(message, cause);
	}



	public ImpossivelBuscarReceitaPorIngredienteException(String message) {
		super(message);
	}



	public ImpossivelBuscarReceitaPorIngredienteException(Throwable cause) {
		super(cause);
	}



	public ImpossivelBuscarReceitaPorIngredienteException() {
	}

}
