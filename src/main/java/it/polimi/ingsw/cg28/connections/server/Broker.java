package it.polimi.ingsw.cg28.connections.server;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import it.polimi.ingsw.cg28.connections.BrokerInterface;
import it.polimi.ingsw.cg28.connections.PublisherInterface;
import it.polimi.ingsw.cg28.connections.SubscriberInterface;
import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;

/**
 * The Broker class manages all the functions related to publishing topic updates and subscription management.
 * @author Alessandro, Marco, Mario
 *
 */
public class Broker implements BrokerInterface, PublisherInterface {
	
	 	private static final Logger LOG = Logger.getLogger(Broker.class.getName());

	    private final Set<String> topics;
	    private final Multimap<String, PlayerID> subscriptions;
	    private final Map<PlayerID, SubscriberInterface> subscribers;

	   /**
	    * The constructor of the class, creates a new Broker object initialising the subscribers HashMap,
	    * the topics HashSet and the subscriptions HashMultiMap.
	    */
	    public Broker() {

	        subscriptions = HashMultimap.create();
	        subscribers = new HashMap<>();
	        topics = new HashSet<>();

	    }

	    /**
	     * {@inheritDoc} - Inserts the PlayerID value to the subscribers Map.
	     */
	    @Override
	    public synchronized void subscribe(SubscriberInterface subscriber, PlayerID player) {

	        Preconditions.checkNotNull(subscriber,"SubscriberInterface must not be null.");
	        Preconditions.checkNotNull(player,"Player must not be null.");

	        if (subscribers.values().contains(subscriber)) {
	            return; // Already in subscribers
	        } else if (subscribers.keySet().contains(player)) {
	            throw new IllegalArgumentException("Subscriber is already connected to the " +
	                    "broker with another interface.");
	        }

	        subscribers.put(player, subscriber);

	    }

	    /**
	     * {@inheritDoc} - Removes the subscriber from the subscribers Map, if it was there.
	     */
	    @Override
	    public synchronized void unsubscribe(SubscriberInterface subscriber) {

	        // If it was already removed nothing happens
	        boolean unsubscribed = subscribers.values().remove(subscriber);

	        if (unsubscribed) {
	            LOG.log(Level.INFO, "Client unsubscribed.");
	        }

	    }

	    /**
	     * {@inheritDoc} - This implementation checks if the topic is valid, then triggers the message dispatch
	     * to all interested clients: if the EventMsg contains a Player name equal to the topic, this means
	     * that the message should be dispatched to all clients subscripted to the topic; otherwise, the method
	     * automatically dispatches the message to the one interested client (whose name is the one
	     * contained in the EventMsg).
	     */
	    @Override
	    public synchronized void publish(EventMsg message, String topic) {

	    	Preconditions.checkNotNull(message, "Message must not be null.");
	      
	    	if (!topics.contains(topic)) {
	            throw new IllegalArgumentException("Invalid topic.");
	        }

	        // Find the clients subscribed to a topic
	        // All elements having topic as a key
	        Collection<PlayerID> subscribedToTopic = subscriptions.get(topic);
	        
	        if(message.getPlayer().getName().equals(topic))
	        	publishAll(message, topic);
	        else{
	        	
	        	if(subscribedToTopic.contains(message.getPlayer())){
	        		
		            SubscriberInterface subscriber = subscribers.get(message.getPlayer());
	
		            if (subscriber == null) {
		                LOG.log(Level.INFO, "Found a client not connected to the broker.");
		                return;
		            }
	
		            try {
		                subscriber.dispatchMessage(message);
		            } catch (RemoteException e) {
		                LOG.log(Level.INFO, "Error while dispatching message to subscriber.", e);
		                unsubscribe(subscriber);
		            }
	        	}

	        }

	    }

