package it.polimi.ingsw.cg28.view.messages.event;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;

public class PlayerStatusMsgTest {
	
	private ModelStatus model;
	PlayerID[] players = new PlayerID[2];
	
	@Before
	public void before() {
		
		players[0] = new PlayerID("TestPlayer1");
		players[1] = new PlayerID("TestPlayer2");
	
		ModelTestConfiguration config = new ModelTestConfiguration();
		model = config.buildModelTest(players);
	}

	@Test (expected = NullPointerException.class)
	public void testConstructorWithNullModelParameter() {
		new PlayerStatusMsg(null, new PlayerID("TestReceiver1"), new PlayerID("TestPlayer1"));
	}
	
	@Test (expected = NullPointerException.class)
	public void testConstructorWithNullReceiverParameter() {
		new PlayerStatusMsg(model, null, new PlayerID("TestPlayer1"));
	}
	
	@Test (expected = NullPointerException.class)
	public void testConstructorWithNullPlayerParameter() {
		new PlayerStatusMsg(model, new PlayerID("TestReceiver1"), null);
	}
	
	@Test
	public void testConstructor() {
		PlayerStatusMsg msg = new PlayerStatusMsg(model, players[0], players[1]);
		
		assertNotNull(msg);
		assertNotNull(msg.getTableStatusMsg());
		assertNotNull(msg.getTPlayer());
		assertTrue(msg.getTPlayer().getName().equals("TestPlayer1"));
		assertTrue(msg.getPlayer().getName().equals("TestPlayer1"));
	}

}
