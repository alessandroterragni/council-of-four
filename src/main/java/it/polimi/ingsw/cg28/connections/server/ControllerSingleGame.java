package it.polimi.ingsw.cg28.connections.server;

import java.util.*;

import it.polimi.ingsw.cg28.connections.PublisherInterface;
import it.polimi.ingsw.cg28.controller.Game;
import it.polimi.ingsw.cg28.controller.Observable;
import it.polimi.ingsw.cg28.controller.Observer;
import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.view.messages.action.ActionMsg;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;
import it.polimi.ingsw.cg28.view.messages.event.SimpleEventMsg;

/**
 * The controller initialise a game instance and after that works as a view module on MVC pattern.
 * Manage request from the players notifying it to the game (actionMsg) 
 * and publish update (eventMsg) received on the publisher interface. 
 * 
 * @author Alessandro, Marco, Mario
 *
 */
public class ControllerSingleGame extends Observable<ActionMsg> implements Observer<EventMsg>{

	private UUID gameID;
	private List<PlayerID> players;
    private final PublisherInterface publisherInterface;
    private Game game;
    private ActionMsgController controller;

    /**
     * The constructor of the class, creates a new ControllerSingleGame.
     * @param publisherInterface - The PublisherInterface to be used to dispatch messages to the
     * players in the match
     * @param interactionComponent - Component to interact with ControllerMultipleGames
     */
    public ControllerSingleGame(PublisherInterface publisherInterface, InteractionComponent interactionComponent) {

        this.publisherInterface = publisherInterface;
        players = new ArrayList<>();
        gameID = UUID.randomUUID();
        controller = new ActionMsgController(this, interactionComponent);
        
        publisherInterface.addTopic(getTopic());

    }

    /**
     * Adds a player to the list of players for this match and subscribes it to the game's topic
     * in the publisher interface.
     * @param player - The player to be added
     */
    public void addPlayer(PlayerID player) {

        if (isGameNull()) {
            players.add(player);
            publisherInterface.subscribeClientToTopic(getTopic(), player);
        }

    }

    /**
     * Initialises and starts the new Game, which will be referred to via its topic. This method also makes sure that
     * both the ControllerSingleGame is observing the Game, and vice-versa.
     */
    public void initGame() {
    	
    	
    	game = new Game(getTopic(),  players, controller);
    	
    	game.registerObserver(this);
    	this.registerObserver(game);
    	
    	game.startGame();
    	
    }
    
    /**
     * Fetches the String used to refer to the Game inside the PublisherInterface's set of topics.
     * @return the String which identifies the topic of the game
     */
    public String getTopic() {

        return "GAME" + gameID.toString();

    }
	
    /**
     * Processes the request through ActionMsgController.
     * @param request - ActionMsg request to process.
     */
	public void handleRequest(ActionMsg request) {
		
		request.handle(controller);
		
	}

	/**
	 * Updates the status after a change by publishing the change itself for the game's topic.
	 */
	@Override
	public void update(EventMsg change) {
		
		publisherInterface.publish(change, getTopic());
		
	}

	
	/**
	 * Manages a player who left the game while the game is not yet started.
	 * @param player who leave the game.
	 */
	public void removePlayer(PlayerID player) {

		if (isGameNull()) {
			
            players.remove(player);
            
            EventMsg event = new SimpleEventMsg("Removed from waiting list, reconnect to be added again!");
            event.setPlayer(player);
            publisherInterface.publish(event, getTopic());
            
            publisherInterface.unsubscribeClientFromTopic(getTopic(), player);
            
        }
		
	}
	
	/**
	 * Method to return game current player.
	 * @return PlayerID related to current player.
	 */
	public PlayerID currentPlayer(){
		return game.getGameController().getCurrentPlayer();
	}
	
	/**
	 * Returns true if bazaar is on.
	 * @return true if bazaar is on.
	 */
	public boolean isBazaar(){
		return game.getActionMsgHandler().isBazaar();
	}

	/**
	 * Returns true if the Game is not yet initialised.
	 * @return true if the Game is not yet initialised.
	 */
	public boolean isGameNull() {
		return game == null;
	}
	
	/**
	 * Returns list of players related to the Game instance.
	 * @return list of players related to the Game instance.
	 */
	public List<PlayerID> getPlayers() {
		return players;
	}
	
	
}

