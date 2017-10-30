package it.polimi.ingsw.cg28.view;

import it.polimi.ingsw.cg28.connections.RequestHandler;
import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.view.messages.event.BazaarStatusMsg;
import it.polimi.ingsw.cg28.view.messages.event.ChatEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.EndBazaarMsg;
import it.polimi.ingsw.cg28.view.messages.event.EndBuyTurnEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.EndGameEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.EndSellTurnEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.EndTurnEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.FilledMsg;
import it.polimi.ingsw.cg28.view.messages.event.GiveMeActionMsg;
import it.polimi.ingsw.cg28.view.messages.event.GiveMeBuyActionMsg;
import it.polimi.ingsw.cg28.view.messages.event.GiveMeSellActionMsg;
import it.polimi.ingsw.cg28.view.messages.event.PlayerStatusMsg;
import it.polimi.ingsw.cg28.view.messages.event.SimpleEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.StartEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.TableStatusMsg;
import it.polimi.ingsw.cg28.view.messages.event.WelcomeBazaarEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.WelcomeEventMsg;

/**
 * Interface to implement Visitor Pattern on {@link it.polimi.ingsw.cg28.view.messages.event.EventMsg} objects.
 * @author Mario
 */
public interface ViewHandler {
	
	/**
	 * Defines a visit strategy to handle a StartEventMsg. 
	 * @param StartEventMsg to visit.
	 */
	public void handle(StartEventMsg startEventMsg);
	
	/**
	 * Defines a visit strategy to handle a GiveMeActionMsg. 
	 * @param GiveMeActionMsg to visit.
	 */
	public void handle(GiveMeActionMsg giveMeActionMsg);
	
	/**
	 * Defines a visit strategy to handle a WelcomeEventMsg. 
	 * @param WelcomeEventMsg to visit.
	 */
	public void handle(WelcomeEventMsg welcomeEventMsg);
	
	/**
	 * Defines a visit strategy to handle a FilledMsg. 
	 * @param FilledMsg to visit.
	 */
	public void handle(FilledMsg filledMsg);
	
	/**
	 * Defines a visit strategy to handle a PlayerStatusMsg. 
	 * @param PlayerStatusMsg to visit.
	 */
	public void handle(PlayerStatusMsg playerStatusMsg);
	
	/**
	 * Defines a visit strategy to handle a SimpleEventMsg. 
	 * @param SimpleEventMsg to visit.
	 */
	public void handle(SimpleEventMsg simpleEventMsg);
	
	/**
	 * Defines a visit strategy to handle a WelcomeBazaarEventMsg. 
	 * @param WelcomeBazaarEventMsg to visit.
	 */
	public void handle(WelcomeBazaarEventMsg welcomeBazaarEventMsg);
	
	/**
	 * Defines a visit strategy to handle a GiveMeBuyActionMsg. 
	 * @param GiveMeBuyActionMsg to visit.
	 */
	public void handle(GiveMeBuyActionMsg giveMeBuyActionMsg);
	
	/**
	 * Defines a visit strategy to handle a GiveMeSellActionMsg. 
	 * @param GiveMeSellActionMsg to visit.
	 */
	public void handle(GiveMeSellActionMsg giveMeSellActionMsg);
	
	/**
	 * Defines a visit strategy to handle a EndBazaarMsg. 
	 * @param EndBazaarMsg to visit.
	 */
	public void handle(EndBazaarMsg endBazaarMsg);
	
	/**
	 * Defines a visit strategy to handle a BazaarStatusMsg. 
	 * @param BazaarStatusMsg to visit.
	 */
	public void handle(BazaarStatusMsg bazaarStatusMsg);
	
	/**
	 * Defines a visit strategy to handle a ChatEventMsg. 
	 * @param ChatEventMsg to visit.
	 */
	public void handle(ChatEventMsg chatEventMsg);
	
	/**
	 * Defines a visit strategy to handle a TableStatusMsg. 
	 * @param TableStatusMsg to visit.
	 */
	public void handle(TableStatusMsg tableStatusMsg);

	/**
	 * Defines a visit strategy to handle a EndTurnEventMsg. 
	 * @param EndTurnEventMsg to visit.
	 */
	public void handle(EndTurnEventMsg endTurnEventMsg);
	
	/**
	 * Defines a visit strategy to handle a EndSellTurnEventMsg. 
	 * @param EndSellTurnEventMsg to visit.
	 */
	public void handle(EndSellTurnEventMsg endSellTurnEventMsg);
	
	/**
	 * Defines a visit strategy to handle a EndBuyTurnEventMsg. 
	 * @param EndBuyTurnEventMsg to visit.
	 */
	public void handle(EndBuyTurnEventMsg endBuyTurnEventMsg);
	
	/**
	 * Defines a visit strategy to handle a EndGameEventMsg. 
	 * @param EndGameEventMsg to visit.
	 */
	public void handle(EndGameEventMsg endGameEventMsg);
	
	/**
	 * Initialises the Handler.
	 * @param requestHandler RequestHandler to dispatch requests
	 * @param player - PlayerID to communicate with server-side.
	 */
	public void initialize(RequestHandler requestHandler, PlayerID player);

	

}
