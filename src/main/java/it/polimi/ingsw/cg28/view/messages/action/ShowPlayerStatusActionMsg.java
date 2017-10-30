package it.polimi.ingsw.cg28.view.messages.action;

import it.polimi.ingsw.cg28.controller.ActionMsgHandlerInterface;

/**
 * Represents an action message containing the request to show the current player's status and
 * possessions.
 * @author Marco
 *
 */
public class ShowPlayerStatusActionMsg extends ActionMsg {

	private static final long serialVersionUID = 7466717414923466887L;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handle(ActionMsgHandlerInterface actionMsgHandler) {
		actionMsgHandler.handle(this);
	}

}
