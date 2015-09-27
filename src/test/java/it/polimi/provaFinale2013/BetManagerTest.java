package it.polimi.provaFinale2013;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import it.polimi.provaFinale2013.exceptions.DoubleBetSameHorseException;
import it.polimi.provaFinale2013.exceptions.MoreThanOneHorseArrivedException;
import it.polimi.provaFinale2013.exceptions.NoMoreMoneyInBankException;
import it.polimi.provaFinale2013.model.ActionCard;
import it.polimi.provaFinale2013.model.Bank;
import it.polimi.provaFinale2013.model.Banknote;
import it.polimi.provaFinale2013.model.Bet;
import it.polimi.provaFinale2013.model.BetManager;
import it.polimi.provaFinale2013.model.BetToken;
import it.polimi.provaFinale2013.model.BetTokenManager;
import it.polimi.provaFinale2013.model.BlackBoard;
import it.polimi.provaFinale2013.model.CLIPlayer;
import it.polimi.provaFinale2013.model.Deck;
import it.polimi.provaFinale2013.model.MovementCard;
import it.polimi.provaFinale2013.model.Player;
import it.polimi.provaFinale2013.model.Track;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class BetManagerTest {

	private Bank bank;
	private Player player;

	@Before
	public void setup() {
		bank = new Bank();
		player = new CLIPlayer(System.out, System.in, bank, null);
		List<Banknote> danari = new ArrayList<Banknote>();
		danari.add(new Banknote(100));
		danari.add(new Banknote(500));
		danari.add(new Banknote(1000));
		for (int i = 0; i < 100; i++) {
			bank.putBanknotes(new Banknote(100));
		}
		for (int i = 0; i < 100; i++) {
			bank.putBanknotes(new Banknote(500));
		}
		for (int i = 0; i < 100; i++) {
			bank.putBanknotes(new Banknote(1000));
		}
		try {
			player.putDanari(danari);
		} catch (RemoteException e) {
			assertTrue(false);
		}
		try {
			player.putVP(2);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testBet() {
		BetManager betManager = new BetManager(bank);
		List<Banknote> danari = new ArrayList<Banknote>();
		danari.add(new Banknote(100));
		danari.add(new Banknote(500));
		danari.add(new Banknote(1000));
		Bet bet1 = new Bet(danari, new BetToken(1, BetToken.WIN));
		try {
			betManager.putBet(bet1, player);
		} catch (DoubleBetSameHorseException e) {
			e.printStackTrace();
			assertTrue(false);
		}
		Track track = new Track(new Deck<ActionCard>());
		BlackBoard blackBoard = new BlackBoard();
		blackBoard.initWithDesiredSorting(1, 2, 3, 4, 5, 6);
		track.start(blackBoard);

		MovementCard mc = new MovementCard(6, 5, 4, 3, 2, 1);

		try {
			track.executeMovementCard(mc, blackBoard);
			track.executeMovementCard(mc, blackBoard);
			track.executeMovementCard(mc, blackBoard);
			track.executeMovementCard(mc, blackBoard);
			track.executeMovementCard(mc, blackBoard);
			track.executeMovementCard(mc, blackBoard);
			track.executeMovementCard(mc, blackBoard);
			track.executeMovementCard(mc, blackBoard);
			track.executeMovementCard(mc, blackBoard);
			track.executeMovementCard(mc, blackBoard);
			track.executeMovementCard(mc, blackBoard);
			track.executeMovementCard(mc, blackBoard);
			track.executeMovementCard(mc, blackBoard);
			track.executeMovementCard(mc, blackBoard);
			track.executeMovementCard(mc, blackBoard);
			track.executeMovementCard(mc, blackBoard);
			track.executeMovementCard(mc, blackBoard);
			track.executeMovementCard(mc, blackBoard);
			track.executeMovementCard(mc, blackBoard);
			track.executeMovementCard(mc, blackBoard);
			track.executeMovementCard(mc, blackBoard);
			track.executeMovementCard(mc, blackBoard);
			track.executeMovementCard(mc, blackBoard);
			track.executeMovementCard(mc, blackBoard);
			track.executeMovementCard(mc, blackBoard);
			track.executeMovementCard(mc, blackBoard);
			track.executeMovementCard(mc, blackBoard);
		} catch (MoreThanOneHorseArrivedException e) {
			assertTrue(false);
		} catch (Exception e) {
		}

		int valueBefore = 1000;
		try {
			valueBefore = player.getValue();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			try {
				betManager.payBets(track, blackBoard, new BetTokenManager());
			} catch (RemoteException e) {
				assertTrue(false);
			}
		} catch (NoMoreMoneyInBankException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
		int valueAfter = -1000;
		try {
			valueAfter = player.getValue();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.printf("Danari before: %d danari after: %d betValue: %d", valueBefore, valueAfter, bet1.getValue());
		assertEquals(bet1.getValue() * 3, valueAfter - valueBefore);

	}

}
