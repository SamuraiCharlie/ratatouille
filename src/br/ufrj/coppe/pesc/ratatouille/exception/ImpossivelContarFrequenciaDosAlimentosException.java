package br.ufrj.coppe.pesc.ratatouille.exception;

public class ImpossivelContarFrequenciaDosAlimentosException extends Exception {

	private static final long serialVersionUID = -103145030792654800L;



	public ImpossivelContarFrequenciaDosAlimentosException() {
	}



	public ImpossivelContarFrequenciaDosAlimentosException(String message) {
		super(message);
	}



	public ImpossivelContarFrequenciaDosAlimentosException(Throwable cause) {
		super(cause);
	}



	public ImpossivelContarFrequenciaDosAlimentosException(String message, Throwable cause) {
		super(message, cause);
	}



	public ImpossivelContarFrequenciaDosAlimentosException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
