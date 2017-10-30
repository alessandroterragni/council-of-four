package it.polimi.ingsw.cg28.view.messages.action.turn;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.bonus.CoinBonus;
import it.polimi.ingsw.cg28.controller.bonus.VictoryPointBonus;
import it.polimi.ingsw.cg28.exception.InvalidActionException;
import it.polimi.ingsw.cg28.model.BusinessPermitTile;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;

public class EmporiumTileActionMsgTest {
	
	private EmporiumTileActionMsg msg;
	private ModelStatus model;
	PlayerID[] players = new PlayerID[2];
	
	@Before
	public void before() {
		
		players[0] = new PlayerID("TestPlayer1");
		players[1] = new PlayerID("TestPlayer2");
		
		msg = new EmporiumTileActionMsg(players[0]);
	
		ModelTestConfiguration config = new ModelTestConfiguration();
		model = config.buildModelTest(players);
	}

	@Test (expected = NullPointerException.class)
	public void testConstructorWithNullParameter() {
		new EmporiumTileActionMsg(null);
	}
	
	@Test
	public void testConstructor() {
		EmporiumTileActionMsg newMsg = new EmporiumTileActionMsg(new PlayerID("TestPlayer1"));
		
		assertNotNull(newMsg);
		assertTrue(newMsg.getPlayerID().getName().equals("TestPlayer1"));
		assertTrue(newMsg.getCodes().length == 2);
		assertTrue(newMsg.getCodesRequest().length == 2);
		assertTrue(newMsg.getName().equals("Build an emporium using a permit tile"));
	}
	
	@Test (expected = NullPointerException.class)
	public void testFillWithNullParameter() throws InvalidActionException {
		msg.fill(null);
	}
	
	@Test (expected = InvalidActionException.class)
	public void testFillWithNotEnoughTiles() throws InvalidActionException {
		model.getPlayer(msg.getPlayerID()).getPossessedTiles().removeAll
							(model.getPlayer(msg.getPlayerID()).getPossessedTiles());
		msg.fill(model);
	}
	
	@Test
	public void testFill() throws InvalidActionException {		
		String[] letters = { "Juvelar", "Graden" };
		BusinessPermitTile tile1 = new BusinessPermitTile(new VictoryPointBonus(4), letters);
		BusinessPermitTile tile2 = new BusinessPermitTile(new CoinBonus(2), letters);
		
		model.getPlayer(players[0]).getPossessedTiles().add(tile1);
		model.getPlayer(players[0]).getPossessedTiles().add(tile2);
		msg.fill(model);
		
		assertTrue(msg.getTiles().size() == model.getPlayer(msg.getPlayerID()).getPossessedTiles().size());
		assertTrue(msg.getTowns().size() == 2);
	}
	
	@Test (expected = NullPointerException.class)
	public void testSetCodesWithNullParameter() {
		msg.setCodes(null);
	}
	
	@Test
	public void testSetCodesWithRegularParsedInput() throws InvalidActionException {
		String[] toSet = { "2", "1" };
		
		String[] letters = { "Juvelar", "Graden" };
		BusinessPermitTile tile1 = new BusinessPermitTile(new VictoryPointBonus(4), letters);
		BusinessPermitTile tile2 = new BusinessPermitTile(new CoinBonus(2), letters);
		
		model.getPlayer(players[0]).getPossessedTiles().add(tile1);
		model.getPlayer(players[0]).getPossessedTiles().add(tile2);
		msg.fill(model);
		
		assertTrue(msg.setCodes(toSet));
		assertTrue(msg.getTileCode() == 2);
		assertTrue(msg.getTownCode() == 1);
	}
	
	@Test
	public void testSetCodesWithIrregularParsedInput() throws InvalidActionException {
		String[] toSet = { "5", "4" };
		
		String[] letters = { "Juvelar", "Graden" };
		BusinessPermitTile tile1 = new BusinessPermitTile(new VictoryPointBonus(4), letters);
		BusinessPermitTile tile2 = new BusinessPermitTile(new CoinBonus(2), letters);
		
		model.getPlayer(players[0]).getPossessedTiles().add(tile1);
		model.getPlayer(players[0]).getPossessedTiles().add(tile2);
		msg.fill(model);
		
		assertFalse(msg.setCodes(toSet));
	}
	
	@Test
	public void testSetCodesWithIrregularFormatInput() throws InvalidActionException {
		String[] toSet = { "k", "l" };
		
		String[] letters = { "Juvelar", "Graden" };
		BusinessPermitTile tile1 = new BusinessPermitTile(new VictoryPointBonus(4), letters);
		BusinessPermitTile tile2 = new BusinessPermitTile(new CoinBonus(2), letters);
		
		model.getPlayer(players[0]).getPossessedTiles().add(tile1);
		model.getPlayer(players[0]).getPossessedTiles().add(tile2);
		msg.fill(model);
		
		assertFalse(msg.setCodes(toSet));
	}

}
