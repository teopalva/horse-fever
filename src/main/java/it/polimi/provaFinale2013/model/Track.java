package it.polimi.provaFinale2013.model;

import it.polimi.provaFinale2013.exceptions.AlreadyArrivedException;
import it.polimi.provaFinale2013.exceptions.MoreThanOneHorseArrivedException;
import it.polimi.provaFinale2013.exceptions.NoMoreThanOneHorseArrivedException;
import it.polimi.provaFinale2013.exceptions.NotFinishedException;
import it.polimi.provaFinale2013.exceptions.NotResettedException;
import it.polimi.provaFinale2013.exceptions.NotStartedException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * This class contains the logic for managing the race.
 */
public class Track implements Serializable {

	private static final long serialVersionUID = -3841028144867612312L;
	/**
	 * Status of the race
	 */
	public static final int STOPPED = 0;
	public static final int STARTED = 1;
	public static final int ARRIVED = 2;
	public static final int MORE_THAN_ONE = 3;

	public static final int FINAL_POSITION = 12;

	private int status = STOPPED;
	/**
	 * position of horses on track
	 */
	private List<Integer> horsesPosition;
	private List<List<ActionCard>> actionCards;
	/**
	 * contains the indexes of the horses in order of arrival from 0 to 5
	 */
	private List<Integer> arrivePositions;

	/**
	 * Indicates the num of card movements executed
	 */
	private int numRound;

	transient private Deck<ActionCard> actionDeck;

	/**
	 * Store the last movement card
	 */
	private MovementCard lastMovementCard = null;

	/**
	 * The constructor of the class
	 * 
	 * @param actionDeck the action deck
	 */
	public Track(Deck<ActionCard> actionDeck) {
		this.actionDeck = actionDeck;
		horsesPosition = new ArrayList<Integer>(6);
		arrivePositions = new ArrayList<Integer>(6);
		actionCards = new ArrayList<List<ActionCard>>(6);
		for (int i = 0; i < 6; i++) {
			actionCards.add(new ArrayList<ActionCard>());
		}
		reset();
	}

	/**
	 * Resets the race, all horses in start position.
	 */
	public void reset() {
		status = STOPPED;
		horsesPosition.clear();
		for (int i = 0; i < 6; i++) {
			horsesPosition.add(0);
		}
		arrivePositions.clear();
		numRound = 0;
		for (List<ActionCard> l : actionCards) {
			Iterator<ActionCard> aItr = l.iterator();
			while (aItr.hasNext()) {
				ActionCard a = aItr.next();
				aItr.remove();
				actionDeck.pushBottomCard(a);
			}
		}
	}

	/**
	 * Puts an action card over a stable.
	 * 
	 * @param c ActionCard to put over a stable
	 * @param stable stable where the ActionCard is put [1..6]
	 */
	public void putActionCard(ActionCard c, int stable) {
		c.setFlipped(true);
		actionCards.get(stable - 1).add(c);
	}

	/**
	 * Flips all ActionCards
	 */
	public void flipActionCards() {
		for (List<ActionCard> l : actionCards) {
			for (ActionCard a : l) {
				a.setFlipped(false);
			}
		}
	}

	/**
	 * Initial phase of the race Call only when you want to show action cards and execute their
	 * effects
	 */
	public void start(BlackBoard blackBoard) throws NotResettedException {
		if (status != STOPPED) {
			throw new NotResettedException();
		}
		status = STARTED;
		flipActionCards();
		resolveStartActionCards(blackBoard);
	}

	/**
	 * Executes a movement over all horses
	 * 
	 * @param movementCard movementCard to execute
	 * @param b the blackBoard
	 * @throws MoreThanOneHorseArrivedException
	 */
	public void executeMovementCard(MovementCard movementCard, BlackBoard b) throws MoreThanOneHorseArrivedException {

		//save the movement card
		lastMovementCard = movementCard;

		List<Integer> movements = movementCard.getMovementsForHorse(b);

		//Resolve action cards
		resolveActionCardsBeforeMovement(movements);

		for (int i = 0; i < 6; i++) {
			horsesPosition.set(i, horsesPosition.get(i) + movements.get(i));
		}
		resolveActionCardsArrival();
		computeSomeHorsesArrived(b);
		numRound++;
	}

