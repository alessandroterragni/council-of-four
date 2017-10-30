package it.polimi.ingsw.cg28.view.messages.event;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;

public class GiveMeBuyActionMsgTest {

	@Test (expected = NullPointerException.class)
	public void testConstructorWithNullParameter() {
		new GiveMeBuyActionMsg(null);
	}
	
	@Test
	public void testConstructor() {
		GiveMeBuyActionMsg msg = new GiveMeBuyActionMsg(new PlayerID("TestPlayer1"));
		
		assertNotNull(msg);
		assertTrue(msg.getPlayer().getName().equals("TestPlayer1"));
	}

}
