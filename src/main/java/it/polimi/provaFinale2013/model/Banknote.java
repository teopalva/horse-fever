package it.polimi.provaFinale2013.model;

import java.io.Serializable;

/**
 * This is used in order to model a banknote
 */
public class Banknote implements Serializable, Cloneable {

	private static final long serialVersionUID = 3929101148306514842L;
	private int value;

	/**
	 * the constructor of the class
	 * 
	 * @param value
	 */
	public Banknote(int value) {
		if (value != 100 && value != 500 && value != 1000) {
			throw new IllegalArgumentException();
		}
		this.value = value;
	}

	/**
	 * get the value of the banknote
	 * 
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * clone the banknote
	 */
	public Banknote clone() {
		return new Banknote(value);
	}
}
