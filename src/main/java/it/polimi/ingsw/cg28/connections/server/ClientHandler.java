package it.polimi.ingsw.cg28.connections.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.cg28.connections.RequestHandler;
import it.polimi.ingsw.cg28.view.messages.action.ActionMsg;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;

/**
 * Manages the client's communication with the server via socket architecture.
 * @author Alessandro, Marco, Mario
 *
 */
public class ClientHandler extends SocketHandler {

    private static final Logger LOG = Logger.getLogger(ClientHandler.class.getName());

    private final RequestHandler requestHandler;

    /**
     * The constructor of the class, creates a new ClientHandler
     * @param client - The client socket associated with this handler
     * @param requestHandler - The request handler interface associated with this handler
     */
    public ClientHandler(Socket client, RequestHandler requestHandler) {

        super(client);
        this.requestHandler = requestHandler;

    }

    /**
     * Receives an object from the associated socket's input stream.
     * @return the next object in the input stream
     * @throws ClassNotFoundException if there is any issue with class recognition
     * @throws IOException if there is any issue reading data from the input stream
     */
    protected Object receiveObject() throws ClassNotFoundException, IOException {

        Object receivedObject;

        try {

            ObjectInputStream inputStream = new ObjectInputStream(getSocket().getInputStream());

            receivedObject = inputStream.readObject();

        } catch (SocketException e) {
            LOG.log(Level.WARNING, "Error while setting socket timeout.", e);
            throw e;
        } catch (IOException e) {
            LOG.log(Level.WARNING, "Error while receiving message from client.", e);
            throw e;
        } 
        	catch (ClassNotFoundException e) {
            LOG.log(Level.WARNING, "Can't read from input stream.", e);
            throw e;
        }

        return receivedObject;

    }

    /**
     * Sends an object as a response to the server through its dedicated socket communication.
     * @param response - The object to be sent to the server as a response
     */
    protected void sendResponse(Object response) {

        try {

            ObjectOutputStream outputStream = new ObjectOutputStream(getSocket().getOutputStream());
            if (response != null) {
                outputStream.writeObject(response);
            } else {
                outputStream.writeObject(new InvalidRequestMsg("There was a problem " +
                        "handling the request."));
            }
            outputStream.flush();

        } catch (IOException e) {
            LOG.log(Level.WARNING, "Can't reply to the client.", e);
        } 

    }

    /**
     * The run method for the client's socket communication thread.
     * It receives the client's request and forwards it to the server, according to its type.
     */
    @Override
    public void run() { 
    	
    	while (true){
        	
		        try {
		
		            Object request = receiveObject();

		            if (request instanceof ActionMsg) {
		                requestHandler.processRequest((ActionMsg) request);
		            } else {
		            	
		            	synchronized(getSocket()){
		            		EventMsg response = new InvalidRequestMsg("Unknown request.");
		            		sendResponse(response);
		            	}
		            }
		
		     
		        } catch (IOException | ClassNotFoundException e) {
		            LOG.log(Level.WARNING, "Unhandled errors during client-server communication", e);
		            return; 
		        }
	        }
     }

}
