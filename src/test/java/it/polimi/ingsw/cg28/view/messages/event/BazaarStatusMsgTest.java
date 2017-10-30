package it.polimi.ingsw.cg28.view.messages.event;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.model.BazaarModel;

public class BazaarStatusMsgTest {

	@Test (expected = NullPointerException.class)
	public void testConstructorWithNullModelParameter() {
		new BazaarStatusMsg(null, new PlayerID("TestPlayer1"));
	}
	
	@Test (expected = NullPointerException.class)
	public void testConstructorWithNullPlayerParameter() {
		new BazaarStatusMsg(new BazaarModel(), null);
	}
	
	@Test
	public void testConstructor() {
		BazaarStatusMsg msg = new BazaarStatusMsg(new BazaarModel(), new PlayerID("TestPlayer1"));
		
		assertNotNull(msg);
		assertNotNull(msg.getShelf());
		assertTrue(msg.getPlayer().getName().equals("TestPlayer1"));
	}

}
