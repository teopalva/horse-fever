package it.polimi.provaFinale2013.graphics;

import java.awt.Color;

import javax.swing.SwingConstants;

/**
 * 
 * This PopUp asks for the player's name.
 */
public class EnterNameWindow extends PopUp {
	private static final long serialVersionUID = -6983129591251684421L;
	private javax.swing.JTextField jTextField;
	private GUIPlayer player;

	/**
	 * Constructor of the EnterNameWindow.
	 * 
	 * @param player the GUIPlayer the popup refers to
	 */
	public EnterNameWindow(GUIPlayer player) {
		super("EnterName.png");

		this.player = player;
		jTextField = new javax.swing.JTextField(20);
		jTextField.setFont(font);
		jTextField.setForeground(Color.white);
		jTextField.addActionListener(new java.awt.event.ActionListener() {
			/**
			 * Calls the action performed when the Confirm button is clicked.
			 */
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonActionPerformed(evt);
			}
		});

		jTextField.setHorizontalAlignment(SwingConstants.CENTER);
		jTextField.setBorder(null);
		jTextField.setCaretColor(new java.awt.Color(255, 255, 255));
		jTextField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
		jTextField.setOpaque(false);

		javax.swing.GroupLayout jPanelLayout = new javax.swing.GroupLayout(jPanel);
		jPanel.setLayout(jPanelLayout);
		jPanelLayout.setHorizontalGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanelLayout.createSequentialGroup().addGap(150, 150, 150)
						.addComponent(jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(160, Short.MAX_VALUE)));
		jPanelLayout.setVerticalGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				javax.swing.GroupLayout.Alignment.TRAILING,
				jPanelLayout.createSequentialGroup().addContainerGap(300, Short.MAX_VALUE)
						.addComponent(jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(230, 230, 230)));
		jTextField.requestFocus();
	}

	/**
	 * The action performed when you click the "Confirm" button: the name is set and the popup is
	 * closed.
	 */
	@Override
	protected synchronized void jButtonActionPerformed(java.awt.event.ActionEvent evt) {
		String playerName = jTextField.getText();
		if (!playerName.isEmpty()) {
			//Notify the player name
			player.setName(playerName);
			dispose();
		}
	}

}
