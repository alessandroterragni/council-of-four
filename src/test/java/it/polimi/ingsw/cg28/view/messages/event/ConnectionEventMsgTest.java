package it.polimi.ingsw.cg28.view.messages.event;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;

public class ConnectionEventMsgTest {

	@Test (expected = NullPointerException.class)
	public void testConstructorWithNullParameter() {
		new ConnectionEventMsg(null);
	}
	
	@Test
	public void testConstructor() {
		ConnectionEventMsg msg = new ConnectionEventMsg(new PlayerID("TestPlayer1"));
		
		assertNotNull(msg);
		assertTrue(msg.getPlayerID().getName().equals("TestPlayer1"));
	}

}
