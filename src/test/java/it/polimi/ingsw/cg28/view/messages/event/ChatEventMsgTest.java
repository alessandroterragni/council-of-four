package it.polimi.ingsw.cg28.view.messages.event;

import static org.junit.Assert.*;

import org.junit.Test;

public class ChatEventMsgTest {

	@Test (expected = NullPointerException.class)
	public void testConstructorWithNullParameter() {
		new ChatEventMsg(null);
	}
	
	@Test
	public void testConstructor() {
		ChatEventMsg msg = new ChatEventMsg("TestMessage");
		
		assertNotNull(msg);
		assertTrue(msg.getString().equals("TestMessage"));
	}

}
