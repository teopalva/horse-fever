package it.polimi.provaFinale2013;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import it.polimi.provaFinale2013.exceptions.NoMoreMoneyInBankException;
import it.polimi.provaFinale2013.exceptions.NotEnoughMoneyException;
import it.polimi.provaFinale2013.model.ActionCard;
import it.polimi.provaFinale2013.model.Bank;
import it.polimi.provaFinale2013.model.Banknote;
import it.polimi.provaFinale2013.model.CLIPlayer;
import it.polimi.provaFinale2013.model.CharacterCard;
import it.polimi.provaFinale2013.model.Player;
import it.polimi.provaFinale2013.model.StableOwnerCard;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class PlayerTest {

	private Player player;

	@Before
	public void setup() {
		player = new CLIPlayer(System.out, System.in, new Bank(), "TestPlayer");
	}

	@Test
	public void testName() {
		try {
			assertEquals("TestPlayer", player.getName());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCards() {
		try {
			player.putCard(new CharacterCard("Test", 1, 1));
			player.putCard(new StableOwnerCard("Test", 1));
			player.putCard(new ActionCard("Test", 1, 'L'));
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue(false);
		}
		assertTrue(true);
	}

	@Test
	public void testMessage() {
		try {
			player.displayMessage("Test message");
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue(false);
		}
		assertTrue(true);
	}

	@Test
	public void testDanari() {
		try {
			List<Banknote> banknotes = new ArrayList<Banknote>();
			banknotes.add(new Banknote(100));
			banknotes.add(new Banknote(100));
			banknotes.add(new Banknote(100));
			banknotes.add(new Banknote(500));
			banknotes.add(new Banknote(500));
			banknotes.add(new Banknote(500));
			banknotes.add(new Banknote(1000));
			banknotes.add(new Banknote(1000));
			player.putDanari(banknotes);
			banknotes = player.removeDanari(1500);
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue(false);
		} catch (NotEnoughMoneyException e) {
			e.printStackTrace();
			assertTrue(false);
		} catch (NoMoreMoneyInBankException e) {
			e.printStackTrace();
			assertTrue(false);
		}

		try {
			List<Banknote> banknotes = new ArrayList<Banknote>();
			banknotes.add(new Banknote(100));
			banknotes.add(new Banknote(100));
			banknotes.add(new Banknote(100));
			banknotes.add(new Banknote(500));
			banknotes.add(new Banknote(500));
			banknotes.add(new Banknote(500));
			banknotes.add(new Banknote(1000));
			banknotes.add(new Banknote(1000));
			player.putDanari(banknotes);
			banknotes = player.removeDanari(1500000);
			assertTrue(false);
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue(false);
		} catch (NotEnoughMoneyException e) {
			assertTrue(true);
		} catch (NoMoreMoneyInBankException e) {
			assertTrue(true);
		}

		assertTrue(true);
	}

	@Test
	public void testLoseWinGame() {
		try {
			player.putVP(10);
			player.removeVP(5);
			player.loseGame();
			player.winGame();
			assertEquals(5, player.getVP());
			assertEquals(0, player.getValue());
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue(false);
		}
		assertTrue(true);

	}

}
