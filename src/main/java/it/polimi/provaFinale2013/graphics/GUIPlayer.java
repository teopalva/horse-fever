package it.polimi.provaFinale2013.graphics;

import it.polimi.provaFinale2013.exceptions.NotEnoughMoneyException;
import it.polimi.provaFinale2013.exceptions.NotManageableException;
import it.polimi.provaFinale2013.model.AbstractPlayer;
import it.polimi.provaFinale2013.model.Action;
import it.polimi.provaFinale2013.model.Bet;
import it.polimi.provaFinale2013.model.BetContainer;
import it.polimi.provaFinale2013.model.BetToken;
import it.polimi.provaFinale2013.model.BlackBoard;
import it.polimi.provaFinale2013.model.RemoteBank;
import it.polimi.provaFinale2013.model.Track;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.SwingUtilities;

/**
 * This class manages all the actions that each GUI player can do and the events related to him.
 */
public class GUIPlayer extends AbstractPlayer {

	private MainWindow mainWindow;
	private boolean wakeUp;
	private boolean wait = false;

	//Pop-up object
	private SeeHandWindow seeHandWindow = null;
	private WinWindow winWindow = null;
	private LoseWindow loseWindow = null;

	//Return objects
	private Bet bet;
	private Action action;
	private int firstHorse;

	//The first bet
	private BetToken firstBetToken = null;

	/**
	 * Constructor with name.
	 * 
	 * @param bank the bank
	 * @param name the name of the player
	 */
	public GUIPlayer(RemoteBank bank, String name) {
		super(System.out, System.in, bank, name);
		mainWindow = new MainWindow(this);
		mainWindow.setVisible(true);
	}

	/**
	 * Constructor without player name. You need to set the player name with setName().
	 * 
	 * @param bank the bank
	 */
	public GUIPlayer(RemoteBank bank) {
		super(System.out, System.in, bank, "");
		mainWindow = new MainWindow(this);
		mainWindow.setVisible(true);
		displaySetName();
	}

	/**
	 * If called it causes the immediate loss of the match.
	 */
	@Override
	public void loseGame() {
		displayLoseWindow();
	}

	/**
	 * Called when this player has won the game.
	 */
	@Override
	public void winGame() {
		displayWinWindow();
	}

	/**
	 * Manages the player's bet by showing the MakeBetWindow on screen and managing its logic.
	 */
	@Override
	public synchronized Bet requestMakeBet(final List<BetToken> betTokens, final boolean mandatory) throws NotEnoughMoneyException, RemoteException {
		final GUIPlayer _player = this;
		dismissSeeHandWindow();

		//Check if player has enough money for minimum bet
		while (getVP() > 0 && !checkMinimumBet(getValue())) {
			removeVP(2);
		}
		;

		//Check if player has lost
		if (getVP() <= 0) {
			throw new NotEnoughMoneyException();
		}

		//Indicate two bet same type same horse
		boolean error = false;
		do {
			error = false;
			SwingUtilities.invokeLater(new Runnable() {
				/**
				 * Runs the makeBetWindow popup on the EDT.
				 */
				public void run() {
					MakeBetWindow makeBetWindow = new MakeBetWindow(betTokens, _player, mandatory);
					makeBetWindow.setLocationRelativeTo(mainWindow);
					makeBetWindow.setVisible(true);
				}
			});

			try {
				wait();
			} catch (InterruptedException e) {
				throw new NotManageableException(e);
			}

			if (!mandatory) {
				//Second bet
				if (bet != null) {
					if (bet.getBetToken().equals(firstBetToken)) {
						error = true;
						this.putDanari(bet.getDanari());
					}
				} else {
					error = false;
				}
			} else {
				firstBetToken = bet.getBetToken();
			}

		} while ((bet == null && mandatory) || error);

		return bet;
	}

