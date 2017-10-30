package it.polimi.ingsw.cg28.view.messages.action.turn;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.exception.InvalidActionException;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;

public class EmporiumKingActionMsgTest {
	
	private EmporiumKingActionMsg msg;
	private ModelStatus model;
	PlayerID[] players = new PlayerID[2];
	
	@Before
	public void before() {
		
		players[0] = new PlayerID("TestPlayer1");
		players[1] = new PlayerID("TestPlayer2");
		
		msg = new EmporiumKingActionMsg(players[0]);
	
		ModelTestConfiguration config = new ModelTestConfiguration();
		model = config.buildModelTest(players);
	}

	@Test (expected = NullPointerException.class)
	public void testConstructorWithNullParameter() {
		new EmporiumKingActionMsg(null);
	}
	
	@Test
	public void testConstructor() {
		EmporiumKingActionMsg newMsg = new EmporiumKingActionMsg(new PlayerID("TestPlayer1"));
		
		assertNotNull(newMsg);
		assertTrue(newMsg.getPlayerID().getName().equals("TestPlayer1"));
		assertTrue(newMsg.getCodes().length == 2);
		assertTrue(newMsg.getCodesRequest().length == 2);
		assertTrue(newMsg.getName().equals("Build an emporium with the help of the king"));
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
		
		assertTrue(msg.getPoliticCards().length == model.getPlayer(msg.getPlayerID()).getPoliticCardsHand().size());
		assertNotNull(msg.getMap());
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
		String[] toSet = { "2", "1 2" };
		msg.fill(model);
		
		assertTrue(msg.setCodes(toSet));
	}
	
	@Test
	public void testSetCodesWithIrregularParsedInput() throws InvalidActionException {
		String[] toSet = { "5", "4 8 10" };
		msg.fill(model);
		
		assertFalse(msg.setCodes(toSet));
	}
	
	@Test
	public void testSetCodesWithIrregularFormatInput() throws InvalidActionException {
		String[] toSet = { "k", "l 8 r" };
		msg.fill(model);
		
		assertFalse(msg.setCodes(toSet));
	}
	
}
