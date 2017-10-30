package it.polimi.ingsw.cg28.view.messages.event;

import static org.junit.Assert.*;

import org.junit.Test;

public class WelcomeBazaarEventMsgTest {

	@Test
	public void testConstructor() {
		WelcomeBazaarEventMsg msg = new WelcomeBazaarEventMsg();
		
		assertNotNull(msg);
		assertTrue(msg.getWelcome().equals("\nApu says: Welcome to my Bazaar!\nSHOPLIFTERS WILL BE EXECUTED!\nHere you can sell "
			+ "your items (Assistants, Politic Card and Permit Tiles)\n\n"));
	}

}
