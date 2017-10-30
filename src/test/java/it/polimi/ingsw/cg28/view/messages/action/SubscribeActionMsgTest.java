package it.polimi.ingsw.cg28.view.messages.action;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;

public class SubscribeActionMsgTest {
	
	@Test
	public void testConstructor() {
		SubscribeActionMsg msg = new SubscribeActionMsg(new PlayerID("TestPlayer1"));
		
		assertNotNull(msg);
		assertTrue(msg.getPlayerID().getName().equals("TestPlayer1"));
	}

}
