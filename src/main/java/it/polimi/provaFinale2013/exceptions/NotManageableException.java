package it.polimi.provaFinale2013.exceptions;

/**
 * Called when you can't do nothing in every part of the program. Someone catch in the main class
 * and manage it in the future
 */
public class NotManageableException extends RuntimeException {
	private static final long serialVersionUID = 6924473041674753922L;

	/**
	 * The constructor of the class
	 * 
	 * @param e
	 */
	public NotManageableException(Exception e) {
	}

	/**
	 * Constructor of the class
	 */
	public NotManageableException() {
	}
}
