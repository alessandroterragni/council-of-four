package it.polimi.ingsw.cg28.connections.client;

import java.rmi.RemoteException;

import it.polimi.ingsw.cg28.connections.BrokerInterface;
import it.polimi.ingsw.cg28.connections.RequestHandler;
import it.polimi.ingsw.cg28.connections.SubscriberInterface;
import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.view.ViewController;

/**
 * Abstract class that provides methods to create new connections of both RMI and Socket architectures.
 * @author Alessandro, Marco, Mario
 *
 */
public abstract class ConnectionTypeFactory {

    private final ViewController viewController;
    private final Subscriber subscriber;

    private PlayerID player;
    private String playerName;

    /**
     * The constructor of the class, creates a new connection.
     * @param viewController - The view controller to use in the new connection
     * @param playerName - The name of the player that connects from a client using this new connection
     */
    public ConnectionTypeFactory(ViewController viewController, String playerName) {

        if (viewController == null) {
            throw new IllegalArgumentException("ViewUpdater must not be null.");
        }

        this.viewController = viewController;
        subscriber = new Subscriber(viewController);
        
        this.playerName = playerName;
        player = null;

    }

    /**
     * Sets up the connection, in a way that the connection setup procedure used (RMI or Socket)
     * is transparent to the user.
     * @throws RemoteException if any issues during the setup occurs
     */
    public void setupConnection() throws RemoteException {
    	
    	//RMI calls method on Broker and RequestHandler stubs
    	//Socket calls method on Socket Client hiding the client socket communication
        player = getRequestHandler().connect(playerName);
        getBrokerInterface().subscribe(getSubscriberInterface(), player);

    }

    /**
     * Fetches the request handler relative to the setup connection type.
     * @return the connection's request handler
     */
    public abstract RequestHandler getRequestHandler();

    /**
     * Fetches the broker interface relative to the setup connection type.
     * @return the connection's broker interface
     */
    public abstract BrokerInterface getBrokerInterface();

    /**
     * Sets the connected player's ID to the specified one.
     * @param player - The ID to set
     */
    protected void setPlayerID(PlayerID player) {

        this.player = player;

    }

    /**
     * Fetches the connected player's ID.
     * @return the player's ID
     */
    public PlayerID getPlayerID() {

        return player;

    }

    /**
     * Fetches the view controller for this connection.
     * @return the view controller
     */
    public ViewController getViewController() {

        return viewController;

    }

    /**
     * Fetches the subscriber entity for this connection.
     * @return the subscriber
     */
    public Subscriber getSubscriber() {

        return subscriber;

    }

    /**
     * Fetches the subscriber interface used in this connection.
     * @return the subscriber interface
     */
    public SubscriberInterface getSubscriberInterface() {

        return subscriber;

    }
    
    /**
     * Fetches a string containing the connected player's name.
     * @return the player's name
     */
    public String getPlayerName() {
		return playerName;
	}


}
