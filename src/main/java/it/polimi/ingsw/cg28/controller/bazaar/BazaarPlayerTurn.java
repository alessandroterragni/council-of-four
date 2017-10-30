package it.polimi.ingsw.cg28.controller.bazaar;

import com.google.common.base.Preconditions;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.actions.bazaar.SellAssistantsAction;
import it.polimi.ingsw.cg28.controller.actions.bazaar.SellPermitTilesAction;
import it.polimi.ingsw.cg28.controller.actions.bazaar.SellPoliticCardsAction;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;

/**
 * Abstract class representing a bazaar turn.
 * @author Mario
 *
 */
public abstract class BazaarPlayerTurn {
	
	private final PlayerID player;
	
	/**
	 * The abstract constructor of the class.
	 * @param player - The ID of the player playing this turn
	 * @throws NullPointerException if player is null
	 */
	public BazaarPlayerTurn(PlayerID player){
		
		Preconditions.checkNotNull(player);
		
		this.player = player;
	}
	
	/**
	 * Abstract method, defines the message required from the specific kind of turn.
	 * @return
	 */
	public abstract EventMsg msgRequired();
	public abstract boolean isEnded();
	
	/**
	 * Returns the ID of the player playing this turn
	 * @return
	 */
	public PlayerID getPlayer(){
		return this.player;
	}
	
	/**
	 * Specifics an update strategy for a SellAssistantAction. Default do nothing.
	 * @param bazaarAction SellAssistantAction action.
	 */
	public void update(SellAssistantsAction bazaarAction){
		return;
	}
	
	/**
	 * Specifics an update strategy for a SellPermitTilesAction. Default do nothing.
	 * @param bazaarAction SellPermitTilesAction action.
	 */
	public void update(SellPermitTilesAction bazaarAction){
		return;
	}
	
	/**
	 * Specifics an update strategy for a SellPoliticCardsAction. Default do nothing.
	 * @param bazaarAction SellPoliticCardsAction action.
	 */
	public void update(SellPoliticCardsAction bazaarAction){
		return;
	}

}
