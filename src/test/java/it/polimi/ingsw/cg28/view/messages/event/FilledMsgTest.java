package it.polimi.ingsw.cg28.view.messages.event;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.view.messages.action.ActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.HireAssistantActionMsg;
import it.polimi.ingsw.cg28.view.messages.action.turn.OneMoreMainActionMsg;

public class FilledMsgTest {

	@Test (expected = NullPointerException.class)
	public void testConstructorWithNullParameter() {
		new FilledMsg(null);
	}
	
	@Test
	public void testConstructor() {
		ActionMsg actionMsg = new OneMoreMainActionMsg(new PlayerID("TestPlayer1"));
		FilledMsg msg = new FilledMsg(actionMsg);
		
		assertNotNull(msg);
		assertNotNull(msg.getActionMsg());
		assertTrue(msg.getActionMsg().getPlayerID().getName().equals("TestPlayer1"));
		assertTrue(msg.getActionMsg() instanceof OneMoreMainActionMsg);
	}
	
	@Test (expected = NullPointerException.class)
	public void testSetActionMsgWithNullParameter() {
		ActionMsg actionMsg = new OneMoreMainActionMsg(new PlayerID("TestPlayer1"));
		FilledMsg msg = new FilledMsg(actionMsg);
		
		msg.setActionMsg(null);
	}
	
	@Test
	public void testSetActionMsg() {
		ActionMsg actionMsg = new OneMoreMainActionMsg(new PlayerID("TestPlayer1"));
		FilledMsg msg = new FilledMsg(actionMsg);
		ActionMsg actionMsg2 = new HireAssistantActionMsg(new PlayerID("TestPlayer2"));
		
		msg.setActionMsg(actionMsg2);
		
		assertNotNull(msg.getActionMsg());
		assertTrue(msg.getActionMsg().getPlayerID().getName().equals("TestPlayer2"));
		assertTrue(msg.getActionMsg() instanceof HireAssistantActionMsg);
	}

}
