package it.polimi.provaFinale2013.graphics;

import it.polimi.provaFinale2013.exceptions.NotManageableException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * This is the first window which is displayed inside the main frame and gives the possibility to
 * choose the desired type of game.
 */
public class MenuWindow extends JLabel implements PlayerNumberDelegate, ClientServerDelegate, IpDelegate {

	private static final long serialVersionUID = 4473373039264933149L;
	private javax.swing.JButton jButtonCLI;
	private javax.swing.JButton jButtonGUI;
	private javax.swing.JButton jButtonRMI;
	private javax.swing.JButton jButtonQuit;
	private javax.swing.JLabel jLabel;
	private javax.swing.JPanel jPanel;
	private int numPlayers = 0;
	private boolean side;
	private String ip;
	private String clientIp;
	private String serverIp;
	private boolean wakeUp;

	/**
	 * Constructor of the MenuWindow.
	 */
	public MenuWindow() {

		jPanel = new javax.swing.JPanel();
		jButtonCLI = new javax.swing.JButton();
		jButtonGUI = new javax.swing.JButton();
		jButtonRMI = new javax.swing.JButton();
		jButtonQuit = new javax.swing.JButton();
		jLabel = new javax.swing.JLabel();

		java.awt.GridBagConstraints gridBagConstraints;
		setLayout(new java.awt.GridBagLayout());

		jPanel.setOpaque(false);
		jPanel.setPreferredSize(new java.awt.Dimension(850, 680));

		jButtonCLI.setBorderPainted(false);
		jButtonCLI.setContentAreaFilled(false);
		jButtonCLI.setActionCommand("1");
		jButtonCLI.addActionListener(new java.awt.event.ActionListener() {
			/**
			 * Calls the action performed when this button is clicked, based on its action command.
			 */
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonActionPerformed(evt);
			}
		});

