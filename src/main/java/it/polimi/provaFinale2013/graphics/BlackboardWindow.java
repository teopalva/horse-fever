package it.polimi.provaFinale2013.graphics;

import it.polimi.provaFinale2013.model.BetToken;
import it.polimi.provaFinale2013.model.BlackBoard;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.imgscalr.Scalr;

/**
 * This is the JPanel which contains the 6 rows of the quotations with their stables.
 */
public class BlackboardWindow extends JPanel {

	private static final long serialVersionUID = 2267721863618358610L;

	private JPanel jPanelToken2;
	private JPanel jPanelToken3;
	private JPanel jPanelToken4;
	private JPanel jPanelToken5;
	private JPanel jPanelToken6;
	private JPanel jPanelToken7;

	/**
	 * The constructor of BlackboardWindow with its 6 internal JPanels with GroupLayout.
	 */
	public BlackboardWindow() {
		jPanelToken2 = new JPanel();
		jPanelToken3 = new JPanel();
		jPanelToken4 = new JPanel();
		jPanelToken5 = new JPanel();
		jPanelToken6 = new JPanel();
		jPanelToken7 = new JPanel();

		setSize(new java.awt.Dimension(205, 153));
		setLayout(new java.awt.GridLayout(6, 1));

		add(jPanelToken2);
		add(jPanelToken3);
		add(jPanelToken4);
		add(jPanelToken5);
		add(jPanelToken6);
		add(jPanelToken7);
		jPanelToken2.setOpaque(false);
		jPanelToken3.setOpaque(false);
		jPanelToken4.setOpaque(false);
		jPanelToken5.setOpaque(false);
		jPanelToken6.setOpaque(false);
		jPanelToken7.setOpaque(false);
	}

	/**
	 * This function displays the stables at the right quotation.
	 * 
	 * @param blackBoard the blackboard which contains the quotations
	 */
	public void displayBlackBoard(BlackBoard blackBoard) {
		// Remove all previous quotations
		jPanelToken2.removeAll();
		jPanelToken3.removeAll();
		jPanelToken4.removeAll();
		jPanelToken5.removeAll();
		jPanelToken6.removeAll();
		jPanelToken7.removeAll();
		for (int i = 2; i <= 7; i++) {
			List<Integer> stables = blackBoard.getStablesAtQuotation(i);
			// Display new quotations
			for (Integer stable : stables) {
				Image img = (new BetToken(stable, BetToken.WIN)).getImage();
				Image scaledImage = Scalr.resize((BufferedImage) img, 25, 25);
				JLabel imageLabel = new JLabel();
				imageLabel.setSize(25, 25);
				imageLabel.setIcon(new ImageIcon(scaledImage));
				switch (i) {
				case 2:
					jPanelToken2.add(imageLabel);
					break;
				case 3:
					jPanelToken3.add(imageLabel);
					break;
				case 4:
					jPanelToken4.add(imageLabel);
					break;
				case 5:
					jPanelToken5.add(imageLabel);
					break;
				case 6:
					jPanelToken6.add(imageLabel);
					break;
				case 7:
					jPanelToken7.add(imageLabel);
					break;
				default:
					break;
				}
			}
		}
	}
}
