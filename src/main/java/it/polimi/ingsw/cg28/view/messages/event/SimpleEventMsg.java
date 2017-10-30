package it.polimi.ingsw.cg28.view.messages.event;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.view.ViewHandler;

/**
 * A standard event message, only contains a string message.
 * @author Marco
 *
 */
public class SimpleEventMsg extends EventMsg {

	private static final long serialVersionUID = 498094227431386649L;
	private String string;
	
	/**
	 * The constructor of the class, creates a new SimpleEventMsg.
	 * @param string - The string message associated with this event
	 * @throws NullPointerException if the specified string is null
	 */
	public SimpleEventMsg(String string){
		
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
	 * Fetches the related string message.
	 * @return The string message associated with this event
	 */
	public String getString() {
		return string;
	}

	

}
