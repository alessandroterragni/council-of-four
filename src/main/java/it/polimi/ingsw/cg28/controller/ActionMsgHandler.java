package it.polimi.ingsw.cg28.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.cg28.controller.updaters.Updater;
import it.polimi.ingsw.cg28.exception.InvalidActionException;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.model.parser.Configuration;
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
import it.polimi.ingsw.cg28.view.messages.event.BazaarStatusMsg;
import it.polimi.ingsw.cg28.view.messages.event.EndBazaarMsg;
import it.polimi.ingsw.cg28.view.messages.event.EndTurnEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;
import it.polimi.ingsw.cg28.view.messages.event.PlayerStatusMsg;

/**
 * Concrete visit strategy implementation of ActionMsgHandlerInterface. 
 * Specifies how to manage ActionMsg received by a Game. It modifies the model and send back
 * event messages related notifying game observers. It also has a reference to a gameController and a BazaarController
 * and sets the right one to the game to manage game advancements.
 * @see ActionMsgHandlerInterface
 * @author Mario
 *
 */
public class ActionMsgHandler implements ActionMsgHandlerInterface {
	
	private Game game;
	private GameController gameController;
	private ApuBazaarController bazaarController;
	
	private static final Logger log = Logger.getLogger(Game.class.getName());
	
	/**
	 * Constructor of the class.
	 * @param game the Game related to the Handler
	 */
	public ActionMsgHandler(Game game){
		this.game = game;
	}
	
	/**
	 * Generic actionMsg can't be handled.
	 */
	@Override
	public void handle(ActionMsg actionMsg) {
		log.warning("Generic ActionMsg in ActionMsgHandler!");
	}
	
	/**
	 * Handles StartMsg. Sets a new gameController to game with the configuration parameters contained 
	 * in the message. It also cancel the configTimer of game. Calls the play() method of game.
	 */
	@Override
	public void handle(StartMsg actionMsg) {
		
		if(game.getConfigTimer() != null)
			game.getConfigTimer().cancel();
		
		if(gameController == null){
			Configuration configuration = new Configuration(actionMsg);
			
			Logger.getLogger(Game.class.getName()).info("Choosen Map: " + configuration.getMapChosen());
			
			gameController = new GameController(game.getPlayers(), configuration, game.getGameID());
			game.setGameController(gameController);
			
			game.play();
				
		}
		
	}
	
	/**
	 * Handles TurnActionMsg. If the message is not filled, it tries to fill it with model informations: if it can't he notifies 
	 * game observers with a SimpleEventMsg containing the error message, if he succeeds to fill it sends the related event 
	 * message to observers setting the filled flag to true. If the message is already filled calls the update method on the 
	 * current AbstractGameController connected to Game and manages the Updater returned. NOTE: If bazaar is not on
	 * and the current gameController turn is waiting for a bonus the method doesn't fill other messages and notifies the
	 * player the turn is waiting for a response to BonusActionMsg.
	 */
	@Override
	public void handle(TurnActionMsg actionMsg){
		
		if (!actionMsg.isFilled()){
			
			if (checkBonus())
				return;
			
			try{
				
				actionMsg.fill(gameController.getModel());
				
			} catch (InvalidActionException e){
				
				game.sendSimpleMsg(e.getMessage(), false);
				this.handle(new QuitActionMsg());
				log.log(Level.INFO,"Action not allowed player " + actionMsg.getPlayerID().getName(), e);
				return;
				
			}
			
			actionMsg.setFilled(true);
			
			EventMsg event = actionMsg.relatedEventMsg();
			event.setPlayer(game.getGameController().getCurrentPlayer());
			
			game.notifyObservers(event);
			
		} else
			update(actionMsg);
		
	}
	
	/**
	 * Method to check if current PlayerTurn is waiting for a bonus.
	 * @return true if current PlayerTurn is waiting for a bonus.
	 */
	private boolean checkBonus() {
		
		if(!isBazaar()){
			ModelStatus model = gameController.getModel();
			PlayerID currentPlayer = gameController.getCurrentPlayer();
			
			if(model.getPlayer(currentPlayer).getTurn().isBonus()){
				game.sendSimpleMsg("Fill the bonus msg first", false);
				return true;
			}
		}
				
		return false;
		
	}

	/**
	 * Private method to call the update method on the current gameController
	 * connected to Game and to manage the Updater returned.
	 * @param actionMsg to be processed
	 */
	private void update(TurnActionMsg actionMsg){
		
		Updater updater = game.getGameController().update(actionMsg);
		game.sendSimpleMsg("Player "+ game.getGameController().getCurrentPlayer().getName() +
				" : [" + actionMsg.getName() + "] action", true);
		updater.update(this);
		
	}
	
