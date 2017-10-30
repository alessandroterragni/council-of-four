package it.polimi.ingsw.cg28.view.messages.event;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;

public class GiveMeSellActionMsgTest {

	@Test (expected = NullPointerException.class)
	public void testConstructorWithNullPlayerParameter() {
		boolean[] salability = { true, false, true };
		new GiveMeSellActionMsg(null, salability);
	}
	
	@Test (expected = NullPointerException.class)
	public void testConstructorWithNullBoolArray() {
		new GiveMeSellActionMsg(new PlayerID("TestPlayer1"), null);
	}
	
	@Test
	public void testConstructor() {
		boolean[] salability = { true, false, true };
		GiveMeSellActionMsg msg = new GiveMeSellActionMsg(new PlayerID("TestPlayer1"), salability);
		
		assertNotNull(msg);
		assertTrue(msg.canSellAssistant());
		assertFalse(msg.canSellPoliticCards());
		assertTrue(msg.canSellPermitTiles());
		assertTrue(msg.getPlayer().getName().equals("TestPlayer1"));
	}

}
