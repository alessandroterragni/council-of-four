package it.polimi.ingsw.cg28.view.messages.action;
import it.polimi.ingsw.cg28.controller.ActionMsgHandlerInterface;

/**
 * Represents the action message that is sent to the server to start the actual match.
 * @author Marco
 *
 */
public class StartMsg extends ActionMsg{

	private static final long serialVersionUID = 2739320517823844507L;
	private boolean bazaar;
	
	/**
	 * The constructor of the class, creates a new StartMsg.
	 */
	public StartMsg(){
		bazaar = true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handle(ActionMsgHandlerInterface actionMsgHandler){
		actionMsgHandler.handle(this);
	}
	
	/**
	 * Indicates if the current match includes the bazaar rule or not.
	 * @return The boolean value indicating the use of the bazaar rule or less
	 */
	public boolean yesBazaar() {
		return bazaar;
	}

	/**
	 * Sets the usage of the bazaar to <code>true</code> of <code>false</code> based on
	 * the given parameter.
	 * @param bazaar The boolean value to be set
	 */
	public void setBazaar(boolean bazaar) {
		this.bazaar = bazaar;
	}

}
