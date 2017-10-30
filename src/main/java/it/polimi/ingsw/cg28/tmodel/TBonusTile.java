package it.polimi.ingsw.cg28.tmodel;

import java.io.Serializable;

import com.google.common.base.Preconditions;

/**
 * Represents a BonusTile variant which can be transfered from the server to the client without
 * making the real model's rep accessible from a remote client
 * @author Mario
 *
 */
public class TBonusTile implements Serializable {

	private static final long serialVersionUID = -5449684522324405634L;
	private String identifier;
	private TBonus bonus;
	
	/**
	 * The constructor of the class, creates a new TBonusTile
	 * @param bonus TBonus related to the tile
	 * @param identifier Tile identifier
	 * @throws NullPointerException if bonus or identifier are null
	 */
	public TBonusTile(TBonus bonus, String identifier){
		
		Preconditions.checkNotNull(bonus);
		Preconditions.checkNotNull(identifier);
		
		this.bonus = bonus;
		this.identifier = identifier;
	}
	
	/**
	 * Fetches this tile's associated identifier.
	 * @return the String representing this tile's associated identifier
	 */
	public String getIdentifier() {
		return identifier;
	}
	
	/**
	 * Fetches this tile's associated bonus.
	 * @return the TBonus object representing this tile's associated bonus
	 */
	public TBonus getBonus() {
		return bonus;
	}

}
