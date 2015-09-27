package it.polimi.provaFinale2013.model;

import java.awt.Image;
import java.io.Serializable;

/**
 * This abstract class manages the common characteristics to all the cards. A card can be flipped
 * (boolean)
 */
public abstract class Card implements Serializable, Cloneable {

	private static final long serialVersionUID = -1163464539360105461L;
	private boolean flipped; //true if the card is covered

	/**
	 * Constructor of the Card class.
	 */
	public Card() {
		this.flipped = true;
	}

	/**
	 * This method changes the actual flipped state of the card.
	 */
	public void flip() {
		if (flipped) {
			flipped = false;
		} else {
			flipped = true;
		}
	};

	/**
	 * This method returns the actual flipped state of the card.
	 * 
	 * @return true if the card is covered, false otherwise
	 */
	public boolean getFlipped() {
		return flipped;
	}

	/**
	 * This method sets the flipped state.
	 * 
	 * @param flip is the state you want the card to have (true if the card is covered, false
	 * otherwise)
	 */
	public void setFlipped(boolean flip) {
		flipped = flip;
	}

	/**
	 * @return a unique Card ID
	 */
	public long getId() {
		return serialVersionUID;
	}

	/**
	 * The equals method
	 */
	@Override
	//Need to be reimplemented
	public boolean equals(Object o) {
		throw new RuntimeException();
	}

	/**
	 * The clone method
	 */
	@Override
	//Need to be reimplemented
	public Card clone() {
		throw new RuntimeException();
	}

	/**
	 * The hashcode method
	 */
	@Override
	//Need to be reimplemented
	public int hashCode() {
		return 0;
	}

	/**
	 * Get the image of this card in the correct state
	 * 
	 * @return the image of the card
	 */
	public Image getImage() {
		Factory factory = Factory.getFactory();
		return factory.getImage(this);
	}

}
