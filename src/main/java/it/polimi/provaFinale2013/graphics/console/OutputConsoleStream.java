package it.polimi.provaFinale2013.graphics.console;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * This class emulate the System.out and write every character sent to the stream on the textarea
 */
public class OutputConsoleStream extends OutputStream {

	private final JTextArea textArea;

	/**
	 * Constructor of the class
	 * 
	 * @param textArea the text area where write characterss
	 * @param title the title of
	 */
	public OutputConsoleStream(final JTextArea textArea) {
		this.textArea = textArea;
	}

	/**
	 * Flush the stream
	 */
	@Override
	public void flush() {
	}

	/**
	 * Close the stream
	 */
	@Override
	public void close() {
	}

	/**
	 * Write a byte on the buffer (text area)
	 */
	@Override
	public void write(final int b) throws IOException {
		SwingUtilities.invokeLater(new Runnable() {
			/**
			 * Called on graphics interface thread It insert the character inside the text area
			 */
			public void run() {
				textArea.append(String.format("%c", b));
			}
		});
	}

	/**
	 * Write a buffer of bytes on the buffer (text area)
	 */
	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		for (int i = 0; i < len - off; i++) {
			write(b[i + off]);
		}
	}
}