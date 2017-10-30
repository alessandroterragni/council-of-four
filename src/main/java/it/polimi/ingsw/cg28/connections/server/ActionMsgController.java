package it.polimi.ingsw.cg28.connections.server;

import it.polimi.ingsw.cg28.controller.ActionMsgHandlerInterface;
import it.polimi.ingsw.cg28.controller.PlayerID;
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
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;
import it.polimi.ingsw.cg28.view.messages.event.SimpleEventMsg;

/**
 * Concrete visit strategy implementation of ActionMsgHandlerInterface.
 * Component to manage ActionMsg when the game is not started and message that doesn't need to be 
 * processed by Game. It also checks the message could be accepted by the game a the moment it is received.
 * Through InteractionComponent it can communicate with the related ControllerMultipleGames.
 * @author Mario
 *
 */
public class ActionMsgController implements ActionMsgHandlerInterface {
	
	private ControllerSingleGame singleGame;
	private String topic;
	private InteractionComponent interactionComponent;
	
	/**
	 * Constructor of the class
	 * @param singleGame - Single game which the controller is related to 
	 * @param interactionComponent - component to interact with ControllerMultipleGames
	 */
	public ActionMsgController(ControllerSingleGame singleGame, InteractionComponent interactionComponent) {
		this.singleGame = singleGame;
		this.interactionComponent = interactionComponent;
		this.topic = singleGame.getTopic();
	}
	
	/**
	 * Manage generic ActionMsg notifying it to singleGame.
	 */
	@Override
	public void handle(ActionMsg actionMsg) {
		singleGame.notifyObservers(actionMsg);
	}
	
	/**
	 * Private method to check if current turn is associated to the same player of the message.
	 * @param actionMsg - message to check
	 * @return true if current turn is associated to the same player of the message
	 */
	private boolean checkTurn(ActionMsg actionMsg){
		return singleGame.currentPlayer().equals(actionMsg.getPlayerID());
	}
	
	/**
	 * Private method to check if bazaar is on
	 * @return true if bazaar is on
	 */
	private boolean checkBazaar(){
		return singleGame.isBazaar();
	}
	
	/**
	 * Method to notify players controller couldn't process the message received
	 */
	private void error(){
		EventMsg event = new SimpleEventMsg("Couldn't perform this action!");
		event.setPlayer(new PlayerID(topic));
		singleGame.update(event);
	}
	
	/**
	 * Manage StartMsg as a generic action message.
	 */
	@Override
	public void handle(StartMsg actionMsg) {
		ActionMsg action = actionMsg;
		handle(action);
	}
	
	/**
	 * Private method to notify a player with a SimpleEventMsg he/she isn't the current player playing.
	 * @param player - playerID related to player to notify
	 */
	private void errorTurn(PlayerID player) {
		EventMsg event = new SimpleEventMsg("Not your Turn!");
		event.setPlayer(player);
		singleGame.update(event);
	}
	
	/**
	 * Checks if a TurnAction could be performed at the moment by the player who sent the message, 
	 * otherwise notifies the player the message couldn't be processed.
	 */
	@Override
	public void handle(TurnActionMsg actionMsg){
		
		if(checkTurn(actionMsg)){
			ActionMsg action = actionMsg;
			handle(action);
		} else {
			errorTurn(actionMsg.getPlayerID());
		}
	}
	
	/**
	 * Manage BuySalableActionMsg as a bazaar action message.
	 */
	@Override
	public void handle(BuySalableActionMsg actionMsg){
		BazaarActionMsg action = actionMsg;
		handle(action);
	}
	
	/**
	 * Checks if a QuitAction could be performed at the moment by the player who sent the message, 
	 * otherwise notifies the player the message couldn't be processed.
	 */
	@Override
	public void handle(QuitActionMsg actionMsg) {
		
		if(checkTurn(actionMsg)){
			ActionMsg action = actionMsg;
			handle(action);
		} else { 
			errorTurn(actionMsg.getPlayerID());
		}
	}

