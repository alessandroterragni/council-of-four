package it.polimi.ingsw.cg28.controller.actions;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.PlayerTurn;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;
import it.polimi.ingsw.cg28.view.messages.event.GiveMeActionMsg;

public class OneMoreMainActionTest {

	private OneMoreMainAction action;
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
	public void testOneMoreMainAction() {
		action = new OneMoreMainAction();
		assertNotNull("the action has been correctly built", action);
	}

	@Test (expected = NullPointerException.class )
	public void testActwithNullParameter() {
		action = new OneMoreMainAction();
		action.act(null);
	}
	
	@Test
	public void testActPlayer() {
		
		action = new OneMoreMainAction();
		Player p = model.getPlayer(players[0]);
		p.getAssistants().setValue(10);
		
		PlayerTurn turn = p.getTurn();
		
		turn.takeAction(new MainAction());
		
		assertTrue(!((GiveMeActionMsg)turn.msgRequired()).canDoMainAction());

		action.act(p);
		
		assertTrue("The number of assistants has been correctly decremented",p.getAssistants().getValue()==7);
		assertTrue("The action has been correctly added to the player turn",((GiveMeActionMsg)turn.msgRequired()).canDoMainAction());
		
	}


}
