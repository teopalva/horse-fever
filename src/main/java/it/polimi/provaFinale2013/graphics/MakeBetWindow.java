package it.polimi.provaFinale2013.graphics;

import it.polimi.provaFinale2013.exceptions.NoMoreMoneyInBankException;
import it.polimi.provaFinale2013.exceptions.NotEnoughMoneyException;
import it.polimi.provaFinale2013.model.Bet;
import it.polimi.provaFinale2013.model.BetToken;
import it.polimi.provaFinale2013.model.Factory;

import java.awt.Color;
import java.awt.Image;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * This popup lets the player make his bet by selecting the amount of money and the horse.
 */
public class MakeBetWindow extends PopUp {

	private static final long serialVersionUID = -4202091544111039682L;

	private List<BetToken> betTokens;
	private GUIPlayer player;

	private javax.swing.JButton jButton01;
	private javax.swing.JButton jButton02;
	private javax.swing.JButton jButton03;
	private javax.swing.JButton jButton04;
	private javax.swing.JButton jButton05;
	private javax.swing.JButton jButton06;

	private List<JButton> buttons;

	private javax.swing.JButton jButtonUp;
	private javax.swing.JButton jButtonDown;

	private javax.swing.JButton jButtonBank1;
	private javax.swing.JButton jButtonBank2;
	private javax.swing.JButton jButtonBank3;

	private javax.swing.JLabel jLabelMoney;

	private int bettedMoney = 0;
	private BetToken selectedBetToken = null;

	private List<Boolean> present;
	private boolean mandatory;

