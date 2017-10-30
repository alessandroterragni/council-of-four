package it.polimi.ingsw.cg28.view.messages.action.bazaar;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.exception.InvalidActionException;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;

public class SellAssistantsActionMsgTest {

	private SellAssistantsActionMsg msg;
	private ModelStatus model;
	PlayerID[] players = new PlayerID[2];
	
	@Before
	public void before() {
		
		players[0] = new PlayerID("TestPlayer1");
		players[1] = new PlayerID("TestPlayer2");
		
		msg = new SellAssistantsActionMsg(players[0]);
	
		ModelTestConfiguration config = new ModelTestConfiguration();
		model = config.buildModelTest(players);
	}

	@Test (expected = NullPointerException.class)
	public void testConstructorWithNullParameter() {
		new SellAssistantsActionMsg(null);
	}
	
	@Test
	public void testConstructor() {
		SellAssistantsActionMsg newMsg = new SellAssistantsActionMsg(new PlayerID("TestPlayer1"));
		
		assertNotNull(newMsg);
		assertTrue(newMsg.getCodes().length == 2);
		assertTrue(newMsg.getCodesRequest().length == 2);
		assertTrue(newMsg.getName().equals("Sell assistants"));
	}
	
	@Test (expected = NullPointerException.class)
	public void testFillWithNullParameter() throws InvalidActionException {
		msg.fill(null);
	}
	
	@Test (expected = InvalidActionException.class)
	public void testFillWithNotEnoughAssistants() throws InvalidActionException {
		model.getPlayer(msg.getPlayerID()).getAssistants().setValue(0);
		msg.fill(model);
	}
	
	@Test
	public void testFill() throws InvalidActionException {
		model.getPlayer(msg.getPlayerID()).getAssistants().setValue(10);
		msg.fill(model);
		
		assertTrue(msg.getNumbAssistants() == 10);
	}
	
	@Test (expected = NullPointerException.class)
	public void testSetCodesWithNullParamter() {
		msg.setCodes(null);
	}
	
	@Test
	public void testSetCodesWithRegularParsedInput() throws InvalidActionException {
		String[] toSet = { "3", "5" };
		
		model.getPlayer(msg.getPlayerID()).getAssistants().setValue(10);
		msg.fill(model);
		
		assertTrue(msg.setCodes(toSet));
		assertTrue(msg.getNumbAssistantsToSell() == 3);
		assertTrue(msg.getPrice() == 5);
	}
	
	@Test
	public void testSetCodesWithIrregularParsedInput() throws InvalidActionException {
		String[] toSet = { "20", "-2" };
		
		model.getPlayer(msg.getPlayerID()).getAssistants().setValue(10);
		msg.fill(model);
		
		assertFalse(msg.setCodes(toSet));
	}
	
	@Test
	public void testSetCodesWithIrregularFormatInput() throws InvalidActionException {
		String[] toSet = { "a", "p" };
		
		model.getPlayer(msg.getPlayerID()).getAssistants().setValue(10);
		msg.fill(model);
		
		assertFalse(msg.setCodes(toSet));
	}

}
