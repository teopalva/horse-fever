package it.polimi.provaFinale2013.model;

import java.io.Serializable;

/**
 * The Character Card class.
 * 
 * @extends Card
 * @implements Serializable
 */
public class CharacterCard extends Card implements Serializable, Cloneable {

	private static final long serialVersionUID = -7410346510535747784L;
	private String name;
	private int danari;
	private int stableQuotation;

	/**
	 * Constructor of the CharacterCard.
	 * 
	 * @param name
	 * @param danari
	 * @param stableQuotation
	 */
	public CharacterCard(String name, int danari, int stableQuotation) {
		this.name = name;
		this.stableQuotation = stableQuotation;
		this.danari = danari;
	}

	/**
	 * Get the name of the card
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the quotation for the stable owned by the character
	 * 
	 * @return the quotation [2..7]
	 */
	public int getStableQuotation() {
		return stableQuotation;
	}

	/**
	 * Return the sum of danary owned by the character
	 * 
	 * @return the sum of danari
	 */
	public int getDanari() {
		return danari;
	}

	/**
	 * clone method
	 */
	@Override
	public CharacterCard clone() {
		return new CharacterCard(name, danari, stableQuotation);
	}

	/**
	 * equals method
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CharacterCard)) {
			return false;
		}
		CharacterCard c = (CharacterCard) obj;

		if (c.name.equals(name)) {
			return true;
		}
		return false;

	}

	/**
	 * hashCode method
	 */
	@Override
	public int hashCode() {
		int hash = 1;
		hash = hash * 17 + 379;
		hash = hash * 31 + name.hashCode();
		hash = hash * 13;
		return hash;
	}
}
