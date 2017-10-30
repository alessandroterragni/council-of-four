package it.polimi.ingsw.cg28.view.messages.action;

import it.polimi.ingsw.cg28.controller.PlayerID;

/**
 * An action message indicating a subscription request.
 * @author Alessandro, Marco, Mario
 *
 */
public class SubscribeActionMsg extends ActionMsg{

	private static final long serialVersionUID = -5783939670443942528L;

	/**
	 * The constructor of the class, creates a new message.
	 * @param player - The requesting player's ID 
	 */
	public SubscribeActionMsg(PlayerID player) {
		
		setPlayerID(player);
	}
	
}
