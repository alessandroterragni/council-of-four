package it.polimi.ingsw.cg28.connections.client;

import java.io.IOException;

import it.polimi.ingsw.cg28.connections.BrokerInterface;
import it.polimi.ingsw.cg28.connections.RequestHandler;
import it.polimi.ingsw.cg28.view.ViewController;

/**
 * The Socket implementation of the ConnectionTypeFactory, creates a new SocketClient
 * which implements both the RequestHandler and the BrokerInterface.
 * @author Mario
 */
public class SocketFactory extends ConnectionTypeFactory {

    private static final int SOCKET_SERVER_PORT = 1337;
    private final SocketClient socketClient;

    /**
     * The constructor of the class
     * @param host - The host to associate the new Socket with
     * @param viewController - The view controller to associate to new client socket
     * @param playerName - The name of the player behind the client
     * @throws IOException if there is any issue during the creation of a new client socket
     */
    public SocketFactory(String host, ViewController viewController, String playerName) throws IOException {

        super(viewController, playerName);

        if (host == null) {
            throw new IllegalArgumentException("Host should not be null");
        }

        socketClient = new SocketClient(host, SOCKET_SERVER_PORT, getSubscriber());

        setupConnection();
        socketClient.start();

    }

    /**
     * {@inheritDoc} - Returns the client-side socket, which acts as a request handler for this type of connection.
     */
    @Override
    public RequestHandler getRequestHandler() {

        return socketClient;

    }

    /**
     * {@inheritDoc} - Returns the client-side socket, which acts as a broker for this type of connection.
     */
    @Override
    public BrokerInterface getBrokerInterface() {

        return socketClient;

    }
}