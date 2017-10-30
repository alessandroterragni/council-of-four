package it.polimi.ingsw.cg28.view.messages.event;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;

public class EndBuyTurnEventMsgTest {

	@Test (expected = NullPointerException.class)
	public void testConstructorWithNullPlayerParameter() {
		new EndBuyTurnEventMsg(null, "TestMessage");
	}
	
	@Test (expected = NullPointerException.class)
	public void testConstructorWithNullStringParameter() {
		new EndBuyTurnEventMsg(new PlayerID("TestPlayer1"), null);
	}
	
	@Test
	public void testConstructor() {
		EndBuyTurnEventMsg msg = new EndBuyTurnEventMsg(new PlayerID("TestPlayer1"), "TestMessage");
		
		assertNotNull(msg);
		assertTrue(msg.getPlayer().getName().equals("TestPlayer1"));
		assertTrue(msg.getString().equals("TestMessage"));
	}

}
