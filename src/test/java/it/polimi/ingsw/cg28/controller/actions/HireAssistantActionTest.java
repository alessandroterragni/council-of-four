package it.polimi.ingsw.cg28.controller.actions;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.PlayerTurn;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;

public class HireAssistantActionTest {

	private HireAssistantAction action;
	private ModelStatus model;
	private PlayerID [] players;
	
	@Before
	public void before(){
		
		players = new PlayerID[1];
		players[0] = new PlayerID("Player1");
		
		ModelTestConfiguration config = new ModelTestConfiguration();
		model = config.buildModelTest(players);
		model.getPlayer(players[0]).setTurn(new PlayerTurn(players[0]));
			
	}
	
	
	@Test
	public void testHireAssistantAction() {
		action = new HireAssistantAction();
		assertNotNull("the action has been correctly built", action);
	}
	
	@Test (expected = NullPointerException.class )
	public void testActwithNullParameter() {
		action = new HireAssistantAction();
		action.act(null);
	}
	
	
	@Test
	public void testActPlayer() {
		
		Player p = model.getPlayer(players[0]);
		action = new HireAssistantAction();
		action.act(p);
		assertTrue("The number of assistants has been correctly incremented",p.getAssistants().getValue()==2);
		assertTrue("The number of coins has been correctly decremented",p.getCoins().getValue()==7);
	}



}
