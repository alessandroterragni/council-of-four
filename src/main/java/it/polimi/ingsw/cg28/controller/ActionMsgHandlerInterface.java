package it.polimi.ingsw.cg28.controller;

import it.polimi.ingsw.cg28.view.messages.action.ActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.BazaarStartActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.BazaarStopActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.ChatActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.EndBuyTurnMsg;
import it.polimi.ingsw.cg28.view.messages.action.EndGameMsg;
import it.polimi.ingsw.cg28.view.messages.action.EndSellTurnMsg;
import it.polimi.ingsw.cg28.view.messages.action.EndTurnMsg;
import it.polimi.ingsw.cg28.view.messages.action.LeaveGameActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.QuitActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.ShowBazaarStatusMsg;
import it.polimi.ingsw.cg28.view.messages.action.ShowPlayerStatusActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.StartMsg;
import it.polimi.ingsw.cg28.view.messages.action.bazaar.BazaarActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bazaar.BuySalableActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.TurnActionMsg;

/**
 * Interface to implement Visitor Pattern on ActionMsg objects
 * @author Mario
 *
 */
public interface ActionMsgHandlerInterface {
	
	/**
	 * Defines a visit "strategy" for a generic ActionMsg.
	 * @param ActionMsg to visit
	 */
	public void handle(ActionMsg actionMsg);
	
	/**
	 * Defines a visit "strategy" for a StartMsg.
	 * @param StartMsg to visit
	 */
	public void handle(StartMsg actionMsg);
	
	/**
	 * Defines a visit "strategy" for a generic TurnActionMsg.
	 * @param TurnActionMsg to visit
	 */
	public void handle(TurnActionMsg actionMsg);
	
	/**
	 * Defines a visit "strategy" for a BuySalableActionMsg.
	 * @param BuySalableActionMsg to visit
	 */
	public void handle(BuySalableActionMsg actionMsg);
	
	/**
	 * Defines a visit "strategy" for a QuitActionMsg.
	 * @param QuitActionMsg to visit
	 */
	public void handle(QuitActionMsg actionMsg);
	
	/**
	 * Defines a visit "strategy" for a EndTurnMsg.
	 * @param EndTurnMsg to visit
	 */
	public void handle(EndTurnMsg actionMsg);
	
	/**
	 * Defines a visit "strategy" for a EndSellTurnMsg.
	 * @param EndSellTurnMsg to visit
	 */
	public void handle(EndSellTurnMsg actionMsg);
	
	/**
	 * Defines a visit "strategy" for a EndBuyTurnMsg.
	 * @param EndBuyTurnMsg to visit
	 */
	public void handle(EndBuyTurnMsg actionMsg);
	
	/**
	 * Defines a visit "strategy" for a BazaarStartActionMsg.
	 * @param BazaarStartActionMsg to visit
	 */
	public void handle(BazaarStartActionMsg actionMsg);
	
	/**
	 * Defines a visit "strategy" for a BazaarStopActionMsg.
	 * @param BazaarStopActionMsg to visit
	 */
	public void handle(BazaarStopActionMsg actionMsg);
	
	/**
	 * Defines a visit "strategy" for a BazaarActionMsg.
	 * @param BazaarActionMsg to visit
	 */
	public void handle(BazaarActionMsg actionMsg);
	
	/**
	 * Defines a visit "strategy" for a ShowPlayerStatusActionMsg.
	 * @param ShowPlayerStatusActionMsg to visit
	 */
	public void handle(ShowPlayerStatusActionMsg actionMsg);
	
	/**
	 * Defines a visit "strategy" for a ShowBazaarStatusMsg.
	 * @param ShowBazaarStatusMsg to visit
	 */
	public void handle(ShowBazaarStatusMsg actionMsg);
	
	/**
	 * Defines a visit "strategy" for a LeaveGameActionMsg.
	 * @param LeaveGameActionMsg to visit
	 */
	public void handle(LeaveGameActionMsg actionMsg);
	
	/**
	 * Defines a visit "strategy" for a ChatActionMsg.
	 * @param ChatActionMsg to visit
	 */
	public void handle(ChatActionMsg chat);
	
	/**
	 * Defines a visit "strategy" for a EndGameMsg.
	 * @param EndGameMsg to visit
	 */
	public void handle(EndGameMsg eventMsg);

}
