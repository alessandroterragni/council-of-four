package it.polimi.ingsw.cg28.connections;

import java.util.Set;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;

/**
 * This interface provides the methods to manage the publish-subscribe paradigm.
 * @author Alessandro, Marco, Mario
 *
 */
public interface PublisherInterface {

	/**
	 * Publishes an event message related to a topic, triggering the message dispatch for the right subscribers.
	 * @param message - The EventMsg that should be published in relation to the topic
	 * @param topic - The String identifying the topic to which the message is related
	 */
    void publish(EventMsg message, String topic);

    /**
     * Adds a topic to the caller's set if it is not already there.
     * @param topic - The topic to be added
     */
    void addTopic(String topic);

    /**
     * Adds a topic to the caller's set and subscribes all the input players to it.
     * @param topic - The topic to be added
     * @param clients - The clients to be subscribed to the newly added topic
     */
    void addTopic(String topic, Set<PlayerID> clients);
    
    /**
     * Subscribes a client to a topic.
     * @param topic - The topic to subscribe the client to
     * @param client - The client to subscribe
     */
    void subscribeClientToTopic(String topic, PlayerID client);

    /**
     * Subscribes a set of clients to a topic.
     * @param topic - The topic to subscribe the clients to
     * @param clients - The clients to be subscribed
     */
    void subscribeClientsToTopic(String topic, Set<PlayerID> clients);

    /**
     * Removes the subscription of a client from a topic.
     * @param topic - The topic to unsubscribe the client from
     * @param client - The client to be unsubscribed
     */
    void unsubscribeClientFromTopic(String topic, PlayerID client);

    /**
     * Removes the client from all its former associations with topics.
     * @param client - The client to be removed
     */
    void removeClient(PlayerID client);

    /**
     * Removes all the specified clients from their former associations with topics.
     * @param clients - The set of clients to be removed
     */
    void removeClients(Set<PlayerID> clients);

    /**
     * Removes a topic from the caller's set of topics.
     * @param topic - The topic to be removed
     */
    void removeTopic(String topic);

}