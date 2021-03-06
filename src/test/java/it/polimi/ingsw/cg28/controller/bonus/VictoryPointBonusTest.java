package it.polimi.ingsw.cg28.controller.bonus;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.PlayerTurn;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;

public class VictoryPointBonusTest {

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
	public void testVictoryPointBonus() {
		assertNotNull("the bonus is correctly constructed",new VictoryPointBonus(10000));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testVictoryPointBonusWIthNegativeParameter() {
		new VictoryPointBonus(-5);
	}

	@Test
	public void testGetBonus() {
		Bonus bonus = new VictoryPointBonus(10);
		Player p = model.getPlayer(players[0]);
		int oldNumberOfVictoryPoints = p.getScore().getValue();
		bonus.getBonus(p);
		int newNumberOfVictoryPoints = p.getScore().getValue();
		assertTrue("The number of Victory Points of the player is correctly incremented", newNumberOfVictoryPoints==oldNumberOfVictoryPoints+10);
	}
	
	@Test (expected = NullPointerException.class)
	public void testGetBonusWithNullParameter() {
		Bonus bonus = new VictoryPointBonus(50);
		bonus.getBonus(null);
	}

	@Test
	public void testGetValue() {
		Bonus bonus = new VictoryPointBonus(50);
		assertTrue(bonus.getValue()==50);
	}

}
