package it.polimi.provaFinale2013.graphics.console;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import javax.swing.JTextArea;

/**
 * This class is a special JTextArea that permits the user getting input and output stream in order
 * to emulate a terminal You have only to instantiate and call getIn() and getOut()
 */
public class Console extends JTextArea {

	private static final long serialVersionUID = 1342405292290434354L;
	private transient InputStream in;
	private transient PrintStream out;

	/**
	 * Constructor of the class
	 */
	public Console() {
		Font consoleFont = null;
		try {
			InputStream stream = getClass().getResourceAsStream("/edmunds.ttf");
			consoleFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, stream);
			consoleFont = consoleFont.deriveFont(20F);
		} catch (FontFormatException e) {
			consoleFont = new Font("Serif", Font.BOLD, 20);
		} catch (IOException e) {
			consoleFont = new Font("Serif", Font.BOLD, 20);
		}
		setFont(consoleFont);
		startConsole();
	}

	/**
	 * Start the console and set some paramenters
	 */
	private void startConsole() {
		setEditable(false);
		getCaret().setVisible(true);
		out = new PrintStream(new OutputConsoleStream(this));
		in = new InputConsoleStream(this, out);
	}

	/**
	 * Get the input stream of the console
	 * 
	 * @return the input stream of the console
	 */
	public InputStream getIn() {
		return in;
	}

	/**
	 * Get the output stream of the console
	 * 
	 * @return
	 */
	public PrintStream getOut() {
		return out;
	}
}
