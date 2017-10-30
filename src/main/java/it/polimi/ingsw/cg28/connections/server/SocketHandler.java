package it.polimi.ingsw.cg28.connections.server;

import java.net.Socket;

/**
 * This abstract class contains a basic structure to handle a socket connection. Implements
 * Runnable for multithreading support.
 * @author Alessandro, Marco, Mario
 */
abstract class SocketHandler implements Runnable {

    private final Socket socket;

    /**
     * The constructor of the class, creates an new socket handler.
     * @param socket - The socket associated with this handler
     */
    SocketHandler(Socket socket) {

        this.socket = socket;
    }

    /**
     * Fetches the handler's associated socket.
     * @return this handler's socket
     */
    protected Socket getSocket() {

        return socket;

    }

}