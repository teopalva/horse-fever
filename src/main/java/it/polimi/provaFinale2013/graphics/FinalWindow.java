package it.polimi.provaFinale2013.graphics;

/**
 * 
 * This popup designs the basic structure of the dialog which appears when a player wins/loses.
 */
public class FinalWindow extends PopUp {

	private static final long serialVersionUID = -3696999744788046260L;
	private javax.swing.JLabel jLabelPlayer;

	/**
	 * Constructor of the FinalWindow.
	 * 
	 * @param path the String path of the background image
	 * @param player the GUI player the popup refers to
	 */
	public FinalWindow(String path, GUIPlayer player) {
		super(path);

		jLabelPlayer = new javax.swing.JLabel();

		createJLabelPlayer(jLabelPlayer, player);

		javax.swing.GroupLayout jPanelLayout = new javax.swing.GroupLayout(jPanel);
		jPanel.setLayout(jPanelLayout);
		jPanelLayout.setHorizontalGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanelLayout.createSequentialGroup().addGap(119, 119, 119)
						.addComponent(jLabelPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(124, Short.MAX_VALUE)));
		jPanelLayout.setVerticalGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanelLayout.createSequentialGroup().addGap(117, 117, 117)
						.addComponent(jLabelPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(401, Short.MAX_VALUE)));

	}

	/**
	 * Constructor of the FinalWindow, not referred to a specific player.
	 * 
	 * @param path the String path of the background image
	 */
	public FinalWindow(String path) {
		super(path);
	}
}
