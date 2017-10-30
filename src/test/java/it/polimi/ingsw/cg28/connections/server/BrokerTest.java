package it.polimi.ingsw.cg28.connections.server;

import static org.junit.Assert.*;

import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.connections.SubscriberInterface;
import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;
import it.polimi.ingsw.cg28.view.messages.event.SimpleEventMsg;

public class BrokerTest {
	
	private static final Logger LOG = Logger.getLogger(Broker.class.getName());
	
	class AsASubscriber implements SubscriberInterface {
		
		EventMsg message;
		
		@Override
		public void dispatchMessage(EventMsg message) throws RemoteException {
			this.message = message;
		}
		
	};
	
	class AsABadSubscriber implements SubscriberInterface {
		
		EventMsg message;
		
		@Override
		public void dispatchMessage(EventMsg message) throws RemoteException {
			throw new RemoteException();
		}
		
	};
	
	private Broker broker;
	private PlayerID player1;
	private PlayerID player2;
	private PlayerID player3;
	private AsASubscriber subscriberInterface1;
	private AsASubscriber subscriberInterface2;
	private AsASubscriber subscriberInterface3;
	private AsABadSubscriber badSubscriber;
	
	private final String TOPIC = "GAME_TEST";
	
	@Before
	public void setUpTest() {
		
		LOG.setUseParentHandlers(false);
		
		player1 = new PlayerID("Player1");
		player2 = new PlayerID("Player2");
		player3 = new PlayerID("Player3");
		
		subscriberInterface1 = new AsASubscriber();
		subscriberInterface2 = new AsASubscriber();
		subscriberInterface3 = new AsASubscriber();
		badSubscriber = new AsABadSubscriber();
		
		broker = new Broker();
		
	}
	
	@Test
	public void testBroker(){
		assertNotNull(new Broker());
	}
	
	@Test(expected = NullPointerException.class)
	public void testBrokerSubscribeWithNullPlayer(){
		broker = new Broker();
		broker.subscribe(subscriberInterface1, null);
	}
	
	@Test(expected = NullPointerException.class)
	public void testBrokerSubscribeWithNullSubscriber(){
		broker = new Broker();
		broker.subscribe(null, player1);
	}
	