	/**
	 * sprint over all horses
	 * 
	 * @param b the blackboard
	 * @throws MoreThanOneHorseArrivedException
	 */
	public void sprint(BlackBoard b) throws MoreThanOneHorseArrivedException {
		int first = (int) (Math.random() * 6);
		int second = (int) (Math.random() * 6);
		Set<Integer> sprintHorses = new HashSet<Integer>();
		if (first == second) {
			horsesPosition.set(first, horsesPosition.get(first) + 1);
			sprintHorses.add(horsesPosition.get(first) + 1);
		} else {
			horsesPosition.set(first, horsesPosition.get(first) + 1);
			horsesPosition.set(second, horsesPosition.get(second) + 1);
			sprintHorses.add(horsesPosition.get(first) + 1);
			sprintHorses.add(horsesPosition.get(second) + 1);
		}
		resolveActionCardsSprint(sprintHorses);
		computeSomeHorsesArrived(b);
	}

	/**
	 * @return if all horses have arrived
	 */
	public boolean checkAllHorsesArrived() {
		if (arrivePositions.size() < 6) {
			return false;
		}
		status = ARRIVED;
		return true;
	}

	/**
	 * Compute if some horses have arrived and set status
	 * 
	 * @param blackBoard
	 * @throws MoreThanOneHorseArrivedException
	 */
	private void computeSomeHorsesArrived(BlackBoard blackBoard) throws MoreThanOneHorseArrivedException {
		if (arrivePositions.size() >= 6) {
			throw new NotStartedException();
		}

		int max;
		int maxPosition;
		do {
			max = -1;
			maxPosition = -1;
			boolean finalReached = false;
			List<Integer> positions = new ArrayList<Integer>();
			//Find the horse farthest from arrive (already arrived)
			for (int i = 0; i < 6; i++) {
				if (horsesPosition.get(i) >= FINAL_POSITION && horsesPosition.get(i) > max) {
					max = horsesPosition.get(i); //position of the most advanced horse
					maxPosition = i; //index of the most advanced horse
					finalReached = true;
				}
			}
			if (finalReached) {
				for (int i = 0; i < 6; i++) {
					if (horsesPosition.get(i) == max) {
						positions.add(i + 1); //add all the horses at the same position of the most advanced horse (included)
					}
				}

				if (positions.size() > 1) {
					do {
						List<Integer> requestPosition = new ArrayList<Integer>();
						int bestQuotation = 8;
						int countBestQuotation = 0;
						int bestHorse = -1;
						//Search for best quotation
						Iterator<Integer> iItr = positions.iterator();
						while (iItr.hasNext()) {
							Integer i = iItr.next();

							//Search for card 5 or 12
							ActionCard a5 = null;
							ActionCard a12 = null;
							for (ActionCard a : actionCards.get(i - 1)) {
								if (a.getNumber() == 5) {
									a5 = a;
								}
								if (a.getNumber() == 12) {
									a12 = a;
								}
							}

							if (a12 != null) {
								iItr.remove();
							} else if (a5 != null) {
								bestHorse = i;
								bestQuotation = -1;
							} else {
								int horse = i;
								int quotation = blackBoard.getQuotation(horse);
								if (quotation < bestQuotation) {
									bestHorse = horse;
									bestQuotation = quotation;
								}
							}
						}
						//Search for second best quotation
						for (Integer i : positions) {
							int horse = i;
							int quotation = blackBoard.getQuotation(horse);
							if (quotation == bestQuotation) {
								countBestQuotation++;
								requestPosition.add(i);
							}
						}
						if (countBestQuotation > 1) {
							//Two horses at the same quotation
							status = MORE_THAN_ONE;
							throw new MoreThanOneHorseArrivedException(requestPosition);
						} else {
							arrivePositions.add(bestHorse);
							horsesPosition.set(bestHorse - 1, Integer.MIN_VALUE);
							positions.remove(Integer.valueOf(bestHorse));
						}
					} while (positions.size() > 0);
				} else {
					arrivePositions.add(maxPosition + 1);
					horsesPosition.set(maxPosition, Integer.MIN_VALUE);
				}
			}
		} while (maxPosition >= 0);

		if (arrivePositions.size() == 6) {
			status = ARRIVED;
		}
	}

	private void resolveActionCardsBeforeMovement(List<Integer> movements) {

		for (int i = 0; i < 6; i++) {
			for (ActionCard a : actionCards.get(i)) {
				switch (a.getNumber()) {
				case 1:
					//reset the movement
					if (numRound == 0) {
						movements.set(i, Integer.valueOf(0));
					}
					break;
				case 7:
					if (numRound != 0 && positionInRacePessimist(i) == 6) {
						movements.set(i, Integer.valueOf(4));
					}
					break;
				case 8:
					//reset the movement
					if (numRound == 0) {
						movements.set(i, Integer.valueOf(0));
					}
					break;
				case 9:
					if (numRound == 0) {
						if (movements.get(i) > 0) {
							movements.set(i, Integer.valueOf(movements.get(i) - 1));
						}
					}
					break;
				case 14:
					if (numRound != 0 && positionInRaceOptimist(i) == 1 && arrivePositions.size() < 5) {
						movements.set(i, Integer.valueOf(0));
					}
					break;
				}
			}
		}
	}

