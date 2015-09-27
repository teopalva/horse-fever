package it.polimi.provaFinale2013.graphics;

import it.polimi.provaFinale2013.exceptions.NotManageableException;
import it.polimi.provaFinale2013.model.BetContainer;
import it.polimi.provaFinale2013.model.BlackBoard;
import it.polimi.provaFinale2013.model.FamilyGame;
import it.polimi.provaFinale2013.model.Game;
import it.polimi.provaFinale2013.model.GameObserver;
import it.polimi.provaFinale2013.model.Player;
import it.polimi.provaFinale2013.model.Track;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Properties;

/**
 * This class manages the network. It uses RMI.
 */
public final class NetworkManager implements GameObserver {

	private Object playersWaitLock = new Object();
	private int numPlayers = 0; //Actual number of players
	private int players; //Wanted number of players

	private boolean wakeUp;

	private Player playerStub;

	private static final int REGISTRY_PORT = 8001;
	private static NetworkManager networkManager = null;
	Registry registry = null;

	/**
	 * Default constructor.
	 */
	private NetworkManager() {
	}

	/**
	 * Instantiates and returns the NetworkManager.
	 * 
	 * @param players
	 * @return networkManager
	 */
	public synchronized static NetworkManager getServerNetworkManager(int players, String server) {
		if (networkManager == null) {
			networkManager = new NetworkManager();
		}
		networkManager.initServer(server, REGISTRY_PORT);
		synchronized (networkManager) {
			networkManager.players = players;
		}

		return networkManager;
	}

	/**
	 * Instantiates and returns the ClientNetworkManager.
	 * 
	 * @param ip
	 * @return networkManager
	 */
	public synchronized static NetworkManager getClientNetworkManager(String server, String client) {
		if (networkManager == null) {
			networkManager = new NetworkManager();
		}
		networkManager.initClient(server, client, REGISTRY_PORT);
		return networkManager;
	}

	/**
	 * This method configures the RMI server. It creates the registry, the server stub, the server
	 * player and waits for all the players to connect.
	 * 
	 * @param port
	 */
	private synchronized void initServer(final String serverIP, final int port) {
		final GameObserver gameObserver = this;
		numPlayers = 0;
		Thread gameThread = new Thread() {
			/**
			 * Runs the game on a different thread.
			 */
			@Override
			public void run() {
				Game game = null;
				try {
					game = new FamilyGame();

					Properties props = System.getProperties();
					props.setProperty("java.rmi.server.hostname", serverIP);

					//Add networkManager as a game observer
					game.addObserver(gameObserver);
					if (registry == null) {
						registry = LocateRegistry.createRegistry(port);
					}
					Game stub = (Game) UnicastRemoteObject.exportObject(game, 0);
					registry.rebind("Game", stub);

					Player player = new GUIPlayer(game.getBank());
					game.addPlayer(player);
					GameObserver gameObserver = (GameObserver) player;
					game.addObserver(gameObserver);
					//Wait until all players are connected
					try {
						synchronized (playersWaitLock) {
							wakeUp = false;
							while (!wakeUp) {
								playersWaitLock.wait();
							}
						}
					} catch (InterruptedException e) {
						throw new NotManageableException(e);
					}
					game.start();
				} catch (NotManageableException e) {
					HorseFeverWindow.getHorseFeverWindow().addCrashWindow();
				} catch (RemoteException e) {
					e.printStackTrace();
					HorseFeverWindow.getHorseFeverWindow().addCrashWindow();
				}
			}
		};

		gameThread.start();
	}

	/**
	 * This method configures the RMI client. It connects the client to the registry, it creates the
	 * client stub and the client player.
	 * 
	 * @param ip
	 * @param port
	 */
	private synchronized void initClient(final String serverIP, final String clientIP, final int port) {
		Thread gameThread = new Thread() {
			/**
			 * Runs the game on a different thread.
			 */
			@Override
			public void run() {
				Game game = null;
				try {
					Properties props = System.getProperties();
					props.setProperty("java.rmi.server.hostname", clientIP);

					String _port = Integer.valueOf(port).toString();
					game = (Game) Naming.lookup("rmi://" + serverIP + ":" + _port + "/Game");
					System.out.printf("Server>> %s", "rmi://" + serverIP + ":" + _port + "/Game");

					if (game != null) {
						Player player = new GUIPlayer(game.getBank());
						playerStub = (Player) UnicastRemoteObject.exportObject(player, 0);

						game.addPlayer(playerStub);
						GameObserver gameObserver = (GameObserver) playerStub;
						game.addObserver(gameObserver);
					}
				} catch (MalformedURLException e) {
					HorseFeverWindow.getHorseFeverWindow().addCrashWindow();
				} catch (RemoteException e) {
					HorseFeverWindow.getHorseFeverWindow().addCrashWindow();
				} catch (NotBoundException e) {
					HorseFeverWindow.getHorseFeverWindow().addCrashWindow();
				}
			}
		};

		gameThread.start();
	}

	/**
	 * Defines displayTrack in Game observer, but here it does nothing.
	 */
	// GameObserver
	public int displayTrack(Track track) {
		//DO NOTHING
		return 0;
	}

	/**
	 * Defines displayBlackBoard in Game observer, but here it does nothing.
	 */
	public int displayBlackBoard(BlackBoard blackBoard) {
		//DO NOTHING
		return 0;
	}

	/**
	 * Defines displayTurn in Game observer, but here it does nothing.
	 */
	public int displayTurn(int turn, int maxTurns) {
		//DO NOTHING
		return 0;
	}

	/**
	 * Defines displayBets in Game observer, but here it does nothing.
	 */
	public int displayBets(List<BetContainer> bets) {
		//DO NOTHING
		return 0;
	}

	/**
	 * Increases the number of players. If the total number is reached it notifies all the waiting
	 * players.
	 */
	public synchronized int addedPlayer() {
		numPlayers++;
		synchronized (playersWaitLock) {
			if (numPlayers == players) {
				wakeUp = true;
				playersWaitLock.notifyAll();
			}
		}
		return 0;
	}
}
