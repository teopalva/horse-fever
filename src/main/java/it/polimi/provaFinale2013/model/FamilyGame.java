package it.polimi.provaFinale2013.model;

import it.polimi.provaFinale2013.exceptions.CardNotFoundException;
import it.polimi.provaFinale2013.exceptions.DoubleBetSameHorseException;
import it.polimi.provaFinale2013.exceptions.MoreThanOneHorseArrivedException;
import it.polimi.provaFinale2013.exceptions.NoMoreMoneyInBankException;
import it.polimi.provaFinale2013.exceptions.NoMoreThanOneHorseArrivedException;
import it.polimi.provaFinale2013.exceptions.NoPlayersException;
import it.polimi.provaFinale2013.exceptions.NotEnoughMoneyException;
import it.polimi.provaFinale2013.exceptions.NotManageableException;
import it.polimi.provaFinale2013.exceptions.OnePlayerLeftException;
import it.polimi.provaFinale2013.exceptions.TooPlayersExcetpion;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The most important class of the game: it implements all the logic. The game is composed by 4 main
 * stages, and each stage is composed by one or more substages.
 */
public class FamilyGame implements Game {

	/**
	 * Phase between two actions dafault is 0 means no pause
	 */

	private BlackBoard blackBoard;
	private BetManager betManager;
	private Track track;
	private PlayersQueue<Player> players;
	private Bank bank;
	private RemoteBank remoteBank = null;
	private Deck<ActionCard> actionDeck;
	private Deck<StableOwnerCard> stableDeck;
	private Deck<CharacterCard> characterDeck;
	private Deck<MovementCard> movementDeck;

	private BetTokenManager betTokens;
	private int turn;
	private int maxTurns;

	private List<GameObserver> observers;

	/**
	 * The constructor of the class
	 */
	public FamilyGame() {
		blackBoard = new BlackBoard();
		bank = new Bank();
		betManager = new BetManager(bank);

		//Instantiates the factory (not necessary)
		Factory.getFactory();
		actionDeck = Factory.getFactory().makeActionCardDeck();
		stableDeck = Factory.getFactory().makeStableOwnerCardDeck();
		characterDeck = Factory.getFactory().makeCharacterCardDeck();
		movementDeck = Factory.getFactory().makeMovementCardDeck();

		track = new Track(actionDeck);

		players = new PlayersQueue<Player>();
		betTokens = new BetTokenManager();
		turn = 0;
		observers = new ArrayList<GameObserver>();
	}

	/**
	 * Add a player to the game. Do it before starting the game
	 * 
	 * @throws RemoteException
	 */
	public void addPlayer(Player player) throws RemoteException {
		if (players.getNumPlayers() >= 6) {
			throw new TooPlayersExcetpion();
		}
		players.addPlayer(player);
		notifyAddPlayerToObservers();
	}

	/**
	 * The main routine of the game. When called iterate a loop until the game is finished
	 * 
	 * @throws RemoteException
	 */
	public void start() throws NotManageableException, RemoteException {
		int numPlayers = players.getNumPlayers();
		setup();
		try {
			players.getFirstPlayer().makeActive();
		} catch (NoPlayersException e) {
			throw new NotManageableException(e);
		}

		try {
			displayBlackBoardToObservers();
			while (turn <= getTurn(numPlayers)) {
				displayTurnToObservers();
				dealPhase10();
				bettingPhase20();
				racePhase30();
				endingPhase40();
				displayBetsToObservers();
			}
		} catch (NoPlayersException e) {
			//No problem, all players have lost the game at the same time ;-)
		} catch (OnePlayerLeftException e) {
			//Only one player left. He/she is the winner!!!
			players.getFirstPlayer().winGame();
		}
	}

	private int getTurn(int numPlayers) {
		switch (numPlayers) {
		case 2:
			return 6;
		case 3:
			return 6;
		case 4:
			return 4;
		case 5:
			return 5;
		case 6:
			return 6;
		}
		return 0;
	}

	/**
	 * Get the bank of the game
	 * 
	 * @throws RemoteException
	 */
	public RemoteBank getBank() throws RemoteException {
		if (remoteBank == null) {
			remoteBank = (RemoteBank) UnicastRemoteObject.exportObject(bank, 0);
		}
		return remoteBank;
	}

