package br.ufrj.coppe.pesc.ratatouille.exception;

public class ImpossivelObterWebpagesComConteudoException extends Exception {

	private static final long serialVersionUID = -6525655103622306505L;

	public ImpossivelObterWebpagesComConteudoException() {
	}

	public ImpossivelObterWebpagesComConteudoException(String message) {
		super(message);
	}

	public ImpossivelObterWebpagesComConteudoException(Throwable cause) {
		super(cause);
	}

	public ImpossivelObterWebpagesComConteudoException(String message, Throwable cause) {
		super(message, cause);
	}

	public ImpossivelObterWebpagesComConteudoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
