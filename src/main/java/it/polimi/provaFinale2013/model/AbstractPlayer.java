package it.polimi.provaFinale2013.model;

import it.polimi.provaFinale2013.exceptions.AlreadyArrivedException;
import it.polimi.provaFinale2013.exceptions.CardAlreadyPresentException;
import it.polimi.provaFinale2013.exceptions.NoMoreMoneyInBankException;
import it.polimi.provaFinale2013.exceptions.NotEnoughMoneyException;
import it.polimi.provaFinale2013.exceptions.NotStartedException;

import java.io.InputStream;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * This class represent the abstract player. It contains all methods common at every concrete
 * implementation of player
 */
public abstract class AbstractPlayer implements Player, GameObserver {

	protected List<ActionCard> actionCards;
	protected StableOwnerCard stableOwnerCard;
	protected CharacterCard characterCard;
	private PrintStream out;
	private InputStream in;
	protected String name;

	private int VP = 0;

	private Wallet wallet;

	/**
	 * Constructor of the class, it connects input and output streams
	 * 
	 * @param output the output stream
	 * @param input the input stream
	 */
	public AbstractPlayer(PrintStream output, InputStream input, RemoteBank bank, String name) {
		this.name = name;
		in = input;
		out = output;
		actionCards = new ArrayList<ActionCard>();
		stableOwnerCard = null;
		characterCard = null;
		wallet = new Wallet(bank);
	}

	/**
	 * Put a card inside the player
	 * 
	 * @param card the card you want to put
	 */
	public void putCard(Card card) {
		card.setFlipped(false);
		if (card instanceof ActionCard) {
			ActionCard actionCard = (ActionCard) card;
			actionCards.add(actionCard.clone());
			out.printf("Added action card '%s' to your hand%n", actionCard.getName());
		} else if (card instanceof StableOwnerCard) {
			if (stableOwnerCard != null) {
				throw new CardAlreadyPresentException();
			}
			stableOwnerCard = (StableOwnerCard) card;
			out.printf("You are the owner of the stable: %d%n", stableOwnerCard.getStable());
		} else if (card instanceof CharacterCard) {
			if (characterCard != null) {
				throw new CardAlreadyPresentException();
			}
			characterCard = (CharacterCard) card;
			out.printf("You are the character: '%s'%n", characterCard.getName());
		}
	}

	/**
	 * Request the player make a bet. If mandatory=true the player is forced to make a bet
	 * 
	 * @param betTokens bet tokens remained in game
	 * @param mandatory true/false indicate if the bet is mandatory (true) or optional (false)
	 */
	public Bet requestMakeBet(List<BetToken> betTokens, boolean mandatory) throws NotEnoughMoneyException, RemoteException {

		//If player hasn't got money and it's not mandatory he can't bet 
		if (!mandatory && !checkMinimumBet(getValue())) {
			return null;
		}

		while (getVP() > 0 && !checkMinimumBet(getValue())) {
			VP -= 2;
		}

		if (getVP() <= 0) {
			throw new NotEnoughMoneyException();
		}

		Scanner tScan;
		if (!mandatory) {
			char response;
			tScan = new Scanner(in);
			do {
				out.printf("Do you want to make a bet? [Y/n] ");
				String tBuffer = tScan.next();
				response = tBuffer.charAt(0);
				
				if (response == 'n' || response == 'N') {
					tScan.close();
					return null;
				}
			} while (response != 'Y' && response != 'y');
			tScan.close();
		}
		int response;
		int horse = 0;
		boolean error = false;
		do {
			error = false;
			out.printf("Make a bet, you can choose from this list of horses:%n");
			for (BetToken betToken : betTokens) {
				out.printf("%d) %s%n", betToken.getStable(), Stable.getColorForStable(betToken.getStable()));
				betToken.setType(BetToken.WIN);
			}
			out.printf("stable: ");
			tScan = new Scanner(in);
			try {
				horse = tScan.nextInt();
			} catch (InputMismatchException e) {
				error = true;
			}
			tScan.close();
			if (horse < 1 || horse > 6) {
				error = true;
			}
		} while (error || !betTokens.contains(new BetToken(horse, BetToken.WIN)));
		do {
			out.printf("Chose from:%n%d) Win%n%d) Show%nBet: ", BetToken.WIN, BetToken.SHOW);
			response = -1;
			tScan = new Scanner(in);
			try {
				response = tScan.nextInt();
			} catch (InputMismatchException e) {
			}
			tScan.close();
		} while (response != BetToken.WIN && response != BetToken.SHOW);

		List<Banknote> danari = null;
		boolean correctBet = true;
		do {
			correctBet = true;
			out.printf("How many danari do you want to bet?: ");

			tScan = new Scanner(in);
			int number = -1;
			try {
				number = tScan.nextInt();
			} catch (InputMismatchException e) {
			}
			tScan.close();

			if (number < 0 || !checkMinimumBet(number)) {
				correctBet = false;	
				continue;
			}
			try {
				danari = wallet.getBanknotes(number);
			} catch (NotEnoughMoneyException e) {
				out.printf("You haven't got enough danari for this bet%n");
				correctBet = false;
			} catch (Exception e) {
				correctBet = false;
			}
		} while (!correctBet);

		return new Bet(danari, new BetToken(horse, response));
	}

