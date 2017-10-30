package it.polimi.ingsw.cg28.view.messages.action.bazaar;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.bonus.VictoryPointBonus;
import it.polimi.ingsw.cg28.exception.InvalidActionException;
import it.polimi.ingsw.cg28.model.BusinessPermitTile;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;

public class SellPermitTilesActionMsgTest {

	private SellPermitTilesActionMsg msg;
	private ModelStatus model;
	PlayerID[] players = new PlayerID[2];
	
	@Before
	public void before() {
		
		players[0] = new PlayerID("TestPlayer1");
		players[1] = new PlayerID("TestPlayer2");
		
		msg = new SellPermitTilesActionMsg(players[0]);
	
		ModelTestConfiguration config = new ModelTestConfiguration();
		model = config.buildModelTest(players);
	}

	@Test (expected = NullPointerException.class)
	public void testConstructorWithNullParameter() {
		new SellPermitTilesActionMsg(null);
	}
	
	@Test
	public void testConstructor() {
		SellPermitTilesActionMsg newMsg = new SellPermitTilesActionMsg(new PlayerID("TestPlayer1"));
		
		assertNotNull(newMsg);
		assertTrue(newMsg.getCodes().length == 2);
		assertTrue(newMsg.getCodesRequest().length == 2);
		assertTrue(newMsg.getName().equals("Sell permit tiles"));
	}
	
	@Test (expected = NullPointerException.class)
	public void testFillWithNullParameter() throws InvalidActionException {
		msg.fill(null);
	}
	
	@Test (expected = InvalidActionException.class)
	public void testFillWithNotEnoughTiles() throws InvalidActionException {
		msg.fill(model);
	}
	
	@Test
	public void testFill() throws InvalidActionException {
		String[] letters = { "Juvelar" };
		BusinessPermitTile tile = new BusinessPermitTile(new VictoryPointBonus(4), letters);
		model.getPlayer(msg.getPlayerID()).getPossessedTiles().add(tile);
		msg.fill(model);
		
		assertTrue(msg.getTiles().length == 1);
	}
	
	@Test (expected = NullPointerException.class)
	public void testSetCodesWithNullParamter() {
		msg.setCodes(null);
	}
	
	@Test
	public void testSetCodesWithRegularParsedInput() throws InvalidActionException {
		String[] toSet = { "1", "5" };
		
		String[] letters = { "Juvelar" };
		BusinessPermitTile tile = new BusinessPermitTile(new VictoryPointBonus(4), letters);
		model.getPlayer(msg.getPlayerID()).getPossessedTiles().add(tile);
		msg.fill(model);
		
		assertTrue(msg.setCodes(toSet));
		assertTrue(msg.getTiles().length == 1);
		assertTrue(msg.getPrice() == 5);
	}
	
	@Test
	public void testSetCodesWithIrregularParsedInput() throws InvalidActionException {
		String[] toSet = { "20", "-2" };
		
		String[] letters = { "Juvelar" };
		BusinessPermitTile tile = new BusinessPermitTile(new VictoryPointBonus(4), letters);
		model.getPlayer(msg.getPlayerID()).getPossessedTiles().add(tile);
		msg.fill(model);
		
		assertFalse(msg.setCodes(toSet));
	}
	
	@Test
	public void testSetCodesWithIrregularFormatInput() throws InvalidActionException {
		String[] toSet = { "t", "p" };
		
		String[] letters = { "Juvelar" };
		BusinessPermitTile tile = new BusinessPermitTile(new VictoryPointBonus(4), letters);
		model.getPlayer(msg.getPlayerID()).getPossessedTiles().add(tile);
		msg.fill(model);
		
		assertFalse(msg.setCodes(toSet));
	}

}
