package it.polimi.ingsw.cg28.controller;

import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.cg28.connections.server.ActionMsgController;
import it.polimi.ingsw.cg28.view.messages.action.ActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.StartMsg;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;
import it.polimi.ingsw.cg28.view.messages.event.SimpleEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.StartEventMsg;

/**
 * This class manages a single game instance.
 * 
 * Game manages game progresses, "talking" with Model and View modules (MVC pattern).
 * 
 * Manages the progress of a turn-based game through an AbstractGameController
 * and a specific ActionMsgHandler.
 * 
 * Each player has a given time to interact with this component. Otherwise he/she is marked
 * as inactive.
 * 
 * It notifies game request and update to registered observers.
 * 
 * @author Mario
 */
public class Game extends Observable<EventMsg> implements Observer<ActionMsg> {
	
	private static final long INACTIVITY_TIME_ALLOWED = 120000;
	private static final Logger log = Logger.getLogger(Game.class.getName());
	
	private List<PlayerID> players;
	private PlayerID gameID;
	private AbstractGameController gameController;
	private ActionMsgHandler actionMsgHandler;
	private ActionMsgController actionMsgController;
	private InactivityTimer timer;
	private Timer configTimer;
	private TimerTask config;
	private boolean onlyOnePlayer;

	/**
	 * Constructor of the class, one game instance.
	 * @param gameID - Game instance UUID, identifies the instance
	 * @param players - List of players playing the game
	 * @param actionMsgController - Allows the object to notify same game issues to the communication layer
	 */
	public Game(String gameID, List<PlayerID> players, ActionMsgController actionMsgController){
		
		this.gameID = new PlayerID(gameID);
		this.actionMsgHandler = new ActionMsgHandler(this);
		this.actionMsgController = actionMsgController;
		this.players = players;
		
		this.timer = new InactivityTimer();
		
	}

	/**
	 * Method to initialise a game instance. Send to the first player connected a StartEventMsg to give him/her
	 * the possibility to set game configuration. It also initialise a timer, if game won't receive a response in 
	 * time the game starts with default configuration. Order of play is randomly established.
	 */
	public void startGame() {
		
		SetLogger setLogger = new SetLogger();
		setLogger.setLog(this);
		
		EventMsg event = new StartEventMsg();
		event.setPlayer(players.get(0));
		
		Collections.shuffle(players);
		
		configTimer = new Timer();
		config = new TimerTask()
        {
            @Override
            public void run()
            {	

                sendSimpleMsg("Game started with default config", true);
                StartMsg start = new StartMsg();
                start.handle(actionMsgHandler);
                
            }
        };
        
       configTimer.schedule(config, (long) 30000);
       this.notifyObservers(event);
		
	}
	
	/**
	 * Simple method to start the Game. Notifies players with {@link AbstractGameController#startGame()} message.
	 * Calls {@link #getNextTurn()}.
	 */
	public void play() {
		
		EventMsg event = gameController.startGame();
		event.setPlayer(gameID);
		
		notifyObservers(event);
		
		getNextTurn();
		
	}
	
	/**
	 * Method to proceed in the game. If {@link AbstractGameController#endGame()} is not true and there are
	 * at least two players, initialise the nextTurn given by {@link AbstractGameController#getNextTurn()}, notifying
	 * the players with the successive request and ask to actionMsgHandler to handle the update message returned
	 * by {@link AbstractGameController#updateMsg()}. Otherwise the methods ends the game asking to actionMsgHandler 
	 * to handle the end message returned by {@link AbstractGameController#endMsg()}
	 */
	public void getNextTurn() {
		
		timer.cancel();
		timer = new InactivityTimer();
		timer.set(this, INACTIVITY_TIME_ALLOWED);
		
		if (gameController.endGame() || onlyOnePlayer){
			timer.cancel();
			gameController.endMsg().handle(actionMsgHandler);
			return;
		}
			
		EventMsg eventMsg = gameController.getNextTurn();
		
		gameController.updateMsg().handle(actionMsgHandler);
		
		if (eventMsg != null)
			notifyObservers(eventMsg);
		
	}
	
