package it.polimi.ingsw.cg28.controller.updaters;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.connections.server.Broker;
import it.polimi.ingsw.cg28.connections.server.ControllerSingleGame;
import it.polimi.ingsw.cg28.controller.ActionMsgHandler;
import it.polimi.ingsw.cg28.controller.Game;
import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.PlayerTurn;
import it.polimi.ingsw.cg28.controller.actions.MainAction;
import it.polimi.ingsw.cg28.controller.actions.QuickAction;
import it.polimi.ingsw.cg28.controller.bonus.AssistantBonus;
import it.polimi.ingsw.cg28.controller.bonus.DrawCardBonus;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.view.messages.action.StartMsg;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;
import it.polimi.ingsw.cg28.view.messages.event.GiveMeActionMsg;

public class OtherUpdatersTest {
	
	private static final Logger log = Logger.getLogger(Game.class.getName());
	
	class AsABroker extends Broker {
			
			private EventMsg message;
			
			public synchronized void publish(EventMsg message, String topic) {
				this.message = message;
			};
			
			public void subscribeClientToTopic(String topic, PlayerID player) {
				return;
			}
			
			public EventMsg getMessage(){
				return message;
			}
			
			public void setNull(){
				message = null;
			}
			
		};
	
	private NotValidMoveUpdater updater;
	private BonusActionUpdater bonusUpdater;
	private ModelStatus model;
	private List<PlayerID> playerIDs;
	private ActionMsgHandler actionMsgHandler;
	private Game game;
	private AsABroker asABroker;

	@Before
	public void before() throws IOException{
		
		log.setUseParentHandlers(false);
		
		playerIDs = new ArrayList<>();
		playerIDs.add(new PlayerID("Player1"));
		playerIDs.add(new PlayerID("Player2"));
		
		asABroker = new AsABroker();
		
		ControllerSingleGame singleGame = new ControllerSingleGame(asABroker, null);
		game = new Game("GAMETEST", playerIDs, null);
		
		singleGame.registerObserver(game);
		game.registerObserver(singleGame);
		
		actionMsgHandler = game.getActionMsgHandler();
		
		actionMsgHandler.handle(new StartMsg());
		model = actionMsgHandler.getGameController().getModel();
		
	}
	
	@Test
	public void testNotValidMoveUpdater() {
		assertNotNull(new NotValidMoveUpdater("Test"));
	}
	
	@Test(expected = NullPointerException.class)
	public void testNotValidMoveUpdaterWithNullParameter() {
		updater = new NotValidMoveUpdater(null);
	}
	
	@Test
	public void testNotValidMoveUpdaterUpdate() {
		
		updater = new NotValidMoveUpdater("Test");
		updater.update(actionMsgHandler);
		
		assertTrue(asABroker.getMessage().getClass().equals(GiveMeActionMsg.class));
		
		asABroker.setNull();
		assertNull(asABroker.getMessage());
		
		updater = new NotValidMoveUpdater("null");
		updater.update(actionMsgHandler);
		
		assertTrue(asABroker.getMessage().getClass().equals(GiveMeActionMsg.class));
		
	}
	
	@Test
	public void testBonusActionUpdater() {
		assertNotNull(new BonusActionUpdater(new DrawCardBonus(4)));
	}
	
	@Test(expected = NullPointerException.class)
	public void testBonusActionUpdaterWithNullParameter() {
		bonusUpdater = new BonusActionUpdater(null);
	}
	
	@Test
	public void testBonusActionUpdaterUpdate() {
		
		Player p = model.getPlayers()[0];
		bonusUpdater = new BonusActionUpdater(new AssistantBonus(4));
		int precNumberAssistants = p.getAssistants().getValue();
		
		bonusUpdater.update(actionMsgHandler);
		
		assertTrue(p.getAssistants().getValue() == precNumberAssistants + 4);
		assertTrue(asABroker.getMessage().getClass().equals(GiveMeActionMsg.class));
		
	}
	
	public void testPlayerTurnUpdaterEndedTurn() {
		
		PlayerID player = actionMsgHandler.getGameController().getCurrentPlayer();
		PlayerTurn turn = model.getPlayer(player).getTurn();
		
		PlayerTurnUpdater turnUpdater = new PlayerTurnUpdater();
		
		assertFalse(turn.canEnd());
		turnUpdater.update(actionMsgHandler);
		assertTrue(asABroker.getMessage().getClass().equals(GiveMeActionMsg.class));
		assertTrue(asABroker.getMessage().getPlayer().equals(player));
		
		turn.takeAction(new MainAction());
		assertFalse(turn.canEnd());
		turnUpdater.update(actionMsgHandler);
		assertTrue(asABroker.getMessage().getClass().equals(GiveMeActionMsg.class));
		assertTrue(asABroker.getMessage().getPlayer().equals(player));
		
		turn.takeAction(new QuickAction());
		assertTrue(turn.canEnd());
		turn.setBonus(true);
		assertTrue(turn.isBonus());
		assertFalse(turn.canEnd());
		turnUpdater.update(actionMsgHandler);
		assertTrue(asABroker.getMessage().getClass().equals(GiveMeActionMsg.class));
		assertTrue(asABroker.getMessage().getPlayer().equals(player));
		
		turn.setBonus(false);
		assertFalse(turn.isBonus());
		assertTrue(turn.canEnd());
		turnUpdater.update(actionMsgHandler);
		assertTrue(asABroker.getMessage().getClass().equals(GiveMeActionMsg.class));
		assertFalse(asABroker.getMessage().getPlayer().equals(player));
		
	}
}
