package it.polimi.ingsw.cg28.view.messages.event;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;

public class EndSellTurnEventMsgTest {

	@Test (expected = NullPointerException.class)
	public void testConstructorWithNullPlayerParameter() {
		new EndSellTurnEventMsg(null, "EndSellTurn test msg.");
	}
	
	@Test (expected = NullPointerException.class)
	public void testConstructorWithNullStringParameter() {
		new EndSellTurnEventMsg(new PlayerID("TestPlayer1"), null);
	}
	
	@Test
	public void testConstructor() {
		EndSellTurnEventMsg msg = new EndSellTurnEventMsg(new PlayerID("TestPlayer1"), "EndSellTurn test msg.");
		
		assertNotNull(msg);
		assertTrue(msg.getPlayer().getName().equals("TestPlayer1"));
		assertTrue(msg.getString().equals("EndSellTurn test msg."));
	}

}
