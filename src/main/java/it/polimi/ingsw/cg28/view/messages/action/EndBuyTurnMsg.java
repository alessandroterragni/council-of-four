package it.polimi.ingsw.cg28.view.messages.action;

import it.polimi.ingsw.cg28.controller.ActionMsgHandlerInterface;
import it.polimi.ingsw.cg28.view.messages.event.EndBuyTurnEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;

/**
 * Represents an action message containing the request to end the buy-phase of a player during the
 * execution of the bazaar turns in the game.
 * @author Marco
 *
 */
public class EndBuyTurnMsg extends ActionMsg {

	private static final long serialVersionUID = 6019027024747353493L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EventMsg relatedEventMsg() {
		return new EndBuyTurnEventMsg(getPlayerID(), "BuyTurn " + getPlayerID().getName() + " is ended!");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handle(ActionMsgHandlerInterface actionMsgHandler){
		actionMsgHandler.handle(this);
	}

}