	@Test
	public void testBrokerSubscribe(){
		broker = new Broker();
		broker.subscribe(subscriberInterface1, player1);
		
		broker.addTopic(TOPIC);
		broker.subscribeClientToTopic(TOPIC, player1);
		SimpleEventMsg msg = new SimpleEventMsg("TestBroker");
		msg.setPlayer(player1);
		
		broker.subscribe(subscriberInterface1, player1);
		broker.publish(msg, TOPIC);
		
		assertNotNull(subscriberInterface1.message);
		assertTrue(subscriberInterface1.message.getClass().equals(SimpleEventMsg.class));
		SimpleEventMsg received = (SimpleEventMsg) subscriberInterface1.message;
		assertTrue(received.getPlayer().equals(player1));
		assertTrue(received.getString().equals("TestBroker"));
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testBrokerSubscribeWithDifferentSubscriberInterfaces(){
		broker = new Broker();
		broker.subscribe(subscriberInterface1, player1);
		
		broker.subscribe(subscriberInterface2, player1);
		
	}
	
	@Test
	public void testBrokerSubscribeWithDifferentSubscriberInterfacesAfterUnsubscribe(){
		broker = new Broker();
		broker.subscribe(subscriberInterface1, player1);
		broker.unsubscribe(subscriberInterface1);
		broker.subscribe(subscriberInterface2, player1);
	}
	
	@Test(expected = NullPointerException.class)
	public void testBrokerPublishWithNullMsg(){
		
		broker = new Broker();
		broker.subscribe(subscriberInterface1, player1);
		
		broker.addTopic(TOPIC);
		broker.subscribeClientToTopic(TOPIC, player1);
		SimpleEventMsg msg = new SimpleEventMsg("TestBroker");
		msg.setPlayer(player1);
		
		broker.publish(null, TOPIC);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testBrokerPublishWithInvalidTopic(){
		
		broker = new Broker();
		broker.subscribe(subscriberInterface1, player1);
		
		SimpleEventMsg msg = new SimpleEventMsg("TestBroker");
		msg.setPlayer(player1);
		
		broker.publish(msg, TOPIC);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testBrokerSubscribeClientToTopicInvalidTopic(){
		
		broker = new Broker();
		broker.subscribe(subscriberInterface1, player1);
		
		broker.subscribeClientToTopic(TOPIC, player1);
		
	}
	
	@Test
	public void testBrokerPublish(){
		
		broker = new Broker();
		broker.subscribe(subscriberInterface1, player1);
		broker.addTopic(TOPIC);
		
		broker.subscribeClientToTopic(TOPIC, player1);
		SimpleEventMsg msg = new SimpleEventMsg("TestBroker");
		msg.setPlayer(player1);
		
		broker.publish(msg, TOPIC);
		
		assertNotNull(subscriberInterface1.message);
		assertTrue(subscriberInterface1.message.getClass().equals(SimpleEventMsg.class));
		SimpleEventMsg received = (SimpleEventMsg) subscriberInterface1.message;
		assertTrue(received.getPlayer().equals(player1));
		assertTrue(received.getString().equals("TestBroker"));
		
	}
	
	@Test
	public void testBrokerPublishInvalidPlayer(){
		
		broker = new Broker();
		broker.subscribe(subscriberInterface1, player1);
		broker.addTopic(TOPIC);
		
		broker.subscribeClientToTopic(TOPIC, player1);
		SimpleEventMsg msg = new SimpleEventMsg("TestBroker");
		msg.setPlayer(player2);
		
		broker.publish(msg, TOPIC);
		broker.subscribeClientToTopic(TOPIC, player2);
		
		broker.publish(msg, TOPIC);
		assertTrue(subscriberInterface1.message == null);
		
	}
	
	@Test
	public void testBrokerPublishSubscriberException(){
		
		broker = new Broker();
		broker.subscribe(badSubscriber, player1);
		broker.addTopic(TOPIC);
		
		broker.subscribeClientToTopic(TOPIC, player1);
		SimpleEventMsg msg = new SimpleEventMsg("TestBroker");
		msg.setPlayer(player1);
		
		broker.publish(msg, TOPIC);
		
		assertTrue(subscriberInterface1.message == null);
		
		//Throws an exception if badSubscriber is not unsubscribed
		broker.subscribe(subscriberInterface1, player1);
		
	}
	
	@Test
	public void testBrokerPublishToAll(){
		
		broker = new Broker();
		broker.subscribe(subscriberInterface1, player1);
		broker.subscribe(subscriberInterface2, player2);
		broker.subscribe(subscriberInterface3, player3);
		broker.addTopic(TOPIC);
		
		broker.subscribeClientToTopic(TOPIC, player1);
		broker.subscribeClientToTopic(TOPIC, player2);
		SimpleEventMsg msg = new SimpleEventMsg("TestBroker");
		msg.setPlayer(new PlayerID(TOPIC));
		
		broker.publish(msg, TOPIC);
		
		assertNotNull(subscriberInterface1.message);
		assertTrue(subscriberInterface1.message.getClass().equals(SimpleEventMsg.class));
		SimpleEventMsg received = (SimpleEventMsg) subscriberInterface1.message;
		assertTrue(received.getPlayer().getName().equals(TOPIC));
		assertTrue(received.getString().equals("TestBroker"));
		
		assertNotNull(subscriberInterface2.message);
		assertTrue(subscriberInterface2.message.getClass().equals(SimpleEventMsg.class));
		SimpleEventMsg received2 = (SimpleEventMsg) subscriberInterface2.message;
		assertTrue(received2.getPlayer().getName().equals(TOPIC));
		assertTrue(received2.getString().equals("TestBroker"));
		
		assertTrue(subscriberInterface3.message == null);
		
	}
	
	@Test
	public void testBrokerPublishToAllConditions(){
		
		broker = new Broker();
		broker.subscribe(subscriberInterface1, player1);
		broker.subscribe(badSubscriber, player2);
		broker.addTopic(TOPIC);
		
		broker.subscribeClientToTopic(TOPIC, player1);
		broker.subscribeClientToTopic(TOPIC, player2);
		broker.subscribeClientToTopic(TOPIC, player3);
		SimpleEventMsg msg = new SimpleEventMsg("TestBroker");
		msg.setPlayer(new PlayerID(TOPIC));
		
		broker.publish(msg, TOPIC);
		
		assertNotNull(subscriberInterface1.message);
		assertTrue(subscriberInterface1.message.getClass().equals(SimpleEventMsg.class));
		SimpleEventMsg received = (SimpleEventMsg) subscriberInterface1.message;
		assertTrue(received.getPlayer().getName().equals(TOPIC));
		assertTrue(received.getString().equals("TestBroker"));

		assertTrue(subscriberInterface2.message == null);
		
		//Throws an exception if badSubscriber is not unsubscribed
		broker.subscribe(subscriberInterface2, player2);
		assertTrue(subscriberInterface3.message == null);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testBrokerAddTopic(){
		
		broker = new Broker();
		
		broker.addTopic(TOPIC);
		broker.addTopic(TOPIC);
		
	}
	
	@Test
	public void testBrokerPublishToAllAddTopicSetPlayer(){
		
		broker = new Broker();
		
		broker.subscribe(subscriberInterface1, player1);
		broker.subscribe(subscriberInterface2, player2);
		broker.subscribe(subscriberInterface3, player3);
		
		Set<PlayerID> players = new HashSet<>();
		players.add(player1);
		players.add(player2);
		players.add(player3);
		
		broker.addTopic(TOPIC, players);
		
		SimpleEventMsg msg = new SimpleEventMsg("TestBroker");
		msg.setPlayer(new PlayerID(TOPIC));
		
		broker.publish(msg, TOPIC);
		
		assertNotNull(subscriberInterface1.message);
		assertTrue(subscriberInterface1.message.getClass().equals(SimpleEventMsg.class));
		SimpleEventMsg received = (SimpleEventMsg) subscriberInterface1.message;
		assertTrue(received.getPlayer().getName().equals(TOPIC));
		assertTrue(received.getString().equals("TestBroker"));
		
		assertNotNull(subscriberInterface2.message);
		assertTrue(subscriberInterface2.message.getClass().equals(SimpleEventMsg.class));
		SimpleEventMsg received2 = (SimpleEventMsg) subscriberInterface2.message;
		assertTrue(received2.getPlayer().getName().equals(TOPIC));
		assertTrue(received2.getString().equals("TestBroker"));
		
		assertNotNull(subscriberInterface3.message);
		assertTrue(subscriberInterface3.message.getClass().equals(SimpleEventMsg.class));
		SimpleEventMsg received3 = (SimpleEventMsg) subscriberInterface3.message;
		assertTrue(received3.getPlayer().getName().equals(TOPIC));
		assertTrue(received3.getString().equals("TestBroker"));
		
	}
	
	@Test
	public void testBrokerPublishToAllSubscribeClientsToTopic(){
		
		broker = new Broker();
		
		broker.subscribe(subscriberInterface1, player1);
		broker.subscribe(subscriberInterface2, player2);
		broker.subscribe(subscriberInterface3, player3);
		
		Set<PlayerID> players = new HashSet<>();
		players.add(player1);
		players.add(player2);
		players.add(player3);
		
		broker.addTopic(TOPIC);
		
		broker.subscribeClientsToTopic(TOPIC, players);
		
		SimpleEventMsg msg = new SimpleEventMsg("TestBroker");
		msg.setPlayer(new PlayerID(TOPIC));
		
		broker.publish(msg, TOPIC);
		
		assertNotNull(subscriberInterface1.message);
		assertTrue(subscriberInterface1.message.getClass().equals(SimpleEventMsg.class));
		SimpleEventMsg received = (SimpleEventMsg) subscriberInterface1.message;
		assertTrue(received.getPlayer().getName().equals(TOPIC));
		assertTrue(received.getString().equals("TestBroker"));
		
		assertNotNull(subscriberInterface2.message);
		assertTrue(subscriberInterface2.message.getClass().equals(SimpleEventMsg.class));
		SimpleEventMsg received2 = (SimpleEventMsg) subscriberInterface2.message;
		assertTrue(received2.getPlayer().getName().equals(TOPIC));
		assertTrue(received2.getString().equals("TestBroker"));
		
		assertNotNull(subscriberInterface3.message);
		assertTrue(subscriberInterface3.message.getClass().equals(SimpleEventMsg.class));
		SimpleEventMsg received3 = (SimpleEventMsg) subscriberInterface3.message;
		assertTrue(received3.getPlayer().getName().equals(TOPIC));
		assertTrue(received3.getString().equals("TestBroker"));
		
	}
	
	@Test(expected = NullPointerException.class)
	public void testBrokerSubscribeClientToTopicWithClientsNull(){
		
		broker = new Broker();
		broker.addTopic(TOPIC);
		broker.subscribeClientToTopic(TOPIC, null);
		
	}
	
	@Test(expected = NullPointerException.class)
	public void testBrokerSubscribeClientsToTopicWithClientsNull(){
		
		broker = new Broker();
		broker.addTopic(TOPIC);
		broker.subscribeClientsToTopic(TOPIC, null);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testBrokerSubscribeClientsToTopicWithInvalidTopic(){
		
		broker = new Broker();
		broker.addTopic(TOPIC);
		broker.subscribeClientsToTopic("INVALID", null);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testBrokerUnsubscribeClientFromTopicWithInvalidTopic(){
		
		broker = new Broker();
		broker.addTopic(TOPIC);
		broker.unsubscribeClientFromTopic("INVALID", null);
		
	}
	
	@Test(expected = NullPointerException.class)
	public void testBrokerUnsubscribeClientFromTopicWithClientNull(){
		
		broker = new Broker();
		broker.addTopic(TOPIC);
		broker.unsubscribeClientFromTopic(TOPIC, null);
		
	}
	
	@Test
	public void testBrokerUnsubscribeClientFromTopic(){
		
		broker = new Broker();
		
		broker.subscribe(subscriberInterface1, player1);
		broker.subscribe(subscriberInterface2, player2);
		broker.subscribe(subscriberInterface3, player3);
		
		Set<PlayerID> players = new HashSet<>();
		players.add(player1);
		players.add(player2);
		players.add(player3);
		
		broker.addTopic(TOPIC);
		
		broker.subscribeClientsToTopic(TOPIC, players);
		broker.unsubscribeClientFromTopic(TOPIC, player1);
		
		SimpleEventMsg msg = new SimpleEventMsg("TestBroker");
		msg.setPlayer(new PlayerID(TOPIC));
		
		broker.publish(msg, TOPIC);
		
		assertTrue(subscriberInterface1.message==null);
		
		assertNotNull(subscriberInterface2.message);
		assertTrue(subscriberInterface2.message.getClass().equals(SimpleEventMsg.class));
		SimpleEventMsg received2 = (SimpleEventMsg) subscriberInterface2.message;
		assertTrue(received2.getPlayer().getName().equals(TOPIC));
		assertTrue(received2.getString().equals("TestBroker"));
		
		assertNotNull(subscriberInterface3.message);
		assertTrue(subscriberInterface3.message.getClass().equals(SimpleEventMsg.class));
		SimpleEventMsg received3 = (SimpleEventMsg) subscriberInterface3.message;
		assertTrue(received3.getPlayer().getName().equals(TOPIC));
		assertTrue(received3.getString().equals("TestBroker"));
		
	}
	
	@Test(expected = NullPointerException.class)
	public void testBrokerRemoveClientWithNullClient(){
		broker = new Broker();
		broker.removeClient(null);
	}
	
	@Test(expected = NullPointerException.class)
	public void testBrokerRemoveClientsWithNullClients(){
		broker = new Broker();
		broker.removeClients(null);
	}
	
	@Test
	public void testBrokerRemoveClient(){
		broker = new Broker();
		
		broker.subscribe(subscriberInterface1, player1);
		broker.subscribe(subscriberInterface2, player2);
		broker.subscribe(subscriberInterface3, player3);
		
		Set<PlayerID> players = new HashSet<>();
		players.add(player1);
		players.add(player2);
		players.add(player3);
		
		broker.addTopic(TOPIC);
		
		broker.subscribeClientsToTopic(TOPIC, players);
		
		broker.removeClient(player1);
		
		SimpleEventMsg msg = new SimpleEventMsg("TestBroker");
		msg.setPlayer(new PlayerID(TOPIC));
		
		broker.publish(msg, TOPIC);
		
		assertTrue(subscriberInterface1.message==null);
		
		assertNotNull(subscriberInterface2.message);
		assertTrue(subscriberInterface2.message.getClass().equals(SimpleEventMsg.class));
		SimpleEventMsg received2 = (SimpleEventMsg) subscriberInterface2.message;
		assertTrue(received2.getPlayer().getName().equals(TOPIC));
		assertTrue(received2.getString().equals("TestBroker"));
		
		assertNotNull(subscriberInterface3.message);
		assertTrue(subscriberInterface3.message.getClass().equals(SimpleEventMsg.class));
		SimpleEventMsg received3 = (SimpleEventMsg) subscriberInterface3.message;
		assertTrue(received3.getPlayer().getName().equals(TOPIC));
		assertTrue(received3.getString().equals("TestBroker"));
	}
	
	@Test
	public void testBrokerRemoveClients(){
		broker = new Broker();
		
		broker.subscribe(subscriberInterface1, player1);
		broker.subscribe(subscriberInterface2, player2);
		broker.subscribe(subscriberInterface3, player3);
		
		Set<PlayerID> players = new HashSet<>();
		players.add(player1);
		players.add(player2);
		players.add(player3);
		
		broker.addTopic(TOPIC);
		
		broker.subscribeClientsToTopic(TOPIC, players);
		
		broker.removeClients(players);
		
		SimpleEventMsg msg = new SimpleEventMsg("TestBroker");
		msg.setPlayer(new PlayerID(TOPIC));
		
		broker.publish(msg, TOPIC);
		
		assertTrue(subscriberInterface1.message==null);
		assertTrue(subscriberInterface2.message==null);
		assertTrue(subscriberInterface3.message==null);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testBrokerRemoveTopicWithInvalidTopic(){
		
		broker = new Broker();
		broker.removeTopic("INVALID");
		
	}
	
	@Test
	public void testBrokerRemoveTopic(){
		
		broker = new Broker();
		broker.addTopic(TOPIC);
		broker.removeTopic(TOPIC);
		//Throws exception if Topic is in topics list yet
		broker.addTopic(TOPIC);
		
	}
	
	@Test
	public void testBrokerUnsubscribeError(){
		
		broker = new Broker();
		broker.unsubscribe(subscriberInterface1);
		
	}
	
	
}
