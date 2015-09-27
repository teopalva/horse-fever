package it.polimi.provaFinale2013.graphics;

import it.polimi.provaFinale2013.model.ActionCard;
import it.polimi.provaFinale2013.model.BetContainer;
import it.polimi.provaFinale2013.model.BlackBoard;
import it.polimi.provaFinale2013.model.Deck;
import it.polimi.provaFinale2013.model.MovementCard;
import it.polimi.provaFinale2013.model.Track;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.imgscalr.Scalr;

/**
 * This is the game main window in that it shows all the scores and data. It also shows the track
 * and the blackboard.
 */
public class MainWindow extends JLabel {

	private static final long serialVersionUID = -921325641631226657L;
	private JButton jButtonContinue;
	private JButton jButtonSeeHand;
	private JButton jButtonAction1;
	private JButton jButtonAction2;
	private JButton jButtonAction3;
	private JButton jButtonAction4;
	private JButton jButtonAction5;
	private JButton jButtonAction6;
	private JLabel jLabelBackground;
	private JLabel jLabelMovementCard;
	private JLabel jLabelDanari;
	private JLabel jLabelNotifier;
	private JLabel jLabelPlayer;
	private JLabel jLabelTurn;
	private JLabel jLabelVP;
	private javax.swing.JPanel jPanelMain;

	private TrackWindow jPanelTrack;
	private JPanel jPanelBlackBoard;
	private BlackboardWindow jPanelBlackBoardOver;

	private List<Deck<ActionCard>> actionDecks;
	private List<JButton> actionButtons;

	private GUIPlayer player;
	
	private boolean showActionFirst = true;

	/**
	 * Default constructor.
	 */
	public MainWindow() {
	}

	/**
	 * Constructor with player.
	 * 
	 * @param player the GUI player a mainWindow instance refers to.
	 */
	public MainWindow(GUIPlayer player) {
		this.player = player;

		actionDecks = new ArrayList<Deck<ActionCard>>();
		for (int i = 0; i < 6; i++) {
			actionDecks.add(new Deck<ActionCard>());
		}

		java.awt.GridBagConstraints gridBagConstraints;

		jPanelMain = new javax.swing.JPanel();
		jPanelTrack = new TrackWindow();
		jPanelBlackBoard = new JPanel();
		jPanelBlackBoardOver = new BlackboardWindow();

		jButtonContinue = new JButton();
		jButtonSeeHand = new JButton();
		jButtonAction6 = new JButton();
		jButtonAction5 = new JButton();
		jButtonAction4 = new JButton();
		jButtonAction3 = new JButton();
		jButtonAction2 = new JButton();
		jButtonAction1 = new JButton();

		actionButtons = new ArrayList<JButton>();
		actionButtons.add(jButtonAction1);
		actionButtons.add(jButtonAction2);
		actionButtons.add(jButtonAction3);
		actionButtons.add(jButtonAction4);
		actionButtons.add(jButtonAction5);
		actionButtons.add(jButtonAction6);

		jLabelMovementCard = new JLabel();

		jLabelPlayer = new JLabel();
		jLabelTurn = new JLabel();
		jLabelDanari = new JLabel();
		jLabelVP = new JLabel();
		jLabelNotifier = new JLabel();

		jLabelBackground = new JLabel();

		setLayout(new java.awt.GridBagLayout());

		jPanelMain.setOpaque(false);
		jPanelMain.setPreferredSize(new java.awt.Dimension(850, 680));
		jPanelBlackBoard.setOpaque(false);
		jPanelBlackBoardOver.setOpaque(false);

		final MainWindow mw = this;

		jButtonAction6.setBorderPainted(false);
		jButtonAction6.setContentAreaFilled(false);
		jButtonAction6.addActionListener(new java.awt.event.ActionListener() {
			/**
			 * Calls the action performed when this button is clicked, based on the horse it refers
			 * to.
			 */
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mw.updateActionImage(6);
			}
		});

