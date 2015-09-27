package it.polimi.provaFinale2013.graphics;

/**
 * This popup appears when a player loses the game.
 */
public class LoseWindow extends FinalWindow {
	private static final long serialVersionUID = -3827288275029587769L;

	private GUIPlayer player;

	/**
	 * Basic constructor.
	 */
	public LoseWindow() {
		super("LoseWindow.png");
		player = null;
	}

	/**
	 * Constructor with player.
	 * 
	 * @param player the GUI player who is set to lose.
	 */
	public LoseWindow(GUIPlayer player) {
		super("LoseWindow.png", player);
		this.player = player;
	}

	/**
	 * The action performed when you click the "Confirm" button: the popup is closed.
	 */
	@Override
	protected void jButtonActionPerformed(java.awt.event.ActionEvent evt) {
		player.closeLoseWindow();
		this.dispose();
	}
}
