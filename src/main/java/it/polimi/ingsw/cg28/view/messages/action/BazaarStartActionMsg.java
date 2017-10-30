package it.polimi.ingsw.cg28.view.messages.action;


import it.polimi.ingsw.cg28.controller.ActionMsgHandlerInterface;

/**
 * Represent an action message containing a request for initiating the bazaar phase of the game.
 * @author Marco
 *
 */
public class BazaarStartActionMsg extends ActionMsg {
	
	private static final long serialVersionUID = 9016894029428265949L;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handle(ActionMsgHandlerInterface actionMsgHandler){
		actionMsgHandler.handle(this);
	}

}
