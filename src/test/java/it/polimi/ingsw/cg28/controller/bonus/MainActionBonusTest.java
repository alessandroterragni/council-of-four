package it.polimi.ingsw.cg28.controller.bonus;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.PlayerTurn;
import it.polimi.ingsw.cg28.controller.actions.MainAction;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;
import it.polimi.ingsw.cg28.view.messages.event.GiveMeActionMsg;

public class MainActionBonusTest {

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
	
	@Test (expected = IllegalArgumentException.class)
	public void testMainActionBonusWithNegativeParameter() {
		new MainActionBonus(-50);
	}

	@Test
	public void testMainActionBonus() {
		assertNotNull("the bonus is correctly constructed",new MainActionBonus(10000));
	}

	@Test
	public void testGetBonus() {
		Player p = model.getPlayer(players[0]);
		Bonus bonus = new MainActionBonus(1);
		PlayerTurn turn = p.getTurn();
		turn.takeAction(new MainAction());
		bonus.getBonus(p);
		assertTrue("The action has been correctly added to the player turn",((GiveMeActionMsg)turn.msgRequired()).canDoMainAction());
		
	}
	

	@Test (expected = NullPointerException.class)
	public void testGetBonusWithNullParameter() {
		Bonus bonus = new MainActionBonus(5896);
		bonus.getBonus(null);
	}

	@Test
	public void testGetValue() {
		Bonus bonus = new MainActionBonus(10);
		assertTrue(bonus.getValue()==10);
	}

}
