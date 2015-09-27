package it.polimi.provaFinale2013.graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * This class represents a generic window which can appear during the game.
 */
public class IntermediateWindow extends JLabel {

	private static final long serialVersionUID = 3306510160218312852L;

	/**
	 * Constructor of the IntermediateWindow.
	 */
	public IntermediateWindow(String path) {
		setPreferredSize(new java.awt.Dimension(850, 680));
		setIcon(new ImageIcon(getClass().getResource(path)));
		setVisible(true);
	}
}