package it.polimi.ingsw.cg28.connections.server;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.view.ViewHandler;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;

/**
 * Represent a message containing a communication about an invalid request.
 * @author Alessandro, Marco, Mario
 *
 */
public class InvalidRequestMsg extends EventMsg {

	private static final long serialVersionUID = -819108682292189606L;
	private String string;

	/**
	 * The constructor of the class, creates a new InvalidRequestMsg.
	 * @param string - The content of the message
	 * @throws NullPointerException if string parameter is null
	 */
	public InvalidRequestMsg(String string) {
		Preconditions.checkNotNull(string);
		this.string = string;
	}

	/**
	 * Fetches the content of the message object.
	 * @return the content of the message
	 */
	public String getString() {
		return string;
	}

	/**
	 * Message not read by VieHandler
	 */
	@Override
	public void read(ViewHandler viewHandler) {
		return;
	}

}
