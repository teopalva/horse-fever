package it.polimi.provaFinale2013;

import static org.junit.Assert.assertTrue;
import it.polimi.provaFinale2013.model.Bank;
import it.polimi.provaFinale2013.model.CLIPlayer;
import it.polimi.provaFinale2013.model.Player;
import it.polimi.provaFinale2013.model.PlayersQueue;

import java.util.Iterator;

import org.junit.Test;

public class PlayersQueueTest {

	@Test
	public void testPlayerOrder() {
		PlayersQueue<Player> players = new PlayersQueue<Player>();
		Player player1 = new CLIPlayer(System.out, System.in, new Bank(), "player 1");
		Player player2 = new CLIPlayer(System.out, System.in, new Bank(), "player 2");
		Player player3 = new CLIPlayer(System.out, System.in, new Bank(), "player 3");
		Player player4 = new CLIPlayer(System.out, System.in, new Bank(), "player 4");

		players.addPlayer(player1);
		players.addPlayer(player2);
		players.addPlayer(player3);
		players.addPlayer(player4);

		Iterator<Player> pIt = players.iterator();
		Player player = pIt.next();
		if (player != player1) {
			assertTrue(false);
		}
		player = pIt.next();
		if (player != player2) {
			assertTrue(false);
		}
		player = pIt.next();
		if (player != player3) {
			assertTrue(false);
		}
		player = pIt.next();
		if (player != player4) {
			assertTrue(false);
		}

		//Test change first player
		//players.setClockWise(false);
		players.setFirstPlayer(3);
		pIt = players.iterator();
		player = pIt.next();
		if (player != player4) {
			assertTrue(false);
		}
		player = pIt.next();
		if (player != player1) {
			assertTrue(false);
		}
		player = pIt.next();
		if (player != player2) {
			assertTrue(false);
		}
		player = pIt.next();
		if (player != player3) {
			assertTrue(false);
		}
		assertTrue(true);

		//Test change first player
		players.setClockWise(false);
		players.setFirstPlayer(3);
		pIt = players.iterator();
		player = pIt.next();
		if (player != player3) {
			assertTrue(false);
		}
		player = pIt.next();
		if (player != player2) {
			assertTrue(false);
		}
		player = pIt.next();
		if (player != player1) {
			assertTrue(false);
		}
		player = pIt.next();
		if (player != player4) {
			assertTrue(false);
		}
		assertTrue(true);
	}

	@Test
	public void testNumRounds() {
		PlayersQueue<Player> players = new PlayersQueue<Player>();
		Player player1 = new CLIPlayer(System.out, System.in, new Bank(), "player 1");
		Player player2 = new CLIPlayer(System.out, System.in, new Bank(), "player 2");
		Player player3 = new CLIPlayer(System.out, System.in, new Bank(), "player 3");
		Player player4 = new CLIPlayer(System.out, System.in, new Bank(), "player 4");

		players.addPlayer(player1);
		players.addPlayer(player2);
		players.addPlayer(player3);
		players.addPlayer(player4);

		players.setNumRounds(2);
		players.setFirstPlayer(0);

		int c = 0;
		for (@SuppressWarnings("unused") Player p : players) {
			c++;
		}
		if (c != 8) {
			assertTrue(false);
		}
		assertTrue(true);
	}

	@Test
	public void testFirstPlayerException() {
		PlayersQueue<Player> players = new PlayersQueue<Player>();
		Player player1 = new CLIPlayer(System.out, System.in, new Bank(), "player 1");
		Player player2 = new CLIPlayer(System.out, System.in, new Bank(), "player 2");
		Player player3 = new CLIPlayer(System.out, System.in, new Bank(), "player 3");
		Player player4 = new CLIPlayer(System.out, System.in, new Bank(), "player 4");

		players.addPlayer(player1);
		players.addPlayer(player2);
		players.addPlayer(player3);
		players.addPlayer(player4);

		try {
			players.setFirstPlayer(4);
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testRemovePlayer() {
		PlayersQueue<Player> players = new PlayersQueue<Player>();
		Player player1 = new CLIPlayer(System.out, System.in, new Bank(), "player 1");
		Player player2 = new CLIPlayer(System.out, System.in, new Bank(), "player 2");
		Player player3 = new CLIPlayer(System.out, System.in, new Bank(), "player 3");
		Player player4 = new CLIPlayer(System.out, System.in, new Bank(), "player 4");

		players.addPlayer(player1);
		players.addPlayer(player2);
		players.addPlayer(player3);
		players.addPlayer(player4);

		players.setFirstPlayer(0);
		players.removePlayer(player2);

		Iterator<Player> pIt = players.iterator();
		Player player = pIt.next();
		if (player != player1) {
			assertTrue(false);
		}
		player = pIt.next();
		if (player != player3) {
			assertTrue(false);
		}
		player = pIt.next();
		if (player != player4) {
			assertTrue(false);
		}

		if (players.getNumPlayers() != 3) {
			assertTrue(false);
		}

		if (players.getFirstPlayer() != player1) {
			assertTrue(false);
		}

		assertTrue(true);
	}

	@Test
	public void testRemoveWithIteratorPlayer() {
		PlayersQueue<Player> players = new PlayersQueue<Player>();
		Player player1 = new CLIPlayer(System.out, System.in, new Bank(), "player 1");
		Player player2 = new CLIPlayer(System.out, System.in, new Bank(), "player 2");
		Player player3 = new CLIPlayer(System.out, System.in, new Bank(), "player 3");
		Player player4 = new CLIPlayer(System.out, System.in, new Bank(), "player 4");

		players.addPlayer(player1);
		players.addPlayer(player2);
		players.addPlayer(player3);
		players.addPlayer(player4);

		players.setFirstPlayer(0);

		Iterator<Player> prIt = players.iterator();
		prIt.next();
		prIt.next();
		prIt.remove();

		Iterator<Player> pIt = players.iterator();
		Player player = pIt.next();
		if (player != player1) {
			assertTrue(false);
		}
		player = pIt.next();
		if (player != player3) {
			assertTrue(false);
		}
		player = pIt.next();
		if (player != player4) {
			assertTrue(false);
		}

		if (players.getNumPlayers() != 3) {
			assertTrue(false);
		}

		if (players.getFirstPlayer() != player1) {
			assertTrue(false);
		}

		assertTrue(true);
	}

	@Test
	public void testFirstPlayer() {
		PlayersQueue<Player> players = new PlayersQueue<Player>();
		Player player1 = new CLIPlayer(System.out, System.in, new Bank(), "player 1");
		Player player2 = new CLIPlayer(System.out, System.in, new Bank(), "player 2");
		Player player3 = new CLIPlayer(System.out, System.in, new Bank(), "player 3");
		Player player4 = new CLIPlayer(System.out, System.in, new Bank(), "player 4");

		players.addPlayer(player1);
		players.addPlayer(player2);
		players.addPlayer(player3);
		players.addPlayer(player4);

		players.setFirstPlayer(0);

		if (players.getFirstPlayer() != player1) {
			assertTrue(false);
		}

		players.increaseFirstPlayer();

		if (players.getFirstPlayer() != player2) {
			assertTrue(false);
		}

		players.increaseFirstPlayer();

		if (players.getFirstPlayer() != player3) {
			assertTrue(false);
		}

		players.increaseFirstPlayer();

		if (players.getFirstPlayer() != player4) {
			assertTrue(false);
		}

		assertTrue(true);
	}

}
