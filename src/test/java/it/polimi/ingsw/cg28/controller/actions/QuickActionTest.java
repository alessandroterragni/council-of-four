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

public class QuickActionTest {

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
	public void testActPlayer() {
		Player p = model.getPlayer(players[0]);
		PlayerTurn turn = p.getTurn();
		QuickAction action = new QuickAction();
		assertTrue(((GiveMeActionMsg)turn.msgRequired()).canDoQuickAction());
		action.act(p);
		assertTrue(!((GiveMeActionMsg)turn.msgRequired()).canDoQuickAction());
		
	}
}
