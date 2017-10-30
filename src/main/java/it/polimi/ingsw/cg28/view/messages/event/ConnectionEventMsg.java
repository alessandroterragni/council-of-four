package it.polimi.ingsw.cg28.view.messages.event;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.view.ViewHandler;

/**
 * An event message sent by the server indicating a successful connection to said server.
 * @author Alessandro, Marco, Mario
 *
 */
public class ConnectionEventMsg extends EventMsg {

	private static final long serialVersionUID = 1769498348999452385L;
	private PlayerID player;

	/**
	 * The constructor of the class, creates a new message.
	 * @param connect - The ID of the player that was connected to the server
	 * @throws NullPointerException if the input ID is null
	 */
	public ConnectionEventMsg(PlayerID connect) {
		Preconditions.checkNotNull(connect, "The associated player can't be null.");
		this.player = connect;
	}

	/**
	 * Fetches the connected player's ID.
	 * @return the player's ID
	 */
	public PlayerID getPlayerID() {
		return player;
	}

	/**
	 * Connection event message doesn't need to be read by ViewHandler
	 */
	@Override
	public void read(ViewHandler viewHandler) {
		return;	
	}

}