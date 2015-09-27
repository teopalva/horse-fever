package it.polimi.provaFinale2013.graphics.console;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ByteArrayInputStream;
import java.io.PrintStream;

import javax.swing.JTextArea;

/**
 * This class is created in order to be used like a input stream in the console It takes character
 * inside a textarea and report them to the inputstream in order to simulate a System.in
 */
public class InputConsoleStream extends ByteArrayInputStream {

	private int c;
	private Object writeLock;

	private boolean wakeUp;

	/**
	 * Constructor of the class
	 * 
	 * @param text the text area where it takes the characters
	 * @param out the output stream on the console in order to print character that is sent to
	 * textArea
	 */
	public InputConsoleStream(final JTextArea text, final PrintStream out) {
		super(new byte[10000]);
		pos = 0;
		c = 0;

		writeLock = new Object();

		text.addKeyListener(new KeyAdapter() {
			/**
			 * Executed when you release a keyboard keyt and the text area has the focus
			 */
			@Override
			public synchronized void keyReleased(KeyEvent e) {
				synchronized (writeLock) {
					if (e.getKeyChar() == '\n') {
						//Unlock the stream
						buf[c] = ' ';
						c++;
						out.printf("%c", e.getKeyChar());
						dataReady();
					} else if (e.getKeyChar() == '\b') {
						if (c > 0) {
							c--;
							text.setText(text.getText().substring(0, text.getText().length() - 1));
						}
					} else if (Character.isLetter(e.getKeyChar()) || Character.isDigit(e.getKeyChar()) || Character.isWhitespace(e.getKeyChar())) {
						out.printf("%c", e.getKeyChar());
						buf[c] = (byte) e.getKeyChar();
						c++;
					}
					super.keyReleased(e);
				}
			}
		});

		text.addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent e) {
				text.setCaretPosition(text.getCaretPosition());
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				text.setCaretPosition(text.getCaretPosition());
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				text.setCaretPosition(text.getCaretPosition());
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				text.setCaretPosition(text.getCaretPosition());
			}

			@Override
			public void mouseExited(MouseEvent e) {
				text.setCaretPosition(text.getCaretPosition());
			}

		});
	}

	/**
	 * return the number of available byte in the stream
	 */
	@Override
	public synchronized int available() {
		if (pos < c) {
			return c - pos;
		}
		return 0;
	}

	/**
	 * read one character from the stream
	 */
	@Override
	public synchronized int read() {
		try {
			if (available() == 0) {
				wakeUp = false;
				while (!wakeUp) {
					wait();
				}
			}
		} catch (InterruptedException e) {
		}
		return super.read();
	}

	/**
	 * Read a buffer of byte
	 */
	@Override
	public synchronized int read(byte[] b, int off, int len) {
		int lenght = c - pos;
		boolean error = true;
		do {
			try {
				while (available() == 0 || len < lenght) {
					wait();
					lenght = c - pos;
				}
			} catch (InterruptedException e) {
			}

			synchronized (writeLock) {
				lenght = c - pos;
				if (available() == 0 || len < lenght || pos < 0) {
					//Other thread modified length after the notify()
					error = true;
				} else {
					error = false;
				}

				if (!error) {
					System.arraycopy(buf, pos, b, 0, lenght);
					pos += lenght;

					if (pos == c) {
						c = 0;
						pos = 0;
					}
				}
			}

		} while (error);
		
		return lenght;
	}

	/**
	 * Check if there're some characters available in the stream
	 */
	private synchronized void dataReady() {
		wakeUp = true;
		notifyAll();
	}

}