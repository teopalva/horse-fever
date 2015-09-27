package it.polimi.provaFinale2013.model;

import java.io.InputStream;
import java.io.PrintStream;

/**
 * This class represent a player in a Command Line Interface. It own two stream (in, out) and uses
 * these in order to display output and take input
 * 
 */
public class CLIPlayer extends AbstractPlayer {

	/**
	 * Constructor of the class, it connects input and output streams
	 * 
	 * @param output the output stream
	 * @param input the input stream
	 */
	public CLIPlayer(PrintStream output, InputStream input, RemoteBank bank, String name) {
		super(output, input, bank, name);
	}
}