		jButtonGUI.setBorderPainted(false);
		jButtonGUI.setContentAreaFilled(false);
		jButtonGUI.setActionCommand("2");
		jButtonGUI.addActionListener(new java.awt.event.ActionListener() {
			/**
			 * Calls the action performed when this button is clicked, based on its action command.
			 */
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonActionPerformed(evt);
			}
		});

		jButtonRMI.setBorderPainted(false);
		jButtonRMI.setContentAreaFilled(false);
		jButtonRMI.setActionCommand("3");
		jButtonRMI.addActionListener(new java.awt.event.ActionListener() {
			/**
			 * Calls the action performed when this button is clicked, based on its action command.
			 */
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonActionPerformed(evt);
			}
		});

		jButtonQuit.setBorderPainted(false);
		jButtonQuit.setContentAreaFilled(false);
		jButtonQuit.setActionCommand("4");
		jButtonQuit.addActionListener(new java.awt.event.ActionListener() {
			/**
			 * Calls the action performed when this button is clicked, based on its action command.
			 */
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel);
		jPanel.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel1Layout
						.createSequentialGroup()
						.addGroup(
								jPanel1Layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(
												jPanel1Layout
														.createSequentialGroup()
														.addGap(252, 252, 252)
														.addGroup(
																jPanel1Layout
																		.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
																		.addComponent(jButtonGUI, javax.swing.GroupLayout.DEFAULT_SIZE, 281,
																				Short.MAX_VALUE)
																		.addComponent(jButtonCLI, javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
										.addGroup(
												jPanel1Layout
														.createSequentialGroup()
														.addGap(228, 228, 228)
														.addComponent(jButtonRMI, javax.swing.GroupLayout.PREFERRED_SIZE, 329,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGroup(
												jPanel1Layout
														.createSequentialGroup()
														.addGap(341, 341, 341)
														.addComponent(jButtonQuit, javax.swing.GroupLayout.PREFERRED_SIZE, 161,
																javax.swing.GroupLayout.PREFERRED_SIZE))).addContainerGap(293, Short.MAX_VALUE)));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel1Layout.createSequentialGroup().addGap(262, 262, 262)
						.addComponent(jButtonCLI, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(26, 26, 26)
						.addComponent(jButtonGUI, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18)
						.addComponent(jButtonRMI, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
						.addComponent(jButtonQuit, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		add(jPanel, gridBagConstraints);

		jLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("MenuWindow.gif")));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		add(jLabel, gridBagConstraints);

	}

	/**
	 * The actions performed when the buttons are clicked, i.e. the choice of the type of game you
	 * want to start or the Quit button.
	 * 
	 * @param evt
	 */
	private void jButtonActionPerformed(java.awt.event.ActionEvent evt) {
		final HorseFeverWindow hf = HorseFeverWindow.getHorseFeverWindow();
		String command = ((JButton) evt.getSource()).getActionCommand();
		final int num = Integer.parseInt(command);
		Thread t = new Thread() {
			/**
			 * Runs the new game on a different thread.
			 */
			public void run() {
				switch (num) {
				case 1:
					hf.startCLIGame(2);
					hf.repaint();
					break;
				case 2:
					displayNumPlayersWindow();
					hf.startGUIGame(numPlayers);
					hf.repaint();
					break;
				case 3:
					displayClientServerWindow();
					if (side) {
						//Start the server
						displayServerIPWindow();
						displayNumPlayersWindow();
						hf.startGUINetworkServerGame(numPlayers, serverIp);

					} else {
						//Start the client
						displayServerIPWindow();
						displayClientIPWindow();
						hf.startGUINetworkClientGame(numPlayers, serverIp, clientIp);
					}
					break;
				case 4:
					System.exit(0); //quit game
					break;
				}
			}
		};
		t.start();
	}

	/**
	 * Instantiates and displays the NumPlayersWindow.
	 */
	private synchronized void displayNumPlayersWindow() {
		final MenuWindow menu = this;
		SwingUtilities.invokeLater(new Runnable() {
			/**
			 * Runs the EnterNumPlayersWindow popup on the EDT.
			 */
			public void run() {
				EnterNumPlayersWindow enterNumPlayers = new EnterNumPlayersWindow(menu);
				enterNumPlayers.setLocationRelativeTo(menu);
				enterNumPlayers.setVisible(true);
			}
		});

		try {
			wakeUp = false;
			while (!wakeUp) {
				wait();
			}
		} catch (InterruptedException e) {
			throw new NotManageableException(e);
		}
	}

	/**
	 * Setter for number of players.
	 * 
	 * @param numPlayers
	 */
	public synchronized void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
		wakeUp = true;
		notifyAll();
	}

	/**
	 * Instantiates and displays the ClientServerWindow.
	 */
	private synchronized void displayClientServerWindow() {
		final MenuWindow menu = this;
		SwingUtilities.invokeLater(new Runnable() {
			/**
			 * Runs the clientServerWindow popup on the EDT.
			 */
			public void run() {
				ClientServerWindow clientServerWindow = new ClientServerWindow(menu);
				clientServerWindow.setLocationRelativeTo(menu);
				clientServerWindow.setVisible(true);
			}
		});

		try {
			wakeUp = false;
			while (!wakeUp) {
				wait();
			}
		} catch (InterruptedException e) {
			throw new NotManageableException(e);
		}
	}

	/**
	 * Instantiates and displays the server IPWindow.
	 */
	private synchronized void displayServerIPWindow() {
		final MenuWindow menu = this;
		SwingUtilities.invokeLater(new Runnable() {
			/**
			 * Runs the enterIPWindow popup on the EDT.
			 */
			public void run() {
				EnterIPWindow enterIPWindow = new EnterIPWindow(menu, EnterIPWindow.SERVER);
				enterIPWindow.setLocationRelativeTo(menu);
				enterIPWindow.setVisible(true);
			}
		});

		try {
			wakeUp = false;
			while (!wakeUp) {
				wait();
			}
		} catch (InterruptedException e) {
			throw new NotManageableException(e);
		}
		this.serverIp = ip;
	}

	/**
	 * Instantiates and displays the cleint IPWindow.
	 */
	private synchronized void displayClientIPWindow() {
		final MenuWindow menu = this;
		SwingUtilities.invokeLater(new Runnable() {
			/**
			 * Runs the enterIPWindow popup on the EDT.
			 */
			public void run() {
				EnterIPWindow enterIPWindow = new EnterIPWindow(menu, EnterIPWindow.CLIENT);
				enterIPWindow.setLocationRelativeTo(menu);
				enterIPWindow.setVisible(true);
			}
		});

		try {
			wakeUp = false;
			while (!wakeUp) {
				wait();
			}
		} catch (InterruptedException e) {
			throw new NotManageableException(e);
		}
		this.clientIp = ip;
	}

	/**
	 * Setter for the client/server side.
	 */
	public synchronized void setClientServer(boolean side) {
		this.side = side;
		wakeUp = true;
		notifyAll();
	}

	/**
	 * Setter for the IP address.
	 */
	public synchronized void setIp(String ip) {
		this.ip = ip;
		wakeUp = true;
		notifyAll();
	}

}
