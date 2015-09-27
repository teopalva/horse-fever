package it.polimi.provaFinale2013.model;

import it.polimi.provaFinale2013.exceptions.NoBetTokenFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class manages bet tokens. Its main purpose is contains bet tokens put, getAll and remove
 * tokens when needed Every bet token has a type WIN
 */
public class BetTokenManager {

	private List<List<BetToken>> betTokens;

	/**
	 * The constructor of the class
	 */
	public BetTokenManager() {
		betTokens = new ArrayList<List<BetToken>>();
		for (int i = 0; i < 6; i++) {
			betTokens.add(new ArrayList<BetToken>());
		}
	}

	/**
	 * Insert a bet token
	 * 
	 * @param betToken
	 */
	public void putBetToken(BetToken betToken) {
		int stable = betToken.getStable();
		if (stable < 1 || stable > 6) {
			throw new IllegalArgumentException();
		}
		BetToken b = betToken.clone();
		b.setType(BetToken.WIN);
		betTokens.get(stable - 1).add(b);
	}

	/**
	 * Retrieve a copy of all bet tokens
	 * 
	 * @return List of bet tokens
	 */
	public List<BetToken> getAllBetTokens() {
		List<BetToken> list = new ArrayList<BetToken>();
		for (int i = 0; i < 6; i++) {
			for (BetToken b : betTokens.get(i)) {
				list.add(b.clone());
			}
		}
		return removeDoubleBetTokensFromList(list);
	}

	/**
	 * Remove a bet token
	 * 
	 * @param betToken the bet token to remove
	 * @throws NoBetTokenFoundException when the bet token is not present inside
	 */
	public void removeBetToken(BetToken betToken) throws NoBetTokenFoundException {
		int stable = betToken.getStable();
		if (stable < 1 || stable > 6) {
			throw new IllegalArgumentException();
		}
		if (betTokens.get(stable - 1).size() == 0) {
			throw new NoBetTokenFoundException();
		}
		betTokens.get(stable - 1).remove(0);
	}

	/**
	 * Remove duplicated bet tokens in the list passed
	 * 
	 * @param betTokens the bet token list
	 * @return bet token list without duplicate tokens of same type
	 */
	private static List<BetToken> removeDoubleBetTokensFromList(List<BetToken> betTokens) {
		Set<BetToken> betTokensSet = new HashSet<BetToken>();
		for (BetToken betToken : betTokens) {
			betToken.setType(BetToken.WIN);
			betTokensSet.add(betToken);
		}
		return new ArrayList<BetToken>(betTokensSet);
	}
}
