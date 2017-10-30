package it.polimi.ingsw.cg28.controller.bazaar;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.actions.bazaar.SellAssistantsAction;
import it.polimi.ingsw.cg28.controller.actions.bazaar.SellPermitTilesAction;
import it.polimi.ingsw.cg28.controller.actions.bazaar.SellPoliticCardsAction;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;
import it.polimi.ingsw.cg28.view.messages.event.GiveMeSellActionMsg;

/**
 * Represents a bazaar buy turn for a player.
 * @author Mario
 *
 */
public class BazaarSellPlayerTurn extends BazaarPlayerTurn {
	
	private boolean[] canSell;
	
	/**
	 * Constructor of the class.
	 * @param player - The ID of the player playing this turn
	 * @param canSell boolean values of what the player can sell: 
	 * 		  canSell[0] - Assistants; canSell[1] - Politic Cards, canSell[2] - Business Permit Tiles
	 * @throws NullPointerException if player or canSell are null
	 */
	public BazaarSellPlayerTurn(PlayerID player, boolean[] canSell) {
		
		super(player);
		
		Preconditions.checkNotNull(canSell);
		this.canSell = canSell;
	}
	
	/**
	 * Returns a GiveMeSellActionMsg with current player set as turn player and set boolean values.
	 */
	@Override
	public EventMsg msgRequired() {
		return new GiveMeSellActionMsg(getPlayer(), canSell);
	}
	
	/**
	 * Returns true if player has nothing to sell, false otherwise.
	 */
	@Override
	public boolean isEnded() {
		
		if (!canSell[0] && !canSell[1] && !canSell[2])
			return true;
		
		return false;
	}
	
	/**
	 * Specific an update strategy for a SellAssistantAction. Sets the player can't sell assistants anymore
	 * in this turn.
	 * @param bazaarAction SellAssistantAction action.
	 */
	@Override
	public void update(SellAssistantsAction bazaarAction) {
		this.canSell[0] = false;
	}
	
	/**
	 * Specific an update strategy for a SellPoliticCardsAction. Sets the player can't sell politic cards anymore
	 * in this turn.
	 * @param bazaarAction SellPoliticCardsAction action.
	 */
	@Override
	public void update(SellPoliticCardsAction bazaarAction) {
		this.canSell[1] = false;
	}

	
	/**
	 * Specific an update strategy for a SellPermitTilesAction. Sets the player can't sell permit tiles anymore
	 * in this turn.
	 * @param bazaarAction SellPermitTilesAction action.
	 */
	@Override
	public void update(SellPermitTilesAction bazaarAction) {
		this.canSell[2] = false;
	}
	


}
