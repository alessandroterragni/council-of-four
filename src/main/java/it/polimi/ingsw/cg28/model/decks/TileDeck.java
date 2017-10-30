package it.polimi.ingsw.cg28.model.decks;

import java.util.Collections;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Represents a generic deck of tiles.
 * @author Marco
 *
 * @param <T> - *extends Tile* - The type of tile of the deck
 */
public class TileDeck<T extends Tile> extends GenericDeck<T>{

	/**
	 * The constructor for the class.
	 * @param newDeck - The list of objects of type T representing the tiles of the deck
	 */
	public TileDeck(LinkedList<T> newDeck){
		super(newDeck);
	}
	
	/**
	 * enqueue allows to put a target tile on the bottom of the deck.
	 * @param target - The tile to be put on the bottom of the deck
	 * @throws NullPointerException if the input target tile is null
	 */
	public void enqueue(T target){
		if(target == null){
			throw new NullPointerException("Can't enqueue a null tile.");
		}
		this.drawPile.remove(target);
		this.drawPile.addLast(target);
	}
	
	/**
	 * Returns the specified tile. 
	 * @param tileToTake - The tile to remove from the deck
	 * @return The desired tile
	 * @throws NullPointerException if the requested tile is null
	 * @throws NoSuchElementException if the requested tile is not in the deck
	 */
	public T takeTile(T tileToTake){
		
		if(tileToTake == null){
			throw new NullPointerException("No such tile as a null tile in the deck.");
		}
		
		for(T tile : this.drawPile){
			if (tile.equals(tileToTake)){
				this.drawPile.remove(tile);
				return tile;
			}
			else { throw new NoSuchElementException("No such element in tile deck."); }
		}
		return null;
			
	}
	
	/**
	 * Shuffles the deck via the Collections' shuffle() method.
	 * @see Collections
	 */
	public void shuffle(){
		Collections.shuffle(drawPile);		
	}
	
}
