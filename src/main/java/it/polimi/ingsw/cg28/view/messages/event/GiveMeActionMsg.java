/**
 * 
 */
package it.polimi.ingsw.cg28.view.messages.event;


import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.view.ViewHandler;

/**
 * Represents an event message containing the execution of the request for the allowance of performing
 * actions during a turn.
 * @author Marco
 *
 */
public class GiveMeActionMsg extends EventMsg {

	private static final long serialVersionUID = 6399241131370840848L;
	private boolean[] actionAllowed; 
	
	/**
	 * The constructor of the class, creates a new GiveMeActionMsg.
	 * @param currentPlayer - The player associated with this request event
	 * @param actionAllowed - The array indicating the permissions to perform the turn actions
	 * @throws NullPointerException if any parameter is null
	 */
	public GiveMeActionMsg(PlayerID currentPlayer, boolean[] actionAllowed) {
		 
		 Preconditions.checkNotNull(currentPlayer, "The associated player can't be null");
		 Preconditions.checkNotNull(actionAllowed, "The boolean array indicating action allowance can't be null.");
		 
		 setPlayer(currentPlayer);
		 this.actionAllowed=actionAllowed;
	}

	/**
	 * Indicates whether the player can perform a main action or not.
	 * @return The boolean value indicating the availability of a main action
	 */
	public boolean canDoMainAction() {
		return actionAllowed[0];
	}
	
	/**
	 * Indicates whether the player can perform a quick action or not.
	 * @return The boolean value indicating the availability of a quick action
	 */
	public boolean canDoQuickAction() {
		return actionAllowed[1];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void read(ViewHandler viewHandler) {
		
		viewHandler.handle(this);
		
	}
	
}
