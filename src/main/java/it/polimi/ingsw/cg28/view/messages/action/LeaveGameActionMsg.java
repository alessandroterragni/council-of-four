package it.polimi.ingsw.cg28.view.messages.action;

import it.polimi.ingsw.cg28.controller.ActionMsgHandlerInterface;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;
import it.polimi.ingsw.cg28.view.messages.event.SimpleEventMsg;

/**
 * An action message representing the request to leave the game.
 * @author Alessandro, Marco, Mario
 *
 */
public class LeaveGameActionMsg extends ActionMsg {

	private static final long serialVersionUID = 4184164149845515487L;
	private final String string;
	
	/**
	 * The constructor of the class, creates a new LeaveGameActionMsg.
	 * @param string - The message related to this request
	 */
	public LeaveGameActionMsg(String string) {
		
		this.string = string;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handle(ActionMsgHandlerInterface actionMsgHandler){
		
			actionMsgHandler.handle(this);
	}

	/**
	 * {@inheritDoc}  
	 */
	@Override
	public EventMsg relatedEventMsg() {
		return new SimpleEventMsg("[Player " + getPlayerID().getName() +
				" ] left the game\nMessage:" + string +"\n\n");
	}

}
