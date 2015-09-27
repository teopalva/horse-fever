package it.polimi.provaFinale2013.model;

import it.polimi.provaFinale2013.exceptions.CantChangeException;
import it.polimi.provaFinale2013.exceptions.NotEnoughMoneyException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Interface to the remote bank. It permits to export the bank over the network with RMI.
 */
public interface RemoteBank extends Remote {
	/**
	 * Get the physical banknotes of requested value
	 * 
	 * @param value the value you want to get
	 * @return the list of banknoted that fit the value
	 * @throws NotEnoughMoneyException
	 * @throws RemoteException
	 */
	public List<Banknote> getBanknotes(int value) throws NotEnoughMoneyException, RemoteException;

	/**
	 * Return the minimum number of banknotes that fit the value of banknotes taked in input
	 * 
	 * @param banknotes the banknotes you want to change
	 * @return a list of banknotes
	 * @throws CantChangeException
	 * @throws RemoteException
	 */
	public List<Banknote> mergeBanknotes(List<Banknote> banknotes) throws CantChangeException, RemoteException;

	/**
	 * Return a list of banknotes that represent the value of banknote taked in input
	 * 
	 * @param banknote the banknote you want to change
	 * @return the list of banknotes contains "smaller" banknotes
	 * @throws CantChangeException
	 * @throws RemoteException
	 */
	public List<Banknote> splitBanknote(Banknote banknote) throws CantChangeException, RemoteException;
}
