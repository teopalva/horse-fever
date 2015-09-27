package it.polimi.provaFinale2013.exceptions;

/**
 * Called when you call a function that try to set the winner horse, but there aren't horses arrived
 * at the same time
 */
public class NoMoreThanOneHorseArrivedException extends RuntimeException {
	private static final long serialVersionUID = 8948024037779892507L;
}
