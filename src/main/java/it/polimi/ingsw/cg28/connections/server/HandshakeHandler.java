package it.polimi.ingsw.cg28.connections.server;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.cg28.connections.BrokerInterface;
import it.polimi.ingsw.cg28.connections.RequestHandler;
import it.polimi.ingsw.cg28.view.messages.action.ActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.ConnectionActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.SubscribeActionMsg;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;

/**
 * Manages the handshake procedure between the client's socket and the server's socket through a dedicated thread.
 * The procedure consists of the client's request, the server's acceptance and the actual establishment of
 * a data stream through a new socket connection.
 * 
 * @author Alessandro, Marco, Mario
 *
 */
public class HandshakeHandler extends SocketHandler implements Runnable {
	
	private static final Logger LOG = Logger.getLogger(ClientHandler.class.getName());
	
	private ClientHandler clientHandler;
	private SubscriberHandler subscriberHandler;
	private RequestHandler requestHandler;
	private ExecutorService executor;

	/**
	 * The constructor of the class, creates a new handler for the handshake procedure.
	 * @param socket - The socket to connect to the server's one
	 * @param requestHandler - The request handler implementation for this procedure
	 * @param broker - The broker implementation for this procedure
	 * @param executor - The executor service used to run threads involved in the procedure
	 */
	public HandshakeHandler(Socket socket, RequestHandler requestHandler, BrokerInterface broker, ExecutorService executor) {
		super(socket);
		this.requestHandler = requestHandler;
		this.executor = executor;
		clientHandler = new ClientHandler(socket, requestHandler);
		subscriberHandler = new SubscriberHandler(socket, broker);
	}

	/**
	 * The run method for the thread that resolves the handshake procedure.
	 */
	@Override
	public void run() {
		
		try {
			
			EventMsg response;
			
            Object request = clientHandler.receiveObject();

            if (request instanceof ConnectionActionMsg) {
            	
                response = requestHandler.processConnectRequest((ConnectionActionMsg) request);
                clientHandler.sendResponse(response);
                
            } else {
            	
            	if (!(request instanceof ActionMsg)){
	                response = new InvalidRequestMsg("Unknown request.");
	                clientHandler.sendResponse(response);
            	}
            }
            
            SubscribeActionMsg subscribeRequest;
            subscribeRequest = subscriberHandler.receiveSubscriptionRequest();
            subscriberHandler.manageSubscription(subscribeRequest);
            
            executor.execute(clientHandler);
            executor.execute(subscriberHandler);

        } catch (IOException | ClassNotFoundException e) {
            LOG.log(Level.WARNING, "Unhandled errors during client-server communication", e);
        }
		
	}

}
