package it.polimi.ingsw.cg28.connections;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.view.messages.action.ActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.ConnectionActionMsg;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;

/**
 * This interface provides the methods to handle connection requests between sockets.
 * @author Alessandro, Marco, Mario
 *
 */
public interface RequestHandler extends Remote{

	/**
	 * Associates a player with the given name with a new PlayerID
	 * @param playerName - The String containing the name of the Player to be provided with an ID
	 * @return the player's new PlayerID
	 * @throws RemoteException if any remote connection issue occur during the process
	 */
    public PlayerID connect(String playerName) throws RemoteException;

    /**
     * Processes an ActionMsg request.
     * @param request - The ActionMsg to be processed
     * @throws RemoteException if any remote connection issue occur during the processing
     */
    public void processRequest(ActionMsg request) throws RemoteException;
    
    /**
     * Processes a ConnectionActionMsg request.
     * @param request - The ConnectionActionMsg representing the connection request
     * @return the EventMsg related to the successful connection event
     * @throws RemoteException if any remote connection issue occur during the processing
     */
    public EventMsg processConnectRequest(ConnectionActionMsg request) throws RemoteException;

}