	private void setup() throws NotManageableException, RemoteException {

		for (Player p : players) {
			p.putVP(1);
		}

		for (int i = 0; i < 75; i++) {
			bank.putBanknotes(new Banknote(100));
		}
		for (int i = 0; i < 25; i++) {
			bank.putBanknotes(new Banknote(500));
		}
		for (int i = 0; i < 25; i++) {
			bank.putBanknotes(new Banknote(1000));
		}

		actionDeck.shuffle();
		stableDeck.shuffle();
		characterDeck.shuffle();
		movementDeck.shuffle();
		for (int i = 1; i <= 6; i++) {
			switch (players.getNumPlayers()) {
			case 2:
				for (int j = 0; j < 1; j++) {
					betTokens.putBetToken(new BetToken(i, BetToken.WIN));
				}
				break;
			case 3:
				for (int j = 0; j < 2; j++) {
					betTokens.putBetToken(new BetToken(i, BetToken.WIN));
				}
				break;
			case 4:
				for (int j = 0; j < 3; j++) {
					betTokens.putBetToken(new BetToken(i, BetToken.WIN));
				}
				break;
			case 5:
				for (int j = 0; j < 4; j++) {
					betTokens.putBetToken(new BetToken(i, BetToken.WIN));
				}
				break;
			case 6:
				for (int j = 0; j < 4; j++) {
					betTokens.putBetToken(new BetToken(i, BetToken.WIN));
				}
				break;
			default:
				throw new NotManageableException();
			}
		}

		for (Player p : players) {
			CharacterCard c = characterDeck.popCard();
			p.putCard(c);
			int q = c.getStableQuotation();
			StableOwnerCard o = null;
			try {
				o = StableOwnerCard.getStableOwner(q, stableDeck, blackBoard);
				p.putCard(o);
			} catch (CardNotFoundException e) {
				throw new NotManageableException(e);
			}

			try {
				p.putDanari(bank.getBanknotes(c.getDanari()));
			} catch (NotEnoughMoneyException e) {
				throw new NotManageableException(e);
			}
			p.putVP(1);

		}

		//Choose first player randomly
		int firstPlayer = (int) (Math.random() * players.getNumPlayers());
		players.setFirstPlayer(firstPlayer);

		maxTurns = getTurn(players.getNumPlayers());
		turn = 1;
	}

	//---------------------- PHASE 1 ------------------------
	private void dealPhase10() throws RemoteException {
		players.setNumRounds(1);
		players.setClockWise(true);
		for (Player p : players) {
			p.displayMessage(">>>>>>>>>>>>>>DEAL PHASE\n");
			p.putCard(actionDeck.popCard());
			p.putCard(actionDeck.popCard());
		}
	}

	//---------------------- PHASE 2 ------------------------
	private void bettingPhase20() throws NotManageableException, RemoteException {
		firstBets21();
		fixingRace22();
		firstBets23();
	}

	private void firstBets21() throws RemoteException {
		players.setClockWise(true);
		players.setNumRounds(1);
		Iterator<Player> pIter = players.iterator();
		while (pIter.hasNext()) {
			//Take and make active the current player
			Player player = pIter.next();
			Bet bet;
			boolean correctBet = true;
			do {
				bet = null;
				try {
					bet = player.requestMakeBet(betTokens.getAllBetTokens(), true);
					betManager.putBet(bet, player);
					betTokens.removeBetToken(bet.getBetToken());
					correctBet = true;
				} catch (DoubleBetSameHorseException e) {
					//Refund the players money
					player.putDanari(bet.getDanari());
					correctBet = false;
				} catch (NotEnoughMoneyException e) {
					//Remove the player
					player.loseGame();
					pIter.remove();
					correctBet = true;
				}
			} while (!correctBet);
			int pause = displayBetsToObservers();
			pause(pause);
		}
	}

	private void fixingRace22() throws RemoteException {
		players.setClockWise(true);
		players.setNumRounds(2);
		for (Player p : players) {
			Action a = p.requestPlayAction();
			track.putActionCard(a.getActionCard(), a.getStable());
			displayTrackToObservers();
		}
	}

	private void firstBets23() throws NotManageableException, RemoteException {
		players.setClockWise(false);
		players.setNumRounds(1);
		Iterator<Player> pIter = players.iterator();
		while (pIter.hasNext()) {
			//Take and activate the current player
			Player p = pIter.next();
			Bet bet = null;
			boolean correctBet = true;
			do {
				bet = null;
				try {
					bet = p.requestMakeBet(betTokens.getAllBetTokens(), false);
				} catch (NotEnoughMoneyException e1) {
					//The player has lost!!
					p.loseGame();
					pIter.remove();
				}
				if (bet != null) {
					try {
						betManager.putBet(bet, p);
						betTokens.removeBetToken(bet.getBetToken());
					} catch (DoubleBetSameHorseException e) {
						//Refund the players money
						p.putDanari(bet.getDanari());
						correctBet = false;

					}
				}
			} while (!correctBet);
			int pause = displayBetsToObservers();
			pause(pause);
		}
	}

	//---------------------- PHASE 3 ------------------------
	private void racePhase30() throws NotManageableException, NoPlayersException, RemoteException {
		displayTrackToObservers();
		revealActionCards31();
		displayTrackToObservers();
		race32();
		displayTrackToObservers();
		payOff35();
		displayTrackToObservers();
		newOddsOrder36();
		displayTrackToObservers();
	}

