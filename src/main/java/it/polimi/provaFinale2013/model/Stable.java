package it.polimi.provaFinale2013.model;

import java.util.HashMap;
import java.util.Map;

/**
 * This enum contains the numbers associated with colors of stables, useful for text output
 */
public enum Stable {
	BLACK(1, "Black"), BLUE(2, "Blue"), GREEN(3, "Green"), RED(4, "Red"), YELLOW(5, "Yellow"), WHITE(6, "White");

	private int number;
	private String color;

	private static Map<Integer, String> map;
	static {
		map = new HashMap<Integer, String>();
		for (Stable s : Stable.values()) {
			map.put(s.number, s.color);
		}
	}

	/**
	 * Constructor of the class
	 * 
	 * @param n the stable number [1..6]
	 * @param c the color of the stable
	 */
	private Stable(int n, String c) {
		number = n;
		color = c;
	}

	/**
	 * Get the number of the stable
	 * 
	 * @return the number of the stable [1..6]
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Get the color of the stable
	 * 
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * This static method return the color string of the stable
	 * 
	 * @param stable the stable (1 to 6)
	 * @return string color of the string
	 */
	public static String getColorForStable(int stable) {
		if (stable < 1 || stable > 6) {
			throw new IllegalArgumentException();
		}
		return map.get(Integer.valueOf(stable));
	}
}
