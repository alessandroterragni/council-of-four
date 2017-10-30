package it.polimi.ingsw.cg28.view.messages.event;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.view.ViewHandler;

/**
 * Represents an event message containing the execution of the request for allowance to sell products
 * from the bazaar.
 * @author Marco
 *
 */
public class GiveMeSellActionMsg extends EventMsg {

	private static final long serialVersionUID = 5517594560250697050L;
	private boolean[] canSell;
	
	/**
	 * The constructor of the class, creates a new GiveMeSellActionMsg.
	 * @param currentPlayer - The associated player.
	 * @param canSell - An array of boolean indicating the salability of the player's assistants, cards
	 * and tile
	 * @throws NullPointerException if any parameter is null
	 */
	public GiveMeSellActionMsg(PlayerID currentPlayer, boolean[] canSell){
		
		Preconditions.checkNotNull(currentPlayer, "The associated player can't be null.");
		Preconditions.checkNotNull(canSell, "The boolean array indicating salability can't be null.");
		
		setPlayer(currentPlayer);
		this.canSell = canSell;
	}

	/**
	 * Indicates whether the player can sell assistants or not.
	 * @return The boolean value indicating assistants salability
	 */
	public boolean canSellAssistant() {
		return canSell[0];
	}
	
	/**
	 * Indicates whether the player can sell politic cards or not.
	 * @return The boolean value indicating politic cards salability
	 */
	public boolean canSellPoliticCards() {
		return canSell[1];
	}
	
	/**
	 * Indicates whether the player can sell permit tiles or not.
	 * @return The boolean value indicating permit tiles salability
	 */
	public boolean canSellPermitTiles() {
		return canSell[2];
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void read(ViewHandler viewHandler) {
		viewHandler.handle(this);
	}
	
}
