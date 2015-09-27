package it.polimi.provaFinale2013.model;

import java.io.Serializable;

/**
 * 
 * This class contains an action executed by a player use the methods getStable() and
 * getActionCard() in order to retrieve the desired informations
 */

public class Action implements Serializable {

	private static final long serialVersionUID = -1844522380566414797L;
	private ActionCard actionCard;
	private int stable;

	/**
	 * Constructor of the class
	 * 
	 * @param actionCard the actionCard
	 * @param stable the stable [1..6]
	 */
	public Action(ActionCard actionCard, int stable) {
		this.actionCard = actionCard.clone();
		this.stable = stable;
	}

	/**
	 * get the stable
	 * 
	 * @return the stable [1..6]
	 */
	public int getStable() {
		return stable;
	}

	/**
	 * get action card
	 * 
	 * @return the action card
	 */
	public ActionCard getActionCard() {
		return actionCard.clone();
	}

}
