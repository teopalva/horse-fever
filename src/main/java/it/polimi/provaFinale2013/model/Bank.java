package it.polimi.provaFinale2013.model;

import it.polimi.provaFinale2013.exceptions.CantChangeException;
import it.polimi.provaFinale2013.exceptions.NotEnoughMoneyException;

import java.util.ArrayList;
import java.util.List;

/**
 * This class maintains all the money in the game that are owned by no one it can change money
 */
public class Bank extends Wallet implements RemoteBank {

	private static final long serialVersionUID = -6086996520710389417L;

	/**
	 * Constructor of the class
	 */
	public Bank() {
		super();
	}

	/**
	 * It permits to extract banknotes from the wallet delete banknotes after the extraction (like
	 * every bank)
	 * 
	 * @param value to extract
	 * @return list of banknotes
	 */
	@Override
	public List<Banknote> getBanknotes(int value) throws NotEnoughMoneyException {
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

		while (value >= 500 && banknotes500.size() > 0) {
			banknotes.add(banknotes500.get(0));
			banknotes500.remove(0);
			value -= 500;
		}

		while (value >= 100 && banknotes100.size() > 0) {
			banknotes.add(banknotes100.get(0));
			banknotes100.remove(0);
			value -= 100;
		}

		return banknotes;
	}

	/**
	 * Change banknotes
	 * 
	 * @param banknotes that you want to change
	 * @return list of bigger banknotes
	 */
	public List<Banknote> mergeBanknotes(List<Banknote> banknotes) throws CantChangeException {
		List<Banknote> returnBanknotes = new ArrayList<Banknote>();
		List<Banknote> bankNote100 = new ArrayList<Banknote>();
		List<Banknote> bankNote500 = new ArrayList<Banknote>();
		List<Banknote> bankNote1000 = new ArrayList<Banknote>();

		for (Banknote banknote : banknotes) {
			switch (banknote.getValue()) {
			case 100:
				bankNote100.add(banknote);
				break;
			case 500:
				bankNote500.add(banknote);
				break;
			case 1000:
				bankNote1000.add(banknote);
				break;
			default:
				break;
			}
		}

		while (bankNote100.size() > 5) {
			this.banknotes100.add(bankNote100.get(0));
			bankNote100.remove(0);
			this.banknotes100.add(bankNote100.get(0));
			bankNote100.remove(0);
			this.banknotes100.add(bankNote100.get(0));
			bankNote100.remove(0);
			this.banknotes100.add(bankNote100.get(0));
			bankNote100.remove(0);
			this.banknotes100.add(bankNote100.get(0));
			bankNote100.remove(0);
			if (this.banknotes500.size() > 0) {
				bankNote500.add(this.banknotes500.get(0));
				this.banknotes500.remove(0);
			} else {
				throw new CantChangeException();
			}

		}

		while (bankNote500.size() > 2) {
			this.banknotes500.add(bankNote500.get(0));
			bankNote500.remove(0);
			this.banknotes500.add(bankNote500.get(0));
			bankNote500.remove(0);
			if (this.banknotes1000.size() > 0) {
				bankNote1000.add(this.banknotes1000.get(0));
				this.banknotes1000.remove(0);
			} else {
				throw new CantChangeException();
			}

		}

		returnBanknotes.addAll(bankNote100);
		returnBanknotes.addAll(bankNote500);
		returnBanknotes.addAll(bankNote1000);

		return returnBanknotes;
	}

	/**
	 * Change banknotes
	 * 
	 * @param banknote banknote that you want to change
	 * @return list of smaller banknotes
	 */
	public List<Banknote> splitBanknote(Banknote banknote) throws CantChangeException {
		if (banknote.getValue() == 100) {
			throw new CantChangeException();
		}
		List<Banknote> banknotes = new ArrayList<Banknote>();
		int value = banknote.getValue();
		switch (value) {
		case 500:
			while (value >= 100 && banknotes100.size() > 0) {
				banknotes.add(banknotes100.get(0));
				banknotes100.remove(0);
				value -= 100;
			}
			break;
		case 1000:
			while (value >= 500 && banknotes500.size() > 0) {
				banknotes.add(banknotes500.get(0));
				banknotes500.remove(0);
				value -= 500;
			}

			while (value >= 100 && banknotes100.size() > 0) {
				banknotes.add(banknotes100.get(0));
				banknotes100.remove(0);
				value -= 100;
			}
			break;
		}
		if (value > 0) {
			throw new CantChangeException();
		}
		return banknotes;
	}
}
