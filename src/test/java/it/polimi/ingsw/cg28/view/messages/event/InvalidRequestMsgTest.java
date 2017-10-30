package it.polimi.ingsw.cg28.view.messages.event;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.cg28.connections.server.InvalidRequestMsg;

public class InvalidRequestMsgTest {

	private InvalidRequestMsg msg;
	
	@Test
	public void testInvalidRequestMsg() {
		assertNotNull(new InvalidRequestMsg("Test"));
	}
	
	@Test(expected = NullPointerException.class)
	public void testInvalidRequestMsgWithParameterNull() {
		msg = new InvalidRequestMsg(null);
	}
	
	@Test
	public void testInvalidRequestMsgGetter() {
		msg = new InvalidRequestMsg("Test");
		assertTrue(msg.getString().equals("Test"));
	}
	
	@Test
	public void testInvalidRequestMsgRead() {
		msg = new InvalidRequestMsg("Test");
		msg.read(null);
	}

}
