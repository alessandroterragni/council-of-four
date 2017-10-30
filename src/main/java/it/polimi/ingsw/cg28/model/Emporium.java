package it.polimi.ingsw.cg28.model;

import it.polimi.ingsw.cg28.controller.PlayerID;

/**
 * Represents an emporium of a given player.
 * @author Marco
 *
 */
public class Emporium {
	
	private final PlayerID owner;
	
	/**
	 * The constructor of the class.
	 * @param p - The ID of the owner of the emporium
	 * @throws NullPointerException if the player id parameter is null
	 */
	public Emporium(PlayerID p) {
		if(p == null){
			throw new NullPointerException("Owner ID can't be null.");
		}
		this.owner = p;
	}
	
	/**
	 * Fetches this emporium's owner.
	 * @return the owner's PlayerID
	 */
	public PlayerID getOwner() {
		return owner;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
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
		Emporium other = (Emporium) obj;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		return true;
	}
	
	
}
