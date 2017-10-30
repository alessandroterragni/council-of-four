package it.polimi.ingsw.cg28.view.messages.event;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.view.ViewHandler;

/**
 * Represents an event message containing the execution of the closure of the sell-phase of the
 * bazaar turn.
 * @author Marco
 *
 */
public class EndSellTurnEventMsg extends EventMsg {

	private static final long serialVersionUID = 1557529020457115009L;
	private String string;
	
	/**
	 * The constructor of the class, creates a new EndSellTurnEventMsg.
	 * @param currentPlayer - The player referenced by this event
	 * @param string - The string message associated with this event
	 * @throws NullPointerException if any parameter is null
	 */
	public EndSellTurnEventMsg(PlayerID currentPlayer, String string){
		Preconditions.checkNotNull(currentPlayer, "The associated player can't be null.");
		Preconditions.checkNotNull(string, "The associated string message can't be null.");
		
		setPlayer(currentPlayer);
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
