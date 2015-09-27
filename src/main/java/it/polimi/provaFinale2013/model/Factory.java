package it.polimi.provaFinale2013.model;

import it.polimi.provaFinale2013.exceptions.NotManageableException;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class represents a factory which parses the XML database of all game data and stores them in
 * their appropriate structures. It is based on the Factory and Singleton patterns.
 * 
 */
public final class Factory {
	private static Factory factory = null;
	private Deck<ActionCard> actionDeck;
	private Deck<StableOwnerCard> stableDeck;
	private Deck<CharacterCard> characterDeck;
	private Deck<MovementCard> movementDeck;
	private Map<Card, Image> cardsImageMap;
	private List<List<Image>> betTokens;
	private Image trackImage = null;
	private List<Image> stableTokenImages;
	private static final String IMG_STRING = "image";

	/**
	 * The private constructor of the Factory. It parses the XML database of all game data and
	 * stores them in their appropriate structures.
	 */
	private Factory() {
		try {
			actionDeck = new Deck<ActionCard>();
			stableDeck = new Deck<StableOwnerCard>();
			characterDeck = new Deck<CharacterCard>();
			movementDeck = new Deck<MovementCard>();
			cardsImageMap = new HashMap<Card, Image>();
			stableTokenImages = new ArrayList<Image>();
			betTokens = new ArrayList<List<Image>>();

			InputStream is = Factory.class.getResourceAsStream("database.xml");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(is);
			document.getDocumentElement().normalize();
			NodeList node = document.getElementsByTagName("card");

			for (int i = 0; i < node.getLength(); i++) { // loop on all the card nodes

				Node firstNode = node.item(i);

				if (firstNode.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) firstNode;

					NodeList typeElementList = element.getElementsByTagName("type");
					Element typeElement = (Element) typeElementList.item(0);
					NodeList typeList = typeElement.getChildNodes();
					String type = typeList.item(0).getNodeValue();

					retrieveActionCards(element, type);
					retrieveStableOwnerCards(element, type);
					retrieveCharacterCards(element, type);
					retrieveMovementCards(element, type);
				}

			}

			// Retrieves track
			NodeList nodeTrack = document.getElementsByTagName("track");
			Element trackElement = (Element) nodeTrack.item(0);
			trackImage = loadImage(trackElement, IMG_STRING);

			retrieveStableTokens(document);
			retrieveBetTokens(document);

		} catch (Exception e) {
			throw new NotManageableException(e);
		}
	}

	/**
	 * Parses the XML to find the nodes with the given tag.
	 * 
	 * @param element the DOM element
	 * @param tag the String tag for the search
	 * @return the String value contained into the node
	 */
	private String parseByTag(Element element, String tag) {
		NodeList elementList = element.getElementsByTagName(tag);
		element = (Element) elementList.item(0);
		NodeList list = element.getChildNodes();
		return list.item(0).getNodeValue();
	}

	/**
	 * Loads the Image through its parsed path from database.
	 * 
	 * @param element
	 * @param s the image tag
	 * @return the loaded Image
	 * @throws NotManageableException
	 */
	private Image loadImage(Element element, String s) {
		Image image = null;
		String path = parseByTag(element, s);
		try {
			image = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			throw new NotManageableException(e);
		}
		return image;
	}

	/**
	 * Extracts the String from the node with "name" tag .
	 * 
	 * @param element
	 * @return name string
	 */
	private String extractName(Element element) {
		return parseByTag(element, "name");
	}

	/**
	 * Extracts the String from the node with "number" tag .
	 * 
	 * @param element
	 * @param s the String tag of the node with numeric value
	 * @return number
	 */
	private int extractNumber(Element element, String s) {
		String numberStr = parseByTag(element, s);
		return Integer.parseInt(numberStr);
	}

	/**
	 * Extracts the String from the node with "numbers" tag .
	 * 
	 * @param element
	 * @return String of numbers
	 */
	private String extractNumbers(Element element) {
		return parseByTag(element, "numbers");
	}

	/**
	 * Extracts the String from the node with "letter" tag .
	 * 
	 * @param element
	 * @return letter char
	 */
	private char extractLetter(Element element) {
		String letterString = parseByTag(element, "letter");
		return letterString.charAt(0);
	}

	/**
	 * Parses and retreives all the ActionCards' parameters, instantiates them, put them into an
	 * ActionCard deck and thier images into a HashMap.
	 * 
	 * @param element
	 * @param type the card type extracted from a node
	 * @throws NotManageableException
	 */
	private void retrieveActionCards(Element element, String type) {
		if (type.equals("ACTION")) {
			Image image = loadImage(element, IMG_STRING);
			String name = extractName(element);
			int number = extractNumber(element, "number");
			char letter = ' ';
			if (number != 15 && number != 16 && number != 19 && number != 20) {
				letter = extractLetter(element);
			}
			ActionCard actionCard = new ActionCard(name, number, letter);
			actionDeck.pushTopCard(actionCard);
			cardsImageMap.put(actionCard, image);
		}
	}

	/**
	 * Parses and retreives all the StableOwnerCards' parameters, instantiates them, put them into a
	 * StableOwner deck and thier images into a HashMap.
	 * 
	 * @param element
	 * @param type the card type extracted from a node
	 * @throws NotManageableException
	 */
	private void retrieveStableOwnerCards(Element element, String type) {
		if (type.equals("STABLE_OWNER")) {
			Image image = loadImage(element, IMG_STRING);
			String name = extractName(element);
			int stable = extractNumber(element, "stable");
			StableOwnerCard stableCard = new StableOwnerCard(name, stable);
			stableDeck.pushTopCard(stableCard);
			cardsImageMap.put(stableCard, image);
		}
	}

	/**
	 * Parses and retreives all the CharacterCards' parameters, instantiates them, put them into a
	 * CharacterCard deck and thier images into a HashMap.
	 * 
	 * @param element
	 * @param type the card type extracted from a node
	 * @throws NotManageableException
	 */
	private void retrieveCharacterCards(Element element, String type) {
		if (type.equals("CHARACTER")) {
			Image image = loadImage(element, IMG_STRING);
			String name = extractName(element);
			int danari = extractNumber(element, "danari");
			int stableQuotation = extractNumber(element, "stableQuotation");
			CharacterCard characterCard = new CharacterCard(name, danari, stableQuotation);
			characterDeck.pushTopCard(characterCard);
			cardsImageMap.put(characterCard, image);
		}
	}

	/**
	 * Parses and retreives all the MovementCards' parameters, instantiates them, put them into a
	 * MovementCard deck and thier images into a HashMap.
	 * 
	 * @param element
	 * @param type the card type extracted from a node
	 * @throws NotManageableException
	 */
	private void retrieveMovementCards(Element element, String type) {
		if (type.equals("MOVEMENT")) {
			Image image = loadImage(element, IMG_STRING);
			String numbers = extractNumbers(element);
			int n1 = Character.getNumericValue(numbers.charAt(0));
			int n2 = Character.getNumericValue(numbers.charAt(2));
			int n3 = Character.getNumericValue(numbers.charAt(4));
			int n4 = Character.getNumericValue(numbers.charAt(6));
			int n5 = Character.getNumericValue(numbers.charAt(8));
			int n6 = Character.getNumericValue(numbers.charAt(10));

			MovementCard movementCard = new MovementCard(n1, n2, n3, n4, n5, n6);
			movementDeck.pushTopCard(movementCard);
			cardsImageMap.put(movementCard, image);
		}
	}

	/**
	 * Retreives all BetTokens' images and stores them into a list.
	 * 
	 * @param document
	 * @throws NotManageableException
	 */
	private void retrieveBetTokens(Document document) {
		NodeList nodeBetToken = document.getElementsByTagName("betToken");
		for (int i = 0; i < nodeBetToken.getLength(); i++) {
			Node firstNode = nodeBetToken.item(i);
			if (firstNode.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) firstNode;
				extractNumber(element, "horse");
				Image betTokenWinImage = loadImage(element, "WinImage");
				Image betTokenShowImage = loadImage(element, "ShowImage");
				List<Image> betList = new ArrayList<Image>(2);
				betList.add(betTokenWinImage);
				betList.add(betTokenShowImage);
				betTokens.add(betList);
			}
		}
	}

	/**
	 * Retreives all StableTokens' images and stores them into a list.
	 * 
	 * @param document
	 * @throws NotManageableException
	 */
	private void retrieveStableTokens(Document document) {
		NodeList nodeStableToken = document.getElementsByTagName("stableToken");
		for (int i = 0; i < nodeStableToken.getLength(); i++) {
			Node firstNode = nodeStableToken.item(i);
			if (firstNode.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) firstNode;
				int stableNumber = extractNumber(element, "number");
				Image stableTokenImage = loadImage(element, IMG_STRING);
				stableTokenImages.add(stableNumber - 1, stableTokenImage);
			}
		}
	}

	/**
	 * Instantiates a new Factory only if this happens for the first time as the singleton pattern
	 * suggests.
	 * 
	 * @return the static factory object
	 */
	public static synchronized Factory getFactory() {
		if (factory == null) {
			factory = new Factory();
		}
		return factory;
	}

	/**
	 * @return a deep copy of the ActionCard deck
	 */
	public Deck<ActionCard> makeActionCardDeck() {
		return actionDeck.clone();
	}

	/**
	 * @return a deep copy of the StableOwnerCard deck
	 */
	public Deck<StableOwnerCard> makeStableOwnerCardDeck() {
		return stableDeck.clone();
	}

	/**
	 * @return a deep copy of the CharacterCard deck
	 */
	public Deck<CharacterCard> makeCharacterCardDeck() {
		return characterDeck.clone();
	}

	/**
	 * @return a deep copy of the MovementCard deck
	 */
	public Deck<MovementCard> makeMovementCardDeck() {
		return movementDeck.clone();
	}

	/**
	 * @param card any Card
	 * @return the Image associated in the HashMap to the given card
	 */
	public Image getImage(Card card) {
		Image image = null;
		if (card.getFlipped()) {
			try {
				image = ImageIO.read(getClass().getResource("/FamilyGame/AzioniPositive/retro.jpg"));
			} catch (IOException e) {
				throw new NotManageableException(e);
			}
		} else {
			image = cardsImageMap.get(card);
		}
		return image;
	}

	/**
	 * @return the Track Image
	 */
	public Image getTrackImage() {
		return trackImage;
	}

	/**
	 * @param stable the number associated to a stable
	 * @return the Image of the stable token
	 */
	public Image getTokenImageForStable(int stable) {
		return stableTokenImages.get(stable - 1);
	}

	/**
	 * @param stable the number associated to a stable
	 * @param type the String "win" or "show"
	 * @return the Image of the given token
	 */
	public Image getBetTokenImageByStable(int stable, int type) {
		if (type == BetToken.WIN) {
			return (betTokens.get(stable - 1)).get(0);
		} else {
			return (betTokens.get(stable - 1)).get(1);
		}
	}

	/**
	 * @param token any BetToken
	 * @return the right image for the given BetToken
	 */
	public Image getBetTokenImageByToken(BetToken token) {
		if (token.getType() == BetToken.WIN) {
			return (betTokens.get(token.getStable() - 1)).get(0);
		} else {
			return (betTokens.get(token.getStable() - 1)).get(1);
		}
	}

}