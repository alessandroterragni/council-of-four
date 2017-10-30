package it.polimi.ingsw.cg28.connections.server;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.Game;
import it.polimi.ingsw.cg28.controller.Observer;
import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.connections.PublisherInterface;
import it.polimi.ingsw.cg28.view.messages.action.ActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.BazaarStartActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.BazaarStopActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.ChatActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.EndBuyTurnMsg;
import it.polimi.ingsw.cg28.view.messages.action.EndGameMsg;
import it.polimi.ingsw.cg28.view.messages.action.EndSellTurnMsg;
import it.polimi.ingsw.cg28.view.messages.action.EndTurnMsg;
import it.polimi.ingsw.cg28.view.messages.action.LeaveGameActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.QuitActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.ShowBazaarStatusMsg;
import it.polimi.ingsw.cg28.view.messages.action.ShowPlayerStatusActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.StartMsg;
import it.polimi.ingsw.cg28.view.messages.action.bazaar.BuySalableActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.ElectionActionMsg;
import it.polimi.ingsw.cg28.view.messages.event.ChatEventMsg;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;
import it.polimi.ingsw.cg28.view.messages.event.SimpleEventMsg;

public class ActionMsgControllerTest {
	
	private static final Logger log = Logger.getLogger(Game.class.getName());
	
	class AsABroker extends Broker {
		
		EventMsg message;
		
		public synchronized void publish(EventMsg message, String topic) {
			this.message = message;
		};
		
		public void subscribeClientToTopic(String topic, PlayerID player) {
			return;
		}
		
	};
	
	class AsAControllerMultipleGames extends ControllerMultipleGames {
		
		PlayerID playerRemoved;
		List<PlayerID> playersUnsubscribed;
		
		public AsAControllerMultipleGames(PublisherInterface publisher) {
			super(publisher);
			playersUnsubscribed = new ArrayList<>();
		}
		
		public void removePlayer(PlayerID player){
			playerRemoved = player;
		}
		
		public void unsubscribePlayer(PlayerID player, String gameTopic){
			playersUnsubscribed.add(player);
		}
		
	}
	
	class AsAGame extends Game {
		
		ActionMsg received;
		
		public AsAGame(String gameID, List<PlayerID> players, ActionMsgController actionMsgController) {
			super(gameID, players, actionMsgController);
		}
		
		public void inactivePlayer(){
			return;
		}
		
		public void update(ActionMsg change){
			received = change;
		}
		
		public void sendToGame(ActionMsg msg){
			super.update(msg);
		}
		
	}
	
	private PlayerID player1;
	private PlayerID player2;
	private AsAControllerMultipleGames multiple;
	private ActionMsgController controller;
	private AsABroker asABroker;
	private ControllerSingleGame singleGame;
	private AsAGame game;
	private InteractionComponent interactionComponent;
	
	@Before
	public void setTest(){
		
		log.setUseParentHandlers(false);
		
		asABroker = new AsABroker();
		
		multiple = new AsAControllerMultipleGames(asABroker);
		interactionComponent = new InteractionComponent(multiple);
		singleGame = new ControllerSingleGame(asABroker, null);
		
		player1 = new PlayerID("Player1");
		player2 = new PlayerID("Player2");
		List<PlayerID> playerIDs = new ArrayList<>();
		playerIDs.add(player1);
		playerIDs.add(player2);
		
		controller = new ActionMsgController(singleGame, interactionComponent);
		game = new AsAGame("GAMETEST",playerIDs, controller);
		
		singleGame.addPlayer(player1);
		singleGame.addPlayer(player2);
		
		singleGame.initGame();
		
		singleGame.registerObserver(game);
		game.registerObserver(singleGame);
	
	}
	
	private void unRegister(){
		
		for(Observer<ActionMsg> o : singleGame.getObservers())
			if (!o.equals(game))
				singleGame.unregisterObserver(o);
	}

	@Test
	public void testHandleActionMsg(){
		
		ActionMsg msg = new StartMsg();
		controller.handle(msg);
		
		assertTrue(game.received.equals(msg));
		
	}
	
	@Test
	public void testHandleStartMsg() {
		
		ActionMsg msg = new StartMsg();
		msg.handle(controller);
		
		assertTrue(game.received.equals(msg));
		
	}
	
