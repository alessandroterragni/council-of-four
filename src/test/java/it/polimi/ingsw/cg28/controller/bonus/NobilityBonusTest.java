package it.polimi.ingsw.cg28.controller.bonus;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.PlayerTurn;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;

public class NobilityBonusTest {

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
	public void testNobilityBonus() {
		assertNotNull("the bonus is correctly constructed",new NobilityBonus(10000));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testNobilityBonusWithNegativeParameter() {
		new NobilityBonus(-89);
	}

	@Test
	public void testGetBonus() {
		Bonus bonus = new NobilityBonus(10);
		Player p = model.getPlayer(players[0]);
		int oldNumberOfNobilityPoints = p.getNobilityRank().getValue();
		bonus.getBonus(p);
		int newNumberOfNobilityPoints = p.getNobilityRank().getValue();
		assertTrue("The number of Nobility Points of the player is correctly incremented", newNumberOfNobilityPoints==oldNumberOfNobilityPoints+10);
	}
	
	@Test (expected = NullPointerException.class)
	public void testGetBonusWithNullParameter() {
		Bonus bonus = new NobilityBonus(50);
		bonus.getBonus(null);
	}

	@Test
	public void testGetValue() {
		Bonus bonus = new NobilityBonus(10);
		assertTrue(bonus.getValue()==10);
	}

	
}
