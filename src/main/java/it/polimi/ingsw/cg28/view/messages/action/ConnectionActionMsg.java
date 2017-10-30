package it.polimi.ingsw.cg28.view.messages.action;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.PlayerID;

/**
 * An action message indicating a connection request.
 * @author Alessandro, Marco, Mario
 *
 */
public class ConnectionActionMsg extends ActionMsg {

	private static final long serialVersionUID = -6356075200366136284L;
	private String playerName;

	/**
	 * The constructor of the class, creates a new connection message.
	 * @param player - The ID of the player that requests for a connection.
	 * @param playerName - The player's name
	 */
	public ConnectionActionMsg(PlayerID player, String playerName) {
		setPlayerID(player);
		
		Preconditions.checkNotNull(playerName, "The related player must have a non-null name.");
		this.playerName = playerName;
	}
	
	/**
	 * Fetches the string containing the player's name.
	 * @return the player's name
	 */
	public String getPlayerName() {
		return playerName;
	}

}