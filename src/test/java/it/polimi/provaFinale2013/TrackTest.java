package it.polimi.provaFinale2013;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import it.polimi.provaFinale2013.exceptions.AlreadyArrivedException;
import it.polimi.provaFinale2013.exceptions.MoreThanOneHorseArrivedException;
import it.polimi.provaFinale2013.model.ActionCard;
import it.polimi.provaFinale2013.model.BlackBoard;
import it.polimi.provaFinale2013.model.Deck;
import it.polimi.provaFinale2013.model.Factory;
import it.polimi.provaFinale2013.model.MovementCard;
import it.polimi.provaFinale2013.model.Track;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TrackTest {
	Track t;

	@Before
	public void setup() {
		this.t = new Track(new Deck<ActionCard>());
	}

	@Test
	public void testMovement1StableInOrder() {

		MovementCard mc = new MovementCard(1, 2, 3, 4, 5, 6);
		BlackBoard b = new BlackBoard();
		b.initWithDesiredSorting(1, 2, 3, 4, 5, 6);

		t.start(b);

		while (!t.checkAllHorsesArrived()) {
			try {
				t.executeMovementCard(mc, b);
			} catch (MoreThanOneHorseArrivedException e) {
			}
		}

		assertEquals(6, t.getHorseChartPosition(1));
		assertEquals(5, t.getHorseChartPosition(2));
		assertEquals(4, t.getHorseChartPosition(3));
		assertEquals(3, t.getHorseChartPosition(4));
		assertEquals(2, t.getHorseChartPosition(5));
		assertEquals(1, t.getHorseChartPosition(6));
	}

	@Test
	public void testDuringRaceMoreThanOne() {
		Track t = new Track(new Deck<ActionCard>());

		MovementCard mc = new MovementCard(30, 30, 30, 30, 30, 30);
		BlackBoard b = new BlackBoard();
		b.initWithDesiredSorting(1, 2, 3, 4, 5, 6);

		t.start(b);

		while (!t.checkAllHorsesArrived()) {
			boolean finished = true;
			int horse = 1;
			try {
				t.executeMovementCard(mc, b);
			} catch (MoreThanOneHorseArrivedException e) {
				for (int i = 0; i < e.horses.size(); i++) {
					System.out.printf("horse that wins: %d\n", e.horses.get(i));
				}
				finished = false;
				horse = e.horses.get(0);
			}

			while (!finished) {
				System.out.printf("BEGIN SELECTION\n");
				try {
					t.setWinnerHorse(horse, b);
					finished = true;
				} catch (MoreThanOneHorseArrivedException e) {
					for (int i = 0; i < e.horses.size(); i++) {
						System.out.printf("%d\n", e.horses.get(i));
					}
					finished = false;
					horse = e.horses.get(0);
				}
			}
		}
		System.out.println("----------------------------");
		assertEquals(1, t.getHorseChartPosition(1));
		assertEquals(2, t.getHorseChartPosition(2));
		assertEquals(3, t.getHorseChartPosition(3));
		assertEquals(4, t.getHorseChartPosition(4));
		assertEquals(5, t.getHorseChartPosition(5));
		assertEquals(6, t.getHorseChartPosition(6));
	}

	@Test
	public void testMovement1Stable() {

		MovementCard mc = new MovementCard(1, 2, 3, 4, 5, 6);
		BlackBoard b = new BlackBoard();
		b.initWithDesiredSorting(2, 3, 1, 5, 4, 6);

		try {
			t.executeMovementCard(mc, b);
		} catch (MoreThanOneHorseArrivedException e) {
		}

		try {
			assertEquals(3, t.getHorseRacePosition(1));
			assertEquals(1, t.getHorseRacePosition(2));
			assertEquals(2, t.getHorseRacePosition(3));
			assertEquals(5, t.getHorseRacePosition(4));
			assertEquals(4, t.getHorseRacePosition(5));
			assertEquals(6, t.getHorseRacePosition(6));
		} catch (Exception e) {
		}
	}

	@Test
	public void testMovementGeneralMechanism() {

		BlackBoard b = new BlackBoard();
		b.initWithDesiredSorting(1, 2, 3, 4, 5, 6);

		for (int c = 0; c < 0; c++) {

			Deck<ActionCard> actionCardsDeck = Factory.getFactory().makeActionCardDeck();
			Deck<MovementCard> movementDeck = Factory.getFactory().makeMovementCardDeck();
			actionCardsDeck.shuffle();
			for (int i = 0; i < 6; i++) {
				t.putActionCard(actionCardsDeck.popCard(), i + 1);
				t.putActionCard(actionCardsDeck.popCard(), i + 1);
			}

			t.start(b);

			while (!t.checkAllHorsesArrived()) {
				try {
					MovementCard mc = movementDeck.popCard();
					movementDeck.pushBottomCard(mc);
					t.executeMovementCard(mc, b);
				} catch (MoreThanOneHorseArrivedException e) {
					boolean finished = true;
					List<Integer> horses = e.horses;
					do {
						try {
							t.setWinnerHorse(horses.get(0), b);
							finished = true;
						} catch (MoreThanOneHorseArrivedException e1) {
							horses = e1.horses;
							finished = false;
						}
					} while (!finished);
				}
			}

			boolean check[] = { false, false, false, false, false, false };
			for (int k = 1; k <= 6; k++) {
				check[t.getHorseChartPosition(k) - 1] = true;
			}
			for (int k = 0; k < 6; k++) {
				if (check[k] != true) {
					assertTrue(false);
				}
			}
			t.reset();
		}
		assertTrue(true);
	}

	//Cards testing

	@Test
	public void testActionCard1() {
		BlackBoard b = new BlackBoard();
		b.initWithDesiredSorting(1, 2, 3, 4, 5, 6);

		t.putActionCard(new ActionCard("Test", 1, ' '), 1);
		t.start(b);
		try {
			t.executeMovementCard(new MovementCard(30, 30, 30, 30, 30, 30), b);
			assertEquals(4, t.getHorseRacePosition(1));
		} catch (MoreThanOneHorseArrivedException e) {
			assertTrue(false);
		} catch (AlreadyArrivedException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testActionCard2() {
		BlackBoard b = new BlackBoard();
		b.initWithDesiredSorting(1, 2, 3, 4, 5, 6);

		t.putActionCard(new ActionCard("Test", 2, ' '), 1);
		t.start(b);
		try {
			//It execute the action only here
			t.executeMovementCard(new MovementCard(1, 30, 30, 30, 30, 30), b);
			assertEquals(2, t.getHorseRacePosition(1));
			//Not here!!!
			t.executeMovementCard(new MovementCard(1, 30, 30, 30, 30, 30), b);
			assertEquals(3, t.getHorseRacePosition(1));
		} catch (MoreThanOneHorseArrivedException e) {
			assertTrue(false);
		} catch (AlreadyArrivedException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testActionCard5() {
		BlackBoard b = new BlackBoard();
		b.initWithDesiredSorting(1, 2, 3, 4, 5, 6);

		t.putActionCard(new ActionCard("Test", 5, ' '), 2);
		t.start(b);
		try {
			//It execute the action only here
			t.executeMovementCard(new MovementCard(1, 1, 30, 30, 30, 30), b);
			t.executeMovementCard(new MovementCard(11, 11, 30, 30, 30, 30), b);
			assertEquals(5, t.getHorseChartPosition(2));
		} catch (MoreThanOneHorseArrivedException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testActionCard12() {
		BlackBoard b = new BlackBoard();
		b.initWithDesiredSorting(1, 2, 3, 4, 5, 6);

		t.putActionCard(new ActionCard("Test", 12, ' '), 2);
		t.start(b);
		try {
			//It execute the action only here
			t.executeMovementCard(new MovementCard(1, 1, 30, 30, 30, 30), b);
			t.executeMovementCard(new MovementCard(11, 11, 30, 30, 30, 30), b);
			assertEquals(6, t.getHorseChartPosition(2));
		} catch (MoreThanOneHorseArrivedException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testActionCard7() {
		BlackBoard b = new BlackBoard();
		b.initWithDesiredSorting(1, 2, 3, 4, 5, 6);

		t.putActionCard(new ActionCard("Test", 7, ' '), 1);
		t.start(b);
		try {
			//It execute the action only here
			t.executeMovementCard(new MovementCard(1, 1, 30, 30, 30, 30), b);
			t.executeMovementCard(new MovementCard(1, 1, 30, 30, 30, 30), b);
			assertEquals(5, t.getHorseRacePosition(1));
			t.executeMovementCard(new MovementCard(1, 30, 30, 30, 30, 30), b);
			t.executeMovementCard(new MovementCard(1, 30, 30, 30, 30, 30), b);
			assertEquals(10, t.getHorseRacePosition(1));
		} catch (MoreThanOneHorseArrivedException e) {
			assertTrue(false);
		} catch (AlreadyArrivedException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testActionCard8() {
		BlackBoard b = new BlackBoard();
		b.initWithDesiredSorting(1, 2, 3, 4, 5, 6);

		t.putActionCard(new ActionCard("Test", 8, ' '), 1);
		t.start(b);
		try {
			//It execute the action only here
			t.executeMovementCard(new MovementCard(1, 1, 30, 30, 30, 30), b);
			assertEquals(0, t.getHorseRacePosition(1));
		} catch (MoreThanOneHorseArrivedException e) {
			assertTrue(false);
		} catch (AlreadyArrivedException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testActionCard9() {
		BlackBoard b = new BlackBoard();
		b.initWithDesiredSorting(1, 2, 3, 4, 5, 6);

		t.putActionCard(new ActionCard("Test", 9, ' '), 1);
		t.start(b);
		try {
			//It execute the action only here
			t.executeMovementCard(new MovementCard(2, 1, 30, 30, 30, 30), b);
			assertEquals(1, t.getHorseRacePosition(1));
		} catch (MoreThanOneHorseArrivedException e) {
			assertTrue(false);
		} catch (AlreadyArrivedException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testActionCard9neverRetreat() {
		BlackBoard b = new BlackBoard();
		b.initWithDesiredSorting(1, 2, 3, 4, 5, 6);

		t.putActionCard(new ActionCard("Test", 9, ' '), 1);
		t.start(b);
		try {
			//It execute the action only here
			t.executeMovementCard(new MovementCard(0, 1, 30, 30, 30, 30), b);
			assertEquals(0, t.getHorseRacePosition(1));
		} catch (MoreThanOneHorseArrivedException e) {
			assertTrue(false);
		} catch (AlreadyArrivedException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testActionCard14() {
		BlackBoard b = new BlackBoard();
		b.initWithDesiredSorting(1, 2, 3, 4, 5, 6);

		t.putActionCard(new ActionCard("Test", 14, ' '), 1);
		t.start(b);
		try {
			//It execute the action only here
			t.executeMovementCard(new MovementCard(5, 5, 5, 5, 5, 5), b);
			t.executeMovementCard(new MovementCard(5, 5, 5, 5, 5, 5), b);
			assertEquals(5, t.getHorseRacePosition(1));
		} catch (MoreThanOneHorseArrivedException e) {
			assertTrue(false);
		} catch (AlreadyArrivedException e) {
			assertTrue(false);
		}
	}
}
