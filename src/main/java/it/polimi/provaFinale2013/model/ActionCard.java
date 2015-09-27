package it.polimi.provaFinale2013.model;

import java.io.Serializable;

/**
 * The Action Card class
 * 
 * @extends Card
 * @implements Serializable
 */
public class ActionCard extends Card implements Serializable, Cloneable {

	private static final long serialVersionUID = 7440096140202940121L;
	private String name;
	private int number;
	private char letter;

	/**
	 * Constructor of the ActionCard.
	 * 
	 * @param name
	 * @param number
	 * @param letter
	 */
	public ActionCard(String name, int number, char letter) {
		this.name = name;
		this.number = number;
		this.letter = letter;
	}

	/**
	 * @return a unique ActionCard ID
	 */
	@Override
	public long getId() {
		return (number + serialVersionUID);
	}

	/**
	 * get the name of the card
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * get the number of the card
	 * 
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * get the letter of the card
	 * 
	 * @return the letter
	 */
	public char getLetter() {
		return letter;
	}

	/**
	 * clone the action card
	 */
	@Override
	public ActionCard clone() {
		ActionCard a = new ActionCard(name, number, letter);
		a.setFlipped(this.getFlipped());
		return a;
	}

	/**
	 * The equals function
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ActionCard)) {
			return false;
		}
		ActionCard c = (ActionCard) obj;

		if (c.number == number) {
			return true;
		}
		return false;

	}

	/**
	 * The hashcode function
	 */
	@Override
	public int hashCode() {
		int hash = 1;
		hash = hash * 17 + 450;
		hash = hash * 31 + number;
		hash = hash * 13;
		return hash;
	}
}
