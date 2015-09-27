package it.polimi.provaFinale2013;

import static org.junit.Assert.assertTrue;
import it.polimi.provaFinale2013.exceptions.NoMoreMoneyInBankException;
import it.polimi.provaFinale2013.exceptions.NotEnoughMoneyException;
import it.polimi.provaFinale2013.model.Action;
import it.polimi.provaFinale2013.model.ActionCard;
import it.polimi.provaFinale2013.model.Banknote;
import it.polimi.provaFinale2013.model.Bet;
import it.polimi.provaFinale2013.model.BetContainer;
import it.polimi.provaFinale2013.model.BetToken;
import it.polimi.provaFinale2013.model.BlackBoard;
import it.polimi.provaFinale2013.model.Card;
import it.polimi.provaFinale2013.model.Deck;
import it.polimi.provaFinale2013.model.Factory;
import it.polimi.provaFinale2013.model.FamilyGame;
import it.polimi.provaFinale2013.model.GameObserver;
import it.polimi.provaFinale2013.model.Player;
import it.polimi.provaFinale2013.model.Track;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class FamilyGameTest {

	@Test
	public void testTurn() {
		for (int c = 0; c < 1000; c++) {
			for (int i = 2; i <= 6; i++) {
				PlayerTest player = null;
				FamilyGame game = new FamilyGame();
				try {
					for (int j = 0; j < i; j++) {
						player = new PlayerTest();
						game.addPlayer(player);
						game.addObserver(player);
					}
					game.start();
				} catch (RemoteException e) {
					assertTrue(false);
				}
				if (player.getTurn() != numTurn(i)) {
					assertTrue(false);
				}
			}
		}
		assertTrue(true);
	}

	private int numTurn(int players) {
		switch (players) {
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

	//This class permit test the FamilyGame
	class PlayerTest implements Player, GameObserver {
		private int turn;

		public int displayTrack(Track track) throws RemoteException {
			//Nothing
			return 0;
		}

		public int displayBlackBoard(BlackBoard blackBoard) throws RemoteException {
			//Nothing
			return 0;
		}

		public int displayTurn(int turn, int maxTurn) throws RemoteException {
			this.turn = turn;
			return 0;
		}

		public int addedPlayer() throws RemoteException {
			//Nothing
			return 0;
		}

		public void putCard(Card card) throws RemoteException {
			//Nothing
		}

		public Bet requestMakeBet(List<BetToken> betTokens, boolean mandatory) throws NotEnoughMoneyException, RemoteException {
			List<Banknote> banknotes = new ArrayList<Banknote>();
			banknotes.add(new Banknote(100));
			banknotes.add(new Banknote(500));
			banknotes.add(new Banknote(1000));
			banknotes.add(new Banknote(1000));
			banknotes.add(new Banknote(1000));
			banknotes.add(new Banknote(1000));
			banknotes.add(new Banknote(1000));
			banknotes.add(new Banknote(1000));

			BetToken betToken = betTokens.get((int) (Math.random() * betTokens.size()));
			if (mandatory) {
				betToken.setType(BetToken.WIN);
			} else {
				betToken.setType(BetToken.SHOW);
			}
			return new Bet(banknotes, betToken);
		}

		public Action requestPlayAction() throws RemoteException {
			Factory factory = Factory.getFactory();
			Deck<ActionCard> actionCards = factory.makeActionCardDeck();
			return new Action(actionCards.popCard(), (int) (Math.random() * 6 + 1));
		}

		public void displayMessage(String message) throws RemoteException {

		}

		public List<Banknote> removeDanari(int danari) throws NotEnoughMoneyException, NoMoreMoneyInBankException, RemoteException {
			return null;
		}

		public void putDanari(List<Banknote> banknotes) throws RemoteException {
			//Nothing
		}

		public int chooseFirstHorse(List<Integer> horses) throws RemoteException {
			return horses.get((int) (Math.random() * horses.size())).intValue();
		}

		public void putVP(int v) throws RemoteException {
			//Nothing
		}

		public void removeVP(int v) throws RemoteException {
			//Nothing
		}

		public int getVP() throws RemoteException {
			return 1;
		}

		public int getValue() throws RemoteException {
			return 1000;
		}

		public void makeActive() throws RemoteException {
			//Nothing
		}

		public void loseGame() throws RemoteException {
			//Nothing
		}

		public void winGame() throws RemoteException {
			//Nothing
		}

		public String getName() throws RemoteException {
			return "test player";
		}

		public int getTurn() {
			return turn;
		}

		@Override
		public int displayBets(List<BetContainer> bets) throws RemoteException {
			return 0;
		}
	}

}
