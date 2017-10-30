package it.polimi.ingsw.cg28.view.messages.action;

import it.polimi.ingsw.cg28.controller.ActionMsgHandlerInterface;

/**
 * Represents an action message containing the request to roll-back the decision to perform an action
 * during a player's turn.
 * @author Marco
 *
 */
public class QuitActionMsg extends ActionMsg {
	
	private static final long serialVersionUID = 696652191999612044L;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handle(ActionMsgHandlerInterface actionMsgHandler){
		actionMsgHandler.handle(this);
	}

}
