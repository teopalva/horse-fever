package it.polimi.provaFinale2013.model;

import it.polimi.provaFinale2013.exceptions.CantChangeException;
import it.polimi.provaFinale2013.exceptions.NoMoreMoneyInBankException;
import it.polimi.provaFinale2013.exceptions.NotEnoughMoneyException;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class manages the money. It owns all your physical banknotes and has method for retrieve the
 * amount of danari that you want It own a reference to remote bank, It can change automatically
 * banknotes with bank if needed.
 */
public class Wallet implements Serializable {

	private static final long serialVersionUID = 815313461401110219L;
	protected List<Banknote> banknotes100;
	protected List<Banknote> banknotes500;
	protected List<Banknote> banknotes1000;
	private RemoteBank bank;

	/**
	 * The constructor of the class
	 */
	protected Wallet() {
		banknotes100 = new ArrayList<Banknote>();
		banknotes500 = new ArrayList<Banknote>();
		banknotes1000 = new ArrayList<Banknote>();
		this.bank = null;
	}

	/**
	 * The constructor of the class With this constructor the wallet will change automatically
	 * banknotes with the remote bank without any interaction of the user
	 * 
	 * @param bank the bank of the game
	 */
	public Wallet(RemoteBank bank) {
		banknotes100 = new ArrayList<Banknote>();
		banknotes500 = new ArrayList<Banknote>();
		banknotes1000 = new ArrayList<Banknote>();
		this.bank = bank;
	}

	/**
	 * Get the sum of banknotes contained in the wallet
	 * 
	 * @return the sum of banknotes contained in the wallet
	 */
	public int getValue() {
		return banknotes100.size() * 100 + banknotes500.size() * 500 + banknotes1000.size() * 1000;
	}

	/**
	 * It permits inserting the money in the wallet
	 * 
	 * @param banknotes banknotes to insert in the wallet
	 * @throws RemoteException
	 */
	public void putBanknotes(List<Banknote> banknotes) throws RemoteException {
		for (Banknote banknote : banknotes) {
			putBanknotes(banknote);
		}

		//Change the money with the bank
		List<Banknote> allBanknotes = new ArrayList<Banknote>();
		allBanknotes.addAll(banknotes100);
		allBanknotes.addAll(banknotes500);
		allBanknotes.addAll(banknotes1000);
		try {
			List<Banknote> changedBanknotes = bank.mergeBanknotes(allBanknotes);
			banknotes100.clear();
			banknotes500.clear();
			banknotes1000.clear();
			for (Banknote banknote : changedBanknotes) {
				putBanknotes(banknote);
			}
		} catch (CantChangeException e) {
			//Do nothing
		} catch (NullPointerException e) {
			//I'm the bank
		}
	}

	/**
	 * Insert a banknote inside the wallet
	 * 
	 * @param banknote the banknote you want to insert
	 */
	public void putBanknotes(Banknote banknote) {
		switch (banknote.getValue()) {
		case 100:
			banknotes100.add(banknote.clone());
			break;
		case 500:
			banknotes500.add(banknote.clone());
			break;
		case 1000:
			banknotes1000.add(banknote.clone());
			break;
		default:
			break;
		}
	}

	/**
	 * It permits to extract banknotes from the wallet delete banknotes after the extraction (like
	 * every wallet) unlike every wallet it communicate with the bank and change banknotes in order
	 * to ranch the desired value
	 * 
	 * @param value to extract
	 * @return list of banknotes
	 * @throws RemoteException
	 */
	public List<Banknote> getBanknotes(int value) throws NotEnoughMoneyException, NoMoreMoneyInBankException, RemoteException {
		if (value < 0 || value % 100 != 0) {
			throw new IllegalArgumentException();
		}
		if (value > getValue()) {
			throw new NotEnoughMoneyException();
		}

		List<Banknote> banknotes = new ArrayList<Banknote>();

		while (value >= 1000 && banknotes1000.size() > 0) {
			banknotes.add(banknotes1000.get(0));
			banknotes1000.remove(0);
			value -= 1000;
		}

		while (value >= 500) {
			if (banknotes500.size() == 0 && banknotes1000.size() > 0) {
				try {
					putBanknotes(bank.splitBanknote(banknotes1000.get(0)));
					banknotes1000.remove(0);
				} catch (CantChangeException e) {
					break;
				}
			}
			while (banknotes500.size() > 0 && value >= 500) {
				banknotes.add(banknotes500.get(0));
				banknotes500.remove(0);
				value -= 500;
			}
		}

		while (value >= 100) {
			boolean cantChange = false;
			if (banknotes100.size() == 0 && banknotes500.size() > 0) {
				try {
					putBanknotes(bank.splitBanknote(banknotes500.get(0)));
					banknotes500.remove(0);
				} catch (CantChangeException e) {
					cantChange = true;
				}
			}
			if (banknotes100.size() == 0 && banknotes1000.size() > 0) {
				try {
					cantChange = false;
					putBanknotes(bank.splitBanknote(banknotes1000.get(0)));
					banknotes1000.remove(0);
				} catch (CantChangeException e1) {
					cantChange = true;
				}
			}
			if (banknotes100.size() == 0 && banknotes500.size() > 0) {
				try {
					cantChange = false;
					putBanknotes(bank.splitBanknote(banknotes500.get(0)));
					banknotes500.remove(0);
				} catch (CantChangeException e) {
					cantChange = true;
				}
			}
			if (cantChange) {
				throw new NoMoreMoneyInBankException();
			}
			while (value > 0 && banknotes100.size() > 0) {
				banknotes.add(banknotes100.get(0));
				banknotes100.remove(0);
				value -= 100;
			}
		}

		return banknotes;
	}
}
