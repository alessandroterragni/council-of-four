package it.polimi.ingsw.cg28.view.messages.action;


import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.ActionMsgHandlerInterface;
import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.view.messages.event.ChatEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;

/**
 * An action message representing a chat message.
 * @author Alessandro, Marco, Mario
 *
 */
public class ChatActionMsg extends ActionMsg {

	private static final long serialVersionUID = 3974222890064247481L;
	private String string;
	
	/**
	 * The constructor of the class, creates a new chat message.
	 * @param string - The content of the chat message
	 */
	public ChatActionMsg(String string) {
		
		Preconditions.checkNotNull(string, "The related message can't be null.");
			
		this.string = string;
		
	}
	
	/**
	 * Sets the message player's ID to the specified one, then replaces the content of this chat message
	 * by formatting it to fit this form:<br>
	 * [Player] player_name: message
	 */
	@Override
	public void setPlayerID(PlayerID player){
		
		Preconditions.checkNotNull(player, "The related player ID can't be null.");
		super.setPlayerID(player);
		
		StringBuilder s = new StringBuilder();
		s.append("[Player] " + player.getName() + ": ");
		s.append(string);
		string = s.toString();
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EventMsg relatedEventMsg() {
		return new ChatEventMsg(string);
	}
	
	/**
	 * Fetches the message content.
	 * @return the message content in string form
	 */
	public String getString() {
		return string;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handle(ActionMsgHandlerInterface actionMsgHandler) {
		actionMsgHandler.handle(this);
	}
}
