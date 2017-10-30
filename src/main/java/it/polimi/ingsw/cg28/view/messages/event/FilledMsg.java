package it.polimi.ingsw.cg28.view.messages.event;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.view.ViewHandler;
import it.polimi.ingsw.cg28.view.messages.action.ActionMsg;

/**
 * Represented a particular event message containing the execution of the filling of an action message.
 * @author Marco
 *
 */
public class FilledMsg extends EventMsg {
	
	private static final long serialVersionUID = 2196173459761913227L;
	private ActionMsg actionMsg;
	
	/**
	 * The constructor of the class, creates a new FilledMsg.
	 * @param actionMsg - The related ActionMsg
	 * @throws NullPointerException if the input action message is null	
	 */
	public FilledMsg(ActionMsg actionMsg){
		
		Preconditions.checkNotNull(actionMsg, "The related action message can't be null.");
		
		this.actionMsg = actionMsg;
	}

	/**
	 * Fetches the action message contained in this event message.
	 * @return The ActionMsg associated with this event
	 */
	public ActionMsg getActionMsg() {
		return actionMsg;
	}

	/**
	 * Sets the associated action message to the specified one.
	 * @param actionMsg - The ActionMsg to be set
	 * @throws NullPointerException if the message set is null
	 */
	public void setActionMsg(ActionMsg actionMsg) {
		Preconditions.checkNotNull(actionMsg, "Can't set a null action message");
		
		this.actionMsg = actionMsg;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void read(ViewHandler viewHandler) {
		
		viewHandler.handle(this);
		
	}


	
	
	

}
