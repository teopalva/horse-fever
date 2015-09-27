package it.polimi.provaFinale2013.graphics;

/**
 * This popup appears when a player wins the game.
 */
public class WinWindow extends FinalWindow {

	private static final long serialVersionUID = -4268258820555487690L;

	/**
	 * Constructor with player.
	 * 
	 * @param player the GUI player who is set to win.
	 */
	public WinWindow(GUIPlayer player) {
		super("WinWindow.png", player);
	}
}
