package it.polimi.provaFinale2013;

import static org.junit.Assert.assertTrue;
import it.polimi.provaFinale2013.model.BlackBoard;

import org.junit.Before;
import org.junit.Test;

public class BlackBoardTest {
	BlackBoard blackBoard;

	@Before
	public void setup() {
		this.blackBoard = new BlackBoard();
	}

	@Test
	public void quotationsListTest() {
		for (int i = 1; i <= 6; i++) {
			System.out.printf("Quotation for %d: 1:%d\n", i, blackBoard.getQuotation(i));
		}
	}

	@Test
	public void test() {

		boolean problem = false;
		for (int i = 1; i <= 6; i++) {
			if (blackBoard.getQuotation(i) == -1) {
				problem = true;
			}
		}
		assertTrue(!problem);
	}
}
