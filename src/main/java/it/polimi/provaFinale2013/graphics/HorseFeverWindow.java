package it.polimi.provaFinale2013.graphics;

import it.polimi.provaFinale2013.exceptions.NotManageableException;
import it.polimi.provaFinale2013.model.CLIPlayer;
import it.polimi.provaFinale2013.model.FamilyGame;
import it.polimi.provaFinale2013.model.Game;
import it.polimi.provaFinale2013.model.GameObserver;
import it.polimi.provaFinale2013.model.Player;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * This is the main GUI class. It displays MenuWindow and contains the methods for starting the
 * login of the game. It instantiates players and connects logic and graphic components.
 */
public class HorseFeverWindow extends JFrame {
	private static HorseFeverWindow horseFeverWindow;
	private MenuWindow menuWindow;
	private Game game;
	private Cursor c;

	private static final long serialVersionUID = -4451186531663622884L;

	/**
	 * The constructor of HorseFeverWindow, the main GUI class. It sets the frame parameters such as
	 * dimensions, title, cursor, look&feel.
	 */
	public HorseFeverWindow() {
		menuWindow = new MenuWindow();
		getContentPane().add(menuWindow);

		JFrame temp = new JFrame();
		temp.pack();
		Insets insets = temp.getInsets();

		setTitle("                                 Venturini & Palvarini 's             HORSE FEVER ©             close to go back to menu");
		String OS = System.getProperty("os.name");
		if (OS.startsWith("Windows")) {
			setTitle("                                 Venturini & Palvarini 's             HORSE FEVER ©             close to go back to menu ----->");
		}
		if (OS.startsWith("Mac")) {
			setTitle("                     <----- close to go back to menu                  HORSE FEVER ©             Venturini & Palvarini          ");
		}
		setSize(new Dimension(insets.left + insets.right + 840, insets.top + insets.bottom + 665));

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

		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
			/**
			 * Sets the operation when the frame is closed: goes back to menu.
			 */
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					/**
					 * Delegates the EDT the task of showing the menu.
					 */
					@Override
					public void run() {
						getContentPane().removeAll();
						getContentPane().add(menuWindow);
						setVisible(true);
						repaint();
					}
				});
			}
		});

		try {
			// Set cross-platform Java L&F (also called "Metal")
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			setDefaultLookAndFeelDecorated(true);
		} catch (ClassNotFoundException e) {
			setDefaultLookAndFeelDecorated(true);
		} catch (InstantiationException e) {
			setDefaultLookAndFeelDecorated(true);
		} catch (IllegalAccessException e) {
			setDefaultLookAndFeelDecorated(true);
		}

		setVisible(true);
	}

	/**
	 * This method launches the ConsoleWindow for the CLI game, running the game on a different
	 * thread.
	 * 
	 * @param numPlayers the number of players for the new game
	 */
	public void startCLIGame(final int numPlayers) {
		removeMenuWindow();
		addLoadingWindow();

		// Run the game on a different thread
		Thread gameThread = new Thread() {
			/**
			 * Runs a new FamilyGame with CLI players on a new thread.
			 */
			@Override
			public void run() {
				final ConsoleWindow consoleWindow = new ConsoleWindow();
				try {
					game = new FamilyGame();
					for (int i = 0; i < numPlayers; i++) {
						Player player = new CLIPlayer(consoleWindow.getOut(), consoleWindow.getIn(), game.getBank(),
								String.format("player %d", i + 1));
						game.addPlayer(player);
						GameObserver gameObserver = (GameObserver) player;
						game.addObserver(gameObserver);
					}
					SwingUtilities.invokeLater(new Runnable() {
						/**
						 * Delegates the EDT the task of adding the Console to MainWindow.
						 */
						@Override
						public void run() {
							addMainWindow(consoleWindow);
							consoleWindow.focusOnConsole();
						}
					});
					game.start();
				} catch (NotManageableException e) {
					addCrashWindow();
				} catch (RemoteException e) {
					addCrashWindow();
				}
			}
		};
		gameThread.start();
	}

	/**
	 * This method launches the GUI game by instantiating the FamilyGame, the GUI Players and
	 * running the game on a different thread.
	 * 
	 * @param numPlayers the number of players for the new game
	 */
	public void startGUIGame(final int numPlayers) {
		removeMenuWindow();
		addLoadingWindow();
		// Load loading

		// Run the game on a different thread
		Thread gameThread = new Thread() {
			/**
			 * Runs a new FamilyGame with GUI players on a new thread.
			 */
			@Override
			public void run() {
				try {
					game = new FamilyGame();
					for (int i = 0; i < numPlayers; i++) {
						Player player = new GUIPlayer(game.getBank());
						game.addPlayer(player);
						GameObserver gameObserver = (GameObserver) player;
						game.addObserver(gameObserver);
					}
					game.start();
				} catch (NotManageableException e) {
					addCrashWindow();
				} catch (RemoteException e) {
					addCrashWindow();
				}
			}
		};
		gameThread.start();
	}

	/**
	 * This method launches the GUI network game for the server by calling the network manager.
	 * 
	 * @param numPlayers the number of players for the new game
	 * @param serverIp the ip of the server (required for RMI)
	 */
	public void startGUINetworkServerGame(int numPlayers, String serverIp) {
		removeMenuWindow();
		addLoadingWindow();
		NetworkManager.getServerNetworkManager(numPlayers, serverIp);
	}

	/**
	 * This method launches the GUI network game for the clients by calling the network manager.
	 * 
	 * @param numPlayers the number of players for the new game
	 * @param server the IP address of the server
	 * @param client the IP of the client (required for RMI)
	 */
	public void startGUINetworkClientGame(int numPlayers, String server, String client) {
		removeMenuWindow();
		addLoadingWindow();
		NetworkManager.getClientNetworkManager(server, client);
	}

	/**
	 * Removes the MenuWindow from its container.
	 */
	private void removeMenuWindow() {
		getContentPane().remove(menuWindow);
	}

	/**
	 * Displays the LoadingWindow during the memory loading of game data.
	 */
	private void addLoadingWindow() {
		LoadingWindow loadingWindow = new LoadingWindow();
		getContentPane().removeAll();
		getContentPane().add(loadingWindow);
		setVisible(true);
	}

	/**
	 * Displays the LoadingWindow during the memory loading of game data.
	 */
	public void addCrashWindow() {
		CrashWindow crashWindow = new CrashWindow();
		getContentPane().removeAll();
		getContentPane().add(crashWindow);
		setVisible(true);
	}

	/**
	 * Displays a JLabel into the main frame.
	 * 
	 * @param mainWindow the JLabel to be added and set visible.
	 */
	public void addMainWindow(JLabel mainWindow) {
		getContentPane().removeAll();
		getContentPane().add(mainWindow);
		setVisible(true);
		repaint();
	}

	/**
	 * This main method starts the entire game by instantiating the HorseFeverWindow.
	 * 
	 * @param arg
	 */
	public static void main(String arg[]) {
		horseFeverWindow = new HorseFeverWindow();
	}

	/**
	 * 
	 * @return the static attribute horseFeverWindow
	 */
	public static HorseFeverWindow getHorseFeverWindow() {
		return horseFeverWindow;

	}

}