	/**
	 * Checks if a EndTurn action could be performed at the moment by the player who sent the message, 
	 * otherwise notifies the player the message couldn't be processed.
	 */
	@Override
	public void handle(EndTurnMsg actionMsg) {
		
		if(checkTurn(actionMsg)){
			ActionMsg action = actionMsg;
			handle(action);
		} else { 
			errorTurn(actionMsg.getPlayerID());
		}
	}

	/**
	 * Checks if a EndSellTurn action could be performed at the moment by the player who sent the message,
	 *  otherwise notifies the player the message couldn't be processed.
	 */
	@Override
	public void handle(EndSellTurnMsg actionMsg) {
		
		if (checkBazaar() && checkTurn(actionMsg)){
				ActionMsg action = actionMsg;
				handle(action);
		} else { 
			errorTurn(actionMsg.getPlayerID());
		}
		
	}
	
	/**
	 * Checks if a EndBuyTurn action could be performed at the moment by the player who sent the message,
	 * otherwise notifies the player the message couldn't be processed.
	 */
	@Override
	public void handle(EndBuyTurnMsg actionMsg) {
		
		if (checkBazaar() && checkTurn(actionMsg)){
				ActionMsg action = actionMsg;
				handle(action);
		} else { 
			errorTurn(actionMsg.getPlayerID());
		}
		
	}
	
	/**
	 * Manage LeaveGameActionMsg if game is not yet started, otherwise manage the message as a generic ActionMsg
	 */
	@Override
	public void handle(LeaveGameActionMsg actionMsg) {
		
		if (singleGame.isGameNull())
			interactionComponent.removePlayer(actionMsg.getPlayerID());
		else{
			ActionMsg action = actionMsg;
			handle(action);
		}
		
	}

	/**
	 * Manage a ChatActionMsg sending the related message to all the players.
	 */
	@Override
	public void handle(ChatActionMsg chat) {
		
		EventMsg event = chat.relatedEventMsg();
		event.setPlayer(new PlayerID(topic));
		singleGame.update(event);

	}
	
	/**
	 * Manage a EndGameMsg through InteractionComponent
	 */
	@Override
	public void handle(EndGameMsg actionMsg) {
		
		interactionComponent.endGame(singleGame);
		
	}
	
	/**
	 * Manage a BazaarActionMsg checking if bazaar is on, otherwise notifies the player the message couldn't be processed.
	 */
	@Override
	public void handle(BazaarActionMsg actionMsg) {
		
		if (!checkBazaar()){
			EventMsg event = new SimpleEventMsg("Not bazaar turn!");
			event.setPlayer(new PlayerID(topic));
			singleGame.update(event);
		} else {
			TurnActionMsg action = actionMsg;
			handle(action);
		}
		
	}
	
	/**
	 * Notifies the player the BazaarStartActionMsg couldn't be processed.
	 */
	@Override
	public void handle(BazaarStartActionMsg actionMsg) {
		error();
	}

	/**
	 * Notifies the player the BazaarStopActionMsg couldn't be processed.
	 */
	@Override
	public void handle(BazaarStopActionMsg actionMsg) {
		error();
	}
	
	/**
	 * Notifies the player the ShowPlayerStatusActionMsg couldn't be processed.
	 */
	@Override
	public void handle(ShowPlayerStatusActionMsg actionMsg) {
		error();

	}
	
	/**
	 * Notifies the player the ShowBazaarStatusMsg couldn't be processed.
	 */
	@Override
	public void handle(ShowBazaarStatusMsg actionMsg) {
		error();
	}
	
	/**
	 * Allows to unsubscribe a player from the game topic
	 * @param player - PlayerID of player to unsubscribe
	 */
	public void unsubscribeInactivePlayer(PlayerID player){
		
		interactionComponent.unsubscribePlayer(singleGame, player);
		
	}
}