	private void resolveActionCardsSprint(Set<Integer> sprintHorses) {

		//Local movements
		List<Integer> movements = new ArrayList<Integer>();
		for (int i = 0; i < 6; i++) {
			movements.add(0);
		}

		for (Integer horse : sprintHorses) {
			for (ActionCard a : actionCards.get(horse)) {
				switch (a.getNumber()) {
				case 1:
					//Nope
					break;
				case 2:
					//Nope
					break;
				case 3:
					movements.set(horse - 1, movements.get(horse - 1) + 1);
					break;
				case 4:
					movements.set(horse - 1, movements.get(horse - 1) + 1);
					break;
				case 5:
					//Fotofinish
					break;
				case 6:
					//Traguardo
					break;
				case 7:
					//Nope
					break;
				case 8:
					//Nope
					break;
				case 9:
					//Nope
					break;
				case 10:
					movements.set(horse - 1, -10000);
					horsesPosition.set(horse - 1, Integer.valueOf(horsesPosition.get(horse - 1) - 1));
					break;
				case 11:
					movements.set(horse - 1, movements.get(horse - 1) - 1);
					break;
				case 12:
					//No action
					break;
				case 13:
					//No action
					break;
				case 14:
					//Nope
					break;
				case 15:
					//Nope
					break;
				case 16:
					//Nope
					break;
				case 19:
					//Nope
					break;
				case 20:
					//Nope
					break;
				}
			}
		}

		//Set movements inside the race
		for (int i = 0; i < 6; i++) {
			if (movements.get(i) > 0) {
				horsesPosition.set(i, Integer.valueOf(horsesPosition.get(i) + movements.get(i)));
			}
		}

	}

	private void resolveActionCardsArrival() {

		//Local movements
		List<Integer> movements = new ArrayList<Integer>();
		for (int i = 0; i < 6; i++) {
			movements.add(0);
		}

		for (int i = 0; i < 6; i++) {
			for (ActionCard a : actionCards.get(i)) {
				switch (a.getNumber()) {
				case 1:
					//Nope
					break;
				case 2:
					//Nope
					break;
				case 3:
					//Nope
					break;
				case 4:
					//Nope
					break;
				case 5:
					//Fotofinish
					break;
				case 6:
					if (horsesPosition.get(i) >= FINAL_POSITION) {
						movements.set(i, movements.get(i) + 2);
					}
					break;
				case 7:
					//Nope
					break;
				case 8:
					//Nope
					break;
				case 9:
					//Nope
					break;
				case 10:
					//Nope
					break;
				case 11:
					//Nope
					break;
				case 12:
					//No action
					break;
				case 13:
					if (horsesPosition.get(i) >= FINAL_POSITION) {
						movements.set(i, -10000);
					}
					break;
				case 14:
					//Nope
					break;
				case 15:
					//Nope
					break;
				case 16:
					//Nope
					break;
				case 19:
					//Nope
					break;
				case 20:
					//Nope
					break;
				}
			}
		}

		//Set movements inside the race
		for (int i = 0; i < 6; i++) {
			if (movements.get(i) > 0) {
				horsesPosition.set(i, Integer.valueOf(horsesPosition.get(i) + movements.get(i)));
			}
		}
	}

	/**
	 * The temporary chart position in race
	 * 
	 * @param horse
	 * @return the position [1..6]
	 */
	private int positionInRacePessimist(int horse) {
		int k = 1;
		int h = 0;
		//Find the current position in the race (only with not winner horses)
		for (Integer i : horsesPosition) {
			if ((h != horse) && (i.intValue() >= horsesPosition.get(horse) || (i.intValue() < 0))) {
				k++;
			}
			h++;
		}
		//Sum the numbers of arrived horses
		return k;
	}

	private int positionInRaceOptimist(int horse) {
		int k = 1;
		//Find the current position in the race (only with not winner horses)
		for (Integer i : horsesPosition) {
			if (!i.equals(Integer.valueOf(horse)) && (i.intValue() > horsesPosition.get(horse) || (i.intValue() < 0))) {
				k++;
			}
		}
		//Sum the numbers of arrived horses
		return k;
	}

	/**
	 * With this function you can set the winner horse
	 * 
	 * @param horse the horse you want to win
	 * @param b the black board
	 * @throws NoMoreThanOneHorseArrivedException if there isn't more than one horses arrived at
	 * same time
	 * @throws MoreThanOneHorseArrivedException it means that there are other horses arrived at same
	 * time. The dispute is not solved
	 */
	public void setWinnerHorse(int horse, BlackBoard b) throws NoMoreThanOneHorseArrivedException, MoreThanOneHorseArrivedException {
		if (status != MORE_THAN_ONE) {
			throw new NoMoreThanOneHorseArrivedException();
		}
		status = STARTED;
		arrivePositions.add(horse);
		horsesPosition.set(horse - 1, Integer.MIN_VALUE);

		if (arrivePositions.size() < 6) {
			computeSomeHorsesArrived(b);
		}
	}

