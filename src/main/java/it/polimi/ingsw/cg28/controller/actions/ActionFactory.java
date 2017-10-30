/**
 * 
 */
package it.polimi.ingsw.cg28.controller.actions;

import it.polimi.ingsw.cg28.exception.InvalidActionException;
import it.polimi.ingsw.cg28.model.BazaarModel;
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
 * Interface to implement Visitor Pattern on ToTranslate objects.
 * Provides the methods to translate messages into actions
 * @author Alessandro, Mario
 *
 */
public interface ActionFactory {
	
	/**
	 * Method to set to factory a related BazaarModel in order to build bazaar related actions.
	 * @param bazaarModel Model of the bazaar to build actions.
	 */
	public void setBazaarModel(BazaarModel bazaarModel);

	/**
	 * Defines a visit "strategy" for ChangeTileActionMsg in order to build the related Action
	 * @param actionMsg ChangeTileActionMsg to translate
	 * @return Action related to message
	 * @throws InvalidActionException if the action can't be build
	 */
	public Action build(ChangeTileActionMsg actionMsg) throws InvalidActionException;
	
	/**
	 * Defines a visit "strategy" for ElectionActionMsg in order to build the related Action
	 * @param actionMsg ElectionActionMsg to translate
	 * @return Action related to message
	 * @throws InvalidActionException if the action can't be build
	 */
	public Action build(ElectionActionMsg actionMsg) throws InvalidActionException;
	
	/**
	 * Defines a visit "strategy" for EmporiumKingActionMsg in order to build the related Action
	 * @param actionMsg EmporiumKingActionMsg to translate
	 * @return Action related to message
	 * @throws InvalidActionException if the action can't be build
	 */
	public Action build(EmporiumKingActionMsg actionMsg) throws InvalidActionException;
	
	/**
	 * Defines a visit "strategy" for EmporiumTileActionMsg in order to build the related Action
	 * @param actionMsg EmporiumTileActionMsg to translate
	 * @return Action related to message
	 * @throws InvalidActionException if the action can't be build
	 */
	public Action build(EmporiumTileActionMsg actionMsg) throws InvalidActionException;
	
	/**
	 * Defines a visit "strategy" for HireAssistantActionMsg in order to build the related Action
	 * @param actionMsg HireAssistantActionMsg to translate
	 * @return Action related to message
	 * @throws InvalidActionException if the action can't be build
	 */
	public Action build(HireAssistantActionMsg actionMsg) throws InvalidActionException;
	
	/**
	 * Defines a visit "strategy" for OneMoreMainActionMsg in order to build the related Action
	 * @param actionMsg OneMoreMainActionMsg to translate
	 * @return Action related to message
	 * @throws InvalidActionException if the action can't be build
	 */
	public Action build(OneMoreMainActionMsg actionMsg) throws InvalidActionException;
	
	/**
	 * Defines a visit "strategy" for PermitTileActionMsg in order to build the related Action
	 * @param actionMsg PermitTileActionMsg to translate
	 * @return Action related to message
	 * @throws InvalidActionException if the action can't be build
	 */
	public Action build(PermitTileActionMsg actionMsg) throws InvalidActionException;
	
	/**
	 * Defines a visit "strategy" for SendAssistantActionMsg in order to build the related Action
	 * @param actionMsg SendAssistantActionMsg to translate
	 * @return Action related to message
	 * @throws InvalidActionException if the action can't be build
	 */
	public Action build(SendAssistantActionMsg actionMsg) throws InvalidActionException;
	
	/**
	 * Defines a visit "strategy" for ReuseCityBonusActionMsg in order to build the related Action
	 * @param actionMsg ReuseCityBonusActionMsg to translate
	 * @return Action related to message
	 * @throws InvalidActionException if the action can't be build
	 */
	public Action build(ReuseCityBonusActionMsg actionMsg) throws InvalidActionException;
	
	/**
	 * Defines a visit "strategy" for ReusePermitBonusActionMsg in order to build the related Action
	 * @param actionMsg ReusePermitBonusActionMsg to translate
	 * @return Action related to message
	 * @throws InvalidActionException if the action can't be build
	 */
	public Action build(ReusePermitBonusActionMsg actionMsg) throws InvalidActionException;
	
	/**
	 * Defines a visit "strategy" for TakePermitTileBonusActionMsg in order to build the related Action
	 * @param actionMsg TakePermitTileBonusActionMsg to translate
	 * @return Action related to message
	 * @throws InvalidActionException if the action can't be build
	 */
	public Action build(TakePermitTileBonusActionMsg actionMsg) throws InvalidActionException;
	
	/**
	 * Defines a visit "strategy" for SellPoliticCardsActionMsg in order to build the related Action
	 * @param actionMsg SellPoliticCardsActionMsg to translate
	 * @return Action related to message
	 * @throws InvalidActionException if the action can't be build
	 */
	public Action build(SellPoliticCardsActionMsg sellPoliticCardsActionMsg) throws InvalidActionException;
	
	/**
	 * Defines a visit "strategy" for SellAssistantsActionMsg in order to build the related Action
	 * @param actionMsg SellAssistantsActionMsg to translate
	 * @return Action related to message
	 * @throws InvalidActionException if the action can't be build
	 */
	public Action build(SellAssistantsActionMsg sellAssistantsActionMsg) throws InvalidActionException;
	
	/**
	 * Defines a visit "strategy" for SellPermitTilesActionMsg in order to build the related Action
	 * @param actionMsg SellPermitTilesActionMsg to translate
	 * @return Action related to message
	 * @throws InvalidActionException if the action can't be build
	 */
	public Action build(SellPermitTilesActionMsg sellPermitTilesActionMsg) throws InvalidActionException;
	
	/**
	 * Defines a visit "strategy" for BuySalableActionMsg in order to build the related Action
	 * @param actionMsg BuySalableActionMsg to translate
	 * @return Action related to message
	 * @throws InvalidActionException if the action can't be build
	 */
	public Action build(BuySalableActionMsg buySalableActionMsg) throws InvalidActionException;;
	
}