	/**
	 * Constructor of the MakeBetWindow.
	 * 
	 * @param betTokens
	 * @param player
	 * @param mandatory if the player must bet or can skip this betting phase
	 */
	public MakeBetWindow(List<BetToken> betTokens, GUIPlayer player, boolean mandatory) {
		super("MakeBet.png");

		this.mandatory = mandatory;

		bettedMoney = 0;

		buttons = new ArrayList<JButton>();
		present = new ArrayList<Boolean>();
		for (int i = 0; i < 6; i++) {
			present.add(i, false);
		}

		jButton01 = new javax.swing.JButton();
		jButton03 = new javax.swing.JButton();
		jButton04 = new javax.swing.JButton();
		jButton02 = new javax.swing.JButton();
		jButton06 = new javax.swing.JButton();
		jButton05 = new javax.swing.JButton();
		jButtonBank1 = new javax.swing.JButton();
		jButtonBank2 = new javax.swing.JButton();
		jButtonBank3 = new javax.swing.JButton();
		jButtonUp = new javax.swing.JButton();
		jButtonDown = new javax.swing.JButton();

		jLabelMoney = new JLabel("" + bettedMoney, JLabel.CENTER);
		jLabelMoney.setFont(font);
		jLabelMoney.setForeground(Color.white);

		buttons = createButtons("bet", jButton01, jButton02, jButton03, jButton04, jButton05, jButton06);

		jButtonBank1.setActionCommand("100");
		jButtonBank1.setContentAreaFilled(false);
		jButtonBank1.addActionListener(new java.awt.event.ActionListener() {
			/**
			 * Calls the action performed when this button is clicked, based on its action command.
			 */
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonActionPerformed(evt);
			}
		});
		jButtonBank2.setActionCommand("500");
		jButtonBank2.setContentAreaFilled(false);
		jButtonBank2.addActionListener(new java.awt.event.ActionListener() {
			/**
			 * Calls the action performed when this button is clicked, based on its action command.
			 */
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonActionPerformed(evt);
			}
		});
		jButtonBank3.setActionCommand("1000");
		jButtonBank3.setContentAreaFilled(false);
		jButtonBank3.addActionListener(new java.awt.event.ActionListener() {
			/**
			 * Calls the action performed when this button is clicked, based on its action command.
			 */
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonActionPerformed(evt);
			}
		});

		jButtonUp.setActionCommand("07");
		jButtonUp.setContentAreaFilled(false);
		jButtonUp.setBorderPainted(false);
		jButtonUp.addActionListener(new java.awt.event.ActionListener() {
			/**
			 * Calls the action performed when this button is clicked, based on its action command.
			 */
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonActionPerformed(evt);
			}
		});

		jButtonDown.setActionCommand("08");
		jButtonDown.setContentAreaFilled(false);
		jButtonDown.setBorderPainted(false);
		jButtonDown.addActionListener(new java.awt.event.ActionListener() {
			/**
			 * Calls the action performed when this button is clicked, based on its action command.
			 */
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonActionPerformed(evt);
			}
		});

		jPanelLayout.setHorizontalGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanelLayout
						.createSequentialGroup()
						.addGap(37, 37, 37)
						.addGroup(
								jPanelLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(
												jPanelLayout
														.createSequentialGroup()
														.addComponent(jButton01, javax.swing.GroupLayout.PREFERRED_SIZE, 96,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(14, 14, 14)
														.addComponent(jButton02, javax.swing.GroupLayout.PREFERRED_SIZE, 96,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(18, 18, 18)
														.addComponent(jButton03, javax.swing.GroupLayout.PREFERRED_SIZE, 96,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(18, 18, 18)
														.addComponent(jButton04, javax.swing.GroupLayout.PREFERRED_SIZE, 96,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(23, 23, 23)
														.addComponent(jButton05, javax.swing.GroupLayout.PREFERRED_SIZE, 96,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(18, 18, 18)
														.addComponent(jButton06, javax.swing.GroupLayout.PREFERRED_SIZE, 96,
																javax.swing.GroupLayout.PREFERRED_SIZE))

										//-----BUTTOM UP------
										.addGroup(
												javax.swing.GroupLayout.Alignment.TRAILING,
												jPanelLayout
														.createSequentialGroup()
														.addContainerGap(559, Short.MAX_VALUE)
														.addComponent(jButtonUp, javax.swing.GroupLayout.PREFERRED_SIZE, 75,
																javax.swing.GroupLayout.PREFERRED_SIZE).addGap(118, 118, 118))
										//-----BUTTOM DOWN------    
										.addGroup(
												javax.swing.GroupLayout.Alignment.TRAILING,
												jPanelLayout
														.createSequentialGroup()
														.addContainerGap(559, Short.MAX_VALUE)
														.addComponent(jButtonDown, javax.swing.GroupLayout.PREFERRED_SIZE, 75,
																javax.swing.GroupLayout.PREFERRED_SIZE).addGap(118, 118, 118))

										.addGroup(
												jPanelLayout
														.createSequentialGroup()
														.addGap(63, 63, 63)
														.addComponent(jButtonBank1, javax.swing.GroupLayout.PREFERRED_SIZE, 95,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(33, 33, 33)
														.addComponent(jButtonBank2, javax.swing.GroupLayout.PREFERRED_SIZE, 95,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(34, 34, 34)
														.addComponent(jButtonBank3, javax.swing.GroupLayout.PREFERRED_SIZE, 95,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGroup(
																jPanelLayout
																		.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																		.addGroup(
																				jPanelLayout
																						.createSequentialGroup()
																						.addGap(106, 106, 106)
																						.addGroup(
																								jPanelLayout.createParallelGroup(
																										javax.swing.GroupLayout.Alignment.LEADING)
																								//                   .addComponent(jButtonUp, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addComponent(jButtonDown)))
																		.addGroup(
																				jPanelLayout
																						.createSequentialGroup()
																						.addGap(58, 58, 58)
																						.addComponent(jLabelMoney,
																								javax.swing.GroupLayout.PREFERRED_SIZE, 180,
																								javax.swing.GroupLayout.PREFERRED_SIZE)))))
						.addContainerGap(46, Short.MAX_VALUE)));
		jPanelLayout.setVerticalGroup(jPanelLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)

				//-----BUTTOM UP------
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						jPanelLayout.createSequentialGroup().addContainerGap(312, Short.MAX_VALUE)
								.addComponent(jButtonUp, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(265, 265, 265))
				//-----BUTTOM DOWN------
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						jPanelLayout.createSequentialGroup().addContainerGap(443, Short.MAX_VALUE)
								.addComponent(jButtonDown, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(134, 134, 134))

				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						jPanelLayout
								.createSequentialGroup()
								.addContainerGap(177, Short.MAX_VALUE)
								.addGroup(
										jPanelLayout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jButton01, javax.swing.GroupLayout.PREFERRED_SIZE, 95,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jButton04, javax.swing.GroupLayout.PREFERRED_SIZE, 95,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jButton03, javax.swing.GroupLayout.PREFERRED_SIZE, 95,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jButton02, javax.swing.GroupLayout.PREFERRED_SIZE, 95,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jButton06, javax.swing.GroupLayout.PREFERRED_SIZE, 95,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jButton05, javax.swing.GroupLayout.PREFERRED_SIZE, 95,
														javax.swing.GroupLayout.PREFERRED_SIZE))

								.addGroup(
										jPanelLayout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														jPanelLayout
																.createSequentialGroup()
																.addGap(36, 36, 36)
																.addGroup(
																		jPanelLayout
																				.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																				.addComponent(jButtonBank1, javax.swing.GroupLayout.PREFERRED_SIZE,
																						164, javax.swing.GroupLayout.PREFERRED_SIZE)
																				.addComponent(jButtonBank2, javax.swing.GroupLayout.PREFERRED_SIZE,
																						164, javax.swing.GroupLayout.PREFERRED_SIZE)
																				.addComponent(jButtonBank3, javax.swing.GroupLayout.PREFERRED_SIZE,
																						164, javax.swing.GroupLayout.PREFERRED_SIZE)))
												.addGroup(
														jPanelLayout
																.createSequentialGroup()
																.addGap(41, 41, 41)
																//                 .addComponent(jButtonUp)
																.addGap(28, 28, 28)
																.addComponent(jLabelMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 90,
																		javax.swing.GroupLayout.PREFERRED_SIZE).addGap(26, 26, 26)
																.addComponent(jButtonDown))).addGap(128, 128, 128)));

		this.betTokens = betTokens;
		this.player = player;

		refreshImages();
	}

	/**
	 * The actions performed when the buttons are clicked, i.e. the choice of a bet token, the
	 * banknotes, the money counter and the Confirm button.
	 */
	@Override
	protected void jButtonActionPerformed(java.awt.event.ActionEvent evt) {
		String command = ((JButton) evt.getSource()).getActionCommand();
		int betTokenStable = Integer.parseInt(command);
		switch (betTokenStable) {
		case 0:
			//Try to close the window and make bet
			if (!mandatory && bettedMoney == 0) {
				player.makeBet(null);
				dispose();
			}

			if (selectedBetToken != null && player.checkMinimumBet(bettedMoney)) {
				try {
					player.makeBet(new Bet(player.removeDanari(bettedMoney), selectedBetToken));
				} catch (NotEnoughMoneyException e) {
					//Impossible status
				} catch (NoMoreMoneyInBankException e) {
					throw new RuntimeException(e);
				} catch (RemoteException e) {
					throw new RuntimeException(e);
				}
				dispose();
			}
			break;
		//BetTokens buttons
		case 1:
			if (present.get(0)) {
				selectTokenBorder(buttons, jButton01);
				doubleFlip(betTokenStable);
			}
			break;
		case 2:
			if (present.get(1)) {
				selectTokenBorder(buttons, jButton02);
				doubleFlip(betTokenStable);
			}
			break;
		case 3:
			if (present.get(2)) {
				selectTokenBorder(buttons, jButton03);
				doubleFlip(betTokenStable);
			}
			break;
		case 4:
			if (present.get(3)) {
				selectTokenBorder(buttons, jButton04);
				doubleFlip(betTokenStable);
			}
			break;
		case 5:
			if (present.get(4)) {
				selectTokenBorder(buttons, jButton05);
				doubleFlip(betTokenStable);
			}
			break;
		case 6:
			if (present.get(5)) {
				selectTokenBorder(buttons, jButton06);
				doubleFlip(betTokenStable);
			}
			break;
		case 7:
			if (bettedMoney < player.getValue()) {
				bettedMoney += 100;
			}
			break;
		case 8:
			if (bettedMoney > 0) {
				bettedMoney -= 100;
			}
			break;
		case 100:
			if (bettedMoney < player.getValue()) {
				bettedMoney += 100;
			}
			break;
		case 500:
			if (bettedMoney + 499 < player.getValue()) {
				bettedMoney += 500;
			}
			break;
		case 1000:
			if (bettedMoney + 999 < player.getValue()) {
				bettedMoney += 1000;
			}
			break;
		default:
			break;
		}
		refreshImages();
	}

	/**
	 * Refreshes the displayed tokens based on their presence and state.
	 */
	private void refreshImages() {
		//Refresh buttons
		for (int i = 0; i < buttons.size(); i++) {

			//Take first bet token of selected stable
			for (BetToken betToken : betTokens) {
				if (betToken.getStable() - 1 == i) {

					//Set the image
					Image img = Factory.getFactory().getBetTokenImageByToken(betToken);
					buttons.get(i).setIcon(new ImageIcon(img));
					present.set(i, true);
					break;
				}
			}
		}

		//Refresh money label
		jLabelMoney.setText(String.format("%d", bettedMoney));
	}

	/**
	 * Manages the double flipping of the bet token to change its side.
	 * 
	 * @param betTokenStable
	 */
	private void doubleFlip(int betTokenStable) {
		for (BetToken betToken : betTokens) {
			if (betToken.getStable() == betTokenStable) {

				//If already selected flip
				if (selectedBetToken == betToken) {
					if (betToken.getType() == BetToken.SHOW) {
						betToken.setType(BetToken.WIN);
					} else {
						betToken.setType(BetToken.SHOW);
					}
				}
				selectedBetToken = betToken;
				break;
			}
		}
	}

}
