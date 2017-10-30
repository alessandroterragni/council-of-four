package it.polimi.ingsw.cg28.controller.actions;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.PlayerTurn;
import it.polimi.ingsw.cg28.controller.bonus.AssistantBonus;
import it.polimi.ingsw.cg28.exception.InvalidActionException;
import it.polimi.ingsw.cg28.model.BusinessPermitTile;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.model.Town;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;

public class EmporiumTileActionTest {
	
	private ModelStatus model;
	private PlayerID [] players;
	private BusinessPermitTile tile;
	
	@Before
	public void before(){
		
		players = new PlayerID[2];
		players[0] = new PlayerID("Player1");
		players[1] = new PlayerID("Player2");
		
		ModelTestConfiguration config = new ModelTestConfiguration();
		model = config.buildModelTest(players);
		model.getPlayer(players[0]).setTurn(new PlayerTurn(players[0]));
		String[] letters = {"Arkon","Graden"};
		tile = new BusinessPermitTile(new AssistantBonus(9000), letters);
	}
	
	@Test (expected=NullPointerException.class)
	public void testEmporiumTileActionwithNullParameter() {
		new EmporiumTileAction(null, null, null);
	}
	
	@Test (expected=NullPointerException.class)
	public void testEmporiumTileActionwithNullTile() {
		 new EmporiumTileAction(null,model.getMap().getTown("Arkon"),model.getMap()); 
	}
	
	@Test (expected=NullPointerException.class)
	public void testEmporiumTileActionwithNullTown() {
		 new EmporiumTileAction(tile,null,model.getMap()); 
	}
	
	@Test (expected=NullPointerException.class)
	public void testEmporiumTileActionwithNullMap() {
		 new EmporiumTileAction(tile,model.getMap().getTown("Arkon"),null); 
	}	
	

	@Test
	public void testEmporiumTileAction() {
		EmporiumTileAction action = new EmporiumTileAction(tile,model.getMap().getTown("Arkon"),model.getMap()); 
		assertTrue("the action has been correctly constructed",!action.equals(null));
	}
	
	@Test (expected=NullPointerException.class)
	public void testActPlayerWithNullParameter() {
		EmporiumTileAction action = new EmporiumTileAction(tile,model.getMap().getTown("Arkon"),model.getMap()); 
		action.act(null);
	}
	
	@Test 
	public void testActWithNoEmporiuminTheTown() {
		EmporiumTileAction action = new EmporiumTileAction(tile,model.getMap().getTown("Arkon"),model.getMap()); 
		Player p= model.getPlayers()[0];
		action.act(p);
		assertTrue("The number of assistants is unchanged",p.getCoins().getValue()==10);
	}
	
	@Test 
	public void testActWith1EmporiuminTheTown() {
		
		Player p1 = model.getPlayer(players[0]);
		Player p2 = model.getPlayer(players[1]);
		
		Town arkon = model.getMap().getTown("Arkon");
		
		model.getMap().addEmporium(p2.getID(), arkon);
		
		EmporiumTileAction action = new EmporiumTileAction(tile,arkon,model.getMap()); 
	
		action.act(p1);
		
		assertTrue("The number of assistants has been correctly decremented", p1.getAssistants().getValue()==0);
	}
	
	
	@Test
	public void TestTheBuildingOftheEmporium() throws InvalidActionException{
		
		Player p1 = model.getPlayer(players[0]);
		int numbEmpPrec = p1.getEmporiumNumber();
		Town arkon = model.getMap().getTown("Arkon");
		assertTrue("The player p1 has not an emporium in the city",!arkon.hasEmporium(p1.getID()));
		
		EmporiumTileAction action = new EmporiumTileAction(tile,arkon,model.getMap()); 
		
		action.act(p1);
		
		assertTrue("Now the player p1 has an emporium in the city",arkon.hasEmporium(p1.getID()));
		
		assertTrue(p1.getEmporiumNumber() == numbEmpPrec -1);
	}
	
}


