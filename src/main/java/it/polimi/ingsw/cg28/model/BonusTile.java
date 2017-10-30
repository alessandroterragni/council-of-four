package it.polimi.ingsw.cg28.model;

import it.polimi.ingsw.cg28.controller.bonus.Bonus;
import it.polimi.ingsw.cg28.model.decks.Tile;


/**
 * Represents a standard bonus tile.
 * @author Marco
 *
 */
public class BonusTile implements Tile {

	private final Bonus bonus;
	private final String identifier;
	
	/**
	 * The constructor of the class.
	 * @param identifier - The String identifying the bonus given by the tile
	 * @param bonus - The bonus object associated to the tile
	 * @throws NullPointerException if the identifier or the bonus parameters are null.
	 */
	public BonusTile(String identifier, Bonus bonus) {
		if(identifier == null || bonus == null){
			throw new NullPointerException("Identifier and bonus can't be null.");
		}
		this.identifier = identifier;
		this.bonus = bonus;
	}
	
	/**
	 * Fetches the bonus object associated with this tile.
	 * @return The bonus associated with this tile
	 */
	public Bonus getBonus(){
		return this.bonus;
	}

	/**
	 * Fetches the text identifier for the tile's bonus.
	 * @return The String containing the textual ID of the tile's bonus
	 */
	public String getIdentifier() {
		return identifier;
	}
	
}
