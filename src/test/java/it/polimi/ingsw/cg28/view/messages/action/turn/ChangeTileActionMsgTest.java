package it.polimi.ingsw.cg28.view.messages.action.turn;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.exception.InvalidActionException;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;

public class ChangeTileActionMsgTest {

	private ChangeTileActionMsg msg;
	private ModelStatus model;
	PlayerID[] players = new PlayerID[2];
	
	@Before
	public void before() {
		
		players[0] = new PlayerID("TestPlayer1");
		players[1] = new PlayerID("TestPlayer2");
		
		msg = new ChangeTileActionMsg(players[0]);
	
		ModelTestConfiguration config = new ModelTestConfiguration();
		model = config.buildModelTest(players);
	}
	
	@Test (expected = NullPointerException.class)
	public void testConstructorWithNullPlayerParameter() {
		new ChangeTileActionMsg(null);
	}
	
	@Test
	public void testConstructor() {
		ChangeTileActionMsg newMsg = new ChangeTileActionMsg(new PlayerID("TestPlayer"));
		
		assertNotNull(newMsg);
		assertTrue(newMsg.getPlayerID().getName().equals("TestPlayer"));
		assertTrue(newMsg.getCodes().length == 1);
		assertTrue(newMsg.getCodesRequest().length == 1);
		assertTrue(newMsg.getName().equals("Change building permit tiles"));
	}
	
	@Test (expected = NullPointerException.class)
	public void testFillWithNullParameter() throws InvalidActionException {
		msg.fill(null);
	}
	
	@Test
	public void testFill() throws InvalidActionException {
		msg.fill(model);
		
		assertTrue(msg.getRegion().length == 3);
		for(int i = 0; i < msg.getRegion().length; i++){
			assertNotNull(msg.getRegion()[i]);
			assertTrue(msg.getRegion()[i].getRegionType().equals(model.getRegions()[i].getRegionType()));
		}
	}
	
	@Test (expected = NullPointerException.class)
	public void testSetCodesWithNullParamter() {
		msg.setCodes(null);
	}
	
	@Test
	public void testSetCodesWithRegularParsedInput() throws InvalidActionException {
		String[] toSet = { "1" };
		msg.fill(model);
		
		assertTrue(msg.setCodes(toSet));
	}
	
	@Test
	public void testSetCodesWithIrregularParsedInput() throws InvalidActionException {
		String[] toSet = { "4" };
		msg.fill(model);
		
		assertFalse(msg.setCodes(toSet));
	}
	
	@Test
	public void testSetCodesWithIrregularFormatInput() throws InvalidActionException {
		String[] toSet = { "q" };
		msg.fill(model);
		
		assertFalse(msg.setCodes(toSet));
	}

	@Test
	public void testGetName() {
		assertTrue(msg.getName().equals("Change building permit tiles"));
	}
	
}
