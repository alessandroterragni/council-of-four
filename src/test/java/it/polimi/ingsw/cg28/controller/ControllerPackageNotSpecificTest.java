package it.polimi.ingsw.cg28.controller;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.connections.server.ActionMsgController;
import it.polimi.ingsw.cg28.connections.server.Broker;
import it.polimi.ingsw.cg28.connections.server.ControllerSingleGame;
import it.polimi.ingsw.cg28.connections.server.InteractionComponent;
import it.polimi.ingsw.cg28.controller.actions.MainAction;
import it.polimi.ingsw.cg28.controller.actions.QuickAction;
import it.polimi.ingsw.cg28.controller.bonus.DrawCardBonus;
import it.polimi.ingsw.cg28.view.messages.action.EndGameMsg;
import it.polimi.ingsw.cg28.view.messages.action.LeaveGameActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.StartMsg;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;
import it.polimi.ingsw.cg28.view.messages.event.GiveMeActionMsg;

public class ControllerPackageNotSpecificTest {
	
	private static final Logger log = Logger.getLogger(Game.class.getName());
	
	private PlayerID player1;
	private PlayerID player2;
	private InactivityTimer timer;
	private AsAGame asAGame;
	private Game game;
	private AsABroker asABroker;
	
	class AsABroker extends Broker {
		
		EventMsg message;
		
		public synchronized void publish(EventMsg message, String topic) {
			this.message = message;
		};
		
		public void subscribeClientToTopic(String topic, PlayerID player) {
			return;
		}
		
	};
	
	class AsAController extends ActionMsgController {
		
		PlayerID inactivePlayer;
		
		public AsAController(ControllerSingleGame singleGame, InteractionComponent interactionComponent) {
			super(singleGame, interactionComponent);
		}
		
		public void handle(LeaveGameActionMsg leaveGameActionMsg){
			return;
		}
		
		public void handle(EndGameMsg endGameMsg){
			return;
		}
		
		public void unsubscribeInactivePlayer(PlayerID player){
			inactivePlayer = player;
		}
		
	}
		
	class AsAGame extends Game {
		
		private String string;
		
		public AsAGame(String gameID, List<PlayerID> players, ActionMsgController actionMsgController) {
			super(gameID, players, actionMsgController);
		}

		public void inactivePlayer(){
			string = "Inactive";
		}
		
		private String getString(){
			return string;
		}
	}
	
	@Before
	public void set() {
		
		log.setUseParentHandlers(false);
		
		List<PlayerID> players = new ArrayList<>();
		players.add(new PlayerID("Player1"));
		players.add(new PlayerID("Player2"));
		
		asAGame = new AsAGame("GameTest", players, null);

	}
	
	@Test
	public void testPlayerIDEquals() {
		
		player1 = new PlayerID("Player1");
		assertFalse(player1.equals(new DrawCardBonus(1)));
		player2 = player1;
		assertTrue(player1.equals(player2));
		player2 = new PlayerID("Player1");
		assertFalse(player1.equals(player2));
		
	}
	
	@Test 
	public void testInactivityTimer() throws InterruptedException{
		timer = new InactivityTimer();
		assertTrue(asAGame.getString() == null);
		timer.set(asAGame, 5);
		timer.reset(asAGame, 10);
		assertTrue(asAGame.getString() == null);
	}

	@Test
	public void testPlayerTurn(){
		
		PlayerTurn turn = new PlayerTurn(new PlayerID("Player1"));
		assertTrue(turn.canDoMainAction());
		assertTrue(turn.canDoQuickAction());
		
		assertFalse(turn.canEnd());
		assertFalse(turn.isEnded());
		turn.setBonus(true);
		assertFalse(turn.canEnd());
		assertFalse(turn.isEnded());
		turn.setBonus(false);
		
		turn.takeAction(new MainAction());
		assertFalse(turn.canDoMainAction());
		assertTrue(turn.canEnd());
		assertFalse(turn.isEnded());
		turn.setBonus(true);
		assertFalse(turn.canEnd());
		assertFalse(turn.isEnded());
		turn.setBonus(false);
		
		turn.takeAction(new QuickAction());
		assertFalse(turn.canDoQuickAction());
		assertTrue(turn.canEnd());
		assertTrue(turn.isEnded());
		turn.setBonus(true);
		assertFalse(turn.canEnd());
		assertFalse(turn.isEnded());
		
	}
	
	@Test
	public void testGameInactivePlayer() throws IOException{
		
		asABroker = new AsABroker();
		
		ControllerSingleGame singleGame = new ControllerSingleGame(asABroker, null);
		
		player1 = new PlayerID("Player1");
		player2 = new PlayerID("Player2");
		List<PlayerID> playerIDs = new ArrayList<>();
		playerIDs.add(player1);
		playerIDs.add(player2);
		AsAController controller = new AsAController(singleGame, null);
		game = new Game("GAMETEST",playerIDs, controller);
		
		singleGame.registerObserver(game);
		game.registerObserver(singleGame);
		
		game.update(new StartMsg());
		
		assertTrue(controller.inactivePlayer == null);
		assertTrue(game.getGameController().getCurrentPlayer().equals(player1));
		game.inactivePlayer();
		assertTrue(controller.inactivePlayer.equals(player1));
		assertTrue(game.getGameController().getCurrentPlayer().equals(player2));
		
		assertTrue(asABroker.message.getClass().equals(GiveMeActionMsg.class));
		
		assertTrue(asABroker.message.getPlayer().equals(player2));
		
	}
}
