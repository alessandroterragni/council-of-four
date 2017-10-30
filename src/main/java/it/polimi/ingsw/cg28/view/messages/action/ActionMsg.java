package it.polimi.ingsw.cg28.view.messages.action;

import java.io.Serializable;


import it.polimi.ingsw.cg28.controller.ActionMsgHandlerInterface;
import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;
import it.polimi.ingsw.cg28.view.messages.event.SimpleEventMsg;

/**
 * Abstract class representing an ActionMsg: a request that needs to be processed by
 * and eventually asks to modify model status.
 * @author Mario
 *
 */
public abstract class ActionMsg implements Serializable {

	private static final long serialVersionUID = 2411987690232983414L;
	private PlayerID playerID;
	
	/**
	 * Allows a concrete ActionMsgHandlerInterface visitor to visit the message.
	 * @param actionMsgHandler - Concrete ActionMsgHandlerInterface visitor 
	 */
	public void handle(ActionMsgHandlerInterface actionMsgHandler){
		actionMsgHandler.handle(this);
	}
	
	/**
	 * Specifies and returns the related EventMsg.
	 * @return EventMsg related to the ActionMsg
	 */
	public EventMsg relatedEventMsg(){
		
		return new SimpleEventMsg("");
		
	}
	
	/**
	 * Getter of playerID making the request.
	 * @return playerID - related to player making the request
	 */
	public PlayerID getPlayerID(){
		return this.playerID;
	}
	
	/**
	 * Setter of playerID.
	 * @param playerID - related to player making the request
	 */
	public void setPlayerID(PlayerID playerID) {
		this.playerID = playerID;
	}
	


}
