package it.polimi.provaFinale2013.model;

import it.polimi.provaFinale2013.exceptions.DoubleBetSameHorseException;
import it.polimi.provaFinale2013.exceptions.NoMoreMoneyInBankException;
import it.polimi.provaFinale2013.exceptions.NotEnoughMoneyException;
import it.polimi.provaFinale2013.exceptions.NotManageableException;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class manages the bets. You have to add bets with putBet. It can pay winned bets based on
 * track object. No more than two bets for player, it checks for double bet same type same horse
 */
public class BetManager {
	private Bank bank;
	private List<BetContainer> bets;

	/**
	 * Associate the stable number [1..6] to player that own the stable
	 */
	private Map<Integer, Player> playerStableOwner;

	/**
	 * The constructor of the class
	 * 
	 * @param bank the bank used in order to change banknotes when needed
	 */
	public BetManager(Bank bank) {
		this.bank = bank;
		bets = new ArrayList<BetContainer>();
		playerStableOwner = new HashMap<Integer, Player>();
	}

	/**
	 * Put a betToken on a horse. The bet is made by a player
	 * 
	 * @param bet the bet
	 * @param player the player that make the bet
	 * @throws DoubleBetSameHorseException when a player make two bets of same type on same horse
	 */
	public void putBet(Bet bet, Player player) throws DoubleBetSameHorseException {
		BetContainer betContainer = new BetContainer(player, bet);
		if (checkDoubleBetSameHorse(betContainer)) {
			throw new DoubleBetSameHorseException();
		}
		bets.add(betContainer);
	}

	/**
	 * Check if a player has made a double bet on same type and on same orse
	 * 
	 * @param betContainer the bet container that contains the bet and the player
	 * @return true if more than one bet of same type has been made on same horse
	 */
	private boolean checkDoubleBetSameHorse(BetContainer betContainer) {
		int count = 0;

		for (BetContainer b : bets) {
			if (betContainer.getPlayer() == b.getPlayer()) {
				count++;
			}
		}
		if (count > 2) {
			throw new IllegalArgumentException();
		}

		for (BetContainer b : bets) {
			if (betContainer.getPlayer() == b.getPlayer()) {
				BetToken b1 = betContainer.getBet().getBetToken();
				BetToken b2 = b.getBet().getBetToken();
				if (b1.equals(b2)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * This function pay winned bets to players and put losed money in the bank it also get back bet
	 * tokens to betTokenManager
	 * 
	 * @param track the track where the finished race is stored
	 * @param blackBoard the blackBoard with quotations
	 * @param betTokenManager the BetTokenManager where this function will put bet tokens
	 * @throws NoMoreMoneyInBankException
	 * @throws RemoteException
	 */
	public void payBets(Track track, BlackBoard blackBoard, BetTokenManager betTokenManager) throws NoMoreMoneyInBankException, RemoteException {
		for (BetContainer betContainer : bets) {
			Player p = betContainer.getPlayer();
			Bet b = betContainer.getBet();
			if (b.getBetToken().getType() == BetToken.SHOW) {
				//Check SHOW conditions
				if (track.getHorseChartPosition(b.getBetToken().getStable()) == 1) {
					//Calculate won danari
					int danari = p.getValue();
					p.putDanari(b.getDanari());
					danari = p.getValue() - danari;

					//Add banknotes to player and subtract from bank
					try {
						List<Banknote> banknotes = bank.getBanknotes(danari);
						p.putDanari(banknotes);
						p.putVP(1);
					} catch (NotEnoughMoneyException e) {
						throw new NoMoreMoneyInBankException();
					}
				} else {
					//Insert lost danari in bank
					bank.putBanknotes(b.getDanari());
				}
			} else {
				//Check WIN conditions
				if (track.getHorseChartPosition(b.getBetToken().getStable()) == 1) {
					//Calculate won danari
					int danari = p.getValue();
					p.putDanari(b.getDanari());
					danari = p.getValue() - danari;

					//Multiply for the horse quotation
					danari *= blackBoard.getQuotation(b.getBetToken().getStable());

					//Add banknotes to player and subtract from bank
					try {
						List<Banknote> banknotes = bank.getBanknotes(danari);
						p.putDanari(banknotes);
						p.putVP(3);
					} catch (NotEnoughMoneyException e) {
						throw new NoMoreMoneyInBankException();
					}
				} else {
					//Insert lost danari in bank
					bank.putBanknotes(b.getDanari());
				}
			}
			betTokenManager.putBetToken(betContainer.getBet().getBetToken());

		}
		bets.clear();
	}

	/**
	 * Pay stable owners
	 * 
	 * @param track the Track object
	 * @throws RemoteException
	 */
	public void payStableOwners(Track track) throws NotManageableException, RemoteException {
		for (int i = 1; i < 6; i++) {
			int position = track.getHorseChartPosition(i);
			Player owner = playerStableOwner.get(Integer.valueOf(i));
			if (owner == null) {
				continue;
			}
			List<Banknote> banknotes;
			switch (position) {
			case 1:
				try {
					banknotes = bank.getBanknotes(600);
					owner.putDanari(banknotes);
				} catch (NotEnoughMoneyException e) {
					throw new NotManageableException(e);
				}
				break;
			case 2:
				try {
					banknotes = bank.getBanknotes(400);
					owner.putDanari(banknotes);
				} catch (NotEnoughMoneyException e) {
					throw new NotManageableException(e);
				}
				break;
			case 3:
				try {
					banknotes = bank.getBanknotes(200);
					owner.putDanari(banknotes);
				} catch (NotEnoughMoneyException e) {
					throw new NotManageableException(e);
				}
				break;
			}
		}
	}

	/**
	 * Delete every bet inside the BetManager
	 */
	public List<BetToken> reset() {
		List<BetToken> betTokens = new ArrayList<BetToken>();
		for (BetContainer bet : bets) {
			betTokens.add(bet.getBet().getBetToken());
		}
		bets.clear();
		return betTokens;
	}

	/**
	 * Add a player that own the stable. Used in order to pay player when a horse win
	 * 
	 * @param stable that the owner own
	 * @param owner owner of the stable
	 */
	public void addStableOwner(int stable, Player owner) {
		if (stable < 1 || stable > 6 || owner == null) {
			throw new IllegalArgumentException();
		}
		playerStableOwner.put(Integer.valueOf(stable), owner);
	}

	/**
	 * Get all bets associated with players
	 * 
	 * @return a list of bet container that each contains a bet and a player
	 */
	public List<BetContainer> getBetsWithPlayers() {
		List<BetContainer> returnBets = new ArrayList<BetContainer>();
		for (BetContainer betContainer : bets) {
			returnBets.add(betContainer.clone());
		}
		return returnBets;
	}

}