	/**
	 * Handles BuySalableActionMsg Handle TurnActionMsg. If the message is not filled, it tries to fill it with model and
	 * bazaarModel informations : if it can't he notifies game observers with a SimpleEventMsg containing the error message, 
	 * if he succeeds to fill it sends the related event message to observers setting the filled flag to true. If the message 
	 * is already filled calls the update method on the current AbstractGameController connected to Game and manages the Updater returned.
	 */
	@Override
	public void handle(BuySalableActionMsg actionMsg){
		
		if (!actionMsg.isFilled()){
			
			try{
				actionMsg.fill(gameController.getModel(), bazaarController.getBazaarModel());
			} catch (InvalidActionException e){
				log.log(Level.WARNING, e.getMessage(), e);
				game.sendSimpleMsg(e.getMessage(), false);
				this.handle(new QuitActionMsg());
				return;
			}
			
			actionMsg.setFilled(true);
			
			EventMsg event = actionMsg.relatedEventMsg();
			event.setPlayer(game.getGameController().getCurrentPlayer());
			
			game.notifyObservers(event);
			
		} else
			update(actionMsg);
		
	}
	
	/**
	 * Handles QuckActionMsg. It simply notifies game observers with message returned by toDo method of the current
	 * AbstractGameController connected to game.
	 */
	@Override
	public void handle(QuitActionMsg actionMsg) {
		
		game.notifyObservers(game.getGameController().toDo());
		
	}

	/**
	 * Handles EndTurnMsg. If the player can end his turn, it ends the turn calling getNextTurn method on the current AbstractGameController
	 * connected to game. Otherwise notifies game observers with a SimpleEventMsg containing the error message.
	 */
	@Override
	public void handle(EndTurnMsg actionMsg) {
		
		Player currentPlayer = gameController.getModel().getPlayer(game.getGameController().getCurrentPlayer());
		
		if(currentPlayer.getTurn().canEnd()){
			
			EventMsg notification = new EndTurnEventMsg(game.getGameController().getCurrentPlayer(), "Your turn is ended!");
			game.notifyObservers(notification);
			game.sendSimpleMsg("Turn " + gameController.getCurrentPlayer().getName() + " is ended!", true);
			game.getNextTurn();
		}
		else { 
			game.sendSimpleMsg("Player "+  gameController.getCurrentPlayer().getName() + " can't end turn now!", true);
		}
		
	}
	
	/**
	 * Handles EndSellTurnMsg. If bazaar is on ends the sell turn calling getNextTurn method on the current AbstractGameController
	 * connected to game.
	 */
	@Override
	public void handle(EndSellTurnMsg actionMsg) {
		
		if (isBazaar()){
			
			EventMsg event = actionMsg.relatedEventMsg();
			event.setPlayer(game.getGameID());
			game.notifyObservers(event);
			
			game.getNextTurn();
		}
		
	}
	
	/**
	 * Handles EndBuyTurnMsg. If bazaar is on ends the buy turn calling getNextTurn method on the current AbstractGameController
	 * connected to game.
	 */
	@Override
	public void handle(EndBuyTurnMsg actionMsg) {
		
		if (isBazaar()){
			
		EventMsg event = actionMsg.relatedEventMsg();
		event.setPlayer(game.getGameID());
		game.notifyObservers(event);
		
		for(PlayerID player: game.getPlayers()){
			event = new PlayerStatusMsg(gameController.getModel(), player, game.getGameController().getCurrentPlayer());
			game.notifyObservers(event);
		}
		
		game.getNextTurn();
		
		}
	}
	
	/**
	 * Handles BazaarStartActionMsg. Sets a new bazaarController (with only active players)
	 * as AbstractGameController of game. Calls the play() method of game.
	 */
	@Override
	public void handle(BazaarStartActionMsg actionMsg) {
		
		Set<PlayerID> inactivePlayers = gameController.getInactivePlayers();
		List<PlayerID> activePlayers = new ArrayList<>(game.getPlayers());
		
		Iterator<PlayerID> iterator = activePlayers.iterator();
        
		while (iterator.hasNext()) {
            if(inactivePlayers.contains(iterator.next()))
            	iterator.remove();
        }
		
		bazaarController = new ApuBazaarController(gameController.getModel(), activePlayers, game.getGameID());
		game.setGameController(bazaarController);
		game.play();
		
	}
	
