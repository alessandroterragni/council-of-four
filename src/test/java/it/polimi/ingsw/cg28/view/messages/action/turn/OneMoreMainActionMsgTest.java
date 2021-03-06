package it.polimi.ingsw.cg28.view.messages.action.turn;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;

public class OneMoreMainActionMsgTest {
	
	PlayerID[] players = new PlayerID[2];
	
	@Before
	public void before() {
		
		players[0] = new PlayerID("TestPlayer1");
		players[1] = new PlayerID("TestPlayer2");
	}

	@Test (expected = NullPointerException.class)
	public void testConstrucorWithNullParameter() {
		new OneMoreMainActionMsg(null);
	}
	
	@Test
	public void testConstructor() {
		OneMoreMainActionMsg newMsg = new OneMoreMainActionMsg(new PlayerID("TestPlayer1"));
		
		assertNotNull(newMsg);
		assertTrue(newMsg.getName().equals("Perform an additional main action"));
	}

}
