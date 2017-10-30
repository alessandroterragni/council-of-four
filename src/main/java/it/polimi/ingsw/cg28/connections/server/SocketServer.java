package it.polimi.ingsw.cg28.connections.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.cg28.connections.BrokerInterface;
import it.polimi.ingsw.cg28.connections.RequestHandler;

/**
 * Represents a server-side socket.
 * @author Alessandro, Marco, Mario
 *
 */
public class SocketServer extends Thread {

    private static final Logger LOG = Logger.getLogger(SocketServer.class.getName());

    private final int port;
    private final ExecutorService executorService;
    private boolean isStopped;
    private RequestHandler requestHandler;
    private BrokerInterface broker;

    /**
     * The constructor of the class, creates and initialises a new server socket.
     * @param port - The port this socket will be associated to
     * @param executorService - The executor used to run threads associated with the connection to this socket
     * @param requestHandler - The request handler for this socket
     * @param broker - The broker associated with this socket
     */
    public SocketServer(int port, ExecutorService executorService, RequestHandler requestHandler, BrokerInterface broker) {

    	this.port = port;
        this.executorService = executorService;
        this.isStopped = false;
        this.requestHandler = requestHandler;
        this.broker = broker;

    }

    /**
     * Stops the executor service and sets to false the variable that triggers the execution of this socket's thread.
     */
    public synchronized void stopServer() {

        executorService.shutdownNow();
        this.isStopped = true;

    }

    /**
     * The run method for the server's socket dedicated thread.
     */
    @Override
    public void run() {


        try (ServerSocket welcomeSocket = new ServerSocket(getPort())){
       
        	while (!isStopped()) {
	
	            Socket socket = accept(welcomeSocket);
	            getExecutorService().execute(new HandshakeHandler(socket, requestHandler, broker, executorService));
	
	        }

        } catch (IOException e) {
            LOG.log(Level.WARNING, "Error on socket server.", e);
        }

    }

    /**
     * Method to accept one connection and create a new socket
     * @param welcomeSocket the servers'socket
     * @return the new socket created to manage the request of a new client
     */
    private Socket accept(ServerSocket welcomeSocket) {
    	
    	try {
    		
            return welcomeSocket.accept();

        } catch (IOException e) {
        	LOG.log(Level.WARNING, "Error while creating a new socket.", e);
        }
    	
    	LOG.log(Level.WARNING, "Socket not created!");
    	return null;
		
	}

	/**
     * Fetches the server socket's port.
     * @return the server socket's port
     */
    protected int getPort() {

        return port;

    }

    /**
     * Fetches the executor service related to this socket.
     * @return the socket's executor service
     */
    protected ExecutorService getExecutorService() {

        return executorService;

    }

    /**
     * Fetches the socket's request handler.
     * @return the socket's request handler
     */
    protected RequestHandler getRequestHandler() {

        return requestHandler;

    }

    /**
     * Used to retrieve the boolean value that indicates whether this socket is active or not, thus obtaining
     * information on the server's status.
     * @return <code>true</code> if the socket is active, <code>false</code> otherwise
     */
    protected boolean isStopped() {

        return isStopped;

    }
    
    /**
     * Sets the isStopped attribute to the desired value.
     * @param isStopped - The boolean value to set
     */
    protected void setStopped(boolean isStopped) {

        this.isStopped = isStopped;

    }

}