	private void revealActionCards31() throws RemoteException {
		//Start the race
		track.start(blackBoard);
		displayTrackToObservers();
	}

	private void race32() throws NotManageableException, NoPlayersException, RemoteException {
		while (!track.checkAllHorsesArrived()) {

			MovementCard m = movementDeck.popCard();
			m.setFlipped(false);
			try {
				int pause = displayTrackToObservers();
				pause(pause);
				track.executeMovementCard(m, blackBoard);
			} catch (MoreThanOneHorseArrivedException e) {
				boolean finished = true;
				List<Integer> horses = e.horses;
				Player p = players.getFirstPlayer();
				int horse = -1;
				do {
					horse = p.chooseFirstHorse(horses);
					try {
						//Try to set the winner horse
						track.setWinnerHorse(horse, blackBoard);
						finished = true;
					} catch (NoMoreThanOneHorseArrivedException e1) {
						//Not in the right status -> Application problem
						throw new NotManageableException(e1);
					} catch (MoreThanOneHorseArrivedException e1) {
						//First player had to choose the winner horse
						horses = e1.horses;
						finished = false;
					}
					displayTrackToObservers();
				} while (!finished);
			}
			displayTrackToObservers();
			//Repush the card in deck (bottom)
			movementDeck.pushBottomCard(m);
		}
		displayTrackToObservers();
	}

	private void payOff35() throws NotManageableException, RemoteException {
		if (track.getStatus() != Track.ARRIVED) {
			throw new NotManageableException();
		}

		try {
			betManager.payBets(track, blackBoard, betTokens);
			betManager.payStableOwners(track);
		} catch (NoMoreMoneyInBankException e) {
			throw new NotManageableException(e);
		}
	}

	private void newOddsOrder36() throws NotManageableException, RemoteException {
		blackBoard.updateQuotations(track);
		displayBlackBoardToObservers();
	}

	//---------------------- PHASE 4 ------------------------
	private void endingPhase40() throws RemoteException {
		turn++;
		track.reset();
		displayTrackToObservers();
		players.increaseFirstPlayer();
		if (turn > maxTurns) {
			endGame41();
		}
	}

	private void endGame41() throws RemoteException {
		int maxVP = -1;
		int maxDanari = -1;
		List<Player> winnerPlayers = new ArrayList<Player>();
		for (Player player : players) {
			if (player.getVP() > maxVP) {
				maxVP = player.getVP();
				maxDanari = player.getValue();
			} else if (player.getValue() > maxDanari) {
				maxDanari = player.getValue();
			}
		}
		for (Player player : players) {
			if (player.getValue() == maxDanari && player.getVP() == maxVP) {
				winnerPlayers.add(player);
			}
		}
		Player winnerPlayer = winnerPlayers.get((int) (Math.random() * winnerPlayers.size()));
		winnerPlayer.winGame();
		for(Player player : players) {
			if(player != winnerPlayer) {
				player.loseGame();
			}
		}
	}

	//-----------------------OBSERVERS METHOD--------------------------
	private int displayTrackToObservers() throws RemoteException {
		int max = 0;
		for (GameObserver g : observers) {
			int p = g.displayTrack(track);
			if (p > max) {
				max = p;
			}
		}
		return max;
	}

	private int displayBlackBoardToObservers() throws RemoteException {
		int max = 0;
		for (GameObserver g : observers) {
			int p = g.displayBlackBoard(blackBoard);
			if (p > max) {
				max = p;
			}
		}
		return max;
	}

	private int displayTurnToObservers() throws RemoteException {
		int max = 0;
		for (GameObserver g : observers) {
			int p = g.displayTurn(turn, maxTurns);
			if (p > max) {
				max = p;
			}
		}
		return max;
	}

	private int displayBetsToObservers() throws RemoteException {
		int max = 0;
		for (GameObserver g : observers) {
			List<BetContainer> bets = betManager.getBetsWithPlayers();
			int p = g.displayBets(bets);
			if (p > max) {
				max = p;
			}
		}
		return max;
	}

	private void notifyAddPlayerToObservers() throws RemoteException {
		for (GameObserver g : observers) {
			g.addedPlayer();
		}
	}

	/**
	 * Add observer of the game
	 */
	public void addObserver(GameObserver observer) {
		observers.add(observer);
	}

	/**
	 * Set the pause time
	 * 
	 * @param pause the pause in millis
	 *//*
	public void setPauseMillis(long pause) {
		this.pause = pause;
	}
*/
	private void pause(int _pause) {
		if (_pause == 0) {
			return;
		}
		try {
			Thread.sleep(_pause);
		} catch (InterruptedException e) {
			throw new NotManageableException(e);
		}
	}
}
