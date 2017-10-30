package it.polimi.ingsw.cg28.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.bonus.CoinBonus;
import it.polimi.ingsw.cg28.controller.bonus.VictoryPointBonus;

public class KingRewardTileTest {
	
	private KingRewardTile tile;
	
	@Before
	public void before() {
		tile = new KingRewardTile(new VictoryPointBonus(4));
	}

	@Test (expected = NullPointerException.class)
	public void testKingRewardTileWithNullParameter() {
		new KingRewardTile(null);
	}
	
	@Test
	public void testKingRewardTile() {
		KingRewardTile newTile = new KingRewardTile(new CoinBonus(1));
		assertTrue(!newTile.equals(null));
		assertTrue(newTile.getBonus().getClass().equals(new CoinBonus(1).getClass()));
		assertTrue(newTile.getBonus().getValue() == 1);
	}
	
	@Test
	public void testGetBonus() {
		assertTrue(tile.getBonus().getClass().equals(new VictoryPointBonus(4).getClass()));
		assertTrue(tile.getBonus().getValue() == 4);
	}

}
