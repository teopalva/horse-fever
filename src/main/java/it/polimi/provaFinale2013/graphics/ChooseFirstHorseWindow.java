package it.polimi.provaFinale2013.graphics;

import java.util.List;

import javax.swing.JButton;

/**
 * This PopUp lets the player choose the horse he wants to be the winner among the others in the
 * list.
 * 
 */
public class ChooseFirstHorseWindow extends PopUp {

	private static final long serialVersionUID = -7259646729049632445L;

	private List<Integer> horses;
	private GUIPlayer player;
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JButton jButton3;
	private javax.swing.JButton jButton4;
	private javax.swing.JButton jButton5;
	private javax.swing.JButton jButton6;
	private List<JButton> buttons;
	private int clickedStable = 0;

	/**
	 * The constructor of the ChooseFirstHorseWindow.
	 * 
	 * @param horses the list of the numbers of the horses which arrived at the same winning
	 * position
	 * @param player the GUIPlayer who has to choose
	 */
	public ChooseFirstHorseWindow(List<Integer> horses, GUIPlayer player) {
		super("ChooseFirstHorse.png");
		this.horses = horses;
		this.player = player;

		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();
		jButton3 = new javax.swing.JButton();
		jButton4 = new javax.swing.JButton();
		jButton5 = new javax.swing.JButton();
		jButton6 = new javax.swing.JButton();

		buttons = createButtons("stable", jButton1, jButton2, jButton3, jButton4, jButton5, jButton6);
		for (int i = 0; i < buttons.size(); i++) {
			if (!(horses.contains(i + 1))) {
				buttons.get(i).setBorderPainted(false);
				buttons.get(i).setContentAreaFilled(false);
				buttons.get(i).setIcon(null);
			}
		}

		javax.swing.GroupLayout jPanelLayout = new javax.swing.GroupLayout(jPanel);
		jPanel.setLayout(jPanelLayout);
		jPanelLayout.setHorizontalGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanelLayout.createSequentialGroup().addGap(77, 77, 77)
						.addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(32, 32, 32)
						.addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(37, 37, 37)
						.addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(40, 40, 40)
						.addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(33, 33, 33)
						.addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(32, 32, 32)
						.addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(79, Short.MAX_VALUE)));
		jPanelLayout.setVerticalGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				javax.swing.GroupLayout.Alignment.TRAILING,
				jPanelLayout
						.createSequentialGroup()
						.addContainerGap(319, Short.MAX_VALUE)
						.addGroup(
								jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
										.addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(211, 211, 211)));

	}

	/**
	 * The actions performed when the buttons are clicked, i.e. the choice of a horse and the
	 * Confirm button
	 */
	@Override
	protected void jButtonActionPerformed(java.awt.event.ActionEvent evt) {
		String command = ((JButton) evt.getSource()).getActionCommand();
		int num = Integer.parseInt(command);
		switch (num) {
		case 0:
			//Try to close the window
			if (clickedStable != 0) {
				player.chooseFirstHorse(clickedStable);
				dispose();
			}
			break;
		case 1:
			if (horses.contains(1)) {
				clickedStable = 1;
				selectTokenBorder(buttons, jButton1);
			}
			break;
		case 2:
			if (horses.contains(2)) {
				clickedStable = 2;
				selectTokenBorder(buttons, jButton2);
			}
			break;
		case 3:
			if (horses.contains(3)) {
				clickedStable = 3;
				selectTokenBorder(buttons, jButton3);
			}
			break;
		case 4:
			if (horses.contains(4)) {
				clickedStable = 4;
				selectTokenBorder(buttons, jButton4);
			}
			break;
		case 5:
			if (horses.contains(5)) {
				clickedStable = 5;
				selectTokenBorder(buttons, jButton5);
			}
			break;
		case 6:
			if (horses.contains(6)) {
				clickedStable = 6;
				selectTokenBorder(buttons, jButton6);
			}
			break;
		default:
			break;
		}
	}

}
