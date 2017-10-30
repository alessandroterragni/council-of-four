package it.polimi.ingsw.cg28.controller.bonus;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.PlayerID;
import it.polimi.ingsw.cg28.controller.PlayerTurn;
import it.polimi.ingsw.cg28.model.ModelStatus;
import it.polimi.ingsw.cg28.model.Player;
import it.polimi.ingsw.cg28.model.parser.ModelTestConfiguration;

public class CoinBonusTest {

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
	public void testCoinBonusWithNegativeParameter() {
		new CoinBonus(-10);
	}
	
	@Test
	public void testCoinBonus() {
		assertNotNull("the bonus is correctly constructed",new CoinBonus(10000));
	}

	@Test
	public void testGetBonus() {
		Bonus bonus = new CoinBonus(10);
		Player p = model.getPlayer(players[0]);
		int oldNumberOfCoins = p.getCoins().getValue();
		bonus.getBonus(p);
		int newNumberOfCoins = p.getCoins().getValue();
		assertTrue("The number of coins of the player is correctly incremented", newNumberOfCoins==oldNumberOfCoins+10);
	}

	@Test (expected = NullPointerException.class)
	public void testGetBonusWithNullParameter() {
		Bonus bonus = new CoinBonus(10);
		bonus.getBonus(null);
	}
	
	
	@Test
	public void testGetValue() {
		Bonus bonus = new CoinBonus(10);
		assertTrue(bonus.getValue()==10);
	}

}
