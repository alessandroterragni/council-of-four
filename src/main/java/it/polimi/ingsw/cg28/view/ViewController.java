package it.polimi.ingsw.cg28.view;

import it.polimi.ingsw.cg28.view.messages.event.EventMsg;

/**
 * View controller manages EventMsg received dispatching it to the related
 * {@link ViewHandler}.
 * @author Mario
 *
 */
public class ViewController{
	
	private ViewHandler handler;
	
	/**
	 * Constructor of the class. Allows to set the chosen viewHandler.
	 * @param handler - chosen viewHandler
	 */
	public ViewController(ViewHandler handler) {
		this.handler = handler;
	}

	/**
	 * Lets the handler "read" (Visitor pattern) the eventMsg received
	 * @param change
	 */
	public void readMsg(EventMsg change) {	
		
		change.read(handler);
		
	}
}
