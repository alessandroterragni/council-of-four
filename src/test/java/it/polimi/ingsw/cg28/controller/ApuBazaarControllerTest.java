package it.polimi.ingsw.cg28.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.actions.bazaar.SellAssistantsAction;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;
import it.polimi.ingsw.cg28.view.messages.action.ActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.BazaarStopActionMsg;
import it.polimi.ingsw.cg28.view.messages.event.EventMsg;
import it.polimi.ingsw.cg28.view.messages.event.GiveMeBuyActionMsg;
import it.polimi.ingsw.cg28.view.messages.event.GiveMeSellActionMsg;
import it.polimi.ingsw.cg28.view.messages.event.SimpleEventMsg;

public class ApuBazaarControllerTest {
	
	private static final Logger log = Logger.getLogger(Game.class.getName());
	
	private PlayerID player1;
	private PlayerID player2;
	private PlayerID player3;
	private List<PlayerID> players;
	private ApuBazaarController controller;
	private ModelStatus model;

	@Before
	public void setTest() {
		
		players = new ArrayList<>();
		
		player1 = new PlayerID("Player1");
		player2 = new PlayerID("Player2");
		player3 = new PlayerID("Player3");
		players.add(player1);
		players.add(player2);
		players.add(player3);
		
		PlayerID [] playersArray = new PlayerID[3];
		playersArray[0] = player1;
		playersArray[1] = player2;
		playersArray[2] = player3;
		
		ModelTestConfiguration config = new ModelTestConfiguration();
		model = config.buildModelTest(playersArray);
		
		Player[] modelPlayers = model.getPlayers();
		
		modelPlayers[0].getAssistants().setValue(0);
		modelPlayers[0].takeTile(model.getRegion(0).getTile(0));
		
		modelPlayers[1].getPoliticCardsHand().clear();
		
		log.setUseParentHandlers(false);
	}
	
	@Test
	public void testApuBazaarController(){
		assertNotNull(new ApuBazaarController(model, players, new PlayerID("GameTest")));
	}
	
	@Test(expected = NullPointerException.class)
	public void testApuBazaarControllerWithNullModel(){
		controller = new ApuBazaarController(null, players, new PlayerID("GameTest"));
	}
	
	@Test(expected = NullPointerException.class)
	public void testApuBazaarControllerWithNullPlayersList(){
		controller = new ApuBazaarController(model, null, new PlayerID("GameTest"));
	}
	
	@Test(expected = NullPointerException.class)
	public void testApuBazaarControllerWithNullGameID(){
		controller = new ApuBazaarController(model, players, null);
	}
	
	@Test
	public void testApuBazaarControllerConstructor(){
		
		controller = new ApuBazaarController(model, players, new PlayerID("GameTest"));
		controller.startGame();
		assertFalse(controller.endGame());
		
		GiveMeSellActionMsg msg = (GiveMeSellActionMsg) controller.getNextTurn();
		assertTrue(msg.getPlayer().equals(player1));
		assertFalse(msg.canSellAssistant());
		assertTrue(msg.canSellPermitTiles());
		assertTrue(msg.canSellPoliticCards());
		
		msg = (GiveMeSellActionMsg) controller.getNextTurn();
		assertTrue(msg.getPlayer().equals(player2));
		assertTrue(msg.canSellAssistant());
		assertFalse(msg.canSellPermitTiles());
		assertFalse(msg.canSellPoliticCards());
		
		msg = (GiveMeSellActionMsg) controller.getNextTurn();
		assertTrue(msg.getPlayer().equals(player3));
		assertTrue(msg.canSellAssistant());
		assertFalse(msg.canSellPermitTiles());
		assertTrue(msg.canSellPoliticCards());
		
		msg = (GiveMeSellActionMsg) controller.toDo();
		assertTrue(msg.getPlayer().equals(player3));
		assertTrue(msg.canSellAssistant());
		assertFalse(msg.canSellPermitTiles());
		assertTrue(msg.canSellPoliticCards());
		
		assertFalse(controller.endGame());
		
	}
	
	@Test
	public void testApuBazaarControllerEndGame(){
		
		controller = new ApuBazaarController(model, players, new PlayerID("GameTest"));
		controller.startGame();
		assertFalse(controller.endGame());
		
		controller.getNextTurn();
		assertFalse(controller.endGame());
		controller.getNextTurn();
		assertFalse(controller.endGame());
		controller.getNextTurn();
		assertFalse(controller.endGame());
		controller.getNextTurn();
		assertFalse(controller.endGame());
		controller.getNextTurn();
		assertFalse(controller.endGame());
		controller.getNextTurn();
		
		EventMsg msg = controller.getNextTurn();
		assertTrue(msg.getClass().equals(SimpleEventMsg.class));
		assertTrue(((SimpleEventMsg)msg).getString().equals("Error!"));
		assertTrue(controller.endGame());
		
	}
	
	@Test
	public void testApuBazaarControllerInactivePlayer(){
		
		controller = new ApuBazaarController(model, players, new PlayerID("GameTest"));
		controller.startGame();
		assertFalse(controller.endGame());
		
		controller.inactivePlayer(player1);
		
		GiveMeSellActionMsg msg = (GiveMeSellActionMsg) controller.getNextTurn();
		assertTrue(msg.getPlayer().equals(player2));
		assertTrue(msg.canSellAssistant());
		assertFalse(msg.canSellPermitTiles());
		assertFalse(msg.canSellPoliticCards());
		
		msg = (GiveMeSellActionMsg) controller.getNextTurn();
		assertTrue(msg.getPlayer().equals(player3));
		assertTrue(msg.canSellAssistant());
		assertFalse(msg.canSellPermitTiles());
		assertTrue(msg.canSellPoliticCards());
		
		controller.inactivePlayer(player2);
		
		GiveMeBuyActionMsg msgBuy = (GiveMeBuyActionMsg) controller.getNextTurn();
		assertTrue(msgBuy.getPlayer().equals(player3));
		
		assertTrue(controller.endGame());
		
	}
	
	@Test
	public void testApuBazaarControllerEndMsg(){
		
		controller = new ApuBazaarController(model, players, new PlayerID("GameTest"));
		controller.startGame();
		
		model.getPlayer(players.get(0)).getAssistants().setValue(10);
		model.getPlayer(players.get(1)).getAssistants().setValue(10);
		model.getPlayer(players.get(2)).getAssistants().setValue(10);
		
		
		SellAssistantsAction action = new SellAssistantsAction(3, 2, controller.getBazaarModel());
		action.act(model.getPlayer(players.get(0)));
		
		action = new SellAssistantsAction(4, 2, controller.getBazaarModel());
		action.act(model.getPlayer(players.get(1)));
		
		action = new SellAssistantsAction(5, 2, controller.getBazaarModel());
		action.act(model.getPlayer(players.get(2)));
		
		assertTrue(model.getPlayer(players.get(0)).getAssistants().getValue() == 7);
		assertTrue(model.getPlayer(players.get(1)).getAssistants().getValue() == 6);
		assertTrue(model.getPlayer(players.get(2)).getAssistants().getValue() == 5);
		
		ActionMsg msg = controller.endMsg();
		assertTrue(msg.getClass().equals(BazaarStopActionMsg.class));
		
		assertTrue(model.getPlayer(players.get(0)).getAssistants().getValue() == 10);
		assertTrue(model.getPlayer(players.get(1)).getAssistants().getValue() == 10);
		assertTrue(model.getPlayer(players.get(2)).getAssistants().getValue() == 10);
		
	}
	
	
	
}
