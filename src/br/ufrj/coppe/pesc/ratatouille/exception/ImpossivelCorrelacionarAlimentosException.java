package br.ufrj.coppe.pesc.ratatouille.exception;

public class ImpossivelCorrelacionarAlimentosException extends Exception {

	private static final long serialVersionUID = -3585590965542096413L;



	public ImpossivelCorrelacionarAlimentosException() {
	}



	public ImpossivelCorrelacionarAlimentosException(String message) {
		super(message);
	}



	public ImpossivelCorrelacionarAlimentosException(Throwable cause) {
		super(cause);
	}



	public ImpossivelCorrelacionarAlimentosException(String message, Throwable cause) {
		super(message, cause);
	}



	public ImpossivelCorrelacionarAlimentosException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
