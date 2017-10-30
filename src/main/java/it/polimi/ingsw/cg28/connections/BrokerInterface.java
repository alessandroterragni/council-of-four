package it.polimi.ingsw.cg28.connections;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.ingsw.cg28.controller.PlayerID;

/**
 * This interface provides the methods to manage subscriptions.
 * @author Alessandro, Marco, Mario
 *
 */
public interface BrokerInterface extends Remote{

	/**
	 * Subscribes a Player, associating its PlayerID with the specified SubscriberInterface.
	 * @param subscriber - The SubscriberInterface to associate with the player to subscribe
	 * @param player - The PlayerID corresponding to the player to subscribe
	 * @throws RemoteException if any remote connection issue occur during the subscription
	 * @throws IllegalArgumentException if the input player is already subscripted with another SubscriberInterface
	 */
	void subscribe(SubscriberInterface subscriber, PlayerID player) throws RemoteException;

	
	/**
	 * Removes the specified SubscriberInterface, hence effectively unsubscribing the related Player.
	 * @param subscriber - The SubscriberInterface to remove; this is the interface associated to the PlayerID
	 * of the to-be-removed Player
	 * @throws RemoteException if any remote connection issue occur during the unsubscription
	 */
	void unsubscribe(SubscriberInterface subscriber) throws RemoteException;

}
