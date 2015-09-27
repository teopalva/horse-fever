package it.polimi.provaFinale2013.model;

import it.polimi.provaFinale2013.exceptions.NotFinishedException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents the blackboard which contains the quotations [1:2, .., 1:7] of stables
 * [1..6]. The quotation are represented in the public interface like a number(es: 2 indicates 1:2)
 * The stables are represented by a number [1..6]
 */
public class BlackBoard implements Serializable {

	private static final long serialVersionUID = -6263414781533960489L;

	/**
	 * A list of 6 rows (quotations) [0-5]. Each one contains a list of stables [1-6]
	 */
	private List<List<Integer>> quotations;

	/**
	 * Constructor of the BlackBoard.
	 */
	public BlackBoard() {
		quotations = new ArrayList<List<Integer>>();
		for (int i = 0; i < 6; i++) {
			quotations.add(new ArrayList<Integer>());
		}
		init();
	}

	/**
	 * Initializes the quotation with random positions of stables.
	 */
	private void init() {
		List<Integer> stables = new ArrayList<Integer>();
		for (int i = 1; i <= 6; i++) {
			stables.add(i);
		}

		int k = 5;
		while (stables.size() > 0) {
			int rand = (int) (Math.random() * stables.size());
			quotations.get(k).clear();
			quotations.get(k).add(stables.get(rand));
			stables.remove(rand);
			k--;
		}
	}

	/**
	 * Returns the quotation for the stable.
	 * 
	 * @param stable the stable you want the quotation of
	 * @return the quotation of the chosen stable
	 */
	public int getQuotation(int stable) {
		if (stable < 1 || stable > 6) {
			throw new IllegalArgumentException();
		}

		for (int i = 0; i < 6; i++) {
			if (quotations.get(i).contains(Integer.valueOf(stable))) {
				return i + 2;
			}
		}
		return -1;
	}

	/**
	 * Returns the stables for the quotation.
	 * 
	 * @param q indicates the quotation
	 * @return List of stables at the q quotation
	 */
	public List<Integer> getStablesAtQuotation(int q) {
		if (q < 2 || q > 7) {
			throw new IllegalArgumentException();
		}

		//Return a clone of the original list
		return new ArrayList<Integer>(quotations.get(q - 2));
	}

	/**
	 * Updates the quotation of stables.
	 * 
	 * @param track the object track
	 */
	public void updateQuotations(Track track) {
		if (track == null) {
			throw new NullPointerException();
		}
		if (!track.checkAllHorsesArrived()) {
			throw new NotFinishedException();
		}

		for (int i = 0; i < 6; i++) {
			Iterator<Integer> sIter = quotations.get(i).iterator();
			while (sIter.hasNext()) {
				Integer stable = sIter.next();
				int position = track.getHorseChartPosition(stable);
				if (position - 1 < i && position != -1) {
					quotations.get(i - 1).add(stable);
					sIter.remove();
				} else if (position - 1 > i) {
					quotations.get(i + 1).add(stable);
					sIter.remove();
				}
			}
		}

	}

	/**
	 * Return the quotation for all stables in a bidimensional list
	 * 
	 * @return the bidimensional list of the stables on the blackBoard
	 */
	public List<List<Integer>> getQuotations() {
		return quotations;
	}

	/**
	 * Method for debugging and test purpose.
	 */
	public void initWithDesiredSorting(int h1, int h2, int h3, int h4, int h5, int h6) {
		for (int k = 0; k < 6; k++) {
			quotations.get(k).clear();
		}

		quotations.get(0).add(h1); //1:2
		quotations.get(1).add(h2); //1:3
		quotations.get(2).add(h3); //1:4
		quotations.get(3).add(h4); //1:5
		quotations.get(4).add(h5); //1:6
		quotations.get(5).add(h6); //1:7
	}

	/**
	 * Increase the quotation of the selected stable (PA: 1:2 -> increase(+1) -> 1:3)
	 * 
	 * @param stable the stable to increase
	 * @param increase the increase
	 */
	public void increaseQuotation(int stable, int increase) {
		if (stable < 1 || stable > 6) {
			throw new IllegalArgumentException();
		}

		int stableQuotation = 0;
		boolean found = false;
		for (int i = 0; i < 6 && !found; i++) {
			for (int j = 0; j < quotations.get(i).size() && !found; j++) {
				if (quotations.get(i).get(j).equals(stable)) {
					stableQuotation = i;
					quotations.get(i).remove(j);
					found = true;
				}
			}
		}

		if (!found) {
			throw new IllegalArgumentException();
		}

		stableQuotation += increase;
		if (stableQuotation < 0) {
			stableQuotation = 0;
		}
		if (stableQuotation > 5) {
			stableQuotation = 5;
		}

		quotations.get(stableQuotation).add(stable);
	}

}
