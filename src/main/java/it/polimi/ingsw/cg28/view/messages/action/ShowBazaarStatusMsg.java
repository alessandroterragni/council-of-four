package it.polimi.ingsw.cg28.view.messages.action;

import it.polimi.ingsw.cg28.controller.ActionMsgHandlerInterface;

/**
 * Represents an action message containing the request to show the status of the bazaar for this round.
 * @author Marco
 *
 */
public class ShowBazaarStatusMsg extends ActionMsg {

	private static final long serialVersionUID = -1348126526616561424L;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handle(ActionMsgHandlerInterface actionMsgHandler){
		actionMsgHandler.handle(this);
	}


}
