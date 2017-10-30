package it.polimi.ingsw.cg28.view.messages.event;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.view.ViewHandler;
import it.polimi.ingsw.cg28.view.messages.action.EndGameMsg;

/**
 * Represents an event message containing the execution of the ending of the current match.
 * @author Marco
 *
 */
public class EndGameEventMsg extends EventMsg {

	private static final long serialVersionUID = -1717742566208121873L;
	private EndGameMsg actionMsg;

	/**
	 * The constructor of the class, creates a new EndGameEventMsg.
	 * @param actionMsg - The corresponding end game action message
	 * @throws NullPointerException if the input action message is null
	 */
	public EndGameEventMsg(EndGameMsg actionMsg) {
		Preconditions.checkNotNull(actionMsg, "The related action message can't be null");
		this.actionMsg = actionMsg;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void read(ViewHandler viewHandler) {
		viewHandler.handle(this);
	}
	
	/**
	 * Fetches the related action message.
	 * @return The EndGameMsg related to this event message
	 */
	public EndGameMsg getEndGameMsg() {
		return actionMsg;
	}

}
