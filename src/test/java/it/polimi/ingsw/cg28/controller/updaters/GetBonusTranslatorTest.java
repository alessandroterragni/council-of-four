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
import it.polimi.ingsw.cg28.controller.bonus.NobilityBonus;
import it.polimi.ingsw.cg28.controller.bonus.ReuseCityBonus;
import it.polimi.ingsw.cg28.controller.bonus.ReusePermitBonus;
import it.polimi.ingsw.cg28.controller.bonus.TakePermitTileBonus;
import it.polimi.ingsw.cg28.controller.bonus.VictoryPointBonus;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.view.messages.action.StartMsg;
import it.polimi.ingsw.cg28.view.messages.action.bonus.ReuseCityBonusActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bonus.ReusePermitBonusActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.bonus.TakePermitTileBonusActionMsg;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;
import it.polimi.ingsw.cg28.view.messages.event.FilledMsg;
import it.polimi.ingsw.cg28.view.messages.event.GiveMeActionMsg;
import it.polimi.ingsw.cg28.view.messages.event.SimpleEventMsg;

public class GetBonusTranslatorTest {
	
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
		
	};
	
	private GetBonusTranslator translator;
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
	public void testGetBonusTranslator() {
		assertNotNull(new GetBonusTranslator(actionMsgHandler, model.getPlayer(playerIDs.get(0))));
	}
	
	@Test(expected = NullPointerException.class)
	public void testGetBonusTranslatorWithNullActionMsgHandler() {
		translator = new GetBonusTranslator(null, model.getPlayer(playerIDs.get(0)));
	}
	
	@Test(expected = NullPointerException.class)
	public void testGetBonusTranslatorWithNullPlayer() {
		translator = new GetBonusTranslator(actionMsgHandler, null);
	}
	
	@Test
	public void testGetBonusTranslatorTranslateNobilityBonus() {
		
		Player player = model.getPlayer(playerIDs.get(0));
		
		int victoryPointPrec = player.getScore().getValue();
		
		translator = new GetBonusTranslator(actionMsgHandler, player);
		translator.translate(new NobilityBonus(18));
		
		if(model.getNobilityTrackBonus().getTrackBonus(18).getClass().equals(VictoryPointBonus.class)
				&& model.getNobilityTrackBonus().getTrackBonus(18).getValue() == 8){
			assertTrue(player.getScore().getValue() == victoryPointPrec + 8);
			assertTrue(asABroker.getMessage().getClass().equals(SimpleEventMsg.class));
			assertTrue(((SimpleEventMsg)asABroker.getMessage()).getString().equals("You gain: VictoryPointBonus | 8"));	
		}
		
	}
	
	@Test
	public void testGetBonusTranslatorTranslateReusePermitBonusWithOutPermit() {
		
		Player player = model.getPlayer(playerIDs.get(0));
		
		translator = new GetBonusTranslator(actionMsgHandler, player);
		translator.translate(new ReusePermitBonus(1));
		
		assertTrue(asABroker.getMessage().getClass().equals(GiveMeActionMsg.class));
	}
	
	@Test
	public void testGetBonusTranslatorTranslateReusePermitBonus() {
		
		Player player = model.getPlayer(playerIDs.get(0));
		player.takeTile(model.getRegion(0).getTile(0));
		player.takeTile(model.getRegion(0).getTile(1));
		
		translator = new GetBonusTranslator(actionMsgHandler, player);
		translator.translate(new ReusePermitBonus(1));
		
		assertTrue(asABroker.getMessage().getClass().equals(FilledMsg.class));
		
		ReusePermitBonusActionMsg msg = (ReusePermitBonusActionMsg) ((FilledMsg)asABroker.getMessage()).getActionMsg();
		
		assertTrue(msg.getNumbReusePermit() == 1);
		assertTrue(msg.getTiles().size() == 2);
		assertTrue(msg.isFilled());
		assertTrue(msg.getNeedCode());
		assertTrue(player.getTurn().isBonus());
		
	}
	
	@Test
	public void testGetBonusTranslatorTranslateReuseCityBonusWithoutEmporiums() {
		
		Player player = model.getPlayer(playerIDs.get(0));
		
		translator = new GetBonusTranslator(actionMsgHandler, player);
		translator.translate(new ReuseCityBonus(1));
		
		assertTrue(asABroker.getMessage().getClass().equals(GiveMeActionMsg.class));
		
	}
	
	@Test
	public void testGetBonusTranslatorTranslateReuseCityBonus() {
		
		Player player = model.getPlayer(playerIDs.get(0));
		model.getMap().addEmporium(player.getID(), model.getMap().getTown("Arkon"));
		model.getMap().addEmporium(player.getID(), model.getMap().getTown("Burgen"));
		model.getMap().addEmporium(player.getID(), model.getMap().getTown("Esti"));
		
		translator = new GetBonusTranslator(actionMsgHandler, player);
		translator.translate(new ReuseCityBonus(1));
		
		assertTrue(asABroker.getMessage().getClass().equals(FilledMsg.class));
		
		ReuseCityBonusActionMsg msg = (ReuseCityBonusActionMsg) ((FilledMsg)asABroker.getMessage()).getActionMsg();
		
		assertTrue(msg.getNumbReuseCities() == 1);
		assertTrue(msg.isFilled());
		assertTrue(msg.getNeedCode());
		assertTrue(player.getTurn().isBonus());
		
	}
	
	@Test
	public void testGetBonusTranslatorTranslateTakePermitTile() {
		
		Player player = model.getPlayer(playerIDs.get(0));
		
		translator = new GetBonusTranslator(actionMsgHandler, player);
		translator.translate(new TakePermitTileBonus(2));
		
		assertTrue(asABroker.getMessage().getClass().equals(FilledMsg.class));
		
		TakePermitTileBonusActionMsg msg = (TakePermitTileBonusActionMsg) ((FilledMsg)asABroker.getMessage()).getActionMsg();
		
		assertTrue(msg.getNumbTilesToTake() == 2);
		assertTrue(msg.isFilled());
		assertTrue(msg.getNeedCode());
		assertTrue(player.getTurn().isBonus());
		
	}


}
