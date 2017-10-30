package it.polimi.ingsw.cg28.connections.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.cg28.connections.BrokerInterface;
import it.polimi.ingsw.cg28.connections.RequestHandler;
import it.polimi.ingsw.cg28.connections.SubscriberInterface;
import it.polimi.ingsw.cg28.view.ViewController;

/**
 * The RMI implementation of the ConnectionTypeFactory. Sets a new RMI connection up.
 * @author Alessandro, Marco, Mario
 *
 */
public class RMIFactory extends ConnectionTypeFactory {

    private static final Logger LOG = Logger.getLogger(RMIFactory.class.getName());

    public static final int RMI_PORT = 7777;

    private final BrokerInterface broker;
    private final RequestHandler requestHandler;
    private SubscriberInterface subscriberInterface;

    /**
     * The constructor of the class, sets a new RMI connection up.
     * @param host - The host for the new connection
     * @param viewController - The view controller associated with the new connection
     * @param playerName - The name of the player to be connected
     * @throws RemoteException if any issue occurs during the setup of the RMI connection
     * @throws NotBoundException if the bindings are not correctly set
     */
    public RMIFactory(String host, ViewController viewController, String playerName) throws
            RemoteException, NotBoundException {

        super(viewController, playerName);

        try {

            Registry registry = LocateRegistry.getRegistry(host, RMI_PORT);
            requestHandler = (RequestHandler) registry.lookup("RequestHandler");

            broker = (BrokerInterface) registry.lookup("Broker");
            
            subscriberInterface = (SubscriberInterface) UnicastRemoteObject
                    .exportObject(getSubscriber(), 0);

            setupConnection();

        } catch (RemoteException e) {
            LOG.log(Level.SEVERE, "There was a problem establishing a RMI connection to" +
                    " the game manager.", e);
            throw e;
        } catch (NotBoundException | ClassCastException e) {
            LOG.log(Level.SEVERE, "RMI connection error (problem with the remote " +
                    "interface).", e);
            throw e;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RequestHandler getRequestHandler() {

        return requestHandler;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BrokerInterface getBrokerInterface() {

        return broker;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SubscriberInterface getSubscriberInterface() {

        return subscriberInterface;

    }

}
