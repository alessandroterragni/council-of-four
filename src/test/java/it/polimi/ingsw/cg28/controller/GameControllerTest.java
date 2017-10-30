package it.polimi.ingsw.cg28.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.updaters.NotValidMoveUpdater;
import it.polimi.ingsw.cg28.controller.updaters.Updater;
import it.polimi.ingsw.cg28.model.parser.Configuration;
import it.polimi.ingsw.cg28.view.messages.action.BazaarStartActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.StartMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.EmporiumTileActionMsg;
import it.polimi.ingsw.cg28.view.messages.event.GiveMeActionMsg;

public class GameControllerTest {

	private static final Logger log = Logger.getLogger(Game.class.getName());
	
	private PlayerID player1;
	private PlayerID player2;
	private PlayerID player3;
	private List<PlayerID> players;
	private GameController controller;
	private Configuration configuration;

	@Before
	public void setTest() {
		
		players = new ArrayList<>();
		log.setUseParentHandlers(false);
		player1 = new PlayerID("Player1");
		player2 = new PlayerID("Player2");
		player3 = new PlayerID("Player3");
		players.add(player1);
		players.add(player2);
		players.add(player3);
		
		configuration = new Configuration(new StartMsg());
		
	}
	
	@Test
	public void testGameController(){
		assertNotNull(new GameController(players, configuration, new PlayerID("GameTest")));
	}
	
	@Test(expected = NullPointerException.class)
	public void testGameControllerWithNullConfiguration(){
		controller = new GameController(players,null, new PlayerID("GameTest"));
	}
	
	@Test(expected = NullPointerException.class)
	public void testGameControllerrWithNullPlayersList(){
		controller = new GameController(null, configuration, new PlayerID("GameTest"));
	}
	
	@Test(expected = NullPointerException.class)
	public void testGameControllerWithNullGameID(){
		controller = new GameController(players, configuration, null);
	}
	
	@Test
	public void testGameControllerNoBazaar(){
		
		StartMsg startMsg = new StartMsg();
		startMsg.setBazaar(false);
		
		configuration = new Configuration(startMsg);
		controller = new GameController(players, configuration, new PlayerID("GameTest"));
		
		assertFalse(controller.endGame());
		
		GiveMeActionMsg msg = (GiveMeActionMsg) controller.getNextTurn();
		assertTrue(msg.getPlayer().equals(player1));
		assertFalse(controller.endGame());
		
		msg = (GiveMeActionMsg) controller.getNextTurn();
		assertTrue(msg.getPlayer().equals(player2));
		assertFalse(controller.endGame());
		
		msg = (GiveMeActionMsg) controller.getNextTurn();
		assertTrue(msg.getPlayer().equals(player3));
		assertFalse(controller.endGame());
		
		msg = (GiveMeActionMsg) controller.getNextTurn();
		assertTrue(msg.getPlayer().equals(player1));
		assertFalse(controller.endGame());
		
		msg = (GiveMeActionMsg) controller.getNextTurn();
		assertTrue(msg.getPlayer().equals(player2));
		assertFalse(controller.endGame());
		
		msg = (GiveMeActionMsg) controller.getNextTurn();
		assertTrue(msg.getPlayer().equals(player3));
		assertFalse(controller.endGame());
		
		assertFalse(controller.endGame());
			
	}
	
	@Test
	public void testGameControllerEndGame(){
		
		StartMsg startMsg = new StartMsg();
		startMsg.setBazaar(true);
		
		configuration = new Configuration(startMsg);
		controller = new GameController(players, configuration, new PlayerID("GameTest"));
		
		assertFalse(controller.endGame());
		
		GiveMeActionMsg msg = (GiveMeActionMsg) controller.getNextTurn();
		assertTrue(msg.getPlayer().equals(player1));
		assertFalse(controller.endGame());
		
		controller.getModel().setEndGame(true);
		
		msg = (GiveMeActionMsg) controller.getNextTurn();
		assertTrue(msg.getPlayer().equals(player2));
		assertFalse(controller.endGame());
		
		msg = (GiveMeActionMsg) controller.getNextTurn();
		assertTrue(msg.getPlayer().equals(player3));
		
		assertTrue(controller.endGame());
			
	}
	
	@Test
	public void testGameControllerBazaarTurn(){
		
		StartMsg startMsg = new StartMsg();
		startMsg.setBazaar(true);
		
		configuration = new Configuration(startMsg);
		controller = new GameController(players, configuration, new PlayerID("GameTest"));
		
		assertFalse(controller.endGame());
		
		GiveMeActionMsg msg = (GiveMeActionMsg) controller.getNextTurn();
		assertTrue(msg.getPlayer().equals(player1));
		assertFalse(controller.endGame());
		
		msg = (GiveMeActionMsg) controller.getNextTurn();
		assertTrue(msg.getPlayer().equals(player2));
		assertFalse(controller.endGame());
		
		msg = (GiveMeActionMsg) controller.getNextTurn();
		assertTrue(msg.getPlayer().equals(player3));
		assertFalse(controller.endGame());
		
		assertTrue(controller.getNextTurn() == null);
		assertTrue(controller.updateMsg().getClass().equals(BazaarStartActionMsg.class));
			
	}
	
	@Test
	public void testGameControllerInactivePlayer(){
		
		StartMsg startMsg = new StartMsg();
		startMsg.setBazaar(true);
		
		configuration = new Configuration(startMsg);
		controller = new GameController(players, configuration, new PlayerID("GameTest"));
		
		assertFalse(controller.endGame());
		
		assertTrue(controller.inactivePlayer(player1));
		
		GiveMeActionMsg msg = (GiveMeActionMsg) controller.getNextTurn();
		assertTrue(msg.getPlayer().equals(player2));
		assertFalse(controller.endGame());
		
		assertFalse(controller.inactivePlayer(player2));
			
	}
	
	@Test
	public void testAbstractGameController(){
		
		StartMsg startMsg = new StartMsg();
		startMsg.setBazaar(true);
		
		configuration = new Configuration(startMsg);
		controller = new GameController(players, configuration, new PlayerID("GameTest"));
		
		Updater updater = controller.update(new EmporiumTileActionMsg(player1));
		assertTrue(updater.getClass().equals(NotValidMoveUpdater.class));
		
	}

}
