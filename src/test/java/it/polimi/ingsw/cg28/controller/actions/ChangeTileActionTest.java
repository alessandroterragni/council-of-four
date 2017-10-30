package it.polimi.ingsw.cg28.controller.actions;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.PlayerTurn;
import it.polimi.ingsw.cg28.model.BusinessPermitTile;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;

public class ChangeTileActionTest {
	
	private ChangeTileAction action;
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
	
	@Test (expected=NullPointerException.class)
	public void testChangeTileActionWithNullParameter() {
		action = new ChangeTileAction(null);
	}
	
	@Test 
	public void testChangeTileAction() {
		action = new ChangeTileAction(model.getRegion(0));
		assertNotNull("the action has been correctly constructed",  action);
		
	}
	
	@Test (expected=NullPointerException.class)
	public void testActPlayerWithNullParameter() {
		action.act(null);	
	}
	
	@Test 
	public void testActPlayer() {
		
		Player p = model.getPlayer(players[0]);
		BusinessPermitTile original = model.getRegion(0).getUncovered()[0];
		action = new ChangeTileAction(model.getRegion(0));
		action.act(p);
		assertTrue("The number of assistants has been correctly decremented",p.getAssistants().getValue()==0);
		assertTrue("The tiles have been correctly changed", !original.equals(model.getRegion(0).getUncovered()[0]));

	}

	

}
