package it.polimi.provaFinale2013.model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Interface implemented by any object that want to observe the status of the game (ex players)
 * Every method returns number of millisecond that the caller must wait after the call
 */
public interface GameObserver extends Remote {
	/**
	 * Display the track
	 * 
	 * @param track the track to display
	 * @return the number of milliseconds needed in order to display the track
	 * @throws RemoteException
	 */
	public int displayTrack(Track track) throws RemoteException;

	/**
	 * Display the bets
	 * 
	 * @param bets to display
	 * @return the number of milliseconds needed in order to display the bets
	 * @throws RemoteException
	 */
	public int displayBets(List<BetContainer> bets) throws RemoteException;

	/**
	 * Display the black board
	 * 
	 * @param blackBoard the blackboard to display
	 * @return the number of milliseconds needed in order to display the track
	 * @throws RemoteException
	 */
	public int displayBlackBoard(BlackBoard blackBoard) throws RemoteException;

	/**
	 * Display the current turn and the total turns
	 * 
	 * @param turn the current turn
	 * @param maxTurns the max number of turns in this play
	 * @return the number of milliseconds needed in order to display the turn
	 * @throws RemoteException
	 */
	public int displayTurn(int turn, int maxTurns) throws RemoteException;

	/**
	 * Notify that a player was added (useful in object like NetworkManager)
	 * 
	 * @return the number of milliseconds needed in order to display the change of player number (is
	 * always 0 but can be different)
	 * @throws RemoteException
	 */
	public int addedPlayer() throws RemoteException;
}
