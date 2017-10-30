package it.polimi.ingsw.cg28.view.messages.event;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.view.ViewHandler;

/**
 * Represents an event message containing the execution of the request for allowance to buy products
 * from the bazaar.
 * @author Marco
 *
 */
public class GiveMeBuyActionMsg extends EventMsg {
	
	private static final long serialVersionUID = -9000148345713792664L;

	/**
	 * The constructor of the class, creates a new GiveMeBuyActionMsg.
	 * @param currentPlayer - The player associated with this event
	 * @throws NullPointerException if the specified player is null
	 */
	public GiveMeBuyActionMsg(PlayerID currentPlayer){
		
		Preconditions.checkNotNull(currentPlayer, "The associated player can't be null");
		
		setPlayer(currentPlayer);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void read(ViewHandler viewHandler) {
		viewHandler.handle(this);
	}

}
