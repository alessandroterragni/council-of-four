package it.polimi.ingsw.cg28.model;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.bonus.Bonus;

/**
 * Represents a town in the map town network. Towns are identified by a TownID
 * through which the letterCode associated with permit tile can be retrieved.
 * @author Marco
 *
 */
public class Town {
	
	private final String name;
	private final String alloy;
	private boolean king;
	private List<Emporium> emporiums;
	private final Bonus bonus;
	private final Region region;
	
	/**
	 * The constructor of the class.
	 * @param name - The string containing the town's name
	 * @param alloy - The string identifier for the city type
	 * @param kingHere - The flag indicating whether the king is currently in town
	 * @param bonus - The bonus associated with the town
	 * @param region - The region where the city is found
	 * @throws NullPointerException if one among name, alloy and region are null
	 */
	public Town(String name, String alloy, boolean kingHere, Bonus bonus,
			Region region) {
		if(name == null || alloy == null || region == null){
			throw new NullPointerException("Town name, alloy and region can't be null.");
		}
		this.name = name;
		this.alloy = alloy;
		this.king = kingHere;
		this.emporiums = new ArrayList<>();
		this.bonus = bonus;
		this.region = region;
	}
	
	/**
	 * Signals whether the king is in this town or not.
	 * @return The boolean value indicating the presence or less of the king in this town
	 */
	public boolean isKingHere() {
		return king;
	}

	/**
	 * Sets/unsets the king in this town.
	 * @param king - The boolean value to be set for the variable indicating the king's presence
	 */
	protected void setKingHere(boolean king) {
		this.king = king;
	}

	/**
	 * Fetches the town's name.
	 * @return A String containing the town's name
	 */
	public String getTownName() {
		return name;
	}

	/**
	 * Fetches the town's alloy.
	 * @return A String containing the town's alloy
	 */
	public String getAlloy() {
		return alloy;
	}

	/**
	 * Fetches this town's associated bonus object.
	 * @return The Bonus object associated with the town
	 */
	public Bonus getBonus() {
		return bonus;
	}

	/**
	 * Fetches the region in which this town is located.
	 * @return This town's Region
	 */
	public Region getRegion() {
		return region;
	}

	/**
	 * addEmporium adds an emporium for the specified player in the town.
	 * If there is already an emporium built by the same player, the method doesn't
	 * do anything.
	 * @param p - The player whose emporium will be built in the town
	 * @throws NullPointerException if the specified player is null
	 */
	protected void addEmporium(PlayerID p){

		if(p == null){
			throw new NullPointerException("Player can't be null.");
		}
			
		if(hasEmporium(p)) 
			return;
		
		else emporiums.add(new Emporium(p));
	}
	
	/**
	 * Allows to know whether the specified player has already built
	 * an emporium in the city or not.
	 * @param p - The player to be checked
	 * @return <code>true</code> if there already exists an emporium owned by player p, <code>false</code>
	 * otherwise
	 * @throws NullPointerException if the specified player is null
	 */
	public boolean hasEmporium(PlayerID p){
	
		if(p == null){
			throw new NullPointerException("Player can't be null.");
		}
		
		if(emporiums.contains(new Emporium(p))) {
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * Retrieves the number of emporiums built in the city.
	 * @return The number of emporiums already present in town
	 */
	public int emporiumNumber(){
		return emporiums.size();
	}
	
	/**
	 * Fetches an emporium built in the city based on its position.
	 * @param index - The integer offset of the desired emporium in the town
	 * @return The desired Emporium
	 * @throws IllegalArgumentException if the index is negative
	 */
	public Emporium getEmporium(int index){
		if(index < 0){
			throw new IllegalArgumentException("Can't retrieve an emporium from a negative index.");
		}
		return this.emporiums.get(index);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alloy == null) ? 0 : alloy.hashCode());
		result = prime * result + ((bonus == null) ? 0 : bonus.hashCode());
		result = prime * result + ((emporiums == null) ? 0 : emporiums.hashCode());
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
		Town other = (Town) obj;
		if (!alloy.equals(other.alloy))
			return false;
		if (bonus == null) {
			if (other.bonus != null)
				return false;
		} else if (!bonus.equals(other.bonus))
			return false;
		if (emporiums == null) {
			if (other.emporiums != null)
				return false;
		} else if (!emporiums.equals(other.emporiums))
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
