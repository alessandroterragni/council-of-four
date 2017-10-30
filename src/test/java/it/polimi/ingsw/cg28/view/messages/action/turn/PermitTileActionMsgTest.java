package it.polimi.ingsw.cg28.view.messages.action.turn;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.exception.InvalidActionException;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;

public class PermitTileActionMsgTest {

	private PermitTileActionMsg msg;
	private ModelStatus model;
	PlayerID[] players = new PlayerID[2];
	
	@Before
	public void before() {
		
		players[0] = new PlayerID("TestPlayer1");
		players[1] = new PlayerID("TestPlayer2");
		
		msg = new PermitTileActionMsg(players[0]);
	
		ModelTestConfiguration config = new ModelTestConfiguration();
		model = config.buildModelTest(players);
	}
	
	@Test (expected = NullPointerException.class)
	public void testConstructorWithNullParameter() {
		new PermitTileActionMsg(null);
	}
	
	@Test
	public void testConstructor() {
		PermitTileActionMsg newMsg = new PermitTileActionMsg(new PlayerID("TestPlayer1"));
		
		assertNotNull(newMsg);
		assertTrue(newMsg.getPlayerID().getName().equals("TestPlayer1"));
		assertTrue(newMsg.getCodes().length == 3);
		assertTrue(newMsg.getCodesRequest().length == 3);
		assertTrue(newMsg.getName().equals("Acquire a business permit tile"));
	}
	
	@Test (expected = NullPointerException.class)
	public void testFillWithNullParameter() throws InvalidActionException {
		msg.fill(null);
	}
	
	@Test (expected = InvalidActionException.class)
	public void testFillWithNotEnoughCards() throws InvalidActionException {
		model.getPlayer(msg.getPlayerID()).getPoliticCardsHand().removeAll
							(model.getPlayer(msg.getPlayerID()).getPoliticCardsHand());
		msg.fill(model);
	}
	
	@Test
	public void testFill() throws InvalidActionException {		
		msg.fill(model);
		
		assertTrue(msg.getRegions().length == 3);
		for(int i = 0; i < msg.getPoliticCards().length; i++){
			assertTrue(model.getPlayer(msg.getPlayerID()).getPoliticCardsHand().get(i).sameCol(msg.getPoliticCards()[i]));
		}
	}
	
	@Test (expected = NullPointerException.class)
	public void testSetCodesWithNullParameter() {
		msg.setCodes(null);
	}
	
	@Test
	public void testSetCodesWithRegularParsedInput() throws InvalidActionException {
		String[] toSet = { "2", "1", "1 2" };
		msg.fill(model);
		
		assertTrue(msg.setCodes(toSet));
	}
	
	@Test
	public void testSetCodesWithIrregularParsedInput() throws InvalidActionException {
		String[] toSet = { "5", "4", "5 8" };
		msg.fill(model);
		
		assertFalse(msg.setCodes(toSet));
	}
	
	@Test
	public void testSetCodesWithIrregularFormatInput() throws InvalidActionException {
		String[] toSet = { "k", "p", "m n" };
		msg.fill(model);
		
		assertFalse(msg.setCodes(toSet));
	}

}
