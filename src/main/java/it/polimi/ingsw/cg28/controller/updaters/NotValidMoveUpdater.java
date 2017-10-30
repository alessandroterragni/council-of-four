package it.polimi.ingsw.cg28.controller.updaters;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.ActionMsgHandler;
import it.polimi.ingsw.cg28.view.messages.action.QuitActionMsg;
import it.polimi.ingsw.cg28.view.messages.event.SimpleEventMsg;

/**
 * Updater to handle events (concrete Updater interface strategy implementation) related to an exception on Action act.
 * @author Mario
 *
 */
public class NotValidMoveUpdater implements Updater {
	
	private String string;
	
	/**
	 * Class Constructor, takes a string to explain why the updater has been generated.
	 * @param string
	 * @throws NullPointerException if parameter is null
	 */
	public NotValidMoveUpdater(String string){
		
		Preconditions.checkNotNull(string);
		this.string = string;
		
	}
	
	/**
	 * Handles events related to an exception on Action act. Send to the player a SimpleEventMsg
	 * with error message (why the exception is thrown). Quit the precedent action request and
	 * send again the request for the current turn.
	 */
	@Override
	public void update(ActionMsgHandler actionMsgHandler) {
		
		String toSend = "Not Valid Action!";
		SimpleEventMsg simpleEventMsg;
		if("null".equals(string))
			simpleEventMsg = new SimpleEventMsg(toSend);
		else
			simpleEventMsg = new SimpleEventMsg(toSend + " Error message: " + string);
		simpleEventMsg.setPlayer(actionMsgHandler.getGameController().getCurrentPlayer());
		actionMsgHandler.notify(simpleEventMsg);
		
		actionMsgHandler.handle(new QuitActionMsg());
		
	}

}
