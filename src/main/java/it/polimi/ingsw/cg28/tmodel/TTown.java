package it.polimi.ingsw.cg28.tmodel;

import java.io.Serializable;
import java.util.Arrays;

import it.polimi.ingsw.cg28.controller.PlayerID;

/**
 * Represents a Town variant which can be transfered from the server to the client without
 * making the real model's rep accessible from a remote client
 * @author Marco
 *
 */
public class TTown implements Serializable {

	private static final long serialVersionUID = 8576542560655511664L;
	
	private final String name;
	private final String alloy;
	private final String region;
	private final TBonus bonus;
	private final boolean king;
	private final PlayerID[] emporiums;
	
	/**
	 * The constructor of the class, creates a new TTown.
	 * @param name - A string containing the town's name
	 * @param alloy - A string containing the town's alloy
	 * @param region - A string containing the region type where the town is in
	 * @param bonus - The transferable object representing the bonus associated with this town
	 * @param king - A boolean flag indicating the king's presence in town
	 * @param emporiums - An array containing the IDs of the players who have built an emporium in this town
	 * @throws NullPointerException if any parameter among name, alloy, region or emporiums is null
	 */
	public TTown(String name, String alloy, String region, TBonus bonus, boolean king, PlayerID[] emporiums) {

		if(name == null || alloy == null || region == null || emporiums == null){
			throw new NullPointerException("Can't create a TTown with any null parameter.");
		}
		
		this.name = name;
		this.alloy = alloy;
		this.region = region;
		this.bonus = bonus;
		this.king = king;
		this.emporiums = emporiums;
	}

	/**
	 * Fetches the town's name.
	 * @return a string with this town's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Fetches the town's alloy.
	 * @return a string with this town's alloy
	 */
	public String getAlloy() {
		return alloy;
	}

	/**
	 * Fetches the town's region.
	 * @return a string with this town's region type
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * Fetches the transferable bonus associated with the town.
	 * @return a TBonus that matches the town's associated bonus
	 */
	public TBonus getBonus() {
		return bonus;
	}

	/**
	 * Indicates whether the king is in town or not.
	 * @return <code>true</code> if the king is in town, <code>false</code> otherwise
	 */
	public boolean isKingHere() {
		return king;
	}

	/**
	 * Fetches the array of PlayerIDs indicating who has built an emporium in town.
	 * @return an array of PlayerID containing the IDs of emporium owners for this town
	 */
	public PlayerID[] getEmporiums() {
		return emporiums;
	}
	
	/**
	 * Fetches the ID of an emporium based on its position in the emporiums array
	 * @param index - The integer offset of the emporium
	 * @return the desired emporium's owner ID
	 */
	public PlayerID getEmporium(int index){
		if(index < 0 || index >= this.emporiums.length){
			throw new IllegalArgumentException("Input index is illegal: negative or out of bounds.");
		}
		return emporiums[index];
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alloy == null) ? 0 : alloy.hashCode());
		result = prime * result + ((bonus == null) ? 0 : bonus.hashCode());
		result = prime * result + Arrays.hashCode(emporiums);
		result = prime * result + (king ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((region == null) ? 0 : region.hashCode());
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
		TTown other = (TTown) obj;
		if (!alloy.equals(other.alloy))
			return false;
		if (bonus == null) {
			if (other.bonus != null)
				return false;
		} else if (!bonus.equals(other.bonus))
			return false;
		if (!Arrays.equals(emporiums, other.emporiums))
			return false;
		if (king != other.king)
			return false;
		if (!name.equals(other.name))
			return false;
		if (!region.equals(other.region))
			return false;
		return true;
	}
}
