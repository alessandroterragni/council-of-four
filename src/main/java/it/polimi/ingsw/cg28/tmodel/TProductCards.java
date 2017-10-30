package it.polimi.ingsw.cg28.tmodel;

import java.awt.Color;
import java.io.Serializable;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.view.cli.CliPrinter;

/**
 * Throwable object representing a bundle of politicCards on sale in the bazaar.
 * @author Mario
 *
 */
public class TProductCards extends TProduct implements Serializable{
	
	private static final long serialVersionUID = 2076485268585929817L;
	
	private final Color[] cards;
	
	/**
	 * Constructor of class
	 * @param price price of the product
	 * @param playerOwner string that identifies the owner of politicCards
	 * @param cards array of colors of cards on sale (null if allColors)
	 * @throws NullPointerException if playerOwner is null
	 * @throws NullPointerException if cards is null
	 * @throws IllegalArgumentException if price is lower than zero
	 * @throws IllegalArgumentException if cards array length is zero
	 */
	public TProductCards(int price, String playerOwner, Color[] cards) {
		
		super(price, playerOwner);
		
		Preconditions.checkNotNull(cards);
		Preconditions.checkArgument(cards.length > 0);
		
		this.cards = cards;
	}
	
	/**
	 * Returns the array of colors of cards on sale
	 * @return array of colors of cards on sale
	 */
	public Color[] getCards() {
		return cards;
	}

	/**
	 * Method to return the string representation of the TProductCards
	 */
	@Override
	public String print(CliPrinter printer) {
		return printer.printTProductCards(this);
	}

	
	
}
