package it.polimi.ingsw.cg28.connections.server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.cg28.connections.BrokerInterface;
import it.polimi.ingsw.cg28.connections.PublisherInterface;
import it.polimi.ingsw.cg28.connections.RequestHandler;

/**
 * Creates, starts and disconnects all types of servers and server connection services, both for RMI and Socket
 * architectures.
 * @author Alessandro, Marco, Mario
 *
 */
public class ServerConnectionFactory {

    private static final Logger LOG = Logger.getLogger(ServerConnectionFactory.class.getName());

    private static final int RMI_PORT = 7777;
    private static final int SOCKET_SERVER_PORT = 1337;

    private RequestHandler requestHandler;
    private final Broker broker;
    private Registry registry;
    private SocketServer server;

    /**
     * The constructor of the class, creates and initialises a new connection factory.
     */
    public ServerConnectionFactory() {

        broker = new Broker();

    }

    /**
     * Sets the RequestHandler to the desired implementation of the interface.
     * @param requestHandler - The request handler to be set
     */
    public void setRequestHandler(RequestHandler requestHandler) {

        this.requestHandler = requestHandler;

    }

    /**
     * Fetches the publisher interface, or the broker, associated with this connection factory.
     * @return the broker/publisher interface for this connection factory
     */
    public PublisherInterface getPublisherInterface() {

        return broker;

    }

    /**
     * Starts the servers, after checking if the request handler has been set or not.
     */
    public void startServers() {

        if (requestHandler == null) {
            throw new IllegalStateException("RequestHandler is not set.");
        }

        startRMI();
        startSocket();

    }

    /**
     * Stops the running servers.
     */
    public void stopServers() {

        releaseRMI();
        releaseSocket();

    }

    /**
     * Starts the RMI server connection by locating and initialising the registry and binding the broker
     * and the request handler to it. 
     */
    private void startRMI() {

        try {

            registry = LocateRegistry.createRegistry(RMI_PORT);

            BrokerInterface brokerStub = (BrokerInterface) UnicastRemoteObject
                    .exportObject(broker, 0);

            registry.rebind("Broker", brokerStub);

            RequestHandler reqHandlerStub = (RequestHandler) UnicastRemoteObject
                    .exportObject(requestHandler, 0);

            registry.rebind("RequestHandler", reqHandlerStub);


        } catch (RemoteException e) {
            LOG.log(Level.SEVERE, "Can't initialize RMI Registry.", e);
        }

    }

    /**
     * Unbinds all components from the registry, hence stopping the RMI communication from the server's side.
     */
    private void releaseRMI() {

        try {
        	
        	registry.unbind("RequestHandler");
            UnicastRemoteObject.unexportObject(requestHandler, true);
            registry.unbind("Broker");
            UnicastRemoteObject.unexportObject(broker, true);

        } catch (RemoteException | NotBoundException e) {
            LOG.log(Level.WARNING, "Exception thrown while releasing RMI.", e);
        }

    }

    /**
     * Initializes and starts a new socket server, using request handler and broker declarated above.
     */
    private void startSocket() {

        try {

            server = new SocketServer(SOCKET_SERVER_PORT, Executors.newCachedThreadPool(), requestHandler, broker);

            server.start();

        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Can't initialize Socket server.", e);
        }

    }

    /**
     * Stops the running socket server.
     */
    private void releaseSocket() {

        server.stopServer();

    }

}
