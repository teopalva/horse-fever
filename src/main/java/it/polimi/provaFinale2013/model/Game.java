package it.polimi.provaFinale2013.model;

import it.polimi.provaFinale2013.exceptions.NotManageableException;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface implemented by the two types of game (Family and Board)
 */
public interface Game extends Remote {

	/**
	 * Add a player inside the game
	 * 
	 * @param player the player you want to add
	 * @throws RemoteException
	 */
	public void addPlayer(Player player) throws RemoteException;

	/**
	 * Add an observer of the player
	 * 
	 * @param observer the observer you want to add. It must implement GameObserver interface
	 * @throws RemoteException
	 */
	public void addObserver(GameObserver observer) throws RemoteException;

	/**
	 * Start the game. This is the main loop of the game. Is a bloking method. Use ever in a
	 * separeted thread
	 * 
	 * @throws NotManageableException
	 * @throws RemoteException
	 */
	public void start() throws NotManageableException, RemoteException;

	/**
	 * Get the remote reference of the bank. The returned object can be sent over the net using RMI
	 * 
	 * @return the remote bank
	 * @throws RemoteException
	 */
	public RemoteBank getBank() throws RemoteException;
}
