package it.polimi.ingsw.cg28.view.messages.action.turn;

import it.polimi.ingsw.cg28.controller.PlayerID;

/**
 * Represents an ActionMsg related to a MainAction.
 * @author Alessandro
 * @see TurnActionMsg
 */
public abstract class MainActionMsg extends TurnActionMsg {
	
	private static final long serialVersionUID = 6190246650657755176L;
	
	/**
	 * Constructor of the class. Sets need code to true.
	 * @param player - PlayerID making the request.
	 * @throws NullPointerException if player parameter is null.
	 */
	public MainActionMsg(PlayerID player) {
		super(player);
	}

}