		jButtonAction5.setBorderPainted(false);
		jButtonAction5.setContentAreaFilled(false);
		jButtonAction5.addActionListener(new java.awt.event.ActionListener() {
			/**
			 * Calls the action performed when this button is clicked, based on the horse it refers
			 * to.
			 */
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mw.updateActionImage(5);
			}
		});

		jButtonAction4.setBorderPainted(false);
		jButtonAction4.setContentAreaFilled(false);
		jButtonAction4.addActionListener(new java.awt.event.ActionListener() {
			/**
			 * Calls the action performed when this button is clicked, based on the horse it refers
			 * to.
			 */
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mw.updateActionImage(4);
			}
		});

		jButtonAction3.setBorderPainted(false);
		jButtonAction3.setContentAreaFilled(false);
		jButtonAction3.addActionListener(new java.awt.event.ActionListener() {
			/**
			 * Calls the action performed when this button is clicked, based on the horse it refers
			 * to.
			 */
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mw.updateActionImage(3);
			}
		});

		jButtonAction2.setBorderPainted(false);
		jButtonAction2.setContentAreaFilled(false);
		jButtonAction2.addActionListener(new java.awt.event.ActionListener() {
			/**
			 * Calls the action performed when this button is clicked, based on the horse it refers
			 * to.
			 */
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mw.updateActionImage(2);
			}
		});

		jButtonAction1.setBorderPainted(false);
		jButtonAction1.setContentAreaFilled(false);
		jButtonAction1.addActionListener(new java.awt.event.ActionListener() {
			/**
			 * Calls the action performed when this button is clicked, based on the horse it refers
			 * to.
			 */
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mw.updateActionImage(1);
			}
		});

		for (int i = 1; i <= 6; i++) {
			mw.updateActionImage(i);
		}

		jButtonContinue.setBorderPainted(false);
		jButtonContinue.setContentAreaFilled(false);
		jButtonContinue.addActionListener(new java.awt.event.ActionListener() {
			/**
			 * Calls the action performed when the ContinueButton is clicked.
			 */
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonActionPerformedContinue(evt);
			}
		});
		jButtonContinue.setIcon(new javax.swing.ImageIcon(getClass().getResource("ContinueButton.gif")));

		jButtonSeeHand.setBorderPainted(false);
		jButtonSeeHand.setContentAreaFilled(false);
		jButtonSeeHand.addActionListener(new java.awt.event.ActionListener() {
			/**
			 * Calls the action performed when the SeeHandButton is clicked.
			 */
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonActionPerformedSeeHand(evt);
			}
		});

		jPanelTrack.setPreferredSize(new java.awt.Dimension(376, 332));

		javax.swing.GroupLayout jPanelTrackLayout = new javax.swing.GroupLayout(jPanelTrack);
		jPanelTrack.setLayout(jPanelTrackLayout);
		jPanelTrackLayout.setHorizontalGroup(jPanelTrackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 376,
				Short.MAX_VALUE));
		jPanelTrackLayout.setVerticalGroup(jPanelTrackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 332,
				Short.MAX_VALUE));

		// ------------------------------------------------------------

		try {
			Font font = null;
			InputStream is = getClass().getResourceAsStream("/edmunds.ttf");
			font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, is);
			jLabelPlayer.setFont(font.deriveFont(55F));
			jLabelTurn.setFont(font.deriveFont(55F));
			jLabelDanari.setFont(font.deriveFont(40F));
			jLabelVP.setFont(font.deriveFont(55F));
			jLabelNotifier.setFont(font.deriveFont(20F));
		} catch (FontFormatException e) {
			jLabelPlayer.setFont(new Font("Serif", Font.BOLD, 55));
			jLabelTurn.setFont(new Font("Serif", Font.BOLD, 55));
			jLabelDanari.setFont(new Font("Serif", Font.BOLD, 40));
			jLabelVP.setFont(new Font("Serif", Font.BOLD, 55));
			jLabelNotifier.setFont(new Font("Serif", Font.BOLD, 20));
		} catch (IOException e) {
			jLabelPlayer.setFont(new Font("Serif", Font.BOLD, 55));
			jLabelPlayer.setFont(new Font("Serif", Font.BOLD, 55));
			jLabelTurn.setFont(new Font("Serif", Font.BOLD, 55));
			jLabelDanari.setFont(new Font("Serif", Font.BOLD, 40));
			jLabelVP.setFont(new Font("Serif", Font.BOLD, 55));
			jLabelNotifier.setFont(new Font("Serif", Font.BOLD, 20));
		}

		jLabelPlayer.setForeground(Color.white);
		jLabelTurn.setForeground(Color.white);
		jLabelDanari.setForeground(Color.white);
		jLabelVP.setForeground(Color.white);
		jLabelNotifier.setForeground(Color.white);

		jLabelPlayer.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelTurn.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelDanari.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelVP.setHorizontalAlignment(SwingConstants.CENTER);

		javax.swing.GroupLayout jPanelBlackBoardLayout = new javax.swing.GroupLayout(jPanelBlackBoard);
		jPanelBlackBoard.setLayout(jPanelBlackBoardLayout);
		jPanelBlackBoardLayout.setHorizontalGroup(jPanelBlackBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0,
				205, Short.MAX_VALUE));
		jPanelBlackBoardLayout.setVerticalGroup(jPanelBlackBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 153,
				Short.MAX_VALUE));

		jPanelBlackBoard.add(jPanelBlackBoardOver);

		javax.swing.GroupLayout jPanelMainLayout = new javax.swing.GroupLayout(jPanelMain);
		jPanelMain.setLayout(jPanelMainLayout);
		jPanelMainLayout
				.setHorizontalGroup(jPanelMainLayout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(jLabelNotifier, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jPanelMainLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanelMainLayout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanelMainLayout
																		.createSequentialGroup()
																		.addComponent(jLabelMovementCard, javax.swing.GroupLayout.PREFERRED_SIZE, 98,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(jPanelBlackBoard, javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addComponent(jPanelTrack, javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(9, 9, 9)
										.addGroup(
												jPanelMainLayout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanelMainLayout
																		.createSequentialGroup()
																		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																				javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																		.addComponent(jLabelPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, 298,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																jPanelMainLayout
																		.createSequentialGroup()
																		.addGap(302, 302, 302)
																		.addGroup(
																				jPanelMainLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(jLabelTurn,
																								javax.swing.GroupLayout.PREFERRED_SIZE, 127,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(jLabelDanari,
																								javax.swing.GroupLayout.PREFERRED_SIZE, 127,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(jLabelVP,
																								javax.swing.GroupLayout.PREFERRED_SIZE, 127,
																								javax.swing.GroupLayout.PREFERRED_SIZE))))
										.addGap(26, 26, 26))
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jPanelMainLayout
										.createSequentialGroup()
										.addGap(0, 0, Short.MAX_VALUE)
										.addComponent(jButtonAction1, javax.swing.GroupLayout.PREFERRED_SIZE, 51,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jButtonAction2, javax.swing.GroupLayout.PREFERRED_SIZE, 51,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(10, 10, 10)
										.addComponent(jButtonAction3, javax.swing.GroupLayout.PREFERRED_SIZE, 51,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jButtonAction4, javax.swing.GroupLayout.PREFERRED_SIZE, 51,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jButtonAction5, javax.swing.GroupLayout.PREFERRED_SIZE, 51,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jButtonAction6, javax.swing.GroupLayout.PREFERRED_SIZE, 51,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(97, 97, 97)
										.addComponent(jButtonContinue, javax.swing.GroupLayout.PREFERRED_SIZE, 173,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jButtonSeeHand, javax.swing.GroupLayout.PREFERRED_SIZE, 171,
												javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18)));
		jPanelMainLayout.setVerticalGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanelMainLayout
						.createSequentialGroup()
						.addGroup(
								jPanelMainLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(
												jPanelMainLayout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																jPanelMainLayout
																		.createSequentialGroup()
																		.addGap(84, 84, 84)
																		.addComponent(jLabelPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, 83,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																jPanelMainLayout
																		.createSequentialGroup()
																		.addGap(14, 14, 14)
																		.addComponent(jLabelMovementCard, javax.swing.GroupLayout.PREFERRED_SIZE,
																				153, javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addGroup(
												jPanelMainLayout
														.createSequentialGroup()
														.addGap(14, 14, 14)
														.addComponent(jPanelBlackBoard, javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addGap(18, 18, 18)
						.addGroup(
								jPanelMainLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(
												jPanelMainLayout
														.createSequentialGroup()
														.addGap(0, 5, Short.MAX_VALUE)
														.addComponent(jPanelTrack, javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addGroup(
																jPanelMainLayout
																		.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																		.addComponent(jButtonAction6, javax.swing.GroupLayout.PREFERRED_SIZE, 80,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(jButtonAction5, javax.swing.GroupLayout.PREFERRED_SIZE, 80,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(jButtonAction4, javax.swing.GroupLayout.PREFERRED_SIZE, 80,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(jButtonAction3, javax.swing.GroupLayout.PREFERRED_SIZE, 80,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(jButtonAction2, javax.swing.GroupLayout.PREFERRED_SIZE, 80,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(jButtonAction1, javax.swing.GroupLayout.PREFERRED_SIZE, 80,
																				javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(14, 14, 14))
										.addGroup(
												jPanelMainLayout
														.createSequentialGroup()
														.addGap(13, 13, 13)
														.addComponent(jLabelTurn, javax.swing.GroupLayout.PREFERRED_SIZE, 75,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(32, 32, 32)
														.addComponent(jLabelDanari, javax.swing.GroupLayout.PREFERRED_SIZE, 75,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(34, 34, 34)
														.addComponent(jLabelVP, javax.swing.GroupLayout.PREFERRED_SIZE, 75,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addGroup(
																jPanelMainLayout
																		.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																		.addComponent(jButtonContinue, javax.swing.GroupLayout.PREFERRED_SIZE, 82,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(jButtonSeeHand, javax.swing.GroupLayout.PREFERRED_SIZE, 82,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
						.addComponent(jLabelNotifier, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)));

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		add(jPanelMain, gridBagConstraints);

		jLabelBackground.setIcon(new ImageIcon(getClass().getResource("MainWindow.png")));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		add(jLabelBackground, gridBagConstraints);

		hideContinueButton();

	}

	/**
	 * Shows the SeeHand popup when its button is clicked.
	 * 
	 * @param evt
	 */
	private void jButtonActionPerformedSeeHand(java.awt.event.ActionEvent evt) {
		player.displaySeeHand();
	}

	/**
	 * When the button is clicked the game goes on to the next step.
	 * 
	 * @param evt
	 */
	private void jButtonActionPerformedContinue(java.awt.event.ActionEvent evt) {
		player.continueAfterMakeActive();
	}

	/**
	 * Shows and updates the horses on the track and the movement cards which move them.
	 * 
	 * @param track
	 */
	public void displayTrack(Track track) {
		displayHorses(track);
		MovementCard mc = track.getMovementCard();
		if (mc != null) {
			displayMovementCard(mc);
		}
		this.updateActionDecks(track);
		repaint();
	}

	/**
	 * Displays the horses in the track.
	 * 
	 * @param track the Track
	 */
	private void displayHorses(Track track) {
		jPanelTrack.updateHorses(track);
		repaint();
	}

	/**
	 * Displays the movement card near the black board.
	 * 
	 * @param mc movement card to display
	 */
	private void displayMovementCard(MovementCard mc) {
		Image scaledImage = Scalr.resize((BufferedImage) mc.getImage(), 98, 153);
		jLabelMovementCard.setIcon(new ImageIcon(scaledImage));
	}

	/**
	 * Updates player's information: name, VP, danari, turn.
	 */
	public void updatePlayerInformation() {
		jLabelDanari.setText(Integer.valueOf(player.getValue()).toString());
		jLabelPlayer.setText(player.getName());
		jLabelVP.setText(Integer.valueOf(player.getVP()).toString());
	}

	/**
	 * Displays the current turn number.
	 * 
	 * @param turn
	 */
	public void displayTurn(int turn, int maxTurns) {
		String string = String.format("%d/%d", turn, maxTurns);
		jLabelTurn.setText(string);
	}

	/**
	 * Displays the blackboard on its panel on the track.
	 * 
	 * @param blackBoard
	 */
	public void displayBlackBoard(BlackBoard blackBoard) {
		jPanelBlackBoardOver.displayBlackBoard(blackBoard);
	}

	/**
	 * Displays the bets on the track panel.
	 * 
	 * @param bets
	 */
	public void displayBets(List<BetContainer> bets) {
		jPanelTrack.updateBets(bets);
	}

	/**
	 * Sets the continue button blinking status.
	 */
	public void setBlinkContinue() {
		jButtonContinue.setIcon(new javax.swing.ImageIcon(getClass().getResource("ContinueButton.gif")));
	}

	/**
	 * Hides the continue button.
	 */
	public void hideContinueButton() {
		jButtonContinue.setIcon(new javax.swing.ImageIcon(getClass().getResource("ContinueEmpty.png")));
	}

	/**
	 * Updates the "decks" of played cards under each horse.
	 * 
	 * @param track
	 */
	public synchronized void updateActionDecks(Track track) {
		if(track.getStatus() != Track.STARTED || showActionFirst) {
			if(track.getStatus() != Track.STARTED)
				showActionFirst = true;
			else
				showActionFirst = false;
			for (int i = 0; i < 6; i++) {
				actionDecks.get(i).clear();
				for (ActionCard a : track.getActionCardsForStable(i + 1)) {
					actionDecks.get(i).pushTopCard(a.clone());
				}
				updateActionImage(i + 1);
			}
		}
	}

	/**
	 * Updates action cards under a horse.
	 * 
	 * @param horse the number of the horse affected
	 */
	private void updateActionImage(int horse) {
		try {
			ActionCard a = actionDecks.get(horse - 1).popCard();
			actionDecks.get(horse - 1).pushBottomCard(a);
			Image scaledImage = Scalr.resize((BufferedImage) a.getImage(), 51, 80);
			this.actionButtons.get(horse - 1).setIcon(new javax.swing.ImageIcon(scaledImage));
		} catch (Exception e) {
			actionButtons.get(horse - 1).setIcon(null);
		}
		actionButtons.get(horse - 1).revalidate();
	}
	
	/**
	 * Delete bets on trackWindow
	 */
	public void deleteBets() {
		jPanelTrack.deleteBets();
	}

}