	/**
	 * Manages the player's choice of playing an action card over a stable by showing the
	 * PlayActionCardWindow on screen and managing its logic.
	 */
	@Override
	public synchronized Action requestPlayAction() {
		final GUIPlayer _player = this;
		dismissSeeHandWindow();
		SwingUtilities.invokeLater(new Runnable() {
			/**
			 * Runs the playActionCardWindow popup on the EDT.
			 */
			public void run() {
				PlayActionCardWindow playActionCardWindow = new PlayActionCardWindow(actionCards, _player);
				playActionCardWindow.setLocationRelativeTo(mainWindow);
				playActionCardWindow.setVisible(true);
			}
		});

		try {
			wakeUp = false;
			while (!wakeUp) {
				wait();
			}
		} catch (InterruptedException e) {
			throw new NotManageableException(e);
		}

		actionCards.remove(action.getActionCard());

		return action;
	}

	/**
	 * Manages the player's choice of the winner horse by showing the ChooseFirstHorseWindow on
	 * screen and managing its logic.
	 */
	@Override
	public synchronized int chooseFirstHorse(final List<Integer> horses) {
		final GUIPlayer _player = this;
		dismissSeeHandWindow();
		SwingUtilities.invokeLater(new Runnable() {
			/**
			 * Runs the chooseFirstHorseWindow popup on the EDT.
			 */
			public void run() {
				ChooseFirstHorseWindow chooseFirstHorseWindow = new ChooseFirstHorseWindow(horses, _player);
				chooseFirstHorseWindow.setLocationRelativeTo(mainWindow);
				chooseFirstHorseWindow.setVisible(true);
			}
		});

		try {
			wakeUp = false;
			while (!wakeUp) {
				wait();
			}
		} catch (InterruptedException e) {
			throw new NotManageableException(e);
		}

		return firstHorse;
	}

	/**
	 * Calls displayTrack on mainWindow. Wait 1500 millisecond
	 */
	@Override
	public int displayTrack(Track track) {
		mainWindow.displayTrack(track);
		if(track.getStatus() == Track.STARTED) {
			mainWindow.deleteBets();
		}
		return 1500;
	}

	/**
	 * Calls displayBlackBoard on mainWindow. Wait 1500 millisecond
	 */
	@Override
	public int displayBlackBoard(final BlackBoard blackBoard) {
		SwingUtilities.invokeLater(new Runnable() {
			/**
			 * Runs the BlackBoardWindow on the EDT.
			 */
			public void run() {
				mainWindow.displayBlackBoard(blackBoard);
			}
		});
		return 1500;
	}

	/**
	 * Instantiates a new SeeHandWindow and shows it on screen.
	 */
	public void displaySeeHand() {
		seeHandWindow = new SeeHandWindow(actionCards, characterCard, stableOwnerCard, this);
		seeHandWindow.setLocationRelativeTo(mainWindow);
		seeHandWindow.setVisible(true);
	}

	/**
	 * Calls dispose on the seeHandWindow.
	 */
	private void dismissSeeHandWindow() {
		if (seeHandWindow != null) {
			seeHandWindow.dispose();
		}
	}

	/**
	 * Instantiates a new WinWindow and shows it on screen.
	 */
	private void displayWinWindow() {
		final GUIPlayer player = this;
		SwingUtilities.invokeLater(new Runnable() {
			/**
			 * Runs the WinWindow popup on the EDT.
			 */
			public void run() {
				winWindow = new WinWindow(player);
				winWindow.setLocationRelativeTo(HorseFeverWindow.getHorseFeverWindow());
				winWindow.setVisible(true);
			}
		});

	}

	/**
	 * Instantiates a new LoseWindow and shows it on screen.
	 */
	private synchronized void displayLoseWindow() {
		final GUIPlayer player = this;
		SwingUtilities.invokeLater(new Runnable() {
			/**
			 * Runs the LoseWindow popup on the EDT.
			 */
			public void run() {
				loseWindow = new LoseWindow(player);
				loseWindow.setLocationRelativeTo(HorseFeverWindow.getHorseFeverWindow());
				loseWindow.setVisible(true);
			}
		});

		try {
			wakeUp = false;
			while (!wakeUp) {
				wait();
			}
		} catch (InterruptedException e) {
			throw new NotManageableException(e);
		}
	}

