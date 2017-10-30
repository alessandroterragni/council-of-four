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

public class SendAssistantActionTest {

	private SendAssistantAction action;
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
	public void testSendAssistantActionWithNullParameter() {
		action = new SendAssistantAction(null,null,null);
	}
	
	@Test
	public void testSendAssistantAction() {
		action = new SendAssistantAction(model.getRegion(0).getBalcony(),model.getNoblesPool().get(0),model.getNoblesPool());
		assertNotNull("the action has been correctly built", action);
	}
	
	@Test (expected=NullPointerException.class)
	public void testActPlayerWithNullParameter() {
		action = new SendAssistantAction(null,null,null);
	}
	
	@Test  (expected=NullPointerException.class)
	public void testSendAssistantActionWithNullBalcony() {
		action = new SendAssistantAction(null,model.getNoblesPool().get(0),model.getNoblesPool());
	}
	
	@Test (expected=NullPointerException.class)
	public void testSendAssistantActionWithNullCouncillor() {
		action = new SendAssistantAction(model.getRegion(0).getBalcony(),null,model.getNoblesPool());
	}
	
	
	@Test (expected=NullPointerException.class)
	public void testSendAssistantActionWithNullNoblesPool() {
		action = new SendAssistantAction(model.getRegion(0).getBalcony(),model.getNoblesPool().get(0),null);
		
	}

	@Test 
	public void testActPlayer() {
		
		Player p = model.getPlayer(players[0]);
		Councillor selected = model.getNoblesPool().get(0);
		SendAssistantAction action = new SendAssistantAction(model.getRegion(0).getBalcony(),
				selected, model.getNoblesPool());
		Councillor fallen = model.getRegion(0).getBalcony().getCouncillor(0);
		action.act(p);
		assertTrue("The number of assistants has been correctly decremented",p.getAssistants().getValue()==0);
		assertTrue("the councillor has been correctly elected",model.getRegion(0).getBalcony().getCouncillor(3).equals(selected));
		assertTrue("the fallen councillor has been put in the nobles pool", model.getNoblesPool().contains(fallen));
	}
	
	
}
