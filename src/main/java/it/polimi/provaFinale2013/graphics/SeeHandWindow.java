package it.polimi.provaFinale2013.graphics;

import it.polimi.provaFinale2013.model.ActionCard;
import it.polimi.provaFinale2013.model.CharacterCard;
import it.polimi.provaFinale2013.model.Factory;
import it.polimi.provaFinale2013.model.StableOwnerCard;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.ImageIcon;

import org.imgscalr.Scalr;

/**
 * This PopUp displays the cards a player has in his hand.
 */
public class SeeHandWindow extends PopUp {
	private javax.swing.JLabel jLabelAction1;
	private javax.swing.JLabel jLabelAction2;
	private javax.swing.JLabel jLabelCharacter;
	private javax.swing.JLabel jLabelStableOwner;

	private static final long serialVersionUID = 7646541879986232339L;

	/**
	 * Constructor of the SeeHandWindow.
	 * 
	 * @param actionCards
	 * @param characterCard
	 * @param stableOwnerCard
	 * @param player the GUI player the popup refers to
	 */
	public SeeHandWindow(List<ActionCard> actionCards, CharacterCard characterCard, StableOwnerCard stableOwnerCard, GUIPlayer player) {
		super("SeeHand.png");

		jLabelCharacter = new javax.swing.JLabel();
		jLabelStableOwner = new javax.swing.JLabel();
		jLabelAction1 = new javax.swing.JLabel();
		jLabelAction2 = new javax.swing.JLabel();

		Image img = null;
		try {
			BufferedImage scaledImage;
			if (actionCards.size() != 0) {
				if (actionCards.size() <= 2) {
					actionCards.get(0).setFlipped(false);
					img = Factory.getFactory().getImage(actionCards.get(0));
					scaledImage = Scalr.resize((BufferedImage) img, 130, 203);
					jLabelAction1.setIcon(new ImageIcon(scaledImage));
				}
			}

			if (actionCards.size() == 2) {
				actionCards.get(1).setFlipped(false);
				img = Factory.getFactory().getImage(actionCards.get(1));
				scaledImage = Scalr.resize((BufferedImage) img, 130, 203);
				jLabelAction2.setIcon(new ImageIcon(scaledImage));
			}

			img = Factory.getFactory().getImage(stableOwnerCard);
			scaledImage = Scalr.resize((BufferedImage) img, 161, 249);
			jLabelStableOwner.setIcon(new ImageIcon(scaledImage));

			img = Factory.getFactory().getImage(characterCard);
			scaledImage = Scalr.resize((BufferedImage) img, 161, 249);
			jLabelCharacter.setIcon(new ImageIcon(scaledImage));

		} catch (NullPointerException e) {
			//Do nothing
		} catch (IllegalArgumentException e) {
			//Do nothing
		}

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel);
		jPanel.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel1Layout
						.createSequentialGroup()
						.addGap(62, 62, 62)
						.addComponent(jLabelCharacter, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(74, 74, 74)
						.addComponent(jLabelStableOwner, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(11, 11, 11)
						.addGroup(
								jPanel1Layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
										.addGroup(
												jPanel1Layout
														.createSequentialGroup()
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(jLabelAction2, javax.swing.GroupLayout.PREFERRED_SIZE, 130,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGroup(
												jPanel1Layout
														.createSequentialGroup()
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(jLabelAction1, javax.swing.GroupLayout.PREFERRED_SIZE, 130,
																javax.swing.GroupLayout.PREFERRED_SIZE))).addGap(79, 79, 79)));
		jPanel1Layout.setVerticalGroup(jPanel1Layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						jPanel1Layout.createSequentialGroup().addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jLabelStableOwner, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(114, 114, 114))
				.addGroup(
						jPanel1Layout
								.createSequentialGroup()
								.addGap(167, 167, 167)
								.addGroup(
										jPanel1Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jLabelCharacter, javax.swing.GroupLayout.PREFERRED_SIZE, 249,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGroup(
														jPanel1Layout
																.createSequentialGroup()
																.addComponent(jLabelAction1, javax.swing.GroupLayout.PREFERRED_SIZE, 203,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(jLabelAction2, javax.swing.GroupLayout.PREFERRED_SIZE, 203,
																		javax.swing.GroupLayout.PREFERRED_SIZE))).addGap(0, 16, Short.MAX_VALUE)));

	}

}
