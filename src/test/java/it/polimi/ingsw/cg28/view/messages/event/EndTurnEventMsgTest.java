package it.polimi.ingsw.cg28.view.messages.event;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;

public class EndTurnEventMsgTest {

	@Test (expected = NullPointerException.class)
	public void testConstructorWithNullPlayerParameter() {
		new EndTurnEventMsg(null, "EndTurn test msg.");
	}
	
	@Test (expected = NullPointerException.class)
	public void testConstructorWithNullStringParameter() {
		new EndTurnEventMsg(new PlayerID("TestPlayer1"), null);
	}
	
	@Test
	public void testConstructor() {
		EndTurnEventMsg msg = new EndTurnEventMsg(new PlayerID("TestPlayer1"), "EndTurn test msg.");
		
		assertNotNull(msg);
		assertTrue(msg.getPlayer().getName().equals("TestPlayer1"));
		assertTrue(msg.getString().equals("EndTurn test msg."));
	}

}
