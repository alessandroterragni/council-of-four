package it.polimi.ingsw.cg28.model.decks;

import java.util.Collections;
import java.util.LinkedList;

/**
 * Represents a completely generic deck. Specific type of decks all inherit from
 * this one class that offers the draw() method.
 * @author Marco
 *
 * @param <T> - *extends Drawable* - The type of elements of the deck
 */
public abstract class GenericDeck<T extends Drawable> {

	protected final LinkedList<T> drawPile;	//This attribute is protected to allow free access to the subclasses without
										//defining getters and at the same time limiting the access to classes
										//inside the package itself
	
	/**
	 * The constructor for the class. The given list of cards is shuffled before being put into the deck.
	 * @param newDeck - The list of objects of type T representing the objects in the deck
	 * @throws NullPointerException if newDeck is null
	 */
	public GenericDeck(LinkedList<T> newDeck) {
		if(newDeck == null){
			throw new NullPointerException("The list of elements to fill the deck can't be null.");
		}
		Collections.shuffle(newDeck);
		this.drawPile = newDeck;
	}
	
	/**
	 * Draws a new T object from the deck.
	 * @return the first object in the deck
	 */
	public T draw(){
		return drawPile.removeFirst();
	}
	
	/**
	 * Returns the first object from the deck without removing it.
	 * @return the first object in the deck (does not remove it from the draw pile)
	 */
	public T peek(){
		return drawPile.getFirst();
	}
	
	/**
	 * Fetches the draw pile's current size.
	 * @return the integer indicating the draw pile's size
	 */
	public int size(){
		return this.drawPile.size();
	}
}
