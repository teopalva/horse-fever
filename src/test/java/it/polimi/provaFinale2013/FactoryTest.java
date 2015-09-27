package it.polimi.provaFinale2013;

import static org.junit.Assert.assertEquals;
import it.polimi.provaFinale2013.model.ActionCard;
import it.polimi.provaFinale2013.model.CharacterCard;
import it.polimi.provaFinale2013.model.Deck;
import it.polimi.provaFinale2013.model.Factory;
import it.polimi.provaFinale2013.model.MovementCard;
import it.polimi.provaFinale2013.model.StableOwnerCard;

import org.junit.Test;
//import it.polimi.provaFinale2013.graphics.ImageFrame;
//import java.awt.Image;

public class FactoryTest {
	//	private Image image;

	@Test
	public void actionCardsTest() {
		Deck<ActionCard> deck = Factory.getFactory().makeActionCardDeck();
		ActionCard c1 = deck.popCard();
		assertEquals(20, c1.getNumber());
		ActionCard c2 = deck.popCard();
		c2.flip();
		assertEquals(19, c2.getNumber());
		ActionCard c3 = deck.popCard();
		assertEquals(16, c3.getNumber());
		deck.pushTopCard(c3);
		deck.pushTopCard(c2);
		deck.pushTopCard(c1);
		ActionCard c4 = deck.popCard();
		assertEquals(20, c4.getNumber());
		deck.pushTopCard(c4);

		Factory.getFactory().getImage(c1);
		Factory.getFactory().getImage(c2);
		Factory.getFactory().getImage(c3);
	}

	/*	@Test 
		public void AllMovementCardsTest() {
			Deck<MovementCard> deck = Factory.makeMovementCardDeck();
			int counter = 0;
			while(deck.getNumCards() != 0) {
				deck.popCard();
				counter++;
			}
			assertEquals(23, counter);	//number of MovementCards in deck in FamilyGame	
		}
		
		@Test 
		public void AllActionCardsTest() {
			Deck<ActionCard> deck = Factory.makeActionCardDeck();
			int counter = 0;
			while(deck.getNumCards() != 0) {
				deck.popCard();
				counter++;
			}
			assertEquals(18, counter);	//number of actionCards in deck in FamilyGame	
		}
	*/
	@Test
	public void stableCardsTest() {
		Deck<StableOwnerCard> deck = Factory.getFactory().makeStableOwnerCardDeck();
		StableOwnerCard s1 = deck.popCard();
		assertEquals(3, s1.getStable());
		StableOwnerCard s2 = deck.popCard();
		assertEquals(4, s2.getStable());
		StableOwnerCard s3 = deck.popCard();
		assertEquals(1, s3.getStable());
		deck.pushTopCard(s3);
		deck.pushTopCard(s2);
		deck.pushTopCard(s1);
		StableOwnerCard c4 = deck.popCard();
		assertEquals(3, c4.getStable());
		deck.pushTopCard(c4);

		Factory.getFactory().getImage(s1);
		Factory.getFactory().getImage(s2);
		Factory.getFactory().getImage(s3);
	}

	@Test
	public void characterCardsTest() {
		Deck<CharacterCard> deck = Factory.getFactory().makeCharacterCardDeck();
		CharacterCard s1 = deck.popCard();
		assertEquals(6, s1.getStableQuotation());
		CharacterCard s2 = deck.popCard();
		assertEquals(7, s2.getStableQuotation());
		CharacterCard s3 = deck.popCard();
		assertEquals(5, s3.getStableQuotation());
		deck.pushTopCard(s3);
		deck.pushTopCard(s2);
		deck.pushTopCard(s1);
		CharacterCard c4 = deck.popCard();
		assertEquals(6, c4.getStableQuotation());
		deck.pushTopCard(c4);

		Factory.getFactory().getImage(s1);
		Factory.getFactory().getImage(s2);
		Factory.getFactory().getImage(s3);
	}

	@Test
	public void movementCardsTest() {
		Deck<MovementCard> deck = Factory.getFactory().makeMovementCardDeck();
		MovementCard s1 = deck.popCard();
		assertEquals(2, s1.getNumberForHorse(3));
		MovementCard s2 = deck.popCard();
		assertEquals(1, s2.getNumberForHorse(3));
		MovementCard s3 = deck.popCard();
		assertEquals(2, s3.getNumberForHorse(3));
		deck.pushTopCard(s3);
		deck.pushTopCard(s2);
		deck.pushTopCard(s1);
		MovementCard c4 = deck.popCard();
		assertEquals(2, c4.getNumberForHorse(3));
		deck.pushTopCard(c4);

		Factory.getFactory().getImage(s1);
		Factory.getFactory().getImage(s2);
		Factory.getFactory().getImage(s3);
	}

}