	/**
	 * Private method to reset the inactivityTimer when arrives a message from the current player.
	 * @param change - ActionMsg notified to Game.
	 */
	private void checkTimer(ActionMsg change){
		
		if(change.getPlayerID() != null 
			&&  gameController != null 
			&& change.getPlayerID().equals(gameController.getCurrentPlayer())){
			
					timer.reset(this, INACTIVITY_TIME_ALLOWED);
					
				}
		
	}
	
	/**
	 * Handles message notified by objects observed. Resets the inactivityTimer when arrives a message
	 * from the current player.
	 */
	@Override
	public void update(ActionMsg change) {
		
		checkTimer(change);
		
		change.handle(actionMsgHandler);
		
	}
	
	/**
	 * Method to mark current player as inactive. The {@link AbstractGameController#inactivePlayer()} method is called
	 * and the player is unsubscribed from game topic {@link ActionMsgController#unsubscribeInactivePlayer(PlayerID)}.
	 * #getNextTurn() is called. At last if there is only one player remained onlyOnePlayer is set to true (so if there is
	 * only one player remained he can play a last turn).  
	 */
	public synchronized void inactivePlayer() {
		
	  PlayerID inactive = gameController.getCurrentPlayer();
	  
      sendSimpleMsg("Player " + inactive.getName() + " disconnected", true);
      
      boolean morePlayers = gameController.inactivePlayer(inactive);
      
      getNextTurn();
      
      if (!morePlayers)
    	  onlyOnePlayer = true;
      
      actionMsgController.unsubscribeInactivePlayer(inactive);
      
      log.log(Level.INFO, "Player " + inactive.getName() + " disconnected");
		
	}
	
	/**
	 * Method to notify a SimpleEventMessage to players.
	 * @param message - String to put in the message
	 * @param toAll - true if the message must be broadcasted, false otherwise.
	 */
	public void sendSimpleMsg(String message, boolean toAll){
		
		EventMsg notification = new SimpleEventMsg(message);
		
		if(!toAll && gameController != null)
			notification.setPlayer(gameController.getCurrentPlayer());
		else 
			notification.setPlayer(gameID);
	   
		notifyObservers(notification);
	    
	}
	
	/**
	 * Method to set boolean value of OnlyOnePlayer field.
	 * @param onlyOnePlayer - new value of OnlyOnePlayer field
	 */
	public void setOnlyOnePlayer(boolean onlyOnePlayer) {
		this.onlyOnePlayer = onlyOnePlayer;
	}
	
	/**
	 * Method to return the current AbstractGameController connected to game instance.
	 * @return current AbstractGameController connected to game instance
	 */
	public AbstractGameController getGameController() {
		
		return gameController;
		
	}
	
	/**
	 * Method to set the current AbstractGameController connected to game instance.
	 * @param gameController - AbstractGameController to connect to game instance
	 */
	public void setGameController(AbstractGameController gameController) {
		
		this.gameController = gameController;
		
	}
	
	/**
	 * Returns list of players playing the game, also inactive players.
	 * @return list of players playing the game, also inactive players.
	 */
	public List<PlayerID> getPlayers() {
		return players;
	}
	
	/**
	 * Returns the gameID, identifier of game instance.
	 * @return gameID, identifier of game instance.
	 */
	public PlayerID getGameID() {
		return gameID;
	}
	
	/**
	 * Returns the configuration Timer, scheduled by {@link#startGame()}.
	 * @return the configuration Timer.
	 */
	public Timer getConfigTimer() {
		return configTimer;
	}
	
	/**
	 * Returns the actionMsgHandler related to game.
	 * @return actionMsgHandler related to game.
	 */
	public ActionMsgHandler getActionMsgHandler() {
		return actionMsgHandler;
	}
	
	/**
	 * Returns the actionMsgController related to game.
	 * @return actionMsgController related to game.
	 */
	public ActionMsgController getActionMsgController(){
		return actionMsgController;
	}

}