	private PlayerID current(){
		
		if(singleGame.currentPlayer().equals(player1))
			return player1;
		else return player2;
		
	}
	
	private PlayerID noCurrent(){
		
		if(singleGame.currentPlayer().equals(player1))
			return player2;
		else return player1;
		
	}
	
	@Test
	public void testHandleTurnActionMsgNotYourTurn(){
		
		StartMsg msg = new StartMsg();
		singleGame.notifyObservers(msg);
		
		PlayerID noCurrent = noCurrent();
		
		ElectionActionMsg turnActionMsg = new ElectionActionMsg(noCurrent);
		turnActionMsg.handle(controller);
		
		assertTrue(asABroker.message.getClass().equals(SimpleEventMsg.class));
		SimpleEventMsg simple = (SimpleEventMsg) asABroker.message;
		assertTrue(simple.getString().equals("Not your Turn!"));
		assertTrue(simple.getPlayer().equals(noCurrent));
	}
	
	@Test
	public void testHandleTurnActionMsg(){
		
		StartMsg msg = new StartMsg();
		singleGame.notifyObservers(msg);
		
		PlayerID current = current();
		
		ElectionActionMsg turnActionMsg = new ElectionActionMsg(current);
		turnActionMsg.handle(controller);
		
		assertTrue(game.received.equals(turnActionMsg));
		assertTrue(game.received.getPlayerID().equals(current));
	}
	
	@Test
	public void testHandleBuySalableActionMsgNotBazaarTurn(){
		
		StartMsg msg = new StartMsg();
		singleGame.notifyObservers(msg);
		
		PlayerID current = current();
		
		BuySalableActionMsg actionMsg = new BuySalableActionMsg(current);
		actionMsg.handle(controller);
		
		assertTrue(asABroker.message.getClass().equals(SimpleEventMsg.class));
		SimpleEventMsg simple = (SimpleEventMsg) asABroker.message;
		assertTrue(simple.getString().equals("Not bazaar turn!"));
		assertTrue(simple.getPlayer().getName().equals(singleGame.getTopic()));
		
	}
	
	@Test
	public void testHandleBuySalableActionMsgNotYourTurn(){
		
		StartMsg msg = new StartMsg();
		singleGame.notifyObservers(msg);
		
		BazaarStartActionMsg msgBazaar = new BazaarStartActionMsg();
		singleGame.notifyObservers(msgBazaar);
		
		PlayerID noCurrent = noCurrent();
		
		BuySalableActionMsg actionMsg = new BuySalableActionMsg(noCurrent);
		actionMsg.handle(controller);
		
		assertTrue(asABroker.message.getClass().equals(SimpleEventMsg.class));
		SimpleEventMsg simple = (SimpleEventMsg) asABroker.message;
		assertTrue(simple.getString().equals("Not your Turn!"));
		assertTrue(simple.getPlayer().equals(noCurrent));
		
	}
	
	@Test
	public void testHandleBuySalableActionMsg(){
		
		StartMsg msg = new StartMsg();
		singleGame.notifyObservers(msg);
		
		BazaarStartActionMsg msgBazaar = new BazaarStartActionMsg();
		singleGame.notifyObservers(msgBazaar);
		
		PlayerID current = current();
		
		BuySalableActionMsg actionMsg = new BuySalableActionMsg(current);
		actionMsg.handle(controller);
		
		assertTrue(game.received.equals(actionMsg));
		assertTrue(game.received.getPlayerID().equals(current));	
		
	}
	
	@Test
	public void testHandleQuitActionMsg() {
		
		StartMsg msg = new StartMsg();
		singleGame.notifyObservers(msg);
		
		PlayerID current = current();
		
		QuitActionMsg turnActionMsg = new QuitActionMsg();
		turnActionMsg.setPlayerID(current);
		turnActionMsg.handle(controller);
		
		assertTrue(game.received.equals(turnActionMsg));
		assertTrue(game.received.getPlayerID().equals(current));
		
	}
	
