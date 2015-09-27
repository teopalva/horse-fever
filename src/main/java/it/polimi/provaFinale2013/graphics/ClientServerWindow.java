package it.polimi.provaFinale2013.graphics;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * 
 * This PopUp lets the user choose if he wants to be client or server.
 * 
 */
public class ClientServerWindow extends PopUp {
	private static final long serialVersionUID = 5644252736289255158L;
	private javax.swing.JButton jButtonClient;
	private javax.swing.JButton jButtonServer;
	private List<JButton> buttons;
	private boolean side = false;
	private ClientServerDelegate delegate;

	/**
	 * The constructor of the class.
	 * 
	 * @param delegate the interface which manages the chosen side
	 */
	public ClientServerWindow(ClientServerDelegate delegate) {
		super("ClientServerWindow.png");

		this.delegate = delegate;

		jButtonClient = new javax.swing.JButton();
		jButtonServer = new javax.swing.JButton();
		buttons = new ArrayList<JButton>();

		buttons.add(jButtonClient);
		buttons.add(jButtonServer);

		jButtonClient.setActionCommand("1");
		jButtonServer.setActionCommand("2");

		String img = null;
		for (int i = 0; i < 2; i++) {
			buttons.get(i).setBorderPainted(false);
			buttons.get(i).setContentAreaFilled(false);
			if (i == 0) {
				img = "clientButton.png";
			}
			if (i == 1) {
				img = "serverButton.png";
			}
			buttons.get(i).setIcon(new ImageIcon(getClass().getResource(img)));
			buttons.get(i).setFocusPainted(false);
			buttons.get(i).addActionListener(new java.awt.event.ActionListener() {
				/**
				 * Calls the action performed when one of the 2 buttons is clicked.
				 */
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					jButtonActionPerformed(evt);
				}
			});
		}

		javax.swing.GroupLayout jPanelLayout = new javax.swing.GroupLayout(jPanel);
		jPanel.setLayout(jPanelLayout);
		jPanelLayout.setHorizontalGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanelLayout.createSequentialGroup().addGap(174, 174, 174)
						.addComponent(jButtonClient, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(69, 69, 69)
						.addComponent(jButtonServer, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(185, Short.MAX_VALUE)));
		jPanelLayout.setVerticalGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				javax.swing.GroupLayout.Alignment.TRAILING,
				jPanelLayout
						.createSequentialGroup()
						.addContainerGap(221, Short.MAX_VALUE)
						.addGroup(
								jPanelLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jButtonServer, javax.swing.GroupLayout.PREFERRED_SIZE, 250,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jButtonClient, javax.swing.GroupLayout.PREFERRED_SIZE, 249,
												javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(129, 129, 129)));
	}

	/**
	 * The actions performed when the buttons are clicked, i.e. the choice of the side and the
	 * Confirm button
	 */
	@Override
	protected void jButtonActionPerformed(java.awt.event.ActionEvent evt) {
		String command = ((JButton) evt.getSource()).getActionCommand();
		int num = Integer.parseInt(command);
		switch (num) {
		case 0:
			delegate.setClientServer(side);
			this.dispose();
			break;
		case 1:
			side = false; //if client
			selectTokenBorder(buttons, jButtonClient);
			break;
		case 2:
			side = true; //if server
			selectTokenBorder(buttons, jButtonServer);
			break;
		default:
			break;
		}

	}
}
