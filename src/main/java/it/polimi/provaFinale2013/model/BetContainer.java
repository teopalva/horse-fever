package it.polimi.provaFinale2013.model;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * This class is a container for Bet and a reference of the Player that makes this Bet
 */
public class BetContainer implements Cloneable, Serializable {

	private static final long serialVersionUID = 5630902820607159933L;
	private Bet bet;
	transient private Player player;

	//Cached player name
	private String playerName = null;

	/**
	 * the constructor of the class
	 * 
	 * @param p the player
	 * @param b the bet
	 */
	public BetContainer(Player p, Bet b) {
		if (p == null || b == null) {
			throw new NullPointerException();
		}
		bet = b;
		player = p;
		try {
			playerName = player.getName();
		} catch (RemoteException e) {
		}
	}

	/**
	 * get the bet
	 * 
	 * @return the bet
	 */
	public Bet getBet() {
		return bet.clone();
	}

	/**
	 * get the player
	 * 
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Get the name of the player that made this bet
	 * 
	 * @return the name of the player
	 */
	public String getPlayerName() {
		if (playerName != null) {
			return playerName;
		}
		return "";
	}

	/**
	 * The clone function
	 */
	@Override
	public BetContainer clone() {
		return new BetContainer(player, bet.clone());
	}

	/**
	 * The equals function
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof BetContainer)) {
			return false;
		}
		BetContainer b = (BetContainer) o; //I now what I'm doing!!! Look up, ther's the check!!
		if (b.playerName != null && playerName!=null && b.playerName.equals(playerName) && bet.equals(b.bet)) {
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
		hash = hash * 17 + player.hashCode();
		hash = hash * 31 + bet.hashCode();
		hash = hash * 13;
		return hash;
	}

}