package it.polimi.ingsw.cg28.connections.server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.cg28.connections.PublisherInterface;
import it.polimi.ingsw.cg28.connections.RequestHandler;
import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.view.messages.action.ActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.ConnectionActionMsg;
import it.polimi.ingsw.cg28.view.messages.event.ConnectionEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;

/**
 * Manages client connections to a match. The moment two players connected to the pending game, a countdown
 * starts and waits for more connections. Every new connection resets the timer. If the timer expires,
 * a new game is created, and the ControllerSingleGame manages its initialisation and empties the waiting room.
 */
public class ControllerMultipleGames implements RequestHandler{
	
	 private static final Logger LOG = Logger.getLogger(ControllerMultipleGames.class.getName());
	 
	 private static final int COUNTDOWN = 20;

	 private Timer timer;
	 private TimerTask timerTask;
	 private final PublisherInterface publisherInterface;
	 private final Map<PlayerID, ControllerSingleGame> clients;
	 private final List<PlayerID> waitingPlayers;
	 private ControllerSingleGame waitingGame;
	 private InteractionComponent interactionComponent;
	
	/**
	 * The constructor of the class, creates a new ControllerMultipleGames that manages the connections to the
	 * the games.
	 * @param publisher - The PublisherInterface to be set as the ControllerSingleGame's reference for
	 * dispatching messages to the players participating in the match
	 */
    public ControllerMultipleGames(PublisherInterface publisher) {

    	this.publisherInterface = publisher;
        this.interactionComponent = new InteractionComponent(this);
    	timer = new Timer();
        waitingPlayers = new ArrayList<>();
        clients = new HashMap<>();
        waitingGame = new ControllerSingleGame(publisher, interactionComponent);

    }

    /**
     * Adds a new player to the waiting room; when there are two players in the room, it starts the
     * countdown timer before starting a game. The timer is reset after every new connection.
     * @param player - The player to be added to the waiting room
     */
    public void newPlayer(PlayerID player) {

        waitingPlayers.add(player);
        waitingGame.addPlayer(player);
        clients.put(player, waitingGame);

        LOG.log(Level.INFO, "New player added to waiting room.");

        startTimer();

    }
    
    /**
     * Method to update the Timer. If there are at least two players in the waiting room the timer is created.
     * If there is already a timer counting, it is cancelled and a new timer and related timerTask is scheduled.
     * The task scheduled initialises the current waiting game and creates a new one in waiting-state.
     * 
     */
    private void startTimer(){
    	
    	if (waitingPlayers.size() >= 2) {

            timer.cancel();

            timerTask = new TimerTask() {
                @Override
                public void run() {
                    try {

                        waitingGame.initGame();
                        waitingGame = new ControllerSingleGame(publisherInterface, interactionComponent);
                        waitingPlayers.clear();

                        LOG.log(Level.INFO, "New game created.");

                    } catch (Exception e) {
                        LOG.log(Level.SEVERE, "Unexpected exception while creating a " +
                                "new game.", e);
                    }
                }
            };

            timer = new Timer();

            timer.schedule(timerTask, (long) COUNTDOWN * 1000);

        } 
    	
    }
    
    /**
     * Method to delete a player from the waiting players list. If there are more than two players
     * in waiting room restarts the timer.
     * @param player Player to delete
     */
    public void removePlayer(PlayerID player){
    	
    	if (waitingPlayers.contains(player)){
    		
    		timerTask.cancel();
    		startTimer();
    		
    		waitingPlayers.remove(player);
    		
    		LOG.log(Level.INFO, "Player removed from waiting room.");
    		
    		clients.remove(player, waitingGame);
    		waitingGame.removePlayer(player);
    	}
    }

    /**
     * {@inheritDoc} - This implementation simply generates a new ID from the given name String.
     */
    @Override
    public PlayerID connect(String playerName) throws RemoteException {

        PlayerID newPlayer = new PlayerID(playerName);
        newPlayer(newPlayer);
        return newPlayer;

    }

    /**
     * {@inheritDoc} - This implementation associates the PlayerID from the ActionMsg request to its
     * ControllerSingleGame handler, in order for it to handle the request.
     */
    @Override
    public void processRequest(ActionMsg request) throws RemoteException {
    	
    	PlayerID requestPlayerID = request.getPlayerID();
    	ControllerSingleGame game = clients.get(requestPlayerID);
 
		if (game == null){
			LOG.log(Level.INFO, "No game found!");
		}
		else{
			synchronized(game){
				game.handleRequest(request);
			}
		}

    }
    
    /**
     * {@inheritDoc} - This implementation generates and returns a new ConnectionEventMsg for the Player specified
     * in the request.
     */
    @Override
    public EventMsg processConnectRequest(ConnectionActionMsg request) throws RemoteException {

            if (request.getPlayerID() == null) {
                return new ConnectionEventMsg(connect(request.getPlayerName()));
            } else {
                return new InvalidRequestMsg("Reconnection is not supported yet.");
            }
    }

	/**
	 * Removes player from clients list and unsubscribe he/she from the Topic.
	 * @param player player to unsubscribe
	 * @param topic topic related
	 */
	public void unsubscribePlayer(PlayerID player, String topic) {
		
		clients.remove(player);
		publisherInterface.unsubscribeClientFromTopic(topic, player);
		
	}

}
