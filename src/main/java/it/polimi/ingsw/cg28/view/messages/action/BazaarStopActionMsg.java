package it.polimi.ingsw.cg28.view.messages.action;

import it.polimi.ingsw.cg28.controller.ActionMsgHandlerInterface;

/**
 * Represents an action message containing a request to end the bazaar phase of the game.
 * @author Marco
 *
 */
public class BazaarStopActionMsg extends ActionMsg {
	
	private static final long serialVersionUID = -779420884721631141L;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handle(ActionMsgHandlerInterface actionMsgHandler){
		actionMsgHandler.handle(this);
	}

}
