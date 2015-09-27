package it.polimi.provaFinale2013.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a container for money and bet token that it is created by Player and computed by
 * BetManager
 */
public class Bet implements Serializable, Cloneable {

	private static final long serialVersionUID = -5891192939050855708L;
	private List<Banknote> danari;
	private BetToken betToken;

	/**
	 * Constructor of the class
	 * 
	 * @param danari list of Banknotes
	 * @param betToken the bet token
	 */
	public Bet(List<Banknote> danari, BetToken betToken) {
		this.danari = new ArrayList<Banknote>(danari);
		this.betToken = betToken.clone();
	}

	/**
	 * Get the bet token inside the bet
	 * 
	 * @return the betToken
	 */
	public BetToken getBetToken() {
		//Return a clone of the bet token
		return betToken.clone();
	}

	/**
	 * Get the list of banknotes that represent the danari
	 * 
	 * @return
	 */
	public List<Banknote> getDanari() {
		//Return a clone of the list
		return new ArrayList<Banknote>(danari);
	}

	/**
	 * Get the sum of danari contained inside the bet
	 * 
	 * @return the sum of danari
	 */
	public int getValue() {
		int sum = 0;
		for (Banknote banknote : danari) {
			sum += banknote.getValue();
		}
		return sum;
	}

	/**
	 * Clone the bet
	 */
	@Override
	public Bet clone() {
		return new Bet(danari, betToken);
	}

	/**
	 * The equals function
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Bet)) {
			return false;
		}

		Bet b = (Bet) o;
		//Calculate the sum of current danari
		int localSum = 0;
		for (Banknote d : danari) {
			localSum += d.getValue();
		}

		//Calculate the sum of b danari
		int bSum = 0;
		for (Banknote d : b.danari) {
			bSum += d.getValue();
		}

		if (bSum == localSum && betToken.equals(b.betToken)) {
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
		hash = hash * 17 + betToken.hashCode();
		hash = hash * 31 + danari.hashCode();
		hash = hash * 13;
		return hash;
	}
}
