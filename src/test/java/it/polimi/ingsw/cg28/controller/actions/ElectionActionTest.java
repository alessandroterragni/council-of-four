package it.polimi.ingsw.cg28.controller.actions;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.PlayerTurn;
import it.polimi.ingsw.cg28.model.Councillor;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;

public class ElectionActionTest {
	
	private ElectionAction action;
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
	public void testElectionActionWithNullParameter() {
		action = new ElectionAction(null, null, null);
	}
	
	@Test 
	public void testElectionAction() {
		action = new ElectionAction(model.getRegion(1).getBalcony(), model.getNoblesPool().get(1) ,model.getNoblesPool());
		assertNotNull("the action has been correctly constructed", action);
	}
	
	@Test (expected=NullPointerException.class)
	public void testElectionActionWithNullBalcony() {
		action = new ElectionAction(null, model.getNoblesPool().get(1) ,model.getNoblesPool());
	}
	
	@Test (expected=NullPointerException.class)
	public void testElectionActionWithNullCouncillor() {
		action = new ElectionAction(model.getRegion(1).getBalcony(), null,model.getNoblesPool());
	}
	
	@Test (expected=NullPointerException.class)
	public void testElectionActionWithNullConcillors() {
		action = new ElectionAction(null, model.getNoblesPool().get(1) ,null);
	}
	
	@Test (expected=NullPointerException.class)
	public void testActWithNullParameter() {
		ElectionAction action = new ElectionAction(model.getRegion(1).getBalcony(),model.getNoblesPool().get(1),model.getNoblesPool());
		action.act(null);
	}

	@Test
	public void testAct() {
		
		Player p = model.getPlayer(players[0]);
		Councillor selected = model.getNoblesPool().get(0); 
		Councillor fallen = model.getRegion(1).getBalcony().getCouncillor(0); 
		action = new ElectionAction(model.getRegion(1).getBalcony(),selected,model.getNoblesPool());
		action.act(p);
		assertTrue("the councillor has been correctly elected",model.getRegion(1).getBalcony().getCouncillor(3).equals(selected));
		assertTrue("The number of coins has been correctly incremented",p.getCoins().getValue()==14);
		assertTrue("the fallen councillor has been put in the nobles pool",model.getNoblesPool().contains(fallen));
		
	}

	

	

}
