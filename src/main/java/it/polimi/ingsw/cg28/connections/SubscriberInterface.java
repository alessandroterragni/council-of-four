package it.polimi.ingsw.cg28.connections;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.ingsw.cg28.view.messages.event.EventMsg;

/**
 * This interface provides the dispatchMessage method to dispatch an EventMsg to the other interacting part.
 * @author Marco
 *
 */
@FunctionalInterface
public interface SubscriberInterface extends Remote{
	
	/**
	 * Dispatches the input message to the client
	 * @param message - The EventMsg to dispatch
	 * @throws RemoteException if there is a remote connection issue during the message transmission.
	 */
    public void dispatchMessage(EventMsg message) throws RemoteException;

}