	@Test
	public void testHandleQuitActionMsgNotYourTurn() {
		
		StartMsg msg = new StartMsg();
		singleGame.notifyObservers(msg);
		
		PlayerID noCurrent = noCurrent();
		
		QuitActionMsg turnActionMsg = new QuitActionMsg();
		turnActionMsg.setPlayerID(noCurrent);
		turnActionMsg.handle(controller);
		
		assertTrue(asABroker.message.getClass().equals(SimpleEventMsg.class));
		SimpleEventMsg simple = (SimpleEventMsg) asABroker.message;
		assertTrue(simple.getString().equals("Not your Turn!"));
		assertTrue(simple.getPlayer().equals(noCurrent));
		
	}
	
	@Test
	public void testHandleEndTurnMsg() {
		
		StartMsg msg = new StartMsg();
		singleGame.notifyObservers(msg);
		
		PlayerID current = current();
		
		EndTurnMsg turnActionMsg = new EndTurnMsg(current);
		turnActionMsg.handle(controller);
		
		assertTrue(game.received.equals(turnActionMsg));
		assertTrue(game.received.getPlayerID().equals(current));
		
	}
	
	@Test
	public void testHandleEndTurnMsgNotYourTurn() {
		
		StartMsg msg = new StartMsg();
		singleGame.notifyObservers(msg);
		
		PlayerID noCurrent = noCurrent();
		
		EndTurnMsg turnActionMsg = new EndTurnMsg(noCurrent);
		turnActionMsg.handle(controller);
		
		assertTrue(asABroker.message.getClass().equals(SimpleEventMsg.class));
		SimpleEventMsg simple = (SimpleEventMsg) asABroker.message;
		assertTrue(simple.getString().equals("Not your Turn!"));
		assertTrue(simple.getPlayer().equals(noCurrent));
		
	}
	
	@Test
	public void testHandleEndSellTurnMsgNotBazaarTurn() {
		
		StartMsg msg = new StartMsg();
		singleGame.notifyObservers(msg);
		
		PlayerID current = current();
		
		EndSellTurnMsg turnActionMsg = new EndSellTurnMsg();
		turnActionMsg.setPlayerID(current);
		turnActionMsg.handle(controller);
		
		assertTrue(asABroker.message.getClass().equals(SimpleEventMsg.class));
		SimpleEventMsg simple = (SimpleEventMsg) asABroker.message;
		assertTrue(simple.getString().equals("Not your Turn!"));
		assertTrue(simple.getPlayer().equals(current));
		
	}
	
	@Test
	public void testHandleEndSellTurnMsg() {
		
		StartMsg msg = new StartMsg();
		singleGame.notifyObservers(msg);
		
		BazaarStartActionMsg msgBazaar = new BazaarStartActionMsg();
		singleGame.notifyObservers(msgBazaar);
		
		PlayerID current = current();
		
		EndSellTurnMsg turnActionMsg = new EndSellTurnMsg();
		turnActionMsg.setPlayerID(current);
		turnActionMsg.handle(controller);
		
		assertTrue(game.received.equals(turnActionMsg));
		assertTrue(game.received.getPlayerID().equals(current));
		
	}
	
	@Test
	public void testHandleEndSellTurnMsgNotYourTurn() {
		
		StartMsg msg = new StartMsg();
		singleGame.notifyObservers(msg);
		
		BazaarStartActionMsg msgBazaar = new BazaarStartActionMsg();
		singleGame.notifyObservers(msgBazaar);
		
		PlayerID noCurrent = noCurrent();
		
		EndSellTurnMsg turnActionMsg = new EndSellTurnMsg();
		turnActionMsg.setPlayerID(noCurrent);
		turnActionMsg.handle(controller);
		
		assertTrue(asABroker.message.getClass().equals(SimpleEventMsg.class));
		SimpleEventMsg simple = (SimpleEventMsg) asABroker.message;
		assertTrue(simple.getString().equals("Not your Turn!"));
		assertTrue(simple.getPlayer().equals(noCurrent));
		
	}
	
