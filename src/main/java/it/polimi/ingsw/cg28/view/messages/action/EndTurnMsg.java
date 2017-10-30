package it.polimi.ingsw.cg28.view.messages.action;

import it.polimi.ingsw.cg28.controller.ActionMsgHandlerInterface;
import it.polimi.ingsw.cg28.controller.PlayerID;

/**
 * Represents an action message containing the request to end the current player's turn.
 * @author Marco
 *
 */
public class EndTurnMsg extends ActionMsg{
	
	private static final long serialVersionUID = -5970605321019645797L;
	
	/**
	 * The constructor of the class, creates a new EndTurnMsg.
	 * @param player - The player related to this message
	 */
	public EndTurnMsg(PlayerID player) {
		
		setPlayerID(player);
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handle(ActionMsgHandlerInterface actionMsgHandler){
		actionMsgHandler.handle(this);
	}
	
}
