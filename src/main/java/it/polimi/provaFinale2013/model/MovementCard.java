package it.polimi.provaFinale2013.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Movement Card class, which contains 6 numbers, each one associated to a quotation on the
 * blackboard.
 * 
 * @extends Card
 * @implements Serializable
 */
public class MovementCard extends Card implements Serializable {
	private static final long serialVersionUID = 8511970358718587337L;
	private List<Integer> numbers;

	/**
	 * Constructor of the MovementCard.
	 * 
	 * @param n0 to n5: the 6 numbers on a MovementCard [value 0-4]
	 */
	public MovementCard(int n0, int n1, int n2, int n3, int n4, int n5) {
		numbers = new ArrayList<Integer>();
		numbers.add(n0);
		numbers.add(n1);
		numbers.add(n2);
		numbers.add(n3);
		numbers.add(n4);
		numbers.add(n5);
	}

	/**
	 * The constructor of the class
	 * 
	 * @param numbers a list of Integer that represent the numbers
	 */
	public MovementCard(List<Integer> numbers) {
		super();
		//Make a deep copy of the List
		this.numbers = new ArrayList<Integer>();
		for (Integer i : numbers) {
			this.numbers.add(Integer.valueOf(i));
		}
	}

	/**
	 * @param horseQuotation the row of the quotation [0-5]
	 * @return the number of squares the horses on this row have to move forward
	 */
	public int getNumberForHorse(int horseQuotation) {
		return numbers.get(horseQuotation);
	}

	/**
	 * Get a list of Integer that represent movements
	 * 
	 * @return
	 */
	public List<Integer> getNumbers() {
		return numbers;
	}

	/**
	 * Get movements for each horse
	 * 
	 * @param bb
	 * @return list of movements for each horse
	 */
	public List<Integer> getMovementsForHorse(BlackBoard bb) {
		List<Integer> movements = new ArrayList<Integer>();
		for (int i = 0; i < 6; i++) {
			movements.add(numbers.get(bb.getQuotation(i + 1) - 2));
		}
		return movements;
	}

	/**
	 * clone method
	 */
	@Override
	public MovementCard clone() {
		return new MovementCard(numbers);
	}

	/**
	 * equals method
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MovementCard)) {
			return false;
		}
		MovementCard c = (MovementCard) obj;

		boolean equal = true;
		for (int i = 0; i < 6; i++) {
			if (!c.numbers.get(i).equals(numbers.get(i))) {
				equal = false;
			}
		}
		return equal;

	}

	/**
	 * hashCode method
	 */
	@Override
	public int hashCode() {
		int hash = 1;
		hash = (hash * 17 + 173 / 10);
		for (int i = 0; i < 6; i++) {
			hash = hash * 3 + numbers.get(i);
		}
		hash = hash * 13;
		return hash;
	}

}
