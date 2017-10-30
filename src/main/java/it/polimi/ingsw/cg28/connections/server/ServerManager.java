
package it.polimi.ingsw.cg28.connections.server;
import java.util.logging.Logger;

import it.polimi.ingsw.cg28.connections.PublisherInterface;

/**
 * This class contains the server's main method.
 * @author Alessandro, Marco, Mario
 *
 */
public class ServerManager {
	
	 private static final Logger log = Logger.getLogger(ServerManager.class.getName());
	    
    /**
     * Private constructor to hide the implicit public one. Class ServerManager couldn't be instantiated.
     */
	private ServerManager(){
		
	}
	
	/**
	 * Initialises a new server-side connection factory, sets the publisher and the games controller, then
	 * starts all servers.
	 * @param args
	 */
	public static void main(String[] args) {

       ServerConnectionFactory startServer = new ServerConnectionFactory();
       
       PublisherInterface publisherInterface = startServer.getPublisherInterface();
       ControllerMultipleGames mainController = new ControllerMultipleGames(publisherInterface);

       startServer.setRequestHandler(mainController);
       startServer.startServers();
       
       log.info("Server is on!\n");

    }

	

}
 