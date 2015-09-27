package it.polimi.provaFinale2013.model;

import it.polimi.provaFinale2013.exceptions.NoMoreMoneyInBankException;
import it.polimi.provaFinale2013.exceptions.NotEnoughMoneyException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Interface implemented by a player. Game use this in order to communicate with players
 */
public interface Player extends Remote {

	/**
	 * Put a card inside the player
	 * 
	 * @param card the card to put inside
	 * @throws RemoteException
	 */
	public void putCard(Card card) throws RemoteException;

	/**
	 * Request the player for a bet
	 * 
	 * @param betTokens the bet tokens owned by the game
	 * @param mandatory if the bet is mandatory or not
	 * @return the bet that the player made or null
	 * @throws NotEnoughMoneyException
	 * @throws RemoteException
	 */
	public Bet requestMakeBet(List<BetToken> betTokens, boolean mandatory) throws NotEnoughMoneyException, RemoteException;

	/**
	 * Request the player for playing an action card on a horse
	 * 
	 * @return the Action that the player made
	 * @throws RemoteException
	 */
	public Action requestPlayAction() throws RemoteException;

	/**
	 * Display a message
	 * 
	 * @param message the message to display
	 * @throws RemoteException
	 */
	public void displayMessage(String message) throws RemoteException;

	/**
	 * Remove danari from the player
	 * 
	 * @param danari the amount of danari to remove
	 * @return the list of banknotes removed from the player
	 * @throws NotEnoughMoneyException
	 * @throws NoMoreMoneyInBankException
	 * @throws RemoteException
	 */
	public List<Banknote> removeDanari(int danari) throws NotEnoughMoneyException, NoMoreMoneyInBankException, RemoteException;

	/**
	 * Put danari inside the player
	 * 
	 * @param banknotes the banknotes to insert inside the player
	 * @throws RemoteException
	 */
	public void putDanari(List<Banknote> banknotes) throws RemoteException;

	/**
	 * Request the first player choosing the first horse
	 * 
	 * @param horses the list of horses
	 * @return the horse he want to win
	 * @throws RemoteException
	 */
	public int chooseFirstHorse(List<Integer> horses) throws RemoteException;

	/**
	 * Put VP inside the player
	 * 
	 * @param v how many VP put inside
	 * @throws RemoteException
	 */
	public void putVP(int v) throws RemoteException;

	/**
	 * Remove VP from the player
	 * 
	 * @param v how many VP remove
	 * @throws RemoteException
	 */
	public void removeVP(int v) throws RemoteException;

	/**
	 * Get the amount of VP the player own
	 * 
	 * @return the VP inside the player
	 * @throws RemoteException
	 */
	public int getVP() throws RemoteException;

	/**
	 * Get the amount of danari the player own
	 * 
	 * @return the amount of danari the player own
	 * @throws RemoteException
	 */
	public int getValue() throws RemoteException;

	/**
	 * Make the player active
	 * 
	 * @throws RemoteException
	 */
	public void makeActive() throws RemoteException;

	/**
	 * Tells the player that he lost the game
	 * 
	 * @throws RemoteException
	 */
	public void loseGame() throws RemoteException;

	/**
	 * Tells the player that he won the game
	 * 
	 * @throws RemoteException
	 */
	public void winGame() throws RemoteException;

	/**
	 * Get the name of the player
	 * 
	 * @return the name of the player
	 * @throws RemoteException
	 */
	public String getName() throws RemoteException;
}
