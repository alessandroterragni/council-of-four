package it.polimi.ingsw.cg28.model.decks;


import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Represents a generic card deck in the game
 * @author Marco
 *
 * @param <T> - *extends Card* - The type of the deck's cards
 */
public class CardDeck<T extends Card> extends GenericDeck<T>{

	private List<T> discardPile;
	
	/**
	 * The constructor for the class. Also initializes to an empty list the discard pile.
	 * @param newDeck - The list of objects of type T representing the cards of the deck
	 */
	public CardDeck(LinkedList<T> newDeck){
		super(newDeck);
		this.discardPile = new ArrayList<>();
	}

	/**
	 * Fetches this deck's discard pile.
	 * @return the list representing the deck's discard pile
	 */
	public List<T> getDiscardPile() {
		return discardPile;
	}
	
	/**
	 * reshuffleDiscards shuffles the discard pile and refills the draw pile,
	 * then empties the discard pile again.
	 */
	public void reshuffleDiscards(){
		Collections.shuffle(discardPile);
		for(T c : discardPile){
			this.drawPile.addLast(c);
		}
		this.discardPile.removeAll(discardPile);
	}
	
	/**
	 * {@inheritDoc} - This implementation makes sure that if the deck is empty,
	 * the discard pile is re-shuffled into the draw pile, so that a card can always be drawn.
	 */
	@Override
	public T draw(){
		if(this.drawPile.isEmpty()){
			reshuffleDiscards();
		}
		return drawPile.removeFirst();
	}
	
}
