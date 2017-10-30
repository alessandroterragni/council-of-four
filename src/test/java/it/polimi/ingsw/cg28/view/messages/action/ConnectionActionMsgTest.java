package it.polimi.ingsw.cg28.view.messages.action;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;

public class ConnectionActionMsgTest {
	
	@Test (expected = NullPointerException.class)
	public void testConstructorWithNullNameParameter() {
		new ConnectionActionMsg(new PlayerID("TestName"), null);
	}
	
	@Test
	public void testConstructor() {
		ConnectionActionMsg msg = new ConnectionActionMsg(new PlayerID("TestPlayer1"), "TestPlayer1");
		
		assertNotNull(msg);
		assertTrue(msg.getPlayerID().getName().equals("TestPlayer1"));
		assertTrue(msg.getPlayerName().equals("TestPlayer1"));
	}
	
	

}
