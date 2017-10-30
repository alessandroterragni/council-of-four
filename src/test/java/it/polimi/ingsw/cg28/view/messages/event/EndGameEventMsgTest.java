package it.polimi.ingsw.cg28.view.messages.event;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.view.messages.action.EndGameMsg;

public class EndGameEventMsgTest {

	@Test (expected = NullPointerException.class)
	public void testConstructorWithNullParameter() {
		new EndGameEventMsg(null);
	}
	
	@Test
	public void testConstructor() {
		EndGameEventMsg msg = new EndGameEventMsg(new EndGameMsg(new HashMap<>(), new PlayerID("TestPlayer1")));
		
		assertNotNull(msg);
		assertNotNull(msg.getEndGameMsg());
		assertNotNull(msg.getEndGameMsg().getScores());
		assertTrue(msg.getEndGameMsg().getWinner().getName().equals("TestPlayer1"));
	}

}
