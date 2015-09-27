package it.polimi.provaFinale2013.model;

import java.awt.Image;
import java.io.Serializable;

/**
 * This class represent a bet token. It is used in order to make a bet. A BetToken can be of two
 * type: WIN or SHOW It contains also the stable number [1..6]
 */
public class BetToken implements Serializable, Cloneable {

	private static final long serialVersionUID = -325543283196201414L;
	private int stable;
	private int type;

	public final static int WIN = 1;
	public final static int SHOW = 2;

	/**
	 * Constructor of the class
	 * 
	 * @param stable the stable
	 * @param type the type WIN or SHOW
	 */
	public BetToken(int stable, int type) {
		if (type != WIN && type != SHOW) {
			throw new IllegalArgumentException();
		}
		if (stable < 1 || stable > 6) {
			throw new IllegalArgumentException();
		}
		this.type = type;
		this.stable = stable;
	}

	/**
	 * Set the type of the bet token (WIN, SHOW)
	 * 
	 * @param type the type: BetToken.WIN, BetToken.SHOW
	 */
	public void setType(int type) {
		if (type != WIN && type != SHOW) {
			throw new IllegalArgumentException();
		}
		this.type = type;
	}

	/**
	 * Get the type of the bet token (WIN, SHOW)
	 * 
	 * @return the type: BetToken.WIN, BetToken.SHOW
	 */
	public int getType() {
		return type;
	}

	/**
	 * Return the stable of the bet token
	 * 
	 * @return the stable [1..6]
	 */
	public int getStable() {
		return stable;
	}

	/**
	 * Get the image associated with this token in the correct flip status
	 * 
	 * @return the image of this token
	 */
	public Image getImage() {
		Factory factory = Factory.getFactory();
		return factory.getBetTokenImageByToken(this);
	}

	/**
	 * clone the bet token
	 */
	@Override
	public BetToken clone() {
		return new BetToken(stable, type);
	}

	/**
	 * the equals function
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof BetToken)) {
			return false;
		}
		BetToken token = (BetToken) o;
		if (token.type == type && token.stable == stable) {
			return true;
		}
		return false;

	}

	/**
	 * The hashcode function
	 */
	@Override
	public int hashCode() {
		int hash = 1;
		hash = hash * 17 + 422;
		hash = hash * 31 + stable;
		hash = hash * 17 + type;
		hash = hash * 13;
		return hash;
	}

}
