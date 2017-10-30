package it.polimi.ingsw.cg28.connections.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.cg28.connections.BrokerInterface;
import it.polimi.ingsw.cg28.connections.SubscriberInterface;
import it.polimi.ingsw.cg28.view.messages.action.SubscribeActionMsg;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;
import it.polimi.ingsw.cg28.view.messages.event.SubscribeEventMsg;

/**
 * Manages the subscription paradigm for the socket architecture.
 * @author Alessandro, Marco, Mario
 *
 */
public class SubscriberHandler extends SocketHandler implements SubscriberInterface {

    private static final Logger LOG = Logger.getLogger(SubscriberHandler.class.getName());
    private static final int TIMEOUT = 3000;

    private final BrokerInterface brokerInterface;

    private final Queue<EventMsg> broadcastBuffer;
    private ObjectOutputStream outputStream;

    boolean isSubscribed;

    /**
     * The constructor of the class, creates a new handler and initializes the queue used to
     * buffer the events that have to be notified to the subscriber.
     * @param socket - The client socket associated with this handler
     * @param brokerInterface - The broker interface associated with this handler
     */
    public SubscriberHandler(Socket socket, BrokerInterface brokerInterface) {

        super(socket);
        this.brokerInterface = brokerInterface;

        broadcastBuffer = new ConcurrentLinkedQueue<>();

        isSubscribed = false; 

    }

    /**
     * Handles a subscription request, subscribing a client and notifying the success to the client itself.
     * @param subscribeRequest - The subscription message to be processed
     * @throws IOException if any issue occurs during the processing of the request
     */
    protected void manageSubscription(SubscribeActionMsg subscribeRequest) throws IOException {

        try {

            brokerInterface.subscribe(this, subscribeRequest.getPlayerID());
            isSubscribed = true;

        } catch (RemoteException e) {
            LOG.log(Level.WARNING, "Remote exception during subscription.", e);
        }

        EventMsg responseSub = new SubscribeEventMsg(isSubscribed);
        sendMsg(responseSub);

    }

    /**
     * Receives a subscription request through the socket's input stream.
     * @return the subscription request message to be managed
     */
    protected SubscribeActionMsg receiveSubscriptionRequest() {

    	SubscribeActionMsg subscribeMessage = null;

        try {

            getSocket().setSoTimeout(TIMEOUT);
            ObjectInputStream inputStream = new ObjectInputStream(getSocket().getInputStream());
            Object object = inputStream.readObject();
            
            if (!(object instanceof SubscribeActionMsg)) 
            	throw new ClassCastException();
            
            subscribeMessage = (SubscribeActionMsg) object;
            
            getSocket().setSoTimeout(0);

        } catch (SocketException e) {
            LOG.log(Level.WARNING, "Unhandled socket exception.", e);
        } catch (IOException e) {
            LOG.log(Level.WARNING, "Error while reading from input.", e);
        } catch (ClassNotFoundException e) {
            LOG.log(Level.WARNING, "Received invalid message from client.", e);
        }

        return subscribeMessage;

    }

    /**
     * Unsubscribes the client associated with this socket.
     */
    private void unsubscribe() {

        if (isSubscribed) {
            try {
                brokerInterface.unsubscribe(this);
                isSubscribed = false;
            } catch (RemoteException e) {
                LOG.log(Level.WARNING, "Unhandled remote exception.", e);
            } catch (IllegalArgumentException e) {
                LOG.log(Level.WARNING, "Error while unsubscribing.", e);
            }
        }

    }

    /**
     * Sends an object as a message to the server, via the client socket's output stream.
     * @param message - The message to be sent
     * @throws IOException if any issue occurs during the transmission
     */
    private void sendMsg(Object message) throws IOException {

        try {
                
        	outputStream = new ObjectOutputStream(getSocket().getOutputStream());

            outputStream.writeObject(message);
            outputStream.flush();
        } 
        
        catch (IOException e) {
            LOG.log(Level.WARNING, "Error while writing to the output stream.", e);
            unsubscribe();
        }

    }

    /**
     * Cycles to empty the event message buffer, forwarding the messages to the server via socket connection.
     * @throws IOException if there is any issue during the transmission of the messages 
     * @throws InterruptedException if there is any issue during with the concurrent reading of the
     * message buffer
     */
    private void broadcastBuffer() throws IOException, InterruptedException {

        synchronized (broadcastBuffer) {

            while (broadcastBuffer.isEmpty()) {

                try {
                    broadcastBuffer.wait();
                } catch (InterruptedException e) {
                	LOG.log(Level.WARNING, "Exception on broadcast buffer wait.", e);
                    throw e;
                }

            }

            EventMsg broadcastMessage;
            do {
                broadcastMessage = broadcastBuffer.poll();
                if (broadcastMessage != null)
                    sendMsg(broadcastMessage);
            } while (broadcastMessage != null);

        }

    }

    /**
     * The run method for this handler's thread. Continuously broadcasts the event messages to the server
     * via socket connection.
     */
    @Override
    public void run() {

        try {

            while (true) {
            	
            	broadcastBuffer();
            	
            }

        } 
        
        catch (IOException e) {
        	LOG.log(Level.WARNING, "Error on message dispatching.", e);
        	unsubscribe();

        }
        
        catch (InterruptedException e) {
            LOG.log(Level.WARNING, "InterruptedException occurred.");
            Thread.currentThread().interrupt();
        } 
        
        finally {
		            try {
		                getSocket().close();
		            } catch (IOException e) {
		                LOG.log(Level.SEVERE, "Can't close socket.", e);
		            }
        		}

    }

    /**
     * Adds the argument to the message queue, then unlocks the buffer to enable other threads to work on it.
     */
    @Override
    public void dispatchMessage(EventMsg message) throws RemoteException {

        broadcastBuffer.add(message);
        synchronized (broadcastBuffer) {
            broadcastBuffer.notify();
        }

    }

}
