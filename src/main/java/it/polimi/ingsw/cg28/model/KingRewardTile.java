package it.polimi.ingsw.cg28.model;

import it.polimi.ingsw.cg28.controller.bonus.Bonus;
import it.polimi.ingsw.cg28.model.decks.Tile;

/**
 * Represents a specific bonus tile associated with peculiar actions.
 * It is separated from the standard bonus tile to be easily managed by the
 * bonus updaters.
 * @author Marco
 *
 */
public class KingRewardTile implements Tile {

	private final Bonus bonus;
	
	/**
	 * The constructor of the class.
	 * @param bonus - The bonus associated with the king reward tile
	 * @throws NullPointerException if the bonus parameter is null
	 */
	public KingRewardTile(Bonus bonus) {
		if(bonus == null){
			throw new NullPointerException("The associated bonus can't be null.");
		}
		this.bonus = bonus;
	}
	
	/**
	 * Fetches the tile's bonus.
	 * @return The bonus object associated with the tile
	 */
	public Bonus getBonus() {
		return bonus;
	}
	
}
