package it.polimi.ingsw.cg28.view.messages.event;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;

public class GiveMeActionMsgTest {

	@Test (expected = NullPointerException.class)
	public void testConstructorWithNullPlayerParameter() {
		boolean[] allowance = { false, true };
		new GiveMeActionMsg(null, allowance);
	}
	
	@Test (expected = NullPointerException.class)
	public void testConstructorWithNullBoolArrayParameter() {
		new GiveMeActionMsg(new PlayerID("TestPlayer1"), null);
	}
	
	@Test
	public void testConstructor() {
		boolean[] allowance = { false, true };
		GiveMeActionMsg msg = new GiveMeActionMsg(new PlayerID("TestPlayer1"), allowance);
		
		assertNotNull(msg);
		assertTrue(msg.getPlayer().getName().equals("TestPlayer1"));
		assertFalse(msg.canDoMainAction());
		assertTrue(msg.canDoQuickAction());
	}

}
