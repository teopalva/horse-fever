package it.polimi.provaFinale2013.graphics;

import it.polimi.provaFinale2013.model.Factory;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import org.imgscalr.Scalr;

/**
 * This JDialog popup represents the basic structure for all the dilaogs which appear during the
 * game.
 */
public class PopUp extends JDialog {

	private static final long serialVersionUID = -3798590838852906411L;
	protected javax.swing.JPanel jPanel;
	protected javax.swing.JLabel jLabel;
	private javax.swing.JButton jButton;
	protected javax.swing.GroupLayout jPanelLayout;
	private Cursor c;
	protected Font font = null;

	/**
	 * Constructor of the PopUp.
	 * 
	 * @param path the String path of the popup background
	 */
	public PopUp(String path) {
		this.setBounds(new java.awt.Rectangle(0, 0, 750, 600));
		this.setIconImage(null);
		this.setIconImages(null);
		this.setMaximumSize(new java.awt.Dimension(750, 600));
		this.setMinimumSize(new java.awt.Dimension(750, 600));
		this.setModal(true);
		this.setUndecorated(true);
		this.setPreferredSize(new java.awt.Dimension(750, 600));
		this.setResizable(false);
		this.getContentPane().setLayout(new java.awt.GridBagLayout());

		try {
			InputStream is = getClass().getResourceAsStream("/edmunds.ttf");
			font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, is);
			font = font.deriveFont(55F);
		} catch (FontFormatException e) {
			font = new Font("Serif", Font.BOLD, 50);
		} catch (IOException e) {
			font = new Font("Serif", Font.BOLD, 50);
		}

		fillPopUp(path);
	}

	/**
	 * Fills the popup with the desired style and components.
	 * 
	 * @param path the String path of the popup background
	 */
	protected void fillPopUp(String path) {
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image1 = toolkit.getImage(getClass().getResource("Cursor.png"));
		Image image2 = toolkit.getImage(getClass().getResource("CursorWin.png"));
		Dimension bestSize = Toolkit.getDefaultToolkit().getBestCursorSize(34, 55);
		if (bestSize.height == 32 && bestSize.width == 32) {
			c = toolkit.createCustomCursor(image2, new Point(7, 0), "cursorWin");
		} else {
			c = toolkit.createCustomCursor(image1, new Point(12, 0), "cursor");
		}
		this.setCursor(c);

		java.awt.GridBagConstraints gridBagConstraints;
		jPanel = new javax.swing.JPanel();
		jLabel = new javax.swing.JLabel();
		jButton = new javax.swing.JButton();

		jPanel.setMinimumSize(new java.awt.Dimension(750, 600));
		jPanel.setOpaque(false);

		jButton.setBorderPainted(false);
		jButton.setContentAreaFilled(false);
		jButton.setActionCommand("00");
		jButton.addActionListener(new java.awt.event.ActionListener() {
			/**
			 * Calls the action performed when the Conform button is clicked.
			 */
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonActionPerformed(evt);
			}
		});

		jPanelLayout = new javax.swing.GroupLayout(jPanel);
		jPanel.setLayout(jPanelLayout);
		jPanelLayout.setHorizontalGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanelLayout.createSequentialGroup().addGap(252, 252, 252)
						.addComponent(jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(252, Short.MAX_VALUE)));
		jPanelLayout.setVerticalGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						jPanelLayout.createSequentialGroup().addContainerGap(506, Short.MAX_VALUE)
								.addComponent(jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(24, 24, 24)));

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		this.getContentPane().add(jPanel, gridBagConstraints); //get popup

		jLabel.setIcon(new ImageIcon(getClass().getResource(path)));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		this.getContentPane().add(jLabel, gridBagConstraints);

		pack();
	}

	/**
	 * The action performed when you click the "Confirm" button: the popup is closed.
	 * 
	 * @param evt
	 */
	protected void jButtonActionPerformed(java.awt.event.ActionEvent evt) {
		this.dispose();
	}

	/**
	 * Highlights the border of the clicked JButton.
	 * 
	 * @param buttons the List of clickable buttons
	 * @param clicked the JButton which has to be highlighted
	 */
	protected void selectTokenBorder(List<JButton> buttons, JButton clicked) {
		Border border1 = BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.WHITE, Color.WHITE);
		Border border2 = BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.BLACK, Color.BLACK);
		for (JButton b : buttons) {
			b.setBorderPainted(false);
		}
		String command = clicked.getActionCommand();
		int num = Integer.parseInt(command);
		if (num == 6) {
			clicked.setBorder(border2);
		} else {
			clicked.setBorder(border1);
		}
		clicked.setBorderPainted(true);
	}

	/**
	 * Creates 6 generic buttons, gives them an action command and a listener and puts them into a
	 * list.
	 * 
	 * @param type if the buttons refer to "stable" or other kinds.
	 * @param jButton01
	 * @param jButton02
	 * @param jButton03
	 * @param jButton04
	 * @param jButton05
	 * @param jButton06
	 * @return the list of the buttons
	 */
	protected List<JButton> createButtons(String type, JButton jButton01, JButton jButton02, JButton jButton03, JButton jButton04, JButton jButton05,
			JButton jButton06) {

		List<JButton> buttons = new ArrayList<JButton>();
		buttons.add(jButton01);
		buttons.add(jButton02);
		buttons.add(jButton03);
		buttons.add(jButton04);
		buttons.add(jButton05);
		buttons.add(jButton06);

		jButton01.setActionCommand("1");
		jButton02.setActionCommand("2");
		jButton03.setActionCommand("3");
		jButton04.setActionCommand("4");
		jButton05.setActionCommand("5");
		jButton06.setActionCommand("6");

		int index = 1;
		for (JButton b : buttons) {
			b.setBorderPainted(false);
			b.setContentAreaFilled(false);

			if (type.equals("stable")) {
				Image img = Factory.getFactory().getTokenImageForStable(index++);
				Image scaledImage = Scalr.resize((BufferedImage) img, 70, 70);
				b.setIcon(new ImageIcon(scaledImage));
			}

			b.addActionListener(new java.awt.event.ActionListener() {
				/**
				 * This method has to be redefined in each popup it is used in, by defining the
				 * actions to be performed for each action command.
				 */
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					jButtonActionPerformed(evt);
				}
			});
		}
		return buttons;
	}

	/**
	 * Creates a JLabel which displays the player's name with the game default font.
	 * 
	 * @param jLabelPlayer
	 * @param player
	 */
	protected void createJLabelPlayer(JLabel jLabelPlayer, GUIPlayer player) {
		jLabelPlayer.setFont(font);
		jLabelPlayer.setForeground(Color.white);
		jLabelPlayer.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelPlayer.setText(player.getName());
	}

}