	    /**
	     * Dispatches a message to all the clients subscribed to the specified topic.
	     * @param message - The message to be dispatched
	     * @param topic - The topic to which the notified clients must be subscribed
	     */
	    private void publishAll(EventMsg message, String topic) {

	    	Collection<PlayerID> subscribedToTopic = subscriptions.get(topic);
	    	
	    	for (PlayerID player : subscribedToTopic) {

	            // Get the subscriber interface to communicate with the client (if any)
	            SubscriberInterface subscriber = subscribers.get(player);

	            if (subscriber == null) {
	                LOG.log(Level.INFO, "Found a client not connected to the broker.");
	                continue; // Don't send anything
	            }

	            try {
	                // Try to dispatch the message
	                subscriber.dispatchMessage(message);
	            } catch (RemoteException e) {
	                LOG.log(Level.INFO, "Error while dispatching message to subscriber.", e);
	                unsubscribe(subscriber);
	            }
	    	}
			
		}

	    /**
	     * {@inheritDoc}
	     */
		@Override
	    public synchronized void addTopic(String topic) {

	        if (topics.contains(topic)) {
	            throw new IllegalArgumentException("Topic already present.");
	        }

	        topics.add(topic);

	    }

		/**
		 * {@inheritDoc}
		 */
	    @Override
	    public void addTopic(String topic, Set<PlayerID> clients) {

	        addTopic(topic);
	        subscribeClientsToTopic(topic, clients);

	    }

	    /**
	     * {@inheritDoc}
	     * @throws NullPointerException if player parameter is null
	     * @throws IllegalArgumentException if topic isn't in topics list
	     */
	    @Override
	    public void subscribeClientToTopic(String topic, PlayerID player) {

	        if (!topics.contains(topic)) {
	            throw new IllegalArgumentException("Invalid topic.");
	        } else if (player == null) {
	            throw new NullPointerException("Client must not be null");
	        }

	        subscriptions.put(topic, player);

	    }

	    /**
	     * {@inheritDoc}
	     * @throws NullPointerException if clients parameter is null
	     * @throws IllegalArgumentException if topic isn't in topics list
	     */
	    @Override
	    public void subscribeClientsToTopic(String topic, Set<PlayerID> clients) {

	        if (!topics.contains(topic)) {
	            throw new IllegalArgumentException("Invalid topic(does not exist).");
	        } else if (clients == null) {
	            throw new NullPointerException("Clients must not be null");
	        }

	        subscriptions.putAll(topic, clients);

	    }

	    /**
	     * {@inheritDoc}
	     * @throws NullPointerException if client parameter is null
	     * @throws IllegalArgumentException if topic isn't in topics list
	     */
	    @Override
	    public void unsubscribeClientFromTopic(String topic, PlayerID client) {

	        if (!topics.contains(topic)) {
	            throw new IllegalArgumentException("Invalid topic (does not exist).");
	        } else if (client == null) {
	            throw new NullPointerException("Client must not be null");
	        }

	        subscriptions.remove(topic, client);

	    }

	    /**
	     * {@inheritDoc} - Removes the value of the client from the subscriptions MultiMap.
	     * @throws NullPointerException if client parameter is null
	     */
	    @Override
	    public void removeClient(PlayerID client) {

	        if (client == null) {
	            throw new NullPointerException("Client must not be null");
	        }

	        subscriptions.values().remove(client);

	    }

	    /**
	     * {@inheritDoc} - Removes the value of all specified clients from the subscriptions MultiMap.
	     * @throws NullPointerException if clients parameter is null
	     */
	    @Override
	    public void removeClients(Set<PlayerID> clients) {

	        if (clients == null) {
	            throw new NullPointerException("Client must not be null");
	        }

	        subscriptions.values().removeAll(clients);

	    }

	    /**
	     * {@inheritDoc} - Removes the topic from the topics set, removing all the associations with it from
	     * the subscriptions MultiMap.
	     * @throws IllegalArgumentException if topic isn't in topics list
	     */
	    @Override
	    public void removeTopic(String topic) {

	        if (!topics.contains(topic)) {
	            throw new IllegalArgumentException("Invalid topic.");
	        }

	        subscriptions.removeAll(topic);
	        topics.remove(topic);
	    }



}
