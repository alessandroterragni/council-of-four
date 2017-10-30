package it.polimi.ingsw.cg28.view.messages.action;

import it.polimi.ingsw.cg28.controller.ActionMsgHandlerInterface;
import it.polimi.ingsw.cg28.view.messages.event.EndSellTurnEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;

/**
 * Represents an action message containing the request to end the sell-phase of a player during the
 * execution of the bazaar turns in the game.
 * @author Marco
 *
 */
public class EndSellTurnMsg extends ActionMsg {

	private static final long serialVersionUID = 8823271168516608181L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EventMsg relatedEventMsg() {
		return new EndSellTurnEventMsg(getPlayerID(), "SellTurn " + getPlayerID().getName() + " is ended!");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handle(ActionMsgHandlerInterface actionMsgHandler){
		actionMsgHandler.handle(this);
	}

}
