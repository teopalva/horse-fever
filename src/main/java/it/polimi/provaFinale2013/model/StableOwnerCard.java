package it.polimi.provaFinale2013.model;

import it.polimi.provaFinale2013.exceptions.CardNotFoundException;

import java.io.Serializable;
import java.util.NoSuchElementException;

/**
 * The Stable Owner Card class.
 * 
 * @extends Card
 * @implements Serializable
 */
public class StableOwnerCard extends Card implements Serializable, Cloneable {
	private static final long serialVersionUID = 7787335657671915060L;
	private String name;
	private int stable;

	/**
	 * Constructor of the StableOwnerCard.
	 * 
	 * @param number
	 * @param imageManager
	 */
	public StableOwnerCard(String name, int stable) {
		this.name = name;
		this.stable = stable;
	}

	/**
	 * Get the stable associated to this card
	 * 
	 * @return the stable [1..6]
	 */
	public int getStable() {
		return stable;
	}

	/**
	 * The clone method
	 */
	public StableOwnerCard clone() {
		return new StableOwnerCard(name, stable);
	}

	/**
	 * The equals method
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof StableOwnerCard)) {
			return false;
		}
		StableOwnerCard c = (StableOwnerCard) obj;

		if (c.name.equals(name)) {
			return true;
		}
		return false;

	}

	/**
	 * The hashcode method
	 */
	@Override
	public int hashCode() {
		int hash = 1;
		hash = hash * 17 + 177;
		hash = hash * 31 + name.hashCode();
		hash = hash * 13;
		return hash;
	}

	/**
	 * Get the stable owner cards of the owner at the specified quotation from the specified deck
	 * using the stables quotations specified by the black board
	 * 
	 * @param quotation the quotation of the stable that you want to own
	 * @param stableOwnerDeck the stable owner deck that contains all stable owner cards
	 * @param blackBoard the black board that contains all quotations
	 * @return the stable owner card
	 * @throws CardNotFoundException if there's the stable owner card that satisfy desired
	 * parameters
	 */
	public static StableOwnerCard getStableOwner(int quotation, Deck<StableOwnerCard> stableOwnerDeck, BlackBoard blackBoard)
			throws CardNotFoundException {
		if (quotation < 2 || quotation > 7) {
			throw new IllegalArgumentException();
		}
		int stable = -1;
		try {
			stable = blackBoard.getStablesAtQuotation(quotation).get(0);
		} catch (NoSuchElementException e) {
			throw new CardNotFoundException();
		}
		for (StableOwnerCard c : stableOwnerDeck) {

			if (c.getStable() == stable) {
				stableOwnerDeck.removeCard(c);
				return c;
			}
		}
		throw new CardNotFoundException();
	}
}
