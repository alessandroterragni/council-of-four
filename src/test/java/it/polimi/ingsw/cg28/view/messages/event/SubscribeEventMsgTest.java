package it.polimi.ingsw.cg28.view.messages.event;

import static org.junit.Assert.*;

import org.junit.Test;

public class SubscribeEventMsgTest {

	@Test
	public void testConstructor() {
		SubscribeEventMsg msg = new SubscribeEventMsg(true);
		
		assertNotNull(msg);
		assertTrue(msg.isSubscribed());
	}

}
