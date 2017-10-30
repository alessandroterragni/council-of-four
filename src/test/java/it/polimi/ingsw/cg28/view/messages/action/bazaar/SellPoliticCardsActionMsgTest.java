package it.polimi.ingsw.cg28.view.messages.action.bazaar;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.exception.InvalidActionException;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;

public class SellPoliticCardsActionMsgTest {

	private SellPoliticCardsActionMsg msg;
	private ModelStatus model;
	PlayerID[] players = new PlayerID[2];
	
	@Before
	public void before() {
		
		players[0] = new PlayerID("TestPlayer1");
		players[1] = new PlayerID("TestPlayer2");
		
		msg = new SellPoliticCardsActionMsg(players[0]);
	
		ModelTestConfiguration config = new ModelTestConfiguration();
		model = config.buildModelTest(players);
	}

	@Test (expected = NullPointerException.class)
	public void testConstructorWithNullParameter() {
		new SellPoliticCardsActionMsg(null);
	}
	
	@Test
	public void testConstructor() {
		SellPoliticCardsActionMsg newMsg = new SellPoliticCardsActionMsg(new PlayerID("TestPlayer1"));
		
		assertNotNull(newMsg);
		assertTrue(newMsg.getCodes().length == 2);
		assertTrue(newMsg.getCodesRequest().length == 2);
		assertTrue(newMsg.getName().equals("Sell Politic Cards"));
	}
	
	@Test (expected = NullPointerException.class)
	public void testFillWithNullParameter() throws InvalidActionException {
		msg.fill(null);
	}
	
	@Test (expected = InvalidActionException.class)
	public void testFillWithNotEnoughCards() throws InvalidActionException {
		model.getPlayer(msg.getPlayerID()).getPoliticCardsHand().
				removeAll(model.getPlayer(msg.getPlayerID()).getPoliticCardsHand());
		msg.fill(model);
	}
	
	@Test
	public void testFill() throws InvalidActionException {
		msg.fill(model);
		
		assertTrue(msg.getPoliticCards().length == 2);
	}
	
	@Test (expected = NullPointerException.class)
	public void testSetCodesWithNullParamter() {
		msg.setCodes(null);
	}
	
	@Test
	public void testSetCodesWithRegularParsedInput() throws InvalidActionException {
		String[] toSet = { "1 2", "5" };
		
		model.getPlayer(msg.getPlayerID()).getAssistants().setValue(10);
		msg.fill(model);
		
		assertTrue(msg.setCodes(toSet));
		assertTrue(msg.getPrice() == 5);
	}
	
	@Test
	public void testSetCodesWithIrregularParsedInput() throws InvalidActionException {
		String[] toSet = { "0 10", "-2" };
		
		model.getPlayer(msg.getPlayerID()).getAssistants().setValue(10);
		msg.fill(model);
		
		assertFalse(msg.setCodes(toSet));
	}
	
	@Test
	public void testSetCodesWithIrregularFormatInput() throws InvalidActionException {
		String[] toSet = { "a b", "p" };
		
		model.getPlayer(msg.getPlayerID()).getAssistants().setValue(10);
		msg.fill(model);
		
		assertFalse(msg.setCodes(toSet));
	}

}
