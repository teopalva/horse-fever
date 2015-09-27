package it.polimi.provaFinale2013.graphics;

import it.polimi.provaFinale2013.graphics.console.Console;

import java.awt.Color;
import java.io.InputStream;
import java.io.PrintStream;

import javax.swing.JLabel;
import javax.swing.JScrollPane;

/**
 * This class manages the window and the components to play the CLI game.
 * 
 */
public class ConsoleWindow extends JLabel {

	private static final long serialVersionUID = -2913595582423612427L;

	private Console console;
	private JScrollPane sp;

	/**
	 * Constructor of the console inside a JScrollPane
	 */
	public ConsoleWindow() {
		console = new Console();
		console.setOpaque(true);
		console.setBackground(new Color(44, 25, 9));
		console.setForeground(Color.WHITE);

		setLayout(null);
		sp = new JScrollPane(console);
		sp.setBounds(46, 125, 758, 420);
		add(sp);
		setVisible(true);
		setIcon(new javax.swing.ImageIcon(getClass().getResource("ConsoleWindow.png")));
	}

	/**
	 * Console output
	 * 
	 * @return
	 */
	public PrintStream getOut() {
		return console.getOut();
	}

	/**
	 * Console input
	 * 
	 * @return
	 */
	public InputStream getIn() {
		return console.getIn();
	}

	/**
	 * Gives focus to the console
	 */
	public void focusOnConsole() {
		console.requestFocus();
	}

}