	@Test
	public void testHandleEndBuyTurnMsgNotBazaarTurn() {
		
		StartMsg msg = new StartMsg();
		singleGame.notifyObservers(msg);
		
		PlayerID current = current();
		
		EndBuyTurnMsg turnActionMsg = new EndBuyTurnMsg();
		turnActionMsg.setPlayerID(current);
		turnActionMsg.handle(controller);
		
		assertTrue(asABroker.message.getClass().equals(SimpleEventMsg.class));
		SimpleEventMsg simple = (SimpleEventMsg) asABroker.message;
		assertTrue(simple.getString().equals("Not your Turn!"));
		assertTrue(simple.getPlayer().equals(current));
		
	}
	
	@Test
	public void testHandleEndBuyTurnMsg() {
		
		StartMsg msg = new StartMsg();
		singleGame.notifyObservers(msg);
		
		BazaarStartActionMsg msgBazaar = new BazaarStartActionMsg();
		singleGame.notifyObservers(msgBazaar);
		
		PlayerID current = current();
		
		EndBuyTurnMsg turnActionMsg = new EndBuyTurnMsg();
		turnActionMsg.setPlayerID(current);
		turnActionMsg.handle(controller);
		
		assertTrue(game.received.equals(turnActionMsg));
		assertTrue(game.received.getPlayerID().equals(current));
		
	}
	
	@Test
	public void testHandleEndBuyTurnMsgNotYourTurn() {
		
		StartMsg msg = new StartMsg();
		singleGame.notifyObservers(msg);
		
		BazaarStartActionMsg msgBazaar = new BazaarStartActionMsg();
		singleGame.notifyObservers(msgBazaar);
		
		PlayerID noCurrent = noCurrent();
		
		EndBuyTurnMsg turnActionMsg = new EndBuyTurnMsg();
		turnActionMsg.setPlayerID(noCurrent);
		turnActionMsg.handle(controller);
		
		assertTrue(asABroker.message.getClass().equals(SimpleEventMsg.class));
		SimpleEventMsg simple = (SimpleEventMsg) asABroker.message;
		assertTrue(simple.getString().equals("Not your Turn!"));
		assertTrue(simple.getPlayer().equals(noCurrent));
		
	}
	
	@Test
	public void testHandleLeaveGameActionMsgGameNotStarted() {
		
		singleGame = new ControllerSingleGame(asABroker, null);
		
		List<PlayerID> playerIDs = new ArrayList<>();
		playerIDs.add(player1);
		playerIDs.add(player2);
		
		controller = new ActionMsgController(singleGame, interactionComponent);
		
		singleGame.addPlayer(player1);
		singleGame.addPlayer(player2);
		
		assertTrue(singleGame.getPlayers().contains(player1));
		
		LeaveGameActionMsg actionMsg = new LeaveGameActionMsg(" ");
		actionMsg.setPlayerID(player1);
		actionMsg.handle(controller);
		
		assertTrue(multiple.playerRemoved.equals(player1));
	}
	
	@Test
	public void testHandleLeaveGameActionMsg() {
		
		unRegister();
		
		StartMsg msg = new StartMsg();
		singleGame.notifyObservers(msg);
		
		LeaveGameActionMsg actionMsg = new LeaveGameActionMsg(" ");
		actionMsg.setPlayerID(player1);
		actionMsg.handle(controller);
		
		assertTrue(game.received.equals(actionMsg));
		assertTrue(game.received.getPlayerID().equals(player1));
		
	}
	
	@Test
	public void testHandleChatActionMsg() {
		
		unRegister();
		
		ChatActionMsg actionMsg = new ChatActionMsg("ChatMsgTest");
		actionMsg.setPlayerID(player1);
		actionMsg.handle(controller);
		
		StringBuilder s = new StringBuilder();
		s.append("[Player] " + player1.getName() + ": ");
		s.append("ChatMsgTest");
		
		assertTrue(asABroker.message.getClass().equals(ChatEventMsg.class));
		ChatEventMsg chat = (ChatEventMsg) asABroker.message;
		assertTrue(chat.getString().equals(s.toString()));
		assertTrue(chat.getPlayer().getName().equals(singleGame.getTopic()));

	}
	