	/**
	 * Request the player play an action card on a stable
	 */
	public Action requestPlayAction() {
		Scanner tScan;
		ActionCard actionCard = null;
		int horse;
		do {
			out.printf("Chose an Action:%n");
			int k = 0;
			for (ActionCard card : actionCards) {
				out.printf("%d) %s%n", k, card.getName());
				k++;
			}
			out.printf("Action card:");
			int index = -1;
			tScan = new Scanner(in);
			try {
				index = tScan.nextInt();
			} catch (InputMismatchException e) {
			}
			tScan.close();
			try {
				actionCard = actionCards.get(index);
				actionCards.remove(index);
			} catch (Exception e) {
			}
		} while (actionCard == null);

		do {
			out.printf("Chose a Horse [1 to 6]:");
			out.printf("Action card:%n");
			tScan = new Scanner(in);
			horse = -1;
			try {
				horse = tScan.nextInt();
			} catch (InputMismatchException e) {
			}
			tScan.close();
		} while (horse < 1 || horse > 6);

		tScan.close();
		return new Action(actionCard, horse);
	}

	/**
	 * Display a text message to the player
	 * 
	 * @param message the message
	 */
	public void displayMessage(String message) {
		out.printf("%s: %s", name, message);
	}

	/**
	 * Remove the amount of danari from the player and return the banknotes
	 * 
	 * @param danari the amount of danari
	 * @return the List of banknotes
	 */
	public List<Banknote> removeDanari(int danari) throws NotEnoughMoneyException, NoMoreMoneyInBankException, RemoteException {
		out.printf("%s: Removed %d danari from your wallet%n", name, danari);
		return wallet.getBanknotes(danari);
	}

	/**
	 * Put danari inside the player. You have to pass a List of banknotes
	 * 
	 * @param banknotes the list of banknotes
	 */
	public void putDanari(List<Banknote> banknotes) throws RemoteException {
		int valueBefore = getValue();
		wallet.putBanknotes(banknotes);
		out.printf("%s: Added %d danari from your wallet%n", name, getValue() - valueBefore);
	}

	/**
	 * Request the player (first player) to decide which horse is the winner from a list of horses
	 * 
	 * @param horses the list of horses arrived at the same time
	 */
	public int chooseFirstHorse(List<Integer> horses) {
		int horse;
		Scanner tScan;
		do {
			out.printf("Choose the horse that has to win:%n");
			for (Integer h : horses) {
				out.printf("%d ", h.intValue());
			}
			out.printf("%nhorse: ");
			horse = -1;
			tScan = new Scanner(in);
			try {
				horse = tScan.nextInt();
			} catch (InputMismatchException e) {
			}
			tScan.close();
		} while (!horses.contains(Integer.valueOf(horse)));
		return horse;
	}

