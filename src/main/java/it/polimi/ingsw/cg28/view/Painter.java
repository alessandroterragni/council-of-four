package it.polimi.ingsw.cg28.view;

import it.polimi.ingsw.cg28.view.messages.action.bazaar.BuySalableActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bazaar.SellAssistantsActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bazaar.SellPermitTilesActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bazaar.SellPoliticCardsActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bonus.ReuseCityBonusActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bonus.ReusePermitBonusActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bonus.TakePermitTileBonusActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.ChangeTileActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.ElectionActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.EmporiumKingActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.EmporiumTileActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.HireAssistantActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.OneMoreMainActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.PermitTileActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.SendAssistantActionMsg;


/**
 * Interface to implement Visitor Pattern on TurnActionMsg objects.
 * @see it.polimi.ingsw.cg28.view.messages.action.turn.TurnActionMsg#getShowChoices(Painter)
 * @author Mario
 */
public interface Painter {
	
	/**
	 * Defines a visit strategy to "paint" a ChangeTileActionMsg. 
	 * @param ChangeTileActionMsg to visit.
	 */
	public void paint(ChangeTileActionMsg changeTileActionMsg);
	
	/**
	 * Defines a visit strategy to "paint" a EmporiumKingActionMsg. 
	 * @param EmporiumKingActionMsg to visit.
	 */
	public void paint(EmporiumKingActionMsg emporiumKingActionMsg);
	
	/**
	 * Defines a visit strategy to "paint" a ElectionActionMsg. 
	 * @param ElectionActionMsg to visit.
	 */
	public void paint(ElectionActionMsg electionActionMsg);

	/**
	 * Defines a visit strategy to "paint" a EmporiumTileActionMsg. 
	 * @param EmporiumTileActionMsg to visit.
	 */	
	public void paint(EmporiumTileActionMsg emporiumTileActionMsg);
	
	/**
	 * Defines a visit strategy to "paint" a HireAssistantActionMsg. 
	 * @param HireAssistantActionMsg to visit.
	 */
	public void paint(HireAssistantActionMsg hireAssistantActionMsg);
	
	/**
	 * Defines a visit strategy to "paint" a OneMoreMainActionMsg. 
	 * @param OneMoreMainActionMsg to visit.
	 */
	public void paint(OneMoreMainActionMsg oneMoreMainActionMsg);
	
	/**
	 * Defines a visit strategy to "paint" a PermitTileActionMsg. 
	 * @param PermitTileActionMsg to visit.
	 */
	public void paint(PermitTileActionMsg permitTileActionMsg);
	
	/**
	 * Defines a visit strategy to "paint" a SendAssistantActionMsg. 
	 * @param SendAssistantActionMsg to visit.
	 */
	public void paint(SendAssistantActionMsg sendAssistantActionMsg);
	
	/**
	 * Defines a visit strategy to "paint" a ReuseCityBonusActionMsg. 
	 * @param ReuseCityBonusActionMsg to visit.
	 */
	public void paint(ReuseCityBonusActionMsg reuseCityBonusActionMsg);
    
	/**
	 * Defines a visit strategy to "paint" a ReusePermitBonusActionMsg. 
	 * @param ReusePermitBonusActionMsg to visit.
	 */
	public void paint(ReusePermitBonusActionMsg reusePermitBonusActionMsg);
	
	/**
	 * Defines a visit strategy to "paint" a BuySalableActionMsg. 
	 * @param BuySalableActionMsg to visit.
	 */
	public void paint(BuySalableActionMsg buySalableActionMsg);
	
	/**
	 * Defines a visit strategy to "paint" a SellAssistantsActionMsg. 
	 * @param SellAssistantsActionMsg to visit.
	 */
	public void paint(SellAssistantsActionMsg sellAssistantsActionMsg);
	
	/**
	 * Defines a visit strategy to "paint" a SellPermitTilesActionMsg. 
	 * @param SellPermitTilesActionMsg to visit.
	 */
	public void paint(SellPermitTilesActionMsg sellPermitTilesActionMsg);
	
	/**
	 * Defines a visit strategy to "paint" a SellPoliticCardsActionMsg. 
	 * @param SellPoliticCardsActionMsg to visit.
	 */
	public void paint(SellPoliticCardsActionMsg sellPoliticCardsActionMsg);
	
	/**
	 * Defines a visit strategy to "paint" a TakePermitTileBonusActionMsg. 
	 * @param TakePermitTileBonusActionMsg to visit.
	 */
	public void paint(TakePermitTileBonusActionMsg takePermitTileBonusActionMsg);
	
	
}