	/**
	 * Get the position of the horse in the chart
	 * 
	 * @param horse the horse that you want to know the position [1..6]
	 * @return the position of the horse [1..6]
	 */
	public int getHorseChartPosition(int horse) throws NotFinishedException {
		if (status != ARRIVED) {
			throw new NotFinishedException();
		}
		for (int i = 0; i < 6; i++) {
			if (arrivePositions.get(i) == horse) {
				return i + 1;
			}
		}
		return -1;
	}

	/**
	 * Get the position of the horse during the race
	 * 
	 * @param horse the horse that you want to know the position [1..6]
	 * @return the position of the horse in the race [0..+inf]
	 */
	public int getHorseRacePosition(int horse) throws NotStartedException, AlreadyArrivedException {
		if (status != STARTED) {
			throw new NotStartedException();
		}
		if (horsesPosition.get(horse - 1) < 0) {
			throw new AlreadyArrivedException();
		}
		return horsesPosition.get(horse - 1);
	}

	/**
	 * Resolve the cards effect activated during the start of the race
	 * 
	 * @param blackBoard the black board
	 */
	public void resolveStartActionCards(BlackBoard blackBoard) {
		for (int i = 0; i < actionCards.size(); i++) {

			//Remove all cards with same letter
			boolean sameLetter = false;
			do {
				sameLetter = false;
				for (int j = 0; j < actionCards.get(i).size() && !sameLetter; j++) {
					for (int k = j + 1; k < actionCards.get(i).size() && !sameLetter; k++) {
						if (actionCards.get(i).get(j).getLetter() == actionCards.get(i).get(k).getLetter() && j != k
								&& actionCards.get(i).get(k).getLetter() != ' ') {
							ActionCard a1 = actionCards.get(i).get(j);
							ActionCard a2 = actionCards.get(i).get(k);
							actionCards.get(i).remove(a1);
							actionCards.get(i).remove(a2);
							actionDeck.pushBottomCard(a1);
							actionDeck.pushBottomCard(a2);
							sameLetter = true;
							break;
						}
					}
				}
			} while (sameLetter);

			boolean negative = true;
			boolean positive = true;
			Iterator<ActionCard> aItr = actionCards.get(i).iterator();
			while (aItr.hasNext()) {
				ActionCard a = aItr.next();
				a.setFlipped(false);
				switch (a.getNumber()) {
				case 1:
					horsesPosition.set(i, 4); //No movement at start
					break;
				case 2:
					horsesPosition.set(i, 1); //Movement at start
					break;
				case 15:
					blackBoard.increaseQuotation(i + 1, -2);
					break;
				case 16:
					//Eliminate every negative cards
					negative = false;
					break;
				case 19:
					blackBoard.increaseQuotation(i + 1, +2);
					break;
				case 20:
					//Eliminate every positive cards
					positive = false;
					break;
				default:
					break;
				}

			}
			if (!negative) {
				//remove all negative cards i this lane
				aItr = actionCards.get(i).iterator();
				while (aItr.hasNext()) {
					ActionCard a = aItr.next();
					int n = a.getNumber();
					if (n >= 8 && n <= 14) {
						aItr.remove();
						actionDeck.pushBottomCard(a);
					}
				}
			}

			if (!positive) {
				//remove all negative cards i this lane
				aItr = actionCards.get(i).iterator();
				while (aItr.hasNext()) {
					ActionCard a = aItr.next();
					int n = a.getNumber();
					if (n >= 1 && n <= 7) {
						aItr.remove();
						actionDeck.pushBottomCard(a);
					}
				}
			}
		}
	}

	/**
	 * Get the status of the race
	 * 
	 * @return the status of the race, compare with constants: STOPPED, STARTED, ARRIVED,
	 * MORE_THAN_ONE, FINAL_POSITION
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Get the current movement card or null if no movement card is set
	 * 
	 * @return the movement card
	 */
	public MovementCard getMovementCard() {
		if (status == STARTED) {
			return lastMovementCard;
		}
		return null;

	}

	/**
	 * Get action cards played on a stable
	 * 
	 * @param s the stable
	 * @return the list of action cards on this stable
	 */
	public List<ActionCard> getActionCardsForStable(int s) {
		if (s < 1 || s > 6) {
			throw new IllegalArgumentException();
		}
		return actionCards.get(s - 1);
	}

}
