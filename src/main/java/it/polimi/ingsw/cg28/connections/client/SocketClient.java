package it.polimi.ingsw.cg28.connections.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.cg28.connections.BrokerInterface;
import it.polimi.ingsw.cg28.connections.RequestHandler;
import it.polimi.ingsw.cg28.connections.SubscriberInterface;
import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.view.messages.action.ActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.ConnectionActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.SubscribeActionMsg;
import it.polimi.ingsw.cg28.view.messages.event.ConnectionEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;
import it.polimi.ingsw.cg28.view.messages.event.SubscribeEventMsg;

/**
 * Represents a client's socket thread.
 * @author Alessandro, Marco, Marco
 *
 */
public class SocketClient extends Thread implements BrokerInterface, RequestHandler {

    private static final Logger LOG = Logger.getLogger(SocketClient.class.getName());

    // Maximum timeout to receive a response trough a socket
    private static final int TIMEOUT = 3000;
    
    // The SubscriberInterface where to dispatch pub-sub messages
    private final SubscriberInterface subscriberInterface;

    private Socket socket;

    // True after a successful subscription
    private AtomicBoolean subscribed;
    
    /**
     * The constructor of the class, creates a new SocketClient.
     * @param host - The String containing the Host address
     * @param serverPort - The integer number of the TCP port for the socket connection
     * @param subscriberInterface - The SubscriberInterface to use to dispatch messages to the client through
     * its dedicated socket
     * @throws IOException if any IO issues occur during the creation of the object
     */
    public SocketClient(String host, int serverPort, SubscriberInterface subscriberInterface) throws IOException {

        if (host == null) {
            throw new IllegalArgumentException("Host cannot be null.");
        } else if (subscriberInterface == null) {
            throw new IllegalArgumentException("SubscriberInterface cannot be null.");
        }

        this.subscriberInterface = subscriberInterface;

        // Setup the connection
        socket = new Socket(host, serverPort);
        subscribed = new AtomicBoolean(false);

    }
    
    /**
     * The run method for the client's socket dedicated thread.
     */
    @Override
    public void run() {

        try {
            while (true) {

                waitForSubscription();
                Object received;

                synchronized (socket) {

                    socket.setSoTimeout(0);
                    ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                    received = inputStream.readObject();

                }

                if (received instanceof EventMsg) {
                    EventMsg responseMessage = (EventMsg) received;
                    subscriberInterface.dispatchMessage(responseMessage);
                }

            }
        } catch (ClassNotFoundException e) {
            LOG.log(Level.SEVERE, "Error while receiving a subscribe message.", e);
        } catch (RemoteException e) {
            LOG.log(Level.SEVERE, "Error in the subscribe connection.", e);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error occurred while reading from socket.", e);
        }

    }

    /**
     * Private method used to forward requests to the server via socket connection.
     * @param request - The request to be sent
     * @throws IOException if any IO issue occur while forwarding the request message
     * @throws ClassNotFoundException if any of the messages' classes are not the correct ones to process
     */
    private void sendServerRequest(ActionMsg request) throws IOException,
            ClassNotFoundException {
    	
    	ObjectOutputStream serverOutputStream = new ObjectOutputStream(socket
                .getOutputStream());

        serverOutputStream.writeObject(request);
        serverOutputStream.flush();

    }
    
    /**
     * {@inheritDoc} - This implementation forwards the request to the server via the socket connection; the server
     * itself can then proceed to handle it.
     */
    @Override
    public void processRequest(ActionMsg request) {

        try {

            sendServerRequest(request);

        } catch (IOException e) {
            LOG.log(Level.SEVERE, "There was a problem with the server request.", e);
        } catch (ClassNotFoundException e) {
            LOG.log(Level.SEVERE, "An invalid message has been returned from the " +
                    "server", e);
        }

    }
    
    /**
     * {@inheritDoc} - This implementation sends the connection request to the server and returns its
     * response as a ConnectionEventMsg.
     */
    @Override
    public EventMsg processConnectRequest(ConnectionActionMsg request) throws RemoteException {

            try {
            	
				sendServerRequest(request);
            
				ObjectInputStream serverInputStream = new ObjectInputStream(socket.getInputStream());

				Object response = serverInputStream.readObject();
            
				if (response instanceof EventMsg) {

					return (EventMsg)response;

				}
				
            } catch (IOException e) {
                LOG.log(Level.SEVERE, "There was a problem with the server request.", e);
            } catch (ClassNotFoundException e) {
                LOG.log(Level.SEVERE, "An invalid message has been returned from the " +
                        "server", e);
            }

            throw new RemoteException("An error occurred while processing a request.");

    }

