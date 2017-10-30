package it.polimi.ingsw.cg28.view.messages.event;

import static org.junit.Assert.*;

import org.junit.Test;

public class EndBazaarMsgTest {

	@Test (expected = NullPointerException.class)
	public void testConstructorWithNullParameter() {
		new EndBazaarMsg(null);
	}
	
	@Test
	public void testConstructor() {
		EndBazaarMsg msg = new EndBazaarMsg("TestMessage");
		
		assertNotNull(msg);
		assertTrue(msg.getTransactions().equals("TestMessage"));
	}

}
