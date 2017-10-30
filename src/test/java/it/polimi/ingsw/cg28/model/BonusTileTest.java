package it.polimi.ingsw.cg28.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg28.controller.bonus.VictoryPointBonus;

public class BonusTileTest {
	
	private BonusTile tile;
	
	@Before
	public void before() {
		 tile = new BonusTile("VPB", new VictoryPointBonus(4));
	}

	@Test (expected = NullPointerException.class)
	public void testBonusTileWithNullIdParameter() {
		new BonusTile(null, new VictoryPointBonus(4));
	}
	
	@Test (expected = NullPointerException.class)
	public void testBonusTileWithNullBonusParameter() {
		new BonusTile("VPB", null);
	}
	
	@Test
	public void testBonusTile() {
		assertTrue(!tile.equals(null));
	}
	
	@Test
	public void testGetBonus() {
		assertTrue(tile.getBonus().getClass().equals(new VictoryPointBonus(4).getClass()));
		assertTrue(tile.getBonus().getValue() == 4);
	}
	
	@Test
	public void testGetId() {
		assertTrue(tile.getIdentifier().equals("VPB"));
	}

}
