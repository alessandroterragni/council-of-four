package it.polimi.ingsw.cg28.view.messages.action.turn;

import it.polimi.ingsw.cg28.controller.PlayerID;

/**
 * Represents an ActionMsg related to a QuickAction.
 * @author Alessandro
 * @see TurnActionMsg
 */
public abstract class QuickActionMsg extends TurnActionMsg {

	private static final long serialVersionUID = 9208435554385913611L;
	
	/**
	 * Constructor of the class. Sets need code to true.
	 * @param player - PlayerID making the request.
	 * @throws NullPointerException if player parameter is null.
	 */
	public QuickActionMsg(PlayerID player) {	
		super(player);
	}

}