    /**
     * {@inheritDoc} - This implementation requests the ID generation from the server through the socket.
     */
    @Override
    public PlayerID connect(String playerName) throws RemoteException {
    	
       //playerID equals to null identifies a request of connection
       EventMsg response = processConnectRequest(new ConnectionActionMsg(null, playerName));

        if (response instanceof ConnectionEventMsg) {
            return ((ConnectionEventMsg) response).getPlayerID();
        }
        
        LOG.log(Level.SEVERE, "There was a problem while establishing a new connection" +
                " with the server.");
        throw new RemoteException("Error while establishing a new connection to the " +
                "server.");

    }
    
    /**
     * Send to the server a subscribe request
     * @param player PlayerID to communicate with server
     * @throws RemoteException If there's an error while writing to the socket
     */
    private void sendSubscribeRequest(PlayerID player) throws RemoteException {
    	
    	try {

            synchronized (socket) {
                ObjectOutputStream serverOutputStream = new ObjectOutputStream
                        (socket.getOutputStream());

                serverOutputStream.writeObject(new SubscribeActionMsg(player));
                serverOutputStream.flush();
            }

        } catch (IOException e) {
            throw new RemoteException("Error while writing to the socket.", e);
        }
    	
    }
    
    /**
     * Wait a subscribe response from the server
     * @return Object response
     * @throws RemoteException If there's an error while reading from the socket
     */
    private Object receiveSubscribeRequest() throws RemoteException{
    	
    	Object response = null;
    	
    	try {
	    	synchronized (socket) {
	            socket.setSoTimeout(TIMEOUT);
	            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
	            response = inputStream.readObject();
	            socket.setSoTimeout(0);
	        }
	    	
    	 } catch (IOException e) {
             throw new RemoteException("Error while reading from the socket.", e);
         } catch (ClassNotFoundException e) {
             throw new RemoteException("An invalid message has been returned from " +
                     "the server.", e);
         }
	    	
    	return response;
    		
    }

    /**
     * {@inheritDoc} - Signals the subscription to the server, then it waits the server's response; if
     * the server answers with a message that confirms the subscription, this method sets the subscribed
     * attribute to <code>true</code>
     */
    @Override
    public void subscribe(SubscriberInterface subscriber, PlayerID player) throws RemoteException {

        Object response = null;

        try {
        		sendSubscribeRequest(player);

        		response = receiveSubscribeRequest();       

        } catch (RemoteException remoteException) {
        	
        	LOG.log(Level.WARNING, "Error during subscribe process", remoteException);
            throw remoteException;

        }

       if (!(response instanceof SubscribeEventMsg)) {
    	   throw new IllegalArgumentException("Bad subscribe arguments.");
       }

        setSubscribed(((SubscribeEventMsg)response).isSubscribed());

    }

    /**
     * {@inheritDoc} - Closes the connection socket and sets the subscribed attribute to <code>false</code>.
     */
    @Override
    public void unsubscribe(SubscriberInterface subscriber) throws RemoteException {

        try {

            synchronized (socket) {
                socket.close();
            }

        } catch (IOException e) {
        	LOG.log(Level.WARNING, "Error during unsubscribe process", e);
            throw new RemoteException("An error occurred while closing the connection.", e);
        }

        setSubscribed(false);

    }

    /**
     * Sets the subscribed attribute to the desired value.
     * @param subscribed - The boolean value to be set
     */
    private void setSubscribed(boolean subscribed) {

        synchronized (this.subscribed) {
            this.subscribed.set(subscribed);
            this.subscribed.notifyAll();
        }

    }

    /**
     * Private method used to make the thread wait indefinitely for a subscription event, monitoring
     * the AtomicBoolean subscribed.
     */
    private void waitForSubscription() {

        synchronized (subscribed) {
            while (!subscribed.get()) {

                try {
                    subscribed.wait();
                } catch (InterruptedException e) {
                    LOG.log(Level.SEVERE, "InterruptedException occurred.", e);
                    Thread.currentThread().interrupt();
                }

            }

        }

    }
    
}