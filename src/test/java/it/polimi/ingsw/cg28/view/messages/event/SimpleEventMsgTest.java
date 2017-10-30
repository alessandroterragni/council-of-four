package it.polimi.ingsw.cg28.view.messages.event;

import static org.junit.Assert.*;

import org.junit.Test;

public class SimpleEventMsgTest {

	@Test (expected = NullPointerException.class)
	public void testConstructorWithNullParameter() {
		new SimpleEventMsg(null);
	}
	
	@Test
	public void testConstructor() {
		SimpleEventMsg msg = new SimpleEventMsg("TestMessage");
		
		assertNotNull(msg);
		assertTrue(msg.getString().equals("TestMessage"));
	}

}
