package it.polimi.ingsw.cg28.tmodel;

import java.io.Serializable;

/**
 * Represents a BusinessPermitTile variant which can be transfered from the server to the client without
 * making the real model's rep accessible from a remote client
 * @author Marco
 *
 */
public class TBusinessPermitTile implements Serializable{

	private static final long serialVersionUID = 3046818550620950011L;
	private final TBonus bonuses;
	private final String[] letterCodes;
	

	/**
	 * The constructor of the class, creates a new TBusinessPermitTile
	 * @param bonuses - A TBonus object representing all bonuses associated with this tile
	 * @param letters - An array of strings containing the names of the towns in which this permit tile
	 * is valid
	 * @throws NullPointerException if any of the parameters is null
	 */
	public TBusinessPermitTile(TBonus bonuses, String[] letters) {
		if(bonuses == null || letters == null){
			throw new NullPointerException("Can't create a TBusinessPermitTile with any null parameter.");
		}
		this.bonuses = bonuses;
		this.letterCodes = letters;
	}

	/**
	 * Fetches the TBonus object associated with this tile.
	 * @return the TBonus containing the bonuses for this tile
	 */
	public TBonus getBonuses() {
		return bonuses;
	}

	/**
	 * Fetches the array of strings containing the names of the towns associated with this tile.
	 * @return an array of strings with the associated towns' name
	 */
	public String[] getLetterCodes() {
		return letterCodes;
	}
}
