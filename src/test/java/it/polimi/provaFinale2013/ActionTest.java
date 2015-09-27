package it.polimi.provaFinale2013;

import static org.junit.Assert.assertTrue;
import it.polimi.provaFinale2013.model.Action;
import it.polimi.provaFinale2013.model.ActionCard;

import org.junit.Test;

public class ActionTest {

	@Test
	public void test() {
		Action action = new Action(new ActionCard("Prova", 1, 'P'), 1);

		if (action.getStable() != 1) {
			assertTrue(false);
		}
		if (action.getActionCard().equals(action)) {
			assertTrue(false);
		}
		assertTrue(true);
	}

}
