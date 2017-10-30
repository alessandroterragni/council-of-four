package it.polimi.ingsw.cg28.controller.bazaar;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;
import it.polimi.ingsw.cg28.view.messages.event.GiveMeBuyActionMsg;

/**
 * Represents a bazaar buy turn for a player.
 * @author Mario
 *
 */
public class BazaarBuyPlayerTurn extends BazaarPlayerTurn {
	
	/**
	 * Constructor of the class.
	 * @param player - The ID of the player playing this turn
	 * @throws NullPointerException if player is null
	 */
	public BazaarBuyPlayerTurn(PlayerID player) {
		super(player);
	}
	
	/**
	 * Returns a GiveMeBuyActionMsg with current player set as turn player.
	 */
	@Override
	public EventMsg msgRequired() {
		return new GiveMeBuyActionMsg(getPlayer());
	}
	
	/**
	 * Buy turn ends only by player request. Returns false.
	 */
	@Override
	public boolean isEnded() {
		return false;
	}
	
}
