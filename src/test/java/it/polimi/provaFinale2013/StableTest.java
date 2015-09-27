package it.polimi.provaFinale2013;

import static org.junit.Assert.assertTrue;
import it.polimi.provaFinale2013.model.Stable;

import org.junit.Test;

public class StableTest {

	@Test
	public void test() {
		if (Stable.BLACK.getNumber() != 1) {
			assertTrue(false);
		}
		if (Stable.BLUE.getNumber() != 2) {
			assertTrue(false);
		}
		if (Stable.GREEN.getNumber() != 3) {
			assertTrue(false);
		}
		if (Stable.RED.getNumber() != 4) {
			assertTrue(false);
		}
		if (Stable.YELLOW.getNumber() != 5) {
			assertTrue(false);
		}
		if (Stable.WHITE.getNumber() != 6) {
			assertTrue(false);
		}

		if (!Stable.getColorForStable(1).equals("Black")) {
			assertTrue(false);
		}
		if (!Stable.getColorForStable(2).equals("Blue")) {
			assertTrue(false);
		}
		if (!Stable.getColorForStable(3).equals("Green")) {
			assertTrue(false);
		}
		if (!Stable.getColorForStable(4).equals("Red")) {
			assertTrue(false);
		}
		if (!Stable.getColorForStable(5).equals("Yellow")) {
			assertTrue(false);
		}
		if (!Stable.getColorForStable(6).equals("White")) {
			assertTrue(false);
		}

		try {
			Stable.getColorForStable(0);
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}

		try {
			Stable.getColorForStable(-1);
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}

		try {
			Stable.getColorForStable(7);
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}

		assertTrue(true);
	}

}
