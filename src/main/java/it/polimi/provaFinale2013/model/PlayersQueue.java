package it.polimi.provaFinale2013.model;

import it.polimi.provaFinale2013.exceptions.NoPlayersException;
import it.polimi.provaFinale2013.exceptions.NotManageableException;
import it.polimi.provaFinale2013.exceptions.OnePlayerLeftException;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This class represent the queue of players. It simplify operation like more than one round,
 * clockwise and anticlockwise round with iterator
 * 
 * @param <T> The type of Players contained in the queue
 */
public class PlayersQueue<T extends Player> implements Iterable<T> {
	private List<T> players;
	private boolean clockWise;
	private int numRounds;
	private int firstPlayer;

	/**
	 * The constructor of the class
	 */
	public PlayersQueue() {
		players = new ArrayList<T>();
		clockWise = true;
		numRounds = 1;
		firstPlayer = 0;
	}

	/**
	 * Add a player
	 * 
	 * @param player the player that you want to add
	 */
	public void addPlayer(T player) {
		players.add(player);
	}

	/**
	 * Remove a player
	 * 
	 * @param player the player you want to remove
	 */
	public void removePlayer(T player) {
		players.remove(player);
	}

	/**
	 * Set the round method (clockwise or anticlockwise)
	 * 
	 * @param clockWise boolean choice between clockwise (true) and anticlockwise (false)
	 */
	public void setClockWise(boolean clockWise) {
		this.clockWise = clockWise;
	}

	/**
	 * Set the number of the rounds
	 * 
	 * @param rounds the number of rounds
	 */
	public void setNumRounds(int rounds) {
		numRounds = rounds;
	}

	/**
	 * Set the first player index [0..5]
	 * 
	 * @param fp first player
	 */
	public void setFirstPlayer(int fp) {
		if (fp < 0 || fp >= players.size()) {
			throw new IllegalArgumentException();
		}
		firstPlayer = fp;
	}

	/**
	 * Increase first player clockwise
	 */
	public void increaseFirstPlayer() {
		if (players.size() == 0) {
			return;
		}
		firstPlayer = (firstPlayer + 1) % players.size();
	}

	/**
	 * Get the iterator
	 */
	@Override
	public Iterator<T> iterator() {
		Iterator<T> it = new Iterator<T>() {

			private int currentIndex = 0;

			/**
			 * Return true if there's a next player in the queue
			 */
			@Override
			public boolean hasNext() {
				if (players.size() == 1) {
					throw new OnePlayerLeftException();
				}
				if (currentIndex >= players.size() * numRounds) {
					return false;
				}
				return true;
			}

			/**
			 * Get the next players in the queue
			 * 
			 * @return next player
			 */
			@Override
			public T next() {
				if (players.size() == 1) {
					throw new OnePlayerLeftException();
				}
				if (currentIndex >= players.size() * numRounds) {
					throw new NoSuchElementException();
				}
				T element;
				if (clockWise) {
					element = players.get((firstPlayer + currentIndex % players.size()) % players.size());
				} else {
					element = players.get((firstPlayer + players.size() - 1 - currentIndex % players.size()) % players.size());
				}
				currentIndex++;
				try {
					element.makeActive();
				} catch (RemoteException e) {
					throw new NotManageableException(e);
				} //Activate the current player
				return element;
			}

			/**
			 * remove the current player from the queue
			 */
			@Override
			public void remove() {
				currentIndex--;
				if (clockWise) {
					players.remove((firstPlayer + currentIndex % players.size()) % players.size());
					if (firstPlayer > players.size() - 1) {
						firstPlayer = players.size() - 1;
					}
				} else {
					players.remove((firstPlayer + players.size() - 1 - currentIndex % players.size()) % players.size());
					if (firstPlayer > players.size() - 1) {
						firstPlayer = 0;
					}
				}

			}
		};
		return it;
	}

	/**
	 * @return the number of players
	 */
	public int getNumPlayers() {
		return players.size();
	}

	/**
	 * Get the first player
	 * 
	 * @return the first player
	 * @throws NoPlayersException when no players are present
	 */
	public Player getFirstPlayer() throws NoPlayersException {
		if (players.size() == 0) {
			throw new NoPlayersException();
		}
		return players.get(firstPlayer);
	}
}
