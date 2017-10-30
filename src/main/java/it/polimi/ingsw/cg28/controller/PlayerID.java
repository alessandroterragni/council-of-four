package it.polimi.ingsw.cg28.controller;

import java.io.Serializable;
import java.util.UUID;

import com.google.common.base.Preconditions;

/**
 * Class PlayerID identifies the player.
 * PlayerID matches the player name(type String) and a related UUID to identify
 * the player uniquely.
 * @author Mario
 */
public class PlayerID implements Serializable{

	private static final long serialVersionUID = 2178501434786349382L;
	private final String name;
	private final UUID uuidPlayer;
	
	/**
	 * Player ID constructor (UUID is automatically generated)
	 * @param name Name related to Player
	 * @throws NullPointerException if name is null
	 * @see java.util.UUID#randomUUID()
	 */
	public PlayerID(String name){
		Preconditions.checkNotNull(name);
		this.name = name;
		this.uuidPlayer = UUID.randomUUID();	
	}
	
	/**
	 * Getter of player name
	 * @return String name of the player
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Getter of player UUID
	 * @return UUID player UUID
	 */
	public UUID getUuidPlayer() {
		return uuidPlayer;
	}
    
	/**
	 * Override hashCode method for PlayerID class
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((uuidPlayer == null) ? 0 : uuidPlayer.hashCode());
		return result;
	}
	
	/**
	 * Override equals method for PlayerID class
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayerID other = (PlayerID) obj;
		if (!name.equals(other.name))
			return false;
		if (!uuidPlayer.equals(other.uuidPlayer))
			return false;
		return true;
	}
	
}
