/**
 * 
 */
package it.polimi.ingsw.cg28.view.gui;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.cg28.connections.RequestHandler;
import it.polimi.ingsw.cg28.connections.client.Client;
import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.view.messages.action.ActionMsg;

/**
 * Manages the update of a GUI client.
 * @author Alessandro, Mario
 *
 */
public class GuiManager {

	private static final Logger log = Logger.getLogger(Client.class.getName());
	
	private PlayerID player;
	private RequestHandler requestHandler;
	
	/**
	 * The constructor of the class, creates a new client manager.
	 * @param requestHandler The associated request handler
	 * @param player The player associated with the client to be managed
	 * @throws NullPointerException if one of the three parameter is null
	 */
	public GuiManager(RequestHandler requestHandler,PlayerID player) {
		this.requestHandler = requestHandler;
		this.player = player;
	}
	
	/**
	 * Process a request through request handler.
	 * @param change - request to process
	 */
	public void processRequest(ActionMsg change) {
		if (change == null) {
	        log.warning("Change could not be null!");
	        return;
	    }

		change.setPlayerID(player);

         try {
			requestHandler.processRequest(change);
		} catch (RemoteException e) {
			log.log(Level.WARNING, "Error during process request", e);
		}
		
	}

	/**
	 * Returns the playerID related to the client.
	 * @return the player
	 */
	public PlayerID getPlayer() {
		return player;
	}
	
	

}
