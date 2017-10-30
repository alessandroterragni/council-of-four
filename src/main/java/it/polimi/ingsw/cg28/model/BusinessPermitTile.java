package it.polimi.ingsw.cg28.model;

import java.util.Arrays;

import it.polimi.ingsw.cg28.controller.bazaar.Salable;
import it.polimi.ingsw.cg28.controller.bonus.*;
import it.polimi.ingsw.cg28.model.decks.Tile;

/**
 * Represents a build permit tile, used by the player to build an emporium
 * with the dedicated main action.
 * @author Marco
 *
 */
public class BusinessPermitTile implements Tile, Salable {

	private final Bonus bonuses;
	private final String[] letterCodes;
	
	/**
	 * The constructor of the class.
	 * @param bonuses - The bonuses associated with the permit tile
	 * @param letters - The array of strings representing the towns this permit tile allows
	 * to build in
	 * @throws NullPointerException if the bonuses or letters parameters are null.
	 */
	public BusinessPermitTile(Bonus bonuses, String[] letters) {
		if(bonuses == null || letters == null){
			throw new NullPointerException("Bonuses and town letter codes can't be null.");
		}
		this.bonuses = bonuses;
		this.letterCodes = letters;
	}

	/**
	 * Fetches the bonuses on this permit tile.
	 * @return The tile's associated Bonus object
	 */
	public Bonus getBonuses() {
		return bonuses;
	}

	/**
	 * Fetches the textual representation of the cities covered by this permit tile.
	 * @return An array of Strings containing the names of the cities covered by the tile
	 */
	public String[] getLetterCodes() {
		return letterCodes;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bonuses == null) ? 0 : bonuses.hashCode());
		result = prime * result + Arrays.hashCode(letterCodes);
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
		BusinessPermitTile other = (BusinessPermitTile) obj;
		if (!bonuses.equals(other.bonuses))
			return false;
		if (!Arrays.equals(letterCodes, other.letterCodes))
			return false;
		return true;
	}
	
	
}
