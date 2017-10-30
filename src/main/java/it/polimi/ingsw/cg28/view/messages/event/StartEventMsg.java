package it.polimi.ingsw.cg28.view.messages.event;

import it.polimi.ingsw.cg28.view.ViewHandler;

/**
 * Represents an event message containing the execution of the start of the current match.
 * @author Marco
 *
 */
public class StartEventMsg extends EventMsg {

	private static final long serialVersionUID = -2254733827487730234L;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void read(ViewHandler viewHandler) {
		
		viewHandler.handle(this);
		
	}

}
