package it.polimi.ingsw.cg28.view.messages.event;


import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.view.ViewHandler;

/**
 * Represents an event message containing the actual string to be sent as a chat message.
 * @author Marco
 *
 */
public class ChatEventMsg extends EventMsg {

	private static final long serialVersionUID = -6183016575200943403L;
	private String string;

	/**
	 * The constructor of the class, creates a new ChatEventMsg.
	 * @param string - The string containing the actual chat message
	 * @throws NullPointerException if the given string is null
	 */
	public ChatEventMsg(String string) {
		Preconditions.checkNotNull(string, "The associated message can't be null.");
		this.string = string;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void read(ViewHandler viewHandler) {
		viewHandler.handle(this);
	}

	/**
	 * Fetches this message's content.
	 * @return The string representing this chat message's content
	 */
	public String getString() {	
		return string;	
	}

}
