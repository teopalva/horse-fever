package it.polimi.provaFinale2013.exceptions;

import java.util.List;

/**
 * Called when more than one horse arrive in the same time at the finish, and all horses are at the
 * same quotation
 */
public class MoreThanOneHorseArrivedException extends Exception {
	private static final long serialVersionUID = -7966433586254087740L;

	/**
	 * The horses that are arrived at the same time
	 */
	public List<Integer> horses;

	/**
	 * The constructor of the class
	 * 
	 * @param horses
	 */
	public MoreThanOneHorseArrivedException(List<Integer> horses) {
		this.horses = horses;
	}

}