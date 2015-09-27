package it.polimi.provaFinale2013;

import static org.junit.Assert.assertTrue;
import it.polimi.provaFinale2013.model.ActionCard;
import it.polimi.provaFinale2013.model.Bank;
import it.polimi.provaFinale2013.model.CLIPlayer;
import it.polimi.provaFinale2013.model.Deck;
import it.polimi.provaFinale2013.model.GameObserver;
import it.polimi.provaFinale2013.model.Track;

import java.rmi.RemoteException;

import org.junit.Before;
import org.junit.Test;

public class GameObserverTest {

	private GameObserver go;

	@Before
	public void setup() {
		go = new CLIPlayer(System.out, System.in, new Bank(), "TestPlayer");
	}

	@Test
	public void testMethods() {
		try {
			go.addedPlayer();
			go.displayBets(null);
			go.displayTrack(new Track(new Deck<ActionCard>()));
			go.displayTurn(0, 0);
		} catch (RemoteException e) {
			e.printStackTrace();
			assertTrue(false);
		}
		assertTrue(true);
	}

}