	/**
	 * Check if minimum bet rule is respected
	 * 
	 * @param danari the danari the player want to bet
	 * @return true if the minimum bet rule is respected
	 */
	public boolean checkMinimumBet(int danari) {
		if (danari >= 100 * VP) {
			return true;
		}
		return false;
	}

	/**
	 * If called it cause the immediate lost of the match
	 */
	public void loseGame() {
		out.printf("%s: YOU LOST THE GAME!!%n", name);
		name = "Looser";
	}

	/**
	 * If called it cause the immediate win of the match
	 */
	public void winGame() {
		out.printf("%s: YOU WIN THE GAME!!%n", name);
	}

	/**
	 * Put VP to the player
	 * 
	 * @param v the VPs
	 */
	public void putVP(int v) {
		if (v <= 0) {
			throw new IllegalArgumentException();
		}
		VP += v;
		out.printf("%s: Added %d VP%n", name, v);
	}

	/**
	 * Remove VP from player
	 * 
	 * @param v the VPs
	 */
	public void removeVP(int v) {
		if (v <= 0) {
			throw new IllegalArgumentException();
		}
		VP -= v;
		out.printf("%s: Removed %d VP%n", name, v);
	}

	/**
	 * Get the amount of VP form player
	 * 
	 * @return the amount of VP
	 */
	public int getVP() {
		return VP;
	}

	/**
	 * Get the amount of danari from player
	 * 
	 * @return the amount of danari
	 */
	public int getValue() {
		return wallet.getValue();
	}

	/**
	 * Display the track to the player
	 * 
	 * @param track the track
	 */
	public int displayTrack(Track track) {
		switch (track.getStatus()) {
		case Track.STARTED:
			out.printf(" -------- RACE --------%n");
			for (int i = 1; i <= 6; i++) {
				try {
					out.printf("%s] %s%n", Stable.getColorForStable(i), track.getHorseRacePosition(i));
				} catch (NotStartedException e) {
				} catch (AlreadyArrivedException e) {
				}
			}
			break;
		case Track.ARRIVED:
			out.printf(" -------- CHART --------%n");
			for (int i = 1; i <= 6; i++) {
				try {
					out.printf("%s] %s%n", Stable.getColorForStable(i), track.getHorseChartPosition(i));
				} catch (NotStartedException e) {
				}
			}
			break;
		}
		out.printf("%n");
		return 0;
	}

	/**
	 * Display the blackBoard to the player
	 * 
	 * @param blackBoard the black board
	 */
	public int displayBlackBoard(BlackBoard blackBoard) {
		out.printf(" --------- QUOTATIONS ----------%n");
		for (int i = 2; i <= 7; i++) {
			try {
				out.printf("1:%d]", i);
				for (Integer stable : blackBoard.getStablesAtQuotation(i)) {
					out.printf(" %s", Stable.getColorForStable(stable));
				}
			} catch (NotStartedException e) {
			}
		}
		return 0;
	}

	/**
	 * Display turn to the player
	 * 
	 * @param turn the turn
	 */
	public int displayTurn(int turn, int maxTurns) {
		out.printf("--- TURN: %d/%d%n", turn, maxTurns);
		return 0;
	}

	/**
	 * Make the player active. In local this mean that he can execute action on graphic interface
	 */
	public void makeActive() {
		out.printf("------- PLAYER: %s%n", name);
		out.printf("-- VP: %d%n", VP);
		out.printf("-- Danari: %d%n", getValue());
		out.printf("-------------------------%n");
	}

	/**
	 * Get the name of player
	 * 
	 * @return the name
	 */
	public String getName() {
		if (name != null) {
			return new String(name);
		}
		return "";
	}

	/**
	 * Method of GameObserver
	 */
	public int addedPlayer() {
		//DO NOTHING
		return 0;
	}

	/**
	 * Method of GameObserver
	 */
	public int displayBets(List<BetContainer> bets) throws RemoteException {
		//DO NOTHING
		return 0;
	}
}