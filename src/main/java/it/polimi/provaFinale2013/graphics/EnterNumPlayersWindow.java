package it.polimi.provaFinale2013.graphics;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

/**
 * 
 * This PopUp asks for the number of players for the new game.
 * 
 */
public class EnterNumPlayersWindow extends PopUp {
	private static final long serialVersionUID = 5644252736289255158L;
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JButton jButton3;
	private javax.swing.JButton jButton4;
	private javax.swing.JButton jButton5;
	private List<JButton> buttons;
	private int numPlayers = 0;
	private PlayerNumberDelegate delegate;

	/**
	 * Constructor of the EnterNumPlayersWindow.
	 * 
	 * @param delegate the interface which manages the passage of the number of players
	 */
	public EnterNumPlayersWindow(PlayerNumberDelegate delegate) {
		super("EnterNumberOfPlayers.png");

		this.delegate = delegate;

		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();
		jButton3 = new javax.swing.JButton();
		jButton4 = new javax.swing.JButton();
		jButton5 = new javax.swing.JButton();
		buttons = new ArrayList<JButton>();

		buttons.add(jButton1);
		buttons.add(jButton2);
		buttons.add(jButton3);
		buttons.add(jButton4);
		buttons.add(jButton5);

		jButton1.setActionCommand("1");
		jButton2.setActionCommand("2");
		jButton3.setActionCommand("3");
		jButton4.setActionCommand("4");
		jButton5.setActionCommand("5");

		String img = null;
		for (int i = 0; i < 5; i++) {
			buttons.get(i).setBorderPainted(false);
			buttons.get(i).setContentAreaFilled(false);
			if (i == 0) {
				img = "2players.png";
			}
			if (i == 1) {
				img = "3players.png";
			}
			if (i == 2) {
				img = "4players.png";
			}
			if (i == 3) {
				img = "5players.png";
			}
			if (i == 4) {
				img = "6players.png";
			}
			buttons.get(i).setIcon(new ImageIcon(getClass().getResource(img)));
			buttons.get(i).addActionListener(new java.awt.event.ActionListener() {
				/**
				 * Calls the action performed when the buttons are clicked, based on their action
				 * command.
				 */
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					jButtonActionPerformed(evt);
				}
			});
		}

		javax.swing.GroupLayout jPanelLayout = new javax.swing.GroupLayout(jPanel);
		jPanel.setLayout(jPanelLayout);
		jPanelLayout.setHorizontalGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						jPanelLayout.createSequentialGroup().addGap(167, 167, 167)
								.addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(10, 10, 10)
								.addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(11, 11, 11)
								.addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));
		jPanelLayout.setVerticalGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				javax.swing.GroupLayout.Alignment.TRAILING,
				jPanelLayout
						.createSequentialGroup()
						.addContainerGap(285, Short.MAX_VALUE)
						.addGroup(
								jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(244, 244, 244)));
	}

	/**
	 * The actions performed when the buttons are clicked, i.e. the choice of the number of players
	 * and the Confirm button
	 */
	@Override
	protected void jButtonActionPerformed(java.awt.event.ActionEvent evt) {
		String command = ((JButton) evt.getSource()).getActionCommand();
		int num = Integer.parseInt(command);
		switch (num) {
		case 0:
			if (numPlayers != 0) {
				delegate.setNumPlayers(numPlayers);
				this.dispose();
			}
			break;
		case 1:
			numPlayers = 2;
			selectBorder(buttons, jButton1);
			break;
		case 2:
			numPlayers = 3;
			selectBorder(buttons, jButton2);
			break;
		case 3:
			numPlayers = 4;
			selectBorder(buttons, jButton3);
			break;
		case 4:
			numPlayers = 5;
			selectBorder(buttons, jButton4);
			break;
		case 5:
			numPlayers = 6;
			selectBorder(buttons, jButton5);
			break;
		default:
			break;
		}
	}

	/**
	 * Highlights the border of the button which has just been clicked.
	 * 
	 * @param buttons
	 * @param clicked the JButton which has just been clicked
	 */
	private void selectBorder(List<JButton> buttons, JButton clicked) {
		Border border = BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.WHITE, Color.WHITE);
		for (JButton b : buttons) {
			b.setBorderPainted(false);
		}
		clicked.setBorder(border);
		clicked.setBorderPainted(true);
	}

}
