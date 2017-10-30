package it.polimi.ingsw.cg28.view.messages.action;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.view.messages.event.ChatEventMsg;

public class ChatActionMsgTest {

	@Test (expected = NullPointerException.class)
	public void testConstructorWithNullParameter() {
		new ChatActionMsg(null);
	}
	
	@Test
	public void testConstructor() {
		ChatActionMsg msg = new ChatActionMsg("TestMessage");
		
		assertNotNull(msg);
		assertTrue(msg.getString().equals("TestMessage"));
	}
	
	@Test (expected = NullPointerException.class)
	public void testSetPlayerIDWithNullParameter() {
		ChatActionMsg msg = new ChatActionMsg("TestMessage");

		msg.setPlayerID(null);
	}
	
	@Test 
	public void testSetPlayerID() {
		ChatActionMsg msg = new ChatActionMsg("TestMessage");

		msg.setPlayerID(new PlayerID("TestPlayer1"));
		
		assertTrue(msg.getString().equals("[Player] TestPlayer1: TestMessage"));
	}
	
	@Test
	public void testRelatedEventMsg() {
		ChatActionMsg msg = new ChatActionMsg("TestMessage");
		
		assertTrue(msg.relatedEventMsg() instanceof ChatEventMsg);
	}

}
