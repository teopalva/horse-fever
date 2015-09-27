package it.polimi.provaFinale2013.graphics;

import it.polimi.provaFinale2013.model.Action;
import it.polimi.provaFinale2013.model.ActionCard;
import it.polimi.provaFinale2013.model.Factory;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import org.imgscalr.Scalr;

/**
 * This popup lets the player play an action card by selecting it and the horse which is affected.
 */
public class PlayActionCardWindow extends PopUp {
	private javax.swing.JButton jButton01;
	private javax.swing.JButton jButton02;
	private javax.swing.JButton jButton03;
	private javax.swing.JButton jButton04;
	private javax.swing.JButton jButton05;
	private javax.swing.JButton jButton06;
	private javax.swing.JButton jButtonAction1;
	private javax.swing.JButton jButtonAction2;
	private Action action;
	private int clickedStable = 0;
	private ActionCard clickedCard = null;
	private BufferedImage scaledImage = null;
	private List<JButton> buttons;

	private Border border2 = BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.BLACK, Color.BLACK);

	private static final long serialVersionUID = -6017702971852261751L;

	private List<ActionCard> actionCards;
	private GUIPlayer player;

	/**
	 * The basic constructor of the PlayActionCardWindow.
	 */
	public PlayActionCardWindow() {
		super("PlayAction.png");
	}

	/**
	 * The constructor of the PlayActionCardWindow with parameters.
	 * 
	 * @param actionCards
	 * @param player the GUI player the popup refers to.
	 */
	public PlayActionCardWindow(List<ActionCard> actionCards, GUIPlayer player) {
		super("PlayAction.png");
		this.actionCards = actionCards;
		this.player = player;

		jButton01 = new javax.swing.JButton();
		jButton02 = new javax.swing.JButton();
		jButton03 = new javax.swing.JButton();
		jButton04 = new javax.swing.JButton();
		jButton05 = new javax.swing.JButton();
		jButton06 = new javax.swing.JButton();

		jButtonAction1 = new javax.swing.JButton();
		jButtonAction1.setBorderPainted(false);
		jButtonAction1.setContentAreaFilled(false);

		jButtonAction2 = new javax.swing.JButton();
		jButtonAction2.setBorderPainted(false);
		jButtonAction2.setContentAreaFilled(false);

		buttons = createButtons("stable", jButton01, jButton02, jButton03, jButton04, jButton05, jButton06);

		jButtonAction1.setActionCommand("100");
		Image img1 = null;
		if (actionCards.size() != 0) {
			if (actionCards.size() <= 2) {
				actionCards.get(0).setFlipped(false);
				img1 = Factory.getFactory().getImage(actionCards.get(0));
			}
		}
		scaledImage = Scalr.resize((BufferedImage) img1, 130, 203);
		jButtonAction1.setIcon(new ImageIcon(scaledImage));
		jButtonAction1.addActionListener(new java.awt.event.ActionListener() {
			/**
			 * Calls the action performed when this button is clicked, based on its action command.
			 */
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonActionPerformed(evt);
			}
		});

		if (actionCards.size() > 1) {
			jButtonAction2.setActionCommand("200");
			Image img2 = null;
			if (actionCards.size() == 2) {
				actionCards.get(1).setFlipped(false);
				img2 = Factory.getFactory().getImage(actionCards.get(1));
			}
			scaledImage = Scalr.resize((BufferedImage) img2, 130, 203);
			jButtonAction2.setIcon(new ImageIcon(scaledImage));
			jButtonAction2.addActionListener(new java.awt.event.ActionListener() {
				/**
				 * Calls the action performed when this button is clicked, based on its action
				 * command.
				 */
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					jButtonActionPerformed(evt);
				}
			});
		}

		jPanelLayout.setHorizontalGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanelLayout
						.createSequentialGroup()
						.addGroup(
								jPanelLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(
												jPanelLayout
														.createSequentialGroup()
														.addGap(75, 75, 75)
														.addComponent(jButton01, javax.swing.GroupLayout.PREFERRED_SIZE, 70,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(38, 38, 38)
														.addComponent(jButton02, javax.swing.GroupLayout.PREFERRED_SIZE, 70,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(35, 35, 35)
														.addComponent(jButton03, javax.swing.GroupLayout.PREFERRED_SIZE, 70,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(37, 37, 37)
														.addComponent(jButton04, javax.swing.GroupLayout.PREFERRED_SIZE, 70,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(34, 34, 34)
														.addComponent(jButton05, javax.swing.GroupLayout.PREFERRED_SIZE, 70,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(33, 33, 33)
														.addComponent(jButton06, javax.swing.GroupLayout.PREFERRED_SIZE, 70,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGroup(
												jPanelLayout
														.createSequentialGroup()
														.addGap(205, 205, 205)
														.addComponent(jButtonAction1, javax.swing.GroupLayout.PREFERRED_SIZE, 131,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(92, 92, 92)
														.addComponent(jButtonAction2, javax.swing.GroupLayout.PREFERRED_SIZE, 131,
																javax.swing.GroupLayout.PREFERRED_SIZE))).addContainerGap(75, Short.MAX_VALUE)));
		jPanelLayout.setVerticalGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanelLayout
						.createSequentialGroup()
						.addGap(186, 186, 186)
						.addGroup(
								jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jButton01, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jButton02, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jButton03, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jButton04, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jButton05, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jButton06, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(24, 24, 24)
						.addGroup(
								jPanelLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jButtonAction1, javax.swing.GroupLayout.PREFERRED_SIZE, 203,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jButtonAction2, javax.swing.GroupLayout.PREFERRED_SIZE, 203,
												javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap(116, Short.MAX_VALUE)));

	}

	/**
	 * The actions performed when the buttons are clicked, i.e. the choice of an action card, the
	 * horse and the Confirm button.
	 */
	@Override
	protected void jButtonActionPerformed(java.awt.event.ActionEvent evt) {
		String command = ((JButton) evt.getSource()).getActionCommand();
		int num = Integer.parseInt(command);
		switch (num) {
		case 0:
			//Try to close the window
			if (clickedStable != 0 && clickedCard != null) {
				action = new Action(clickedCard, clickedStable);
				player.playAction(action);
				dispose();
			}
			break;
		case 1:
			clickedStable = 1;
			selectTokenBorder(buttons, jButton01);
			break;
		case 2:
			clickedStable = 2;
			selectTokenBorder(buttons, jButton02);
			break;
		case 3:
			clickedStable = 3;
			selectTokenBorder(buttons, jButton03);
			break;
		case 4:
			clickedStable = 4;
			selectTokenBorder(buttons, jButton04);
			break;
		case 5:
			clickedStable = 5;
			selectTokenBorder(buttons, jButton05);
			break;
		case 6:
			clickedStable = 6;
			selectTokenBorder(buttons, jButton06);
			break;
		case 100:
			if (actionCards.size() != 0) {
				if (actionCards.size() <= 2) {
					clickedCard = actionCards.get(0);
					jButtonAction2.setBorderPainted(false);
					jButtonAction1.setBorder(border2);
					jButtonAction1.setBorderPainted(true);
				}
			}
			break;
		case 200:
			if (actionCards.size() == 2) {
				clickedCard = actionCards.get(1);
				jButtonAction1.setBorderPainted(false);
				jButtonAction2.setBorder(border2);
				jButtonAction2.setBorderPainted(true);
			}
			break;
		default:
			break;
		}

	}

}