	@Test
	public void testHandleEndGameMsg() {
		
		unRegister();
		
		Map<PlayerID,Integer> map = new HashMap<>();
		map.put(player1, 3);
		map.put(player2, 2);
		EndGameMsg actionMsg = new EndGameMsg(map, player1);
		
		assertTrue(multiple.playersUnsubscribed.isEmpty());
		
		actionMsg.setPlayerID(player1);
		actionMsg.handle(controller);
		
		assertTrue(multiple.playersUnsubscribed.contains(player1));
		assertTrue(multiple.playersUnsubscribed.contains(player2));
	}
	
	
	@Test
	public void testHandleBazaarStartActionMsg() {
		
		BazaarStartActionMsg actionMsg = new BazaarStartActionMsg();
		
		actionMsg.setPlayerID(player1);
		actionMsg.handle(controller);
		
		assertTrue(asABroker.message.getClass().equals(SimpleEventMsg.class));
		SimpleEventMsg simple = (SimpleEventMsg) asABroker.message;
		assertTrue(simple.getString().equals("Couldn't perform this action!"));
		assertTrue(simple.getPlayer().getName().equals(singleGame.getTopic()));
		
	}
	
	@Test
	public void testHandleBazaarStopActionMsg() {
		
		BazaarStopActionMsg actionMsg = new BazaarStopActionMsg();
		
		actionMsg.setPlayerID(player1);
		actionMsg.handle(controller);
		
		assertTrue(asABroker.message.getClass().equals(SimpleEventMsg.class));
		SimpleEventMsg simple = (SimpleEventMsg) asABroker.message;
		assertTrue(simple.getString().equals("Couldn't perform this action!"));
		assertTrue(simple.getPlayer().getName().equals(singleGame.getTopic()));
	
	}
	

	@Test
	public void testHandleShowPlayerStatusActionMsg() {
		
		ShowPlayerStatusActionMsg actionMsg = new ShowPlayerStatusActionMsg();
		
		actionMsg.setPlayerID(player1);
		actionMsg.handle(controller);
		
		assertTrue(asABroker.message.getClass().equals(SimpleEventMsg.class));
		SimpleEventMsg simple = (SimpleEventMsg) asABroker.message;
		assertTrue(simple.getString().equals("Couldn't perform this action!"));
		assertTrue(simple.getPlayer().getName().equals(singleGame.getTopic()));


	}
	

	@Test
	public void testHandleShowBazaarStatusMsg() {
		
		ShowBazaarStatusMsg actionMsg = new ShowBazaarStatusMsg();
		
		actionMsg.setPlayerID(player1);
		actionMsg.handle(controller);
		
		assertTrue(asABroker.message.getClass().equals(SimpleEventMsg.class));
		SimpleEventMsg simple = (SimpleEventMsg) asABroker.message;
		assertTrue(simple.getString().equals("Couldn't perform this action!"));
		assertTrue(simple.getPlayer().getName().equals(singleGame.getTopic()));

	}
	
	@Test
	public void testUnsubscribeInactivePlayer(){
		
		assertTrue(multiple.playersUnsubscribed.isEmpty());
		controller.unsubscribeInactivePlayer(player1);
		assertTrue(multiple.playersUnsubscribed.contains(player1));
		
	}
	
	@Test
	public void testControllerSingleGameProcessRequest(){
		
		BazaarStartActionMsg actionMsg = new BazaarStartActionMsg();
		
		actionMsg.setPlayerID(player1);
		singleGame.handleRequest(actionMsg);
		
		assertTrue(asABroker.message.getClass().equals(SimpleEventMsg.class));
		SimpleEventMsg simple = (SimpleEventMsg) asABroker.message;
		assertTrue(simple.getString().equals("Couldn't perform this action!"));
		assertTrue(simple.getPlayer().getName().equals(singleGame.getTopic()));
		
	}
	
	@Test
	public void testControllerSingleGameRemovePlayer(){
		
		asABroker.message = null;
		singleGame.removePlayer(player1);
		assertTrue(asABroker.message == null);
		
		singleGame = new ControllerSingleGame(asABroker, interactionComponent);
		singleGame.removePlayer(player1);
		
		assertTrue(asABroker.message.getClass().equals(SimpleEventMsg.class));
		SimpleEventMsg simple = (SimpleEventMsg) asABroker.message;
		assertTrue(simple.getString().equals("Removed from waiting list, reconnect to be added again!"));
		assertTrue(simple.getPlayer().equals(player1));
		
	}
	
	
	

}
