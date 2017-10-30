package it.polimi.ingsw.cg28.view.messages.event;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;

public class WelcomeEventMsgTest {
	
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
	public void testConstructorWithNullParameter() {
		new WelcomeEventMsg(null);
	}
	
	@Test
	public void testConstructor() {
		WelcomeEventMsg msg = new WelcomeEventMsg(model);
		
		assertNotNull(msg);
		assertNotNull(msg.getTableStatus());
		assertNotNull(msg.getNobilityTrack());
		assertTrue(msg.getWelcome().equals("The match has begun!"));
	}

}