	/**
	 * Handles BazaarStopActionMsg. Sets gameController (communicating players disconnected during bazaar turn)
	 * as AbstractGameController of game. Calls the play() method of game.
	 */
	@Override
	public void handle(BazaarStopActionMsg actionMsg) {
		
		for(PlayerID inactivePlayer : bazaarController.getInactivePlayers())
			gameController.inactivePlayer(inactivePlayer);
		
		EventMsg event = new EndBazaarMsg(bazaarController.gameStats());
		event.setPlayer(game.getGameID());
		
		bazaarController = null;
		game.notifyObservers(event);
		game.setGameController(gameController);
		game.getNextTurn();
		
	}
	
	/**
	 * Handles BazaarActionMsg. Calls the same method to handle a TurnActionMsg.
	 * @see #handle(TurnActionMsg actionMsg)
	 */
	@Override
	public void handle(BazaarActionMsg actionMsg) {
		
		TurnActionMsg action = actionMsg;
		handle(action);
		
	}
	
	/**
	 * Handles ShowPlayerStatusActionMsg. Notifies game observers with a PlayerStatusMsg for each player.
	 */
	@Override
	public void handle(ShowPlayerStatusActionMsg actionMsg) {
		
		EventMsg event;
		
		for(PlayerID player: game.getPlayers()){
			event = new PlayerStatusMsg(gameController.getModel(), player, game.getGameController().getCurrentPlayer());
			game.notifyObservers(event);
		}
		
	}
	
	/**
	 * Handles ShowBazaarStatusMsg. If bazaar is on, notifies game observers with a BazaarStatusMsg for each player.
	 */
	@Override
	public void handle(ShowBazaarStatusMsg actionMsg) {
		
		if(isBazaar()){
			
		EventMsg event = new BazaarStatusMsg(bazaarController.getBazaarModel(), game.getGameID());
		game.notifyObservers(event);
		
		}
		
	}
	
	/**
	 * Handles ChatActionMsg. Notifies game observers with the related ChatEventMsg.
	 */
	@Override
	public void handle(ChatActionMsg chat){
		
		EventMsg event = chat.relatedEventMsg();
		event.setPlayer(game.getGameID());
		game.notifyObservers(event);
		
	}
	
	/**
	 * Handles LeaveGameActionMsg. Marks the sender of the message as inactive notifying other players
	 * and subscribing the sender from the game topic.
	 * If there is only one active player remained sets OnlyOnePlayer game attribute to true. 
	 * If the message sender is the current player, calls getNextTurn game method.
	 */
	@Override
	public void handle(LeaveGameActionMsg actionMsg) {
		
		boolean goAhead = game.getGameController().inactivePlayer(actionMsg.getPlayerID());
		
		EventMsg event = actionMsg.relatedEventMsg();
		event.setPlayer(game.getGameID());
		notify(event);
		
		if(!goAhead)
			game.setOnlyOnePlayer(true);
		
		if(game.getGameController().getCurrentPlayer().equals(actionMsg.getPlayerID()))
			game.getNextTurn();
		
		game.getActionMsgController().unsubscribeInactivePlayer(actionMsg.getPlayerID());
		
	}
	
	/**
	 * Handle EndGameMsg. Notifies game observers with the related event message.
	 * Also calls handle(EndGameMsg) method on game ActionMsgController.
	 */
	@Override
	public void handle(EndGameMsg actionMsg) {
		
		EventMsg eventMsg = actionMsg.relatedEventMsg();
		eventMsg.setPlayer(game.getGameID());
		game.notifyObservers(eventMsg);
		
		game.getActionMsgController().handle(actionMsg);
		
	}
	
	/**
	 * Method to notify the EventMsg to game observers.
	 * @param eventMsg to notify
	 */
	public void notify(EventMsg eventMsg){
		
		game.notifyObservers(eventMsg);
		
	}
	
	/**
	 * Returns the GameController associated to the game
	 * @return GameController associated to the game
	 */
	public GameController getGameController() {
		return gameController;
	}
	
	/**
	 * Returns the ApuBazaarController associated to the game bazaar
	 * @return ApuBazaarController associated to the game bazaar
	 */
	public ApuBazaarController getBazaarController() {
		return bazaarController;
	}
	
	/**
	 * Returns true if bazaar is on.
	 * @return true if bazaar is on.
	 */
	public boolean isBazaar(){
		
		if (bazaarController != null)
				return true;
		return false;
		
	}
	
}
