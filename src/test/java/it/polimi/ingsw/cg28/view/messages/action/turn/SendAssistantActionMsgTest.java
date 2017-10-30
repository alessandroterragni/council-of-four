package it.polimi.ingsw.cg28.view.messages.action.turn;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.exception.InvalidActionException;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;

public class SendAssistantActionMsgTest {

	private SendAssistantActionMsg msg;
	private ModelStatus model;
	PlayerID[] players = new PlayerID[2];
	
	@Before
	public void before() {
		
		players[0] = new PlayerID("TestPlayer1");
		players[1] = new PlayerID("TestPlayer2");
		
		msg = new SendAssistantActionMsg(players[0]);
	
		ModelTestConfiguration config = new ModelTestConfiguration();
		model = config.buildModelTest(players);
	}

	@Test (expected = NullPointerException.class)
	public void testConstructorWithNullParameter() {
		new SendAssistantActionMsg(null);
	}
	
	@Test
	public void testConstructor() {
		SendAssistantActionMsg newMsg = new SendAssistantActionMsg(new PlayerID("TestPlayer1"));
		
		assertNotNull(newMsg);
		assertTrue(newMsg.getPlayerID().getName().equals("TestPlayer1"));
		assertTrue(newMsg.getCodes().length == 2);
		assertTrue(newMsg.getCodesRequest().length == 2);
		assertTrue(newMsg.getName().equals("Send an assistant to elect a councillor"));
	}
	
	@Test (expected = NullPointerException.class)
	public void testFillWithNullParameter() throws InvalidActionException {
		msg.fill(null);
	}
	
	@Test
	public void testFill() throws InvalidActionException {		
		msg.fill(model);
		
		assertNotNull(msg.getMap());
		assertTrue(msg.getRegionNumber() == 3);
		for(int i = 0; i < msg.getRegionNumber(); i++){
			assertNotNull(msg.getRegions()[i]);
			assertTrue(msg.getRegions()[i].getRegionType().equals(model.getRegions()[i].getRegionType()));
		}
		assertNotNull(msg.getPool());
		for(int i = 0; i < msg.getPool().getPool().size(); i++){
			assertTrue(msg.getPool().getPool(i).equals(model.getNoblesPool().get(i).getColor()));
		}
	}

	@Test (expected = NullPointerException.class)
	public void testSetCodesWithNullParameter() {
		msg.setCodes(null);
	}
	
	@Test
	public void testSetCodesWithRegularParsedInput() throws InvalidActionException {
		String[] toSet = { "2", "1" };
		msg.fill(model);
		
		assertTrue(msg.setCodes(toSet));
	}
	
	@Test
	public void testSetCodesWithIrregularParsedInput() throws InvalidActionException {
		String[] toSet = { "5", "4" };
		msg.fill(model);
		
		assertFalse(msg.setCodes(toSet));
	}
	
	@Test
	public void testSetCodesWithIrregularFormatInput() throws InvalidActionException {
		String[] toSet = { "k", "l" };
		msg.fill(model);
		
		assertFalse(msg.setCodes(toSet));
	}

}
