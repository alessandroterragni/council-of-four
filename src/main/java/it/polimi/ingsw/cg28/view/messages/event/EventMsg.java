package it.polimi.ingsw.cg28.view.messages.event;

import java.io.Serializable;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.view.ViewHandler;

/**
 * Abstract class representing an EventMsg, a message to notify players about model changes/status and 
 * game progresses.
 * @author Mario
 *
 */
public abstract class EventMsg implements Serializable {

	private static final long serialVersionUID = 4837391747964805297L;
	private PlayerID player;
	
	/**
	 * Returns the PlayerID addressee of the message.
	 * @return PlayerID addressee of the message
	 */
	public PlayerID getPlayer() {
		return player;
	}
	
	/**
	 * Allows to set the PlayerID addressee of the message.
	 * @param player - PlayerID addressee of the message
	 */
	public void setPlayer(PlayerID player) {
		this.player = player;
	}

	/**
	 * Allows a concrete ViewHandler visitor to visit the message in order to manage it.
	 * @param viewHandler - Concrete ViewHandler visitor 
	 */
	public abstract void read(ViewHandler viewHandler);

}
