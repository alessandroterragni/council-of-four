package it.polimi.ingsw.cg28.view.messages.event;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;

public class TableStatusMsgTest {
	
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
		new TableStatusMsg(null, new PlayerID("TestPlayer1"));
	}
	
	@Test (expected = NullPointerException.class)
	public void testConstructorWithNullPlayerParameter() {
		new TableStatusMsg(model, null);
	}
	
	@Test
	public void testConstructor() {
		TableStatusMsg msg = new TableStatusMsg(model, players[0]);
		
		assertNotNull(msg);
		assertNotNull(msg.getMap());
		assertTrue(msg.getBalcony().length == 4);
		assertTrue(msg.getRegions().length == 3);
		assertTrue(msg.getPlayers().length == 2);
		assertTrue(msg.getCurrentTurnPlayer().equals("TestPlayer1"));
		for(int i = 0; i < msg.getRegions().length; i++){
			assertNotNull(msg.getRegions()[i]);
			assertTrue(msg.getRegions()[i].getRegionType().equals(model.getRegions()[i].getRegionType()));
			for(int j = 0; j < msg.getBalcony()[i].getCouncil().length; j++){
				assertTrue(msg.getBalcony()[i].getCouncil()[j].equals(model.getRegion(i).getBalcony().getCouncillor(j).getColor()));
			}
		}
		for(int k = 0; k < msg.getBalcony()[3].getCouncil().length; k++){
			assertTrue(msg.getBalcony()[3].getCouncil()[k].equals(model.getMap().getKingCouncil().getCouncillor(k).getColor()));
		}
		assertNotNull(msg.getBonusTiles());
		assertNotNull(msg.getKingRewards());
	}

}