	//Callback methods for pop-ups
	/**
	 * Callback method for makeBetWindow.
	 * 
	 * @param bet
	 */
	public synchronized void makeBet(Bet bet) {
		this.bet = bet;
		wakeUp = true;
		notifyAll();
	}

	/**
	 * Callback method for playActionWindow.
	 * 
	 * @param action
	 */
	public synchronized void playAction(Action action) {
		this.action = action;
		wakeUp = true;
		notifyAll();
	}

	/**
	 * Callback method for chooseFirstHorseWindow.
	 * 
	 * @param horse
	 */
	public synchronized void chooseFirstHorse(int horse) {
		this.firstHorse = horse;
		wakeUp = true;
		notifyAll();
	}

	/**
	 * Callback method for closeLoseWindow.
	 */
	public synchronized void closeLoseWindow() {
		wakeUp = true;
		notifyAll();
	}

	/**
	 * Activates the mainWindow within the HorseFeverWindow.
	 */
	@Override
	public synchronized void makeActive() {
		if (mainWindow != null) {
			mainWindow.setBlinkContinue();

			HorseFeverWindow.getHorseFeverWindow().addMainWindow(mainWindow);
			mainWindow.updatePlayerInformation();

			wait = true;
			wakeUp = false;

			while (!wakeUp) {
				try {
					wait();
				} catch (InterruptedException e) {
				}
			}
			wait = false;
			mainWindow.hideContinueButton();
		}

	}

	/**
	 * Displays the turn. Wait 1500 millisecond
	 */
	public int displayTurn(int turn, int maxTurns) {
		super.displayTurn(turn, maxTurns);
		mainWindow.displayTurn(turn, maxTurns);
		return 1500;
	}

	/**
	 * GameObserver method Display bets on MainWindow Wait 200 millisecond
	 */
	@Override
	public int displayBets(List<BetContainer> bets) throws RemoteException {
		mainWindow.displayBets(bets);
		return 200;
	}

	/**
	 * Opens a popup window that request for the name of the player. This is a blocking method. It
	 * waits until the player enters the name.
	 * 
	 * @throws NotManageableException
	 */
	public synchronized void displaySetName() throws NotManageableException {
		final GUIPlayer _player = this;
		SwingUtilities.invokeLater(new Runnable() {
			/**
			 * Runs the enterNameWindow popup on the EDT.
			 */
			public void run() {
				EnterNameWindow enterNameWindow = new EnterNameWindow(_player);
				enterNameWindow.setLocationRelativeTo(HorseFeverWindow.getHorseFeverWindow());
				enterNameWindow.setVisible(true);
			}
		});

		try {
			wakeUp = false;
			while (!wakeUp) {
				wait();
			}
		} catch (InterruptedException e) {
			throw new NotManageableException(e);
		}
	}

	/**
	 * Callback method for enterNameWindow.
	 * 
	 * @param name
	 */
	public synchronized void setName(String name) {
		this.name = name;
		wakeUp = true;
		notifyAll();
	}

	/**
	 * Let continue after a make active call that block the mail thread
	 */
	public synchronized void continueAfterMakeActive() {
		if (wait) {
			wakeUp = true;
			notifyAll();
		}
	}

	/**
	 * Imports the inputStream into mainWindow.
	 * 
	 * @param inputStream
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
		inputStream.defaultReadObject();
		seeHandWindow = null;
		winWindow = null;
		loseWindow = null;
	}

	/**
	 * Imports the outputStream into mainWindow.
	 * 
	 * @param stream
	 * @throws java.io.IOException
	 */
	private synchronized void writeObject(java.io.ObjectOutputStream stream) throws java.io.IOException {
		stream.defaultWriteObject();
	}

}
