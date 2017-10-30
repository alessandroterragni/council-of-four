package it.polimi.ingsw.cg28.model;

import java.awt.Color;

import it.polimi.ingsw.cg28.controller.bazaar.Salable;
import it.polimi.ingsw.cg28.model.decks.Card;

/**
 * A politic card used to perform actions linked with councillors.
 * @author Marco
 *
 */
public class PoliticCard implements Card, Salable {
	
	private final Color houseColor;
	private final boolean allColors;
	
	/**
	 * The constructor of the class.
	 * @param col - The card's color
	 * @param isAllCol - The flag indicating whether the card is multicolored or not
	 * @throws NullPointerException if color is null and isAllCol is false (card is not multicolored nor of a
	 * specific color)
	 * @throws IllegalArgumentException if color is not null and isAllCol is true (card is both multicolored
	 * AND of a specific color)
	 */
	public PoliticCard(Color col, boolean isAllCol) {
		if(col == null && !isAllCol){
			throw new NullPointerException("Color can't be null when the card isn't multicolored.");
		}
		if(col != null && isAllCol){
			throw new IllegalArgumentException("A politic card can't be multicolored and"
					+ " of a specific color at the same time!");
		}
		this.houseColor = col;
		this.allColors = isAllCol;
	}
	
	/**
	 * Fetches the card's color.
	 * @return The card's color
	 */
	public Color getHouseColor() {
		return houseColor;
	}
	
	/**
	 * Fetches the indicator of the card being multicolored or less.
	 * @return The boolean value indicating whether the card is multicolored or not
	 */
	public boolean isAllColors() {
		return allColors;
	}
	
	/**
	 * sameCol checks if the boolean value of isAllColors is true, effectively
	 * simulating the fact that the multicolored politic card can be used to match
	 * councillors of every color; it also checks if the card color matches the input one.
	 * @param col - The color to be verified
	 * @return True if the colors are the same or the card is multicolored, false
	 * otherwise
	 */
	public boolean sameCol(Color col) {
		if(isAllColors()){
				return true;
		}
		return this.houseColor == col;
	}

	/**
	 * Copies this card and returns a new, identical one.
	 * @return a deep copy of the politic card 
	 */
	public PoliticCard copy(){
		
		Color houseColorCopy= this.houseColor;
		return new PoliticCard(houseColorCopy,allColors);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (allColors ? 1231 : 1237);
		result = prime * result + ((houseColor == null) ? 0 : houseColor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PoliticCard other = (PoliticCard) obj;
		if (allColors != other.allColors)
			return false;
		if (houseColor == null) {
			if (other.houseColor != null)
				return false;
		} else if (!houseColor.equals(other.houseColor))
			return false;
		return true;
	}

}
