package it.polimi.provaFinale2013.model;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * This class represent a Deck that is a container of cards. It implements method for taking cards
 * from the top or bottom and for shuffle the deck
 * 
 * @param <T> the type of card that you want to put iside the deck
 */
public class Deck<T extends Card> implements Iterable<T>, Cloneable {
	private LinkedList<T> container;

	/**
	 * The constructor of the class
	 */
	public Deck() {
		container = new LinkedList<T>();
	}

	/**
	 * Constructor for deep copy
	 * 
	 * @param c
	 */
	@SuppressWarnings("unchecked")
	public Deck(LinkedList<T> c) {
		this.container = new LinkedList<T>();
		for (T i : c) {
			this.container.add((T) i.clone());
		}
	}

	/**
	 * Pops an element from the stack represented by this list.
	 * 
	 * @return Removes and returns the first element of this list
	 */
	public T popCard() {
		return container.pop();
	}

	/**
	 * Inserts the specified element at the beginning of this list.
	 * 
	 * @param card
	 */
	public void pushTopCard(T card) {
		container.addFirst(card);
	}

	/**
	 * Appends the specified element to the end of this list.
	 * 
	 * @param card
	 */
	public void pushBottomCard(T card) {
		container.addLast(card);
	}

	/**
	 * Remove a card from the deck. In case of two equal cards remove only the first
	 * 
	 * @param card the card that you want to remove
	 */
	public void removeCard(T card) {
		for (T c : container) {
			if (c.equals(card)) {
				container.remove(c);
				break;
			}
		}
	}

	/**
	 * @return number of cards
	 */
	public int getNumCards() {
		return container.size();
	}

	/**
	 * Randomly permutes the list using a default source of randomness.
	 */
	public void shuffle() {
		Collections.shuffle(container);
	}

	/**
	 * clone method
	 */
	@Override
	public Deck<T> clone() {
		return new Deck<T>(container);
	}

	/**
	 * Delete all cards in this deck
	 */
	public void clear() {
		container.clear();
	}

	/**
	 * Get the iterator in order to iterate on the deck
	 */
	@Override
	public Iterator<T> iterator() {
		Iterator<T> it = new Iterator<T>() {

			private int currentIndex = 0;

			@Override
			public boolean hasNext() {
				if (currentIndex >= container.size()) {
					return false;
				}
				return true;
			}

			@Override
			public T next() {
				if (currentIndex >= container.size()) {
					throw new NoSuchElementException();
				}
				T element = container.get(currentIndex);
				currentIndex++;
				return element;
			}

			@Override
			public void remove() {
			}
		};
		return it;
	}

